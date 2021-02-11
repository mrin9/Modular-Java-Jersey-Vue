package com.app.api.controllers;

import com.app.api.BaseController;
import com.app.dao.OrderDao;
import com.app.model.BaseResponse;
import com.app.model.order.*;
import com.app.model.user.UserViewModel;
import com.app.util.Constants;
import com.app.util.HibernateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Path("")
@Tag(name = "Orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController extends BaseController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @GET
    @Path("orders")
    @RolesAllowed({"ADMIN", "CUSTOMER", "SUPPORT"})
    @Operation(
      summary = "Get Details of an order with nested order-lines",
      responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = OrderWithNestedDetailResponse.class)))}
    )
    public Response getOrderDetail(
        @Parameter(description = "Order Id") @QueryParam("order-id") int orderId,
        @Parameter(description = "Customer Id") @QueryParam("customer-id") int customerId,
        @Parameter(description = "Payment Type", schema = @Schema(allowableValues = {"Check","Cash","Card"})) @QueryParam("payment-type") String paymentType,
        @Parameter(description = "Order Status", schema = @Schema(allowableValues = {"On Hold","Shipped","Complete", "New"})) @QueryParam("order-status") String orderStatus,
        @Parameter(description = "Page No, Starts from 1 ", example = "1") @DefaultValue("1") @QueryParam("page") int page,
        @Parameter(description = "Items in each page", example = "20") @DefaultValue("1000") @QueryParam("page-size") int pageSize
    ) {

        OrderWithNestedDetailResponse resp = new OrderWithNestedDetailResponse();
        Session hbrSession = HibernateUtil.getSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);

        UserViewModel userFromToken = (UserViewModel)securityContext.getUserPrincipal();  // securityContext is defined in BaseController
        //Customers can query their own cart only
        if (userFromToken.getRole().equalsIgnoreCase(Constants.UserRoleConstants.ROLE_CUSTOMER)){
            if (customerId>0) {
                customerId = userFromToken.getCustomerId();
            }
        }
        try {
            List<OrderWithNestedDetailModel> orderWithOrderLinesList = OrderDao.getWithOrderLines(hbrSession, page, pageSize, orderId, customerId, paymentType, orderStatus);
            BigInteger total = OrderDao.getOrderCount(hbrSession, orderId, customerId, paymentType, orderStatus);

            resp.setList(orderWithOrderLinesList);
            resp.setTotal(total.intValue());
            resp.setPageStats(total.intValue(),pageSize, page,"");
            resp.setSuccessMessage("List of Orders and nested details " + (customerId>0 ? "- Customer:"+customerId:""));
            return Response.ok(resp).build();
        } catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot delete Order - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
    }

    @DELETE
    @Path("orders/{orderId}")
    @RolesAllowed({"USER"})
    @Operation(
      summary = "Delete an order and all its line-items",
      responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = BaseResponse.class)))}
    )
    public Response deleteOrder(@Parameter(description = "Order Id", example="4002") @PathParam("orderId") Integer orderId) {
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
            } else {
                hbrSession.beginTransaction();
                OrderDao.delete(hbrSession, orderId);
                hbrSession.getTransaction().commit();
                resp.setSuccessMessage(String.format("Order deleted (id:%s)", orderId));
                return Response.ok(resp).build();
            }
        } catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot delete Order - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
    }

    @DELETE
    @Path("/order-item/{orderId}/{productId}")
    @RolesAllowed({"USER"})
    @Operation(
      summary = "Delete an order line-item",
      responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = BaseResponse.class)))}
    )
    public Response deleteOrderItem(
        @Parameter(description = "Order Id") @PathParam("orderId") int orderId,
        @Parameter(description = "Order-Item Id") @PathParam("productId") int productId
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
        } catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot delete Order-Item - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
        resp.setSuccessMessage("Deleted");
        return Response.ok(resp).build();
    }

    @DELETE
    @Path("/order-item/{orderId}")
    @RolesAllowed({"USER"})
    @Operation(
      summary = "Delete all order line-item by product-id",
      responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = BaseResponse.class)))}
    )
    public Response deleteOrderItems(
      @Parameter(description = "Order Id") @PathParam("orderId") int orderId,
      @Parameter(description = "Comma separated list of product ids") @QueryParam("product-ids") String commaSeparatedIds
    ) {
        BaseResponse resp = new BaseResponse();
        String[] productIdArr = commaSeparatedIds.split(",");
        ArrayList<Integer> productIdList = new ArrayList<Integer>();
        for (String productIdStr : productIdArr) {
            productIdList.add(Integer.parseInt(productIdStr.trim()));
        }
        Session hbrSession = HibernateUtil.getSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        try {
            hbrSession.beginTransaction();
            OrderDao.deleteOrderLines(hbrSession, orderId, productIdList);
            hbrSession.getTransaction().commit();
        } catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot delete Order-Item - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
        resp.setSuccessMessage("Deleted all order lines");
        return Response.ok(resp).build();
    }

    @POST
    @Path("/order-item")
    @RolesAllowed({"ADMIN", "CUSTOMER", "SUPPORT"})
    @Operation(
      summary = "Add an order line-item",
      responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = BaseResponse.class)))}
    )
    public Response addOrderItem(OrderItemModel orderItem) {
        BaseResponse resp = new BaseResponse();
        Session hbrSession = HibernateUtil.getSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        try {
            hbrSession.beginTransaction();
            hbrSession.save(orderItem);
            hbrSession.getTransaction().commit();
        } catch (HibernateException | ConstraintViolationException  e) {
            resp.setErrorMessage("Cannot add Order-Item - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
        }
        return Response.ok(resp).build();
    }
}
