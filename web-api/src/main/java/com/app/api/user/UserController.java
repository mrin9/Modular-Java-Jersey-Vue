package com.app.api.user;

import com.app.api.BaseController;
import com.app.model.BaseResponse;
import com.app.model.user.LoginResponse;
import com.app.model.user.User;
import com.app.model.user.UserListResponse;
import com.app.model.user.UserOutputModel;
import com.app.util.HibernateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@Path("users")
@Api(value = "Users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserController extends BaseController {

    @GET
    @Path("")
    @PermitAll
    @ApiOperation(value = "Get list of users")
    @RolesAllowed({"ADMIN"})
    public Response getUserList(
        @ApiParam(value="Page No", example="0") @DefaultValue("0")  @QueryParam("page") int page,
        @ApiParam(value="Items in each page", example="20") @DefaultValue("20") @QueryParam("size") int size,
        @ApiParam(value="User Id") @QueryParam("user-id") String userId,
        @ApiParam(value="Role", allowableValues="USER,ADMIN") @QueryParam("role") String role,
        @ApiParam(value="First Name") @QueryParam("first-name") String firstName
    ) {
        // Fill hibernate search by example user
        User searchUser = new User();
        if (StringUtils.isNotBlank(userId)){ searchUser.setUserId(userId); }
        if (StringUtils.isNotBlank(role)){ searchUser.setRole(role); }
        if (StringUtils.isNotBlank(firstName)){ searchUser.setFirstName(firstName); }

        // Execute the Hibernate Query
        Example userExample = Example.create(searchUser);
        Criteria criteria = HibernateUtil.getSessionFactory().openSession().createCriteria(User.class).add(userExample);
        List<User> userList = criteria.list();

        // Fill the result into userOutput list
        List userFoundList = new ArrayList<UserOutputModel>();
        for (User tmpUser : userList) {
            UserOutputModel usrOutput = new UserOutputModel(tmpUser);
            userFoundList.add(usrOutput);
        }

        UserListResponse resp = new UserListResponse();
        resp.setList(userFoundList);
        resp.setSuccessMessage("List of users");
        return Response.ok(resp).build();
    }

    @GET
    @Path("/logged-user")
    @RolesAllowed({"USER"})
    @ApiOperation(value = "Get Details of Logged in User")
    public Response getLoggedInUser() {

        User userFromToken = (User)securityContext.getUserPrincipal();  // securityContext is defined in BaseController
        UserOutputModel userOutput = new UserOutputModel(userFromToken);

        LoginResponse resp = new LoginResponse();
        resp.setData(userOutput);
        resp.setSuccessMessage("Current Logged in User Details");
        return Response.ok(resp).build();
    }







}
