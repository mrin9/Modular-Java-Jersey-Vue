package com.app.api.order;

import com.app.TomcatStarter;
import com.app.api.BaseController;
import com.app.model.BaseResponse;
import com.app.model.order.*;
import com.app.model.user.User;
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

import static com.app.util.HibernateUtil.getSessionFactory;


@Path("")
@Api(value = "Orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(OrderController.class);

    @GET
    @Path("orders")
    @ApiOperation(value = "Get list of orders", response = OrderResponse.class)
    @RolesAllowed({"ADMIN"})
    public Response getOrderList(
        @ApiParam(value = "Order Id") @QueryParam("order-id") int orderId,
        @ApiParam(value = "Customer Id") @QueryParam("customer-id") int customerId,
        @ApiParam(value = "Employee Id") @QueryParam("employee-id") int employeeId,
        @ApiParam(value = "Payment Type", allowableValues = "Check, Cash, Card") @QueryParam("payment-type") String paymentType,
        @ApiParam(value = "Order Status", allowableValues = "On Hold, Shipped, Complete, New") @QueryParam("order-status") String orderStatus,
        @ApiParam(value = "Page No, Starts from 1 ", example = "1") @DefaultValue("1") @QueryParam("page") int page,
        @ApiParam(value = "Items in each page", example = "20") @DefaultValue("20") @QueryParam("page-size") int pageSize
    ) {
        int recordFrom = 0;
        Criteria criteria = HibernateUtil.getSessionFactory().openSession().createCriteria(OrderModel.class);
        if (orderId > 0) {
            criteria.add(Restrictions.eq("orderId", orderId));
        }
        if (customerId > 0) {
            criteria.add(Restrictions.eq("customerId", customerId));
        }
        if (employeeId > 0) {
            criteria.add(Restrictions.eq("employeeId", employeeId));
        }
        if (StringUtils.isNotBlank(paymentType)) {
            criteria.add(Restrictions.eq("payementType", paymentType));
        }
        if (StringUtils.isNotBlank(orderStatus)) {
            criteria.add(Restrictions.eq("orderStatus", orderStatus));
        }
        if (page <= 0) {
            page = 1;
        }
        if (pageSize <= 0 || pageSize > 1000) {
            pageSize = 20;
        }
        recordFrom = (page - 1) * pageSize;

        // Execute the Hibernate Query
        criteria.setFirstResult((int) (long) recordFrom);
        criteria.setMaxResults((int) (long) pageSize);
        List<OrderModel> orderFoundList = criteria.list();

        criteria.setProjection(Projections.rowCount());
        int totalRows = Math.toIntExact((Long) criteria.uniqueResult());

        OrderResponse resp = new OrderResponse();
        resp.setList(orderFoundList);
        resp.setPageStats(totalRows, pageSize, page, "");
        resp.setSuccessMessage("List of orders");
        return Response.ok(resp).build();
    }

    @DELETE
    @Path("orders/{orderId}")
    @ApiOperation(value = "Delete an order and all its line-items", response = BaseResponse.class)
    @RolesAllowed({"USER"})
    public Response deleteOrder(@ApiParam(value = "Order Id") @PathParam("orderId") int orderId) {

        BaseResponse resp = new BaseResponse();
        if (orderId <= 0 ) {
            resp.setErrorMessage("Must provide a valid Order-id ");
            return Response.ok(resp).build();
        }

        Session hbrSession = HibernateUtil.getSessionFactory().openSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        String hql1 = "delete OrderItemModel where orderId = :orderId";
        String hql2 = "delete OrderModel where id = :orderId";

        Query q1 = hbrSession.createQuery(hql1).setParameter("orderId", orderId);
        Query q2 = hbrSession.createQuery(hql2).setParameter("orderId", orderId);

        try {
            hbrSession.beginTransaction();
            q1.executeUpdate();
            q2.executeUpdate();
            hbrSession.getTransaction().commit();
        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Cannot delete Order " + e.getMessage() + ", " +e.getCause().getMessage());
            return Response.ok(resp).build();
        }
        resp.setSuccessMessage("Deleted");
        return Response.ok(resp).build();


    }

    @GET
    @Path("orders/{orderId}")
    @ApiOperation(value = "Get Details of an order", response = OrderWithNestedDetailResponse.class)
    @RolesAllowed({"USER"})
    public Response getOrderDetail(@ApiParam(value = "Order Id") @PathParam("orderId") int orderId) {

        String sql = "";
        String sqlTemplate = " select order_id, product_id   , customer_id   , order_date, order_status  , shipped_date    , employee_id , payment_type, paid_date, "
                + " ship_name      , ship_address1, ship_address2 , ship_city , ship_state    , ship_postal_code, ship_country, "
                + " product_code   , product_name , category      , quantity  , unit_price    , discount        , date_allocated, order_item_status, "
                + " shipping_fee   , customer_name, customer_email, customer_company from northwind.order_details where order_id = %s order by order_id, product_id ";

        if (orderId >= 0) {
            sql = String.format(sqlTemplate, orderId);
        }
        SQLQuery query = HibernateUtil.getSessionFactory().openSession().createSQLQuery(sql);
        query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List rowList = query.list();

        long prevOrderId = -1, newOrderId;
        int itemCount = 0;
        OrderWithNestedDetailResponse resp = new OrderWithNestedDetailResponse();
        OrderWithNestedDetailModel orderDetail = new OrderWithNestedDetailModel();

        for (Object object : rowList) {
            Map row = (Map) object;
            newOrderId = (int) row.get("ORDER_ID");
            if (prevOrderId != newOrderId) {
                itemCount++;

                orderDetail = new OrderWithNestedDetailModel(
                        (int) row.get("ORDER_ID"),
                        (Date) row.get("ORDER_DATE"),
                        (String) row.get("ORDER_STATUS"),
                        (Date) row.get("SHIPPED_DATE"),
                        (String) row.get("SHIP_NAME"),
                        (String) row.get("SHIP_ADDRESS1"),
                        (String) row.get("SHIP_ADDRESS2"),
                        (String) row.get("SHIP_CITY"),
                        (String) row.get("SHIP_STATE"),
                        (String) row.get("SHIP_POSTAL_CODE"),
                        (String) row.get("SHIP_COUNTRY"),
                        (BigDecimal) row.get("SHIPPING_FEE"),
                        (Integer) row.get("CUSTOMER_ID"),
                        (String) row.get("CUSTOMER_NAME"),
                        (String) row.get("CUSTOMER_EMAIL"),
                        (String) row.get("COMPANY"),
                        (String) row.get("PAYMENT_TYPE"),
                        (Date) row.get("PAID_DATE"),
                        (int) row.get("EMPLOYEE_ID")
                );
                orderDetail.addOrderLine(
                        (int) row.get("PRODUCT_ID"),
                        (String) row.get("PRODUCT_CODE"),
                        (String) row.get("PRODUCT_NAME"),
                        (String) row.get("CATEGORY"),
                        (BigDecimal) row.get("QUANTITY"),
                        (BigDecimal) row.get("UNIT_PRICE"),
                        (BigDecimal) row.get("DISCOUNT"),
                        (Date) row.get("DATE_ALLOCATED"),
                        (String) row.get("ORDER_ITEM_STATUS")
                );

                resp.getList().add(orderDetail);
                prevOrderId = newOrderId;
            } else {
                orderDetail.addOrderLine(
                        (int) row.get("PRODUCT_ID"),
                        (String) row.get("PRODUCT_CODE"),
                        (String) row.get("PRODUCT_NAME"),
                        (String) row.get("CATEGORY"),
                        (BigDecimal) row.get("QUANTITY"),
                        (BigDecimal) row.get("UNIT_PRICE"),
                        (BigDecimal) row.get("DISCOUNT"),
                        (Date) row.get("DATE_ALLOCATED"),
                        (String) row.get("ORDER_ITEM_STATUS")
                );
            }
        }
        resp.setSuccessMessage("List of Orders and neseted details");
        resp.setTotal(itemCount);
        return Response.ok(resp).build();
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
        Session hbrSession = HibernateUtil.getSessionFactory().openSession();
        hbrSession.setFlushMode(FlushMode.ALWAYS);
        String hql = "delete OrderItemModel where orderId = :orderId and productId = :productId";
        Query q = hbrSession.createQuery(hql);
        q.setParameter("orderId", orderId);
        q.setParameter("productId", productId);
        try {
            hbrSession.beginTransaction();
            q.executeUpdate();
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
        Session hbrSession = HibernateUtil.getSessionFactory().openSession();
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
