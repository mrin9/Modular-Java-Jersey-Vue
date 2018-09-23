package com.app.api.order;

import com.app.api.BaseController;
import com.app.model.order.OrderModel;
import com.app.model.order.OrderResponse;
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


@Path("orders")
@Api(value = "Orders")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderController extends BaseController {

    @GET
    @Path("")
    @ApiOperation(value = "Get list of orders and order-details")
    @RolesAllowed({"ADMIN"})
    public Response getOrderList(
        @ApiParam(value="Order Id") @QueryParam("order-id") int orderId,
        @ApiParam(value="Customer Id") @QueryParam("customer-id") int customerId,
        @ApiParam(value="Employee Id") @QueryParam("employee-id") int employeeId,
        @ApiParam(value="Payment Type", allowableValues="Check, Cash, Card") @QueryParam("payment-type") String paymentType,
        @ApiParam(value="Order Status", allowableValues="On Hold, Shipped, Complete, New") @QueryParam("order-status") String orderStatus,
        @ApiParam(value="Page No, Starts from 1 ", example="1") @DefaultValue("1")  @QueryParam("page") int page,
        @ApiParam(value="Items in each page", example="20") @DefaultValue("20") @QueryParam("page-size") int pageSize
    ) {
        int recordFrom=0;
        Criteria criteria = HibernateUtil.getSessionFactory().openSession().createCriteria(OrderModel.class);
        if (orderId > 0) {
            criteria.add(Restrictions.eq("orderId",  orderId ));
        }
        if (customerId > 0) {
            criteria.add(Restrictions.eq("customerId",  customerId ));
        }
        if (employeeId > 0) {
            criteria.add(Restrictions.eq("employeeId",  employeeId ));
        }
        if (StringUtils.isNotBlank(paymentType)) {
            criteria.add(Restrictions.eq("payementType",  paymentType ));
        }
        if (StringUtils.isNotBlank(orderStatus)) {
            criteria.add(Restrictions.eq("orderStatus",  orderStatus ));
        }
        if (page<=0){
            page = 1;
        }
        if (pageSize<=0 || pageSize > 1000){
            pageSize =20;
        }
        recordFrom = (page-1) * pageSize;

        // Execute the Hibernate Query
        criteria.setFirstResult((int) (long)recordFrom);
        criteria.setMaxResults((int) (long)pageSize);
        List<OrderModel> orderFoundList = criteria.list();

        criteria.setProjection(Projections.rowCount());
        int totalRows = Math.toIntExact((Long) criteria.uniqueResult());

        OrderResponse resp = new OrderResponse();
        resp.setList(orderFoundList);
        resp.setPageStats(totalRows, pageSize, page,"");
        resp.setSuccessMessage("List of orders");
        return Response.ok(resp).build();
    }


}
