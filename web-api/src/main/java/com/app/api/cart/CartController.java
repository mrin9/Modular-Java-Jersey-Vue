package com.app.api.cart;

import com.app.api.BaseController;
import com.app.model.BaseResponse;
import com.app.model.cart.CartModel;
import com.app.model.cart.CartResponse;
import com.app.model.employee.EmployeeModel;
import com.app.model.employee.EmployeeResponse;
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


@Path("cart")
@Api(value = "Shopping Cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartController extends BaseController {

    @GET
    @Path("{userId}")
    @ApiOperation(value = "Get cart Items of an User", response = CartResponse.class)
    @RolesAllowed({"ADMIN"})
    public Response getCartItemsByUser(@ApiParam(value="User Id") @PathParam("userId") String userId) {

        Criteria criteria = HibernateUtil.getSessionFactory().openSession().createCriteria(CartModel.class);

        // Execute the Hibernate Query
        List<CartModel> cartItemList = criteria.list();
        criteria.setProjection(Projections.rowCount());
        int totalRows = Math.toIntExact((Long) criteria.uniqueResult());

        CartResponse resp = new CartResponse();
        resp.setList(cartItemList);
        resp.setTotal(totalRows);
        resp.setSuccessMessage("List of Cart Items");
        return Response.ok(resp).build();
    }

    @DELETE
    @Path("{userId}")
    @ApiOperation(value = "Delete cart Items of an User", response = BaseResponse.class)
    @RolesAllowed({"ADMIN"})
    public Response deleteCartItemsByUser(@ApiParam(value="User Id") @PathParam("userId") String userId) {

        BaseResponse resp = new BaseResponse();
        Session hbrSession = HibernateUtil.getSessionFactory().openSession();
        String hql = "delete CartModel where userId = :userId";
        Query q = hbrSession.createQuery(hql).setParameter("userId", userId);
        try {
            hbrSession.beginTransaction();
            int i = q.executeUpdate();
            resp.setSuccessMessage("Rows Deleted :" + i);
            hbrSession.getTransaction().commit();
        }
        catch (HibernateException | ConstraintViolationException  e) {
            resp.setErrorMessage("Cannot delete cart - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
        }
        return Response.ok(resp).build();
    }


    @POST
    @Path("{userId}/{productId}")
    @ApiOperation(value = "Add a cart Item of an User", response = CartResponse.class)
    @RolesAllowed({"ADMIN"})
    public Response addCartItemsForAnUser(
        @ApiParam(value="User Id") @PathParam("userId") String userId,
        @ApiParam(value="Product Id") @PathParam("productId") int productId,
        @ApiParam(value="Quantity") @QueryParam("quantity") BigDecimal quantity
    ) {
        BaseResponse resp = new BaseResponse();
        if (quantity.intValue() < 0){
            resp.setErrorMessage("Quantity must be positive value");
            return Response.ok(resp).build();
        }
        //TODO: Check if quantity is available for that product
        Session hbrSession = HibernateUtil.getSessionFactory().openSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        try {
            CartModel cartItem = new CartModel(userId, productId, quantity);
            hbrSession.beginTransaction();
            hbrSession.save(cartItem);
            hbrSession.getTransaction().commit();
        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot add Cart Item - " + e.getMessage() + ", " +e.getCause().getMessage());
        }
        return Response.ok(resp).build();
    }



}
