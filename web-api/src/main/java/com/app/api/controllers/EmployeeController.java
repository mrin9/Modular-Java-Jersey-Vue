package com.app.api.controllers;

import com.app.api.BaseController;
import com.app.dao.EmployeeDao;
import com.app.model.BaseResponse;
import com.app.model.employee.EmployeeModel;
import com.app.model.employee.EmployeeModel.EmployeeResponse;
import com.app.model.employee.EmployeeUserModel;
import com.app.model.employee.EmployeeUserModel.EmployeeUserResponse;
import com.app.util.HibernateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.FlushMode;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("employees")
@Api(value = "Employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeController extends BaseController {

    @GET
    @ApiOperation(value = "Get list of employees", response = EmployeeUserResponse.class)
    @RolesAllowed({"ADMIN", "SUPPORT"})
    public Response getEmployeeList(
        @ApiParam(value="Employee Id", example="202") @QueryParam("employee-id") int employeeId,
        @ApiParam(value="User Id") @QueryParam("user-id") String userId,
        @ApiParam(value="Department")  @QueryParam("department") String dept,
        @ApiParam(value="Search by name or email - Use % for wildcard like '%ra%'", example="%ra%")    @QueryParam("search") String search,
        @ApiParam(value="Page No, Starts from 1 ", example="1") @DefaultValue("1")  @QueryParam("page")  int page,
        @ApiParam(value="Items in each page", example="20")     @DefaultValue("20") @QueryParam("page-size") int pageSize
    ) {

        int recordFrom=0;
        Criteria criteria = HibernateUtil.getSessionFactory().openSession().createCriteria(EmployeeUserModel.class);

        if (employeeId > 0){
            criteria.add(Restrictions.eq("employeeId",  employeeId ));
        }
        if (StringUtils.isNotBlank(userId)) {
            criteria.add(Restrictions.eq("userId", userId));
        }
        if (StringUtils.isNotBlank(dept)){
            criteria.add(Restrictions.like("department",  dept ).ignoreCase());
        }
        if (StringUtils.isNotBlank(search)){
            criteria.add(
                Restrictions.or(
                    Restrictions.like("firstName",  search ).ignoreCase(),
                    Restrictions.like("lastName",  search ).ignoreCase(),
                    Restrictions.like("email",  search ).ignoreCase()
                )
            );
        }
        if (page<=0){
            page = 1;
        }
        if (pageSize<=0 || pageSize > 1000){
            pageSize =20;
        }
        recordFrom = (page-1) * pageSize;

        // Execute the Total-Count Query first ( if main query is executed first, it results in error for count-query)
        criteria.setProjection(Projections.rowCount());
        Long rowCount = (Long)criteria.uniqueResult();

        // Execute the Main Query
        criteria.setProjection(null);
        criteria.setFirstResult(recordFrom);
        criteria.setMaxResults(pageSize);
        List<EmployeeUserModel> empUserList = criteria.list();
        EmployeeUserResponse resp = new EmployeeUserResponse();
        resp.setList(empUserList);


        resp.setPageStats(rowCount.intValue(), pageSize, page,"");
        resp.setSuccessMessage("List of employees");
        return Response.ok(resp).build();
    }


    @POST
    @ApiOperation(value = "Add a employee", response = BaseResponse.class)
    @RolesAllowed({"ADMIN", "SUPPORT"})
    public Response addEmployee(EmployeeModel emp) {

        BaseResponse resp = new BaseResponse();
        Session hbrSession = HibernateUtil.getSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        try {
            hbrSession.beginTransaction();
            hbrSession.save(emp);
            hbrSession.getTransaction().commit();
        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot add employee - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
        }

        return Response.ok(resp).build();
    }

    @PUT
    @ApiOperation(value = "Update a Employee", response = BaseResponse.class)
    @RolesAllowed({"ADMIN", "SUPPORT"})
    public Response updateEmployee(EmployeeModel emp) {

        BaseResponse resp = new BaseResponse();
        Session hbrSession = HibernateUtil.getSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        try {
            EmployeeModel foundEmp  = EmployeeDao.getById(hbrSession, emp.getId());
            if (foundEmp != null){
                hbrSession.beginTransaction();
                hbrSession.merge(emp);
                hbrSession.getTransaction().commit();
                resp.setSuccessMessage(String.format("Employee Updated (id:%s)", emp.getId()));
                return Response.ok(resp).build();
            }
            else{
                resp.setErrorMessage(String.format("Cannot Update - Employee not found (id:%s)", emp.getId()));
                return Response.ok(resp).build();
            }
        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot update Employee - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }

    }


    @DELETE
    @Path("{employeeId}")
    @ApiOperation(value = "Delete a Employee", response = BaseResponse.class)
    @RolesAllowed({"ADMIN", "SUPPORT"})
    public Response deleteEmployee(@ApiParam(value="Employee Id", example="1") @PathParam("employeeId") Integer employeeId) {

        BaseResponse resp = new BaseResponse();
        if (employeeId == 201 || employeeId == 205){
            resp.setErrorMessage("Employee 201 and 205 are special, they cannot be deleted");
            return Response.ok(resp).build();
        }

        Session hbrSession = HibernateUtil.getSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        try {
            EmployeeModel foundEmp  = EmployeeDao.getById(hbrSession, employeeId);
            if (foundEmp==null){
                resp.setErrorMessage(String.format("Cannot delete - Employee do not exist (id:%s)", employeeId));
                return Response.ok(resp).build();
            }
            else{
                hbrSession.beginTransaction();
                EmployeeDao.delete(hbrSession, employeeId);
                hbrSession.getTransaction().commit();
                resp.setSuccessMessage(String.format("Employee deleted (id:%s)", employeeId));
                return Response.ok(resp).build();
            }
        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot delete Customer - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
    }


}
