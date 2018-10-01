package com.app.api.controllers;

import com.app.api.BaseController;
import com.app.model.BaseResponse;
import com.app.model.customer.CustomerModel;
import com.app.model.employee.EmployeeModel;
import com.app.model.user.*;
import com.app.util.Constants;
import com.app.util.HibernateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

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
        @ApiParam(value="Role", allowableValues="ADMIN, SUPPORT, CUSTOMER") @QueryParam("role") String role
    ) {
        // Fill hibernate search by example user (Hibernate Query-by-example way of searching )
        int recordFrom=0;
        UserViewModel searchUser = new UserViewModel();
        if (StringUtils.isNotBlank(userId)){ searchUser.setUserId(userId); }
        if (StringUtils.isNotBlank(role)){ searchUser.setRole(role); }
        //if (StringUtils.isNotBlank(firstName)){ searchUser.setFirstName(firstName); }
        if (page<=0){
            page = 1;
        }
        if (pageSize<=0 || pageSize > 1000){
            pageSize =20;
        }
        recordFrom = (page-1) * pageSize;

        // Execute the Hibernate Query
        Example userExample = Example.create(searchUser);
        Criteria criteria = getSessionFactory().openSession().createCriteria(UserViewModel.class);
        criteria.add(userExample);
        criteria.setFirstResult( (int) (long)recordFrom);
        criteria.setMaxResults(  (int) (long)pageSize);
        List<UserViewModel> userList = criteria.list();

        // Fill the result into userOutput list
        List userFoundList = new ArrayList<UserOutputModel>();
        for (UserViewModel tmpUser : userList) {
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
    @RolesAllowed({"CUSTOMER", "SUPPORT"})
    @ApiOperation(value = "Get Details of Logged in User")
    public Response getLoggedInUser() {

        UserViewModel userFromToken = (UserViewModel)securityContext.getUserPrincipal();  // securityContext is defined in BaseController
        UserOutputModel userOutput = new UserOutputModel(userFromToken);

        LoginResponse resp = new LoginResponse();
        resp.setData(userOutput);
        resp.setSuccessMessage("Current Logged in User Details");
        return Response.ok(resp).build();
    }

    @GET
    @Path("{userId}")
    @RolesAllowed({"CUSTOMER", "SUPPORT"})
    @ApiOperation(value = "Get Details of a User by id")
    public Response getUserDetailsById(@ApiParam(value="User Id") @PathParam("userId") String userId) {
        Criteria criteria = HibernateUtil.getSessionFactory().openSession().createCriteria(UserViewModel.class);

        UserViewModel userFromToken = (UserViewModel)securityContext.getUserPrincipal();  // securityContext is defined in BaseController
        criteria.add(Restrictions.eq("userId",  userId ));
        UserViewModel userView = (UserViewModel)criteria.uniqueResult();
        UserResponse resp = new UserResponse();

        if (userView!=null) {
            if (userFromToken.getRole().equalsIgnoreCase("ADMIN") || userFromToken.getUserId().equals(userId)) {
                //For Admins and Own-Id - Just remove the password
                userView.setPassword("");
            } else {
                // If not a ADMIN or not his own id then strip all the data and just send the id
                userView.setEmployeeId(0);
                userView.setCustomerId(0);
                userView.setPassword("");
                userView.setRole("");
                userView.setEmail("");
                userView.setFullName("");
            }
            UserOutputModel userOutput = new UserOutputModel(userView);
            resp.setData(userOutput);
            resp.setSuccessMessage("User Details");
        }
        else{
            resp.setErrorMessage("User Not Found");
        }
        return Response.ok(resp).build();
    }

    @DELETE
    @Path("{userId}")
    @ApiOperation(value = "Delete a user by id", response = BaseResponse.class)
    @RolesAllowed({"ADMIN"})
    public Response deleteUser(@ApiParam(value="User Id") @PathParam("userId") String userId) {

        BaseResponse resp = new BaseResponse();
        if (userId.equals("admin") || userId.equals("support") || userId.equals("customer")){
            resp.setErrorMessage("Cannot delete reserved User Ids - 'admin', 'support' or 'customer'");
            return Response.ok(resp).build();
        }

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
    @PermitAll
    @Path("")
    @ApiOperation(value = "Register as a New User", response = BaseResponse.class)
    public Response addUser(UserRegistrationModel registerObj) {

        BaseResponse resp = new BaseResponse();

        UserViewModel userFromToken = (UserViewModel)securityContext.getUserPrincipal();  // securityContext is defined in BaseController
        //Customers can query their own cart only
        if (userFromToken==null || userFromToken.getRole().equalsIgnoreCase(Constants.UserRoleConstants.ROLE_ADMIN)==false ){
            if (registerObj.getRole().equalsIgnoreCase(Constants.UserRoleConstants.ROLE_ADMIN)) {
                resp.setErrorMessage("Role cannot be ADMIN ");
                return Response.ok(resp).build();
            }
        }

        Session hbrSession = HibernateUtil.getSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);



        User user;
        try {
            hbrSession.beginTransaction();
            if (registerObj.getRole().equalsIgnoreCase("CUSTOMER")){
                CustomerModel cust = new CustomerModel(
                    registerObj.getLastName(),
                    registerObj.getFirstName(),
                    registerObj.getEmail(),
                    registerObj.getCompany(),
                    registerObj.getPhone(),
                    registerObj.getAddress1(),
                    registerObj.getAddress2(),
                    registerObj.getCity(),
                    registerObj.getState(),
                    registerObj.getPostalCode(),
                    registerObj.getCountry()
                );
                hbrSession.save(cust);
                user = new User(registerObj.getUserId(), registerObj.getPassword(), registerObj.getRole(), null, cust.getId());
            }
            else{

                EmployeeModel emp = new EmployeeModel(
                    registerObj.getLastName(),
                    registerObj.getFirstName(),
                    registerObj.getEmail(),
                    "",
                    "",
                    registerObj.getDepartment(),
                    registerObj.getManagerId(),
                    registerObj.getPhone(),
                    registerObj.getAddress1(),
                    registerObj.getAddress2(),
                    registerObj.getCity(),
                    registerObj.getState(),
                    registerObj.getPostalCode(),
                    registerObj.getCountry()
                );
                hbrSession.save(emp);
                user = new User(registerObj.getUserId(), registerObj.getPassword(), registerObj.getRole(), emp.getId(), null);
            }
            hbrSession.save(user);
            hbrSession.getTransaction().commit();
            resp.setSuccessMessage("User Registered Successfully");

        }
        catch (HibernateException | ConstraintViolationException  e) {
            resp.setErrorMessage("Cannot add User - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
        }
        return Response.ok(resp).build();
    }

}
