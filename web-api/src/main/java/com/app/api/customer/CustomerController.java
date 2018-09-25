package com.app.api.customer;

import com.app.api.BaseController;
import com.app.model.BaseResponse;
import com.app.model.customer.CustomerModel;
import com.app.model.customer.CustomerResponse;
import com.app.model.user.User;
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


@Path("customers")
@Api(value = "Customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerController extends BaseController {

    @GET
    @Path("")
    @ApiOperation(value = "Get list of customers", response = CustomerResponse.class)
    @RolesAllowed({"ADMIN"})
    public Response getCustomerList(
        @ApiParam(value="Customer Id") @QueryParam("id") int id,
        @ApiParam(value="Company") @QueryParam("company") String company,
        @ApiParam(value="Use % for wildcard like 'Steav%' ")  @QueryParam("first-name") String firstName,
        @ApiParam(value="Page No, Starts from 1 ", example="1") @DefaultValue("1")  @QueryParam("page") int page,
        @ApiParam(value="Items in each page", example="20") @DefaultValue("20") @QueryParam("page-size") int pageSize
    ) {

        int recordFrom=0;
        Criteria criteria = HibernateUtil.getSessionFactory().openSession().createCriteria(CustomerModel.class);

        if (id > 0){
            criteria.add(Restrictions.eq("id",  id ));
        }
        if (StringUtils.isNotBlank(company)){
            criteria.add(Restrictions.eq("company",  company ));
        }
        if (StringUtils.isNotBlank(firstName)){
            criteria.add(Restrictions.eq("firstName",  firstName ));
        }
        if (page<=0){
            page = 1;
        }
        if (pageSize<=0 || pageSize > 1000){
            pageSize =20;
        }
        recordFrom = (page-1) * pageSize;

        // Execute the Hibernate Query
        criteria.setFirstResult( (int) (long)recordFrom);
        criteria.setMaxResults(  (int) (long)pageSize);
        List<CustomerModel> customerList = criteria.list();

        criteria.setProjection(Projections.rowCount());
        int rowCount = Math.toIntExact((Long) criteria.uniqueResult());

        CustomerResponse resp = new CustomerResponse();
        resp.setList(customerList);
        resp.setPageStats(rowCount, pageSize, page,"");
        resp.setSuccessMessage("List of customers");
        return Response.ok(resp).build();
    }

    @POST
    @Path("")
    @ApiOperation(value = "Add a Customer", response = BaseResponse.class)
    @RolesAllowed({"ADMIN"})
    public Response addCustomer(CustomerModel cust) {

        BaseResponse resp = new BaseResponse();
        Session hbrSession = HibernateUtil.getSessionFactory().openSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        try {
            hbrSession.beginTransaction();
            hbrSession.save(cust);
            hbrSession.getTransaction().commit();
        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot add Customer - " + e.getMessage() + ", " +e.getCause().getMessage());
        }

        return Response.ok(resp).build();
    }







}
