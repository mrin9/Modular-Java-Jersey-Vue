package com.app.api.controllers;

import jakarta.annotation.security.PermitAll;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import com.app.api.BaseController;
import com.app.model.user.*;
import com.app.model.user.LoginModel.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.commons.lang3.StringUtils;

import com.app.model.BaseResponse;
import com.app.util.HibernateUtil;

import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.Query;
import com.app.util.TokenUtil;
import org.slf4j.Logger;

@Tag(name = "1st Authenticate Yourself")
@Path("/authenticate")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @POST
    @PermitAll
    @Path("/user")
    @Operation(
      summary = "Authenticates user to access Application",
      responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = LoginResponse.class)))}
    )
    public Response authenticateUser(LoginModel loginModel) {
        String uid = loginModel.getUsername();
        String pwd = loginModel.getPassword();

        BaseResponse resp = new BaseResponse();
        if (StringUtils.isAnyBlank(uid,pwd )  ) {
            resp.setErrorMessage("Missing Username or Password");
            resp.setTypeAndMessage(BaseResponse.MessageTypeEnum.AUTH_FAILED, "Missing Username or Password");
            return Response.status(Response.Status.UNAUTHORIZED).entity(resp).build();
        }

        Session hbrSession = HibernateUtil.getSession();

        String hql = "FROM UserViewModel u WHERE u.userId = :uid and u.password = :pwd";
        Query q = hbrSession.createQuery(hql);
        q.setParameter("uid", uid);
        q.setParameter("pwd", pwd);

        UserViewModel userView = (UserViewModel)q.uniqueResult();  // can throw org.hibernate.NonUniqueResultException
        if (userView!=null){
            String strToken = TokenUtil.createTokenForUser(userView);
            UserOutputModel usrOutput = new UserOutputModel(
                userView.getUserId(),
                userView.getRole(),
                userView.getFullName(),
                userView.getEmail(),
                userView.getEmployeeId(),
                userView.getCustomerId(),
                strToken
            );
            LoginResponse successResp = new LoginResponse(usrOutput);
            return Response.status(Response.Status.OK).entity(successResp).build();
        }

        resp.setTypeAndMessage(BaseResponse.MessageTypeEnum.AUTH_FAILED, "Incorrect username/password");
        return Response.status(Response.Status.UNAUTHORIZED).entity(resp).build();
    }
}
