package com.app.api.controllers;

import com.app.api.BaseController;
import com.app.dao.CustomerDao;
import com.app.dao.ProductDao;
import com.app.model.BaseResponse;
import com.app.model.customer.CustomerModel;
import com.app.model.product.ProductModel;
import com.app.model.product.ProductResponse;
import com.app.util.HibernateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;


@Path("products")
@Api(value = "Products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController extends BaseController {

    @GET
    @Path("")
    @ApiOperation(value = "Get list of products", response = ProductResponse.class)
    @RolesAllowed({"ADMIN"})
    public Response getProductList(
        @ApiParam(value="Product Id") @QueryParam("id") int id,
        @ApiParam(value="Category",allowableValues = "Camera, Laptop, Tablet, Phone") @QueryParam("category") String category,
        @ApiParam(value="Page No, Starts from 1 ", example="1") @DefaultValue("1")    @QueryParam("page") int page,
        @ApiParam(value="Items in each page", example="20")     @DefaultValue("20")   @QueryParam("page-size") int pageSize
    ) {

        int recordFrom=0;
        Criteria criteria = HibernateUtil.getSession().createCriteria(ProductModel.class);

        if (id > 0){
            criteria.add(Restrictions.eq("id",  id ));
        }
        if (StringUtils.isNotBlank(category)){
            criteria.add(Restrictions.eq("category",  category ));
        }
        if (page<=0){
            page = 1;
        }
        if (pageSize <= 0 || pageSize > 1000){
            pageSize = 20;
        }
        recordFrom = (page-1) * pageSize;

        // Execute the Hibernate Query
        criteria.setFirstResult( (int) (long)recordFrom);
        criteria.setMaxResults(  (int) (long)pageSize);
        List<ProductModel> productList = criteria.list();

        criteria.setProjection(Projections.rowCount());
        int rowCount = Math.toIntExact((Long) criteria.uniqueResult());

        ProductResponse resp = new ProductResponse();
        resp.setList(productList);
        resp.setPageStats(rowCount, pageSize, page,"");
        resp.setSuccessMessage("List of products");
        return Response.ok(resp).build();
    }



    @POST
    @Path("")
    @ApiOperation(value = "Add a Product", response = BaseResponse.class)
    @RolesAllowed({"ADMIN", "SUPPORT"})
    public Response addCustomer(ProductModel prod) {

        BaseResponse resp = new BaseResponse();
        Session hbrSession = HibernateUtil.getSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        try {
            hbrSession.beginTransaction();
            hbrSession.save(prod);
            hbrSession.getTransaction().commit();
            resp.setSuccessMessage(String.format("Product Added - New Product ID : %s ", prod.getId()));
            return Response.ok(resp).build();

        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot add Product - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
    }

    @PUT
    @Path("")
    @ApiOperation(value = "Update a Product", response = BaseResponse.class)
    @RolesAllowed({"ADMIN", "SUPPORT"})
    public Response updateProduct(ProductModel prod) {

        BaseResponse resp = new BaseResponse();
        Session hbrSession = HibernateUtil.getSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        try {
            ProductModel foundProd  = ProductDao.getById(hbrSession, prod.getId());
            if (foundProd != null){
                hbrSession.beginTransaction();
                hbrSession.merge(prod);
                hbrSession.getTransaction().commit();
                resp.setSuccessMessage(String.format("Product Updated (id:%s)", prod.getId()));
                return Response.ok(resp).build();
            }
            else{
                resp.setErrorMessage(String.format("Cannot Update - Product not found (id:%s)", prod.getId()));
                return Response.ok(resp).build();
            }
        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot update Product - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }

    }



    @DELETE
    @Path("{productId}")
    @ApiOperation(value = "Delete a Product", response = BaseResponse.class)
    @RolesAllowed({"ADMIN", "SUPPORT"})
    public Response deleteCustomer(@ApiParam(value="Product Id", example="601") @PathParam("productId") Integer productId) {

        BaseResponse resp = new BaseResponse();
        Session hbrSession = HibernateUtil.getSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        try {
            BigDecimal referenceCount = CustomerDao.getReferenceCount(hbrSession, productId);
            if (referenceCount.intValue() > 0){
                resp.setErrorMessage("Cannot delete product, Referenced in other tables");
                return Response.ok(resp).build();
            }
            else {
                ProductModel foundProd  = ProductDao.getById(hbrSession, productId);
                if (foundProd==null){
                    resp.setErrorMessage(String.format("Cannot delete product - Customer do not exist (id:%s)", productId));
                    return Response.ok(resp).build();
                }
                else{
                    hbrSession.beginTransaction();
                    ProductDao.delete(hbrSession, productId);
                    hbrSession.getTransaction().commit();
                    resp.setSuccessMessage(String.format("Product deleted (id:%s)", productId));
                    return Response.ok(resp).build();
                }
            }

        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot delete product - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
    }

}
