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
import org.hibernate.criterion.Projections;

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
    @ApiOperation(value = "Get list of users")
    @RolesAllowed({"ADMIN"})
    public Response getUserList(
        @ApiParam(value="Page No, Starts from 1 ", example="1") @DefaultValue("1")  @QueryParam("page") int page,
        @ApiParam(value="Items in each page", example="20") @DefaultValue("20") @QueryParam("page-size") int pageSize,
        @ApiParam(value="User Id") @QueryParam("user-id") String userId,
        @ApiParam(value="Role", allowableValues="USER,ADMIN") @QueryParam("role") String role,
        @ApiParam(value="Use % for wildcard like 'Steav%' ")  @QueryParam("first-name") String firstName
    ) {
        // Fill hibernate search by example user
        int recordFrom=0;
        User searchUser = new User();
        if (StringUtils.isNotBlank(userId)){ searchUser.setUserId(userId); }
        if (StringUtils.isNotBlank(role)){ searchUser.setRole(role); }
        if (StringUtils.isNotBlank(firstName)){ searchUser.setFirstName(firstName); }
        if (page<=0){
            page = 1;
        }
        if (pageSize<=0 || pageSize > 1000){
            pageSize =20;
        }
        recordFrom = (page-1) * pageSize;

        // Execute the Hibernate Query
        Example userExample = Example.create(searchUser);
        Criteria criteria = HibernateUtil.getSessionFactory().openSession().createCriteria(User.class);
        criteria.add(userExample);
        criteria.setFirstResult( (int) (long)recordFrom);
        criteria.setMaxResults(  (int) (long)pageSize);
        List<User> userList = criteria.list();

        // Fill the result into userOutput list
        List userFoundList = new ArrayList<UserOutputModel>();
        for (User tmpUser : userList) {
            UserOutputModel usrOutput = new UserOutputModel(tmpUser);
            userFoundList.add(usrOutput);
        }

        criteria.setProjection(Projections.rowCount());
        int totalRows = Math.toIntExact((Long) criteria.uniqueResult()); //TODO: I am getting count of all the user what I need is count of users based on the criteria

        UserListResponse resp = new UserListResponse();
        resp.setList(userFoundList);
        resp.setPageStats(totalRows, pageSize, page,"");
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
