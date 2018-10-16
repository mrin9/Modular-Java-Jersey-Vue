package com.app.api.controllers;

import com.app.api.BaseController;
import com.app.dao.StatsDao;
import com.app.model.stats.CategoryCountModel;
import com.app.model.stats.CategoryCountModel.CategoryCountResponse;
import com.app.model.stats.DailySaleModel;
import com.app.model.stats.DailySaleModel.DailySaleResponse;
import com.app.util.HibernateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;


@Path("stats")
@Api(value = "Statistics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatsController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(StatsController.class);

    @GET
    @Path("daily-sale")
    @ApiOperation(value = "Get Sales by date", response = DailySaleResponse.class)
    @RolesAllowed({"ADMIN", "SUPPORT"})
    public Response getDailySale() {
        DailySaleResponse resp = new DailySaleResponse();

        try {
            Session hbrSession = HibernateUtil.getSession();
            hbrSession.beginTransaction();
            List<DailySaleModel> dailySales = StatsDao.getDailySales(hbrSession);
            hbrSession.getTransaction().commit();
            resp.setSuccessMessage("Daily Sales");
            resp.setList(dailySales);
            return Response.ok(resp).build();
        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Internal Error - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
    }

    @GET
    @Path("{orderStats: orders-by-status|orders-by-payment-type}")
    @ApiOperation(value = "Get Orders by status", response = CategoryCountResponse.class)
    @RolesAllowed({"ADMIN", "SUPPORT"})
    public Response getOrdersByStatus(@ApiParam(allowableValues = "orders-by-status,orders-by-payment-type", example="orders-by-status") @PathParam("orderStats") String orderStats) {
        CategoryCountResponse resp = new CategoryCountResponse();
        List<CategoryCountModel> categoryCountList;
        try {
            Session hbrSession = HibernateUtil.getSession();
            hbrSession.beginTransaction();
            if (orderStats.equals("orders-by-status")) {
                categoryCountList = StatsDao.getOrdersByStatus(hbrSession);
            }
            else{
                categoryCountList = StatsDao.getOrdersByPaymentType(hbrSession);
            }
            hbrSession.getTransaction().commit();
            resp.setSuccessMessage("Orders by status");
            resp.setList(categoryCountList);
            return Response.ok(resp).build();
        }
        catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Internal Error - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
    }




}
