package com.app.api.product;

import com.app.api.BaseController;
import com.app.model.customer.CustomerResponse;
import com.app.model.product.ProductModel;
import com.app.model.product.ProductResponse;
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
import org.hibernate.criterion.Restrictions;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;


@Path("products")
@Api(value = "Products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductController extends BaseController {

    @GET
    @Path("")
    @ApiOperation(value = "Get list of products")
    @RolesAllowed({"ADMIN"})
    public Response getProductList(
        @ApiParam(value="Product Id") @QueryParam("id") int id,
        @ApiParam(value="Category",allowableValues = "Camera, Laptop, Tablet, Phone") @QueryParam("category") String category,
        @ApiParam(value="Page No, Starts from 1 ", example="1") @DefaultValue("1")    @QueryParam("page") int page,
        @ApiParam(value="Items in each page", example="20")     @DefaultValue("20")   @QueryParam("page-size") int pageSize
    ) {

        int recordFrom=0;
        Criteria criteria = HibernateUtil.getSessionFactory().openSession().createCriteria(ProductModel.class);

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

}
