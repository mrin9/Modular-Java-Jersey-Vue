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
import org.hibernate.*;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

import static com.app.util.HibernateUtil.getSessionFactory;


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
        // Fill hibernate search by example user (Hibernate Query-by-example way of searching )
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
        Criteria criteria = getSessionFactory().openSession().createCriteria(User.class);
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


    @DELETE
    @Path("{userId}")
    @ApiOperation(value = "Delete a user by id", response = BaseResponse.class)
    @RolesAllowed({"ADMIN"})
    public Response deleteUser(@ApiParam(value="User Id") @PathParam("userId") String userId) {

        BaseResponse resp = new BaseResponse();

        if (StringUtils.isBlank(userId)){
            resp.setErrorMessage("Must provide a valid ID");
            return Response.ok(resp).build();
        }

        String hql = "delete User where userId = :userId";
        Query q = getSessionFactory().openSession().createQuery(hql).setParameter("userId", userId);
        try {
            q.executeUpdate();
        }
        catch (ConstraintViolationException e) {
            resp.setErrorMessage("Cannot delete User - Database constraints are violated");
            return Response.ok(resp).build();
        }
        resp.setSuccessMessage("Deleted");
        return Response.ok(resp).build();
    }

    @POST
    @Path("")
    @ApiOperation(value = "Add a User", response = BaseResponse.class)
    @RolesAllowed({"ADMIN"})
    public Response addUser(User user) {

        BaseResponse resp = new BaseResponse();
        Session hbrSession = HibernateUtil.getSessionFactory().openSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        try {
            hbrSession.beginTransaction();
            hbrSession.save(user);
            hbrSession.getTransaction().commit();
        }
        catch (HibernateException | ConstraintViolationException  e) {
            resp.setErrorMessage("Cannot add User - " + e.getMessage() + ", " +e.getCause().getMessage());
        }

        return Response.ok(resp).build();
    }




}
