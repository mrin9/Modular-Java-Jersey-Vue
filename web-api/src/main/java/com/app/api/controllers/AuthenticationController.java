package com.app.api.controllers;

import javax.annotation.security.PermitAll;
import javax.ws.rs.*;
import javax.ws.rs.core.*;

import com.app.api.BaseController;
import com.app.model.user.*;
import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.*;
import com.app.model.BaseResponse;
import com.app.util.HibernateUtil;

import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.Query;
import com.app.util.TokenUtil;
import org.slf4j.Logger;


@Path("/authenticate")
@Api(value = "1st Authenticate Yourself")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @POST
    @PermitAll
    @ApiOperation(value = "Authenticates user to access Email Security Application", response = LoginResponse.class)
    @Path("/user")
    public Response authenticateUser(LoginModel loginModel) {
        String uid = loginModel.getUsername();
        String pwd = loginModel.getPassword();

        BaseResponse resp = new BaseResponse();
        if (StringUtils.isAnyBlank(uid,pwd )  ) {
            resp.setErrorMessage("Missing Username or Password");
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

        resp.setErrorMessage("Incorrect username/password");
        return Response.status(Response.Status.UNAUTHORIZED).entity(resp).build();
    }




}
