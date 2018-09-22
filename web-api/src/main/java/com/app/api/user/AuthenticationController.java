package com.app.api.user;

import javax.annotation.security.PermitAll;
import javax.servlet.http.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.ws.rs.container.ContainerRequestContext;
import org.apache.commons.lang3.StringUtils;

import io.swagger.annotations.*;
import com.app.api.BaseController;
import com.app.model.user.User;
import com.app.model.user.UserOutputModel;
import com.app.model.user.LoginModel;
import com.app.model.user.LoginResponse;
import com.app.model.BaseResponse;
import com.app.util.HibernateUtil;

import org.slf4j.LoggerFactory;
import org.hibernate.Session;
import org.hibernate.Query;
import com.app.util.TokenUtil;


@Path("/authenticate")
@Api(value = "1st Authenticate Yourself")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthenticationController extends BaseController {
    private static org.slf4j.Logger log = LoggerFactory.getLogger(AuthenticationController.class);


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

        Session hbrSession = HibernateUtil.getSessionFactory().openSession();

        String hql = "FROM User u WHERE u.userId = :uid and u.password = :pwd";
        Query q = hbrSession.createQuery(hql);
        q.setParameter("uid", uid);
        q.setParameter("pwd", pwd);

        User user = (User)q.uniqueResult();  // can throw org.hibernate.NonUniqueResultException
        if (user!=null){
            String strToken = TokenUtil.createTokenForUser(user);
            UserOutputModel usrOutput = new UserOutputModel(
                user.getUserId(),
                user.getRole(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getCompany(),
                strToken
            );
            LoginResponse successResp = new LoginResponse(usrOutput);
            return Response.status(Response.Status.OK).entity(successResp).build();

        }

        resp.setErrorMessage("Incorrect username/password");
        return Response.status(Response.Status.UNAUTHORIZED).entity(resp).build();
    }


    @GET
    @ApiOperation(value = "Renew token with a new expiry time and also get updated licenses", response = LoginResponse.class)
    @Path("/renew-token")
    public Response renewToken() {

        BaseResponse resp = new BaseResponse();

        String newToken    = "";
        User userFromToken = (User)securityContext.getUserPrincipal();  // securityContext is defined in BaseController
        String oldToken    = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION); //requestContext is defined in BaseController

        if (TokenUtil.isExpiringIn30Minutes(oldToken)) {
            newToken = TokenUtil.createTokenForUser(userFromToken);
            UserOutputModel renewUser = new UserOutputModel(
                userFromToken.getUserId(),
                userFromToken.getRole(),
                userFromToken.getFirstName(),
                userFromToken.getLastName(),
                userFromToken.getEmail(),
                userFromToken.getCompany(),
                newToken
            );
            LoginResponse successResp = new LoginResponse(renewUser);
            return Response.status(Response.Status.OK).entity(successResp).build();
        }

        resp.setErrorMessage("Wont Renew token as the old token is still valid for more than 30 minutes");
        return Response.ok(resp).build();
    }



}
