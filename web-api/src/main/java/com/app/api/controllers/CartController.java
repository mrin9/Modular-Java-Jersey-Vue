package com.app.api.controllers;

import com.app.api.BaseController;
import com.app.model.BaseResponse;
import com.app.model.cart.CartModel;
import com.app.model.cart.CartResponse;
import com.app.model.cart.CartViewModel;
import com.app.model.cart.CartViewResponse;
import com.app.model.user.UserViewModel;
import com.app.util.Constants;
import com.app.util.HibernateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.List;
import com.app.dao.CartDao;


@Path("cart")
@Api(value = "Shopping Cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(CartController.class);

    @GET
    @Path("")
    @ApiOperation(value = "Get cart Items of an User", response = CartViewResponse.class)
    @RolesAllowed({"ADMIN", "SUPPORT", "CUSTOMER"})
    public Response getCartItemsByUser(@ApiParam(value="User Id", example="customer") @QueryParam("user-id") String userId) {

        CartViewResponse resp = new CartViewResponse();

        UserViewModel userFromToken = (UserViewModel)securityContext.getUserPrincipal();  // securityContext is defined in BaseController
        //Customers can query their own cart only
        if (userFromToken.getRole().equalsIgnoreCase(Constants.UserRoleConstants.ROLE_CUSTOMER)){
            userId = userFromToken.getUserId();
        }

        Session hbrSession = HibernateUtil.getSession();
        List<CartViewModel> cartItemList =  CartDao.listCartItemsOfUser(hbrSession, userId);
        resp.setList(cartItemList);
        resp.setTotal(cartItemList.size());

        resp.setSuccessMessage("List of Cart Items for user :" + userId);
        return Response.ok(resp).build();
    }

    @POST
    @Path("")
    @ApiOperation(value = "Add a new product to cart", response = BaseResponse.class)
    @RolesAllowed({"ADMIN", "SUPPORT", "CUSTOMER"})
    public Response getCartItemsByUser(
        @ApiParam(value="User Id"   , example="customer") @QueryParam("user-id")    String userId,
        @ApiParam(value="Product Id", example="610")      @QueryParam("product-id") Integer productId,
        @ApiParam(value="Quantity"  , example="2")        @QueryParam("quantity")   Long quantity
    ) {

        BaseResponse resp = new BaseResponse();
        UserViewModel userFromToken = (UserViewModel)securityContext.getUserPrincipal();  // securityContext is defined in BaseController

        //Customers can query their own cart only
        if (userFromToken.getRole().equalsIgnoreCase(Constants.UserRoleConstants.ROLE_CUSTOMER)){
            userId = userFromToken.getUserId();
        }

        Session hbrSession = HibernateUtil.getSession();
        CartModel cart = new CartModel(userId,productId,quantity);
        hbrSession.getTransaction().begin();
        hbrSession.save(cart);
        hbrSession.getTransaction().commit();
        resp.setSuccessMessage(String.format("New product(%s) added to cart of user (%s)", productId, userId));
        return Response.ok(resp).build();
    }


    @DELETE
    @Path("{userId}")
    @ApiOperation(value = "Delete all cart Items of an User", response = BaseResponse.class)
    @RolesAllowed({"ADMIN"})
    public Response deleteCartItemsByUser(@ApiParam(value="User Id", example="customer") @PathParam("userId") String userId) {

        BaseResponse resp = new BaseResponse();
        try {
            Session hbrSession = HibernateUtil.getSession();
            hbrSession.beginTransaction();
            int result = CartDao.removeFromCart(hbrSession, userId, null);
            hbrSession.getTransaction().commit();
            resp.setSuccessMessage(String.format("All the Items from cart are removed for user:%s (Items Removed:%s)", userId, result));
            return Response.ok(resp).build();
        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot update cart item - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
    }

    @DELETE
    @Path("{userId}/{productId}")
    @ApiOperation(value = "Removes a product from cart of a user", response = BaseResponse.class)
    @RolesAllowed({"ADMIN"})
    public Response removeProductFromCartOfUser(
       @ApiParam(value="User Id"   , example="customer") @PathParam("userId") String userId,
       @ApiParam(value="Product Id", example="601") @PathParam("productId") int productId
    ) {

        BaseResponse resp = new BaseResponse();
        try {
            Session hbrSession = HibernateUtil.getSession();
            hbrSession.beginTransaction();
            int result = CartDao.removeFromCart(hbrSession, userId, productId);
            hbrSession.getTransaction().commit();
            resp.setSuccessMessage(String.format("Product:%s from cart is removed for user:%s (Items Removed:%s)", productId, userId, result));
            return Response.ok(resp).build();
        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot update cart item - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
    }

    @PUT
    @Path("{userId}/{productId}/quantity")
    @ApiOperation(value = "Modify cart of a user (by adding, removing or updating) product quantities", response = BaseResponse.class)
    @RolesAllowed({"ADMIN"})
    public Response addCartItemsForAnUser(
        @ApiParam(value="User Id"   , example="customer") @PathParam("userId") String userId,
        @ApiParam(value="Product Id", example="603")      @PathParam("productId") int productId,
        @ApiParam(value="Quantity"  , example="2")        @QueryParam("quantity") Long quantity,
        @ApiParam(value="action"    , example="add", allowableValues = "add, remove, update") @QueryParam("action") String action

    ) {
        BaseResponse resp = new BaseResponse();
        int resultCount;
        if (quantity.intValue() < 0){
            resp.setErrorMessage("Quantity must be positive value");
            return Response.ok(resp).build();
        }
        //TODO: Check if quantity is available for that product


        //First Check if the product is already available in the cart, then just increase the quantity
        try {
            Session hbrSession = HibernateUtil.getSession();
            hbrSession.setFlushMode(FlushMode.ALWAYS);
            CartModel cartItem = CartDao.getProductsInCart(hbrSession, userId, productId);

            if (action.equalsIgnoreCase("add")){
                String msg="";
                hbrSession.beginTransaction();
                if (cartItem == null) {
                    cartItem = new CartModel(userId, productId, quantity);
                    hbrSession.save(cartItem);
                    msg = "Product Added with specified quantities";
                }
                else {
                    Long existingQuantity = cartItem.getQuantity();
                    Long newQuantity = existingQuantity + quantity;
                    hbrSession.beginTransaction();
                    resultCount = CartDao.updateProductQuantityInCart(hbrSession, userId, productId, newQuantity);
                    hbrSession.getTransaction().commit();
                    msg = "Quantities updated for a product that already exist in cart";
                }
                hbrSession.getTransaction().commit();
                resp.setSuccessMessage("Product added to Cart " );
                return Response.ok(resp).build();

            }
            else if (action.equalsIgnoreCase("remove")){
                String msg="";
                if (cartItem == null) {
                    resp.setErrorMessage("Cannot Remove - Product dont exist in the cart");
                    return Response.ok(resp).build();
                }
                else{
                    Long existingQuantity = cartItem.getQuantity();
                    Long newQuantity = existingQuantity- quantity;
                    hbrSession.beginTransaction();
                    if (newQuantity.intValue() <= 0){
                        resultCount = CartDao.removeFromCart(hbrSession, userId, productId);
                        msg = "Product completely removed from cart";
                    }
                    else{
                        resultCount = CartDao.updateProductQuantityInCart(hbrSession, userId, productId, newQuantity);
                        msg = "Product quantity updated after removal";
                    }
                    hbrSession.getTransaction().commit();
                    resp.setSuccessMessage(msg);
                    return Response.ok(resp).build();
                }
            }
            else if (action.equalsIgnoreCase("update")){
                if (cartItem == null) {
                    resp.setErrorMessage("Cannot update - Product dont exist in the cart");
                }
                else{
                    hbrSession.beginTransaction();
                    resultCount = CartDao.updateProductQuantityInCart(hbrSession, userId, productId, quantity);
                    hbrSession.getTransaction().commit();
                    resp.setSuccessMessage("Product Quantity updated " );
                }
                return Response.ok(resp).build();
            }
            else{
                resp.setErrorMessage("Invalid action  - only add, remove and update are allowed");
                return Response.ok(resp).build();
            }
        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot add cart item - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
    }

}
