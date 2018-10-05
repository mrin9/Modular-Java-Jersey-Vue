package com.app.api.controllers;

import com.app.api.BaseController;
import com.app.dao.CustomerDao;
import com.app.dao.OrderDao;
import com.app.model.BaseResponse;
import com.app.model.customer.CustomerModel;
import com.app.model.order.*;
import com.app.model.user.UserViewModel;
import com.app.util.Constants;
import com.app.util.HibernateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;


@Path("")
@Api(value = "Orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(OrderController.class);

    @GET
    @Path("orders")
    @ApiOperation(value = "Get Details of an order with nested order-lines", response = OrderWithNestedDetailResponse.class)
    @RolesAllowed({"ADMIN", "CUSTOMER", "SUPPORT"})
    public Response getOrderDetail(
        @ApiParam(value = "Order Id") @QueryParam("order-id") int orderId,
        @ApiParam(value = "Customer Id") @QueryParam("customer-id") int customerId,
        @ApiParam(value = "Payment Type", allowableValues = "Check, Cash, Card") @QueryParam("payment-type") String paymentType,
        @ApiParam(value = "Order Status", allowableValues = "On Hold, Shipped, Complete, New") @QueryParam("order-status") String orderStatus,
        @ApiParam(value = "Page No, Starts from 1 ", example = "1") @DefaultValue("1") @QueryParam("page") int page,
        @ApiParam(value = "Items in each page", example = "20") @DefaultValue("1000") @QueryParam("page-size") int pageSize
    ) {

        OrderWithNestedDetailResponse resp = new OrderWithNestedDetailResponse();
        Session hbrSession = HibernateUtil.getSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);

        UserViewModel userFromToken = (UserViewModel)securityContext.getUserPrincipal();  // securityContext is defined in BaseController
        //Customers can query their own cart only
        if (userFromToken.getRole().equalsIgnoreCase(Constants.UserRoleConstants.ROLE_CUSTOMER)){
            customerId = userFromToken.getCustomerId();
        }


        try {
            List<OrderWithNestedDetailModel> orderWithOrderLinesList = OrderDao.getWithOrderLines(hbrSession, page,pageSize, orderId, customerId, paymentType, orderStatus);
            resp.setList(orderWithOrderLinesList);
            resp.setTotal(orderWithOrderLinesList.size());
            resp.setSuccessMessage("List of Orders and nested details " + (customerId>0 ? "- Customer:"+customerId:""));
            return Response.ok(resp).build();
        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot delete Order - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
    }

    @DELETE
    @Path("orders/{orderId}")
    @ApiOperation(value = "Delete an order and all its line-items", response = BaseResponse.class)
    @RolesAllowed({"USER"})
    public Response deleteOrder(@ApiParam(value = "Order Id", example="4002") @PathParam("orderId") Integer orderId) {

        BaseResponse resp = new BaseResponse();
        if (orderId <= 0 ) {
            resp.setErrorMessage("Must provide a valid Order-id ");
            return Response.ok(resp).build();
        }
        Session hbrSession = HibernateUtil.getSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        try {
            OrderModel foundOrder  = OrderDao.getById(hbrSession, orderId);
            if (foundOrder==null){
                resp.setErrorMessage(String.format("Cannot delete order - Order do not exist (id:%s)", orderId));
                return Response.ok(resp).build();
            }
            else {
                hbrSession.beginTransaction();
                OrderDao.delete(hbrSession, orderId);
                hbrSession.getTransaction().commit();
                resp.setSuccessMessage(String.format("Order deleted (id:%s)", orderId));
                return Response.ok(resp).build();
            }
        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot delete Order - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
    }

    @DELETE
    @Path("/order-item/{orderId}/{productId}")
    @ApiOperation(value = "Delete an order line-item", response = BaseResponse.class)
    @RolesAllowed({"USER"})
    public Response getOrderDetail(
        @ApiParam(value = "Order Id") @PathParam("orderId") int orderId,
        @ApiParam(value = "Order-Item Id") @PathParam("productId") int productId
    ) {
        BaseResponse resp = new BaseResponse();

        if (orderId <= 0 || productId <= 0) {
            resp.setErrorMessage("Must provide a valid Order-id and Product-id");
            return Response.ok(resp).build();
        }
        Session hbrSession = HibernateUtil.getSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        try {
            hbrSession.beginTransaction();
            OrderDao.deleteOrderLine(hbrSession,orderId,productId);
            hbrSession.getTransaction().commit();
        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot delete Order-Item - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
        resp.setSuccessMessage("Deleted");
        return Response.ok(resp).build();
    }


    @POST
    @Path("/order-item")
    @ApiOperation(value = "Add an Order line-item", response = BaseResponse.class)
    @RolesAllowed({"ADMIN", "CUSTOMER", "SUPPORT"})
    public Response addOrderItem(OrderItemModel orderItem) {

        BaseResponse resp = new BaseResponse();
        Session hbrSession = HibernateUtil.getSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        try {
            hbrSession.beginTransaction();
            hbrSession.save(orderItem);
            hbrSession.getTransaction().commit();
        }
        catch (HibernateException | ConstraintViolationException  e) {
            resp.setErrorMessage("Cannot add Order-Item - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
        }

        return Response.ok(resp).build();
    }


}
