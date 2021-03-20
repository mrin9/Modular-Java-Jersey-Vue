package com.app.api.controllers;

import com.app.api.BaseController;
import com.app.dao.StatsDao;
import com.app.model.stats.CategoryCountModel;
import com.app.model.stats.CategoryCountModel.CategoryCountResponse;
import com.app.model.stats.DailySaleModel;
import com.app.model.stats.DailySaleModel.DailySaleResponse;
import com.app.model.stats.DailyOrderCountModel;
import com.app.model.stats.DailyOrderCountModel.DailyOrderCountResponse;
import com.app.util.HibernateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("stats")
@Tag(name = "Statistics")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class StatsController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(StatsController.class);

    @GET
    @Path("daily-sale")
    @RolesAllowed({"ADMIN", "SUPPORT"})
    @Operation(
      summary = "Get Sales by date",
      responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = DailySaleResponse.class)))}
    )
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
        } catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Internal Error - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
    }

    @GET
    @Path("daily-order-count")
    @RolesAllowed({"ADMIN", "SUPPORT"})
    @Operation(
      summary = "Get Daily order count",
      responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = DailyOrderCountResponse.class)))}
    )
    public Response getDailyOrderCount() {
        DailyOrderCountResponse resp = new DailyOrderCountResponse();
        try {
            Session hbrSession = HibernateUtil.getSession();
            hbrSession.beginTransaction();
            List<DailyOrderCountModel> dailyOrderCount = StatsDao.getDailyOrderCount(hbrSession);
            hbrSession.getTransaction().commit();
            resp.setSuccessMessage("Daily Order Count");
            resp.setList(dailyOrderCount);
            return Response.ok(resp).build();
        } catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Internal Error - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
    }

    @GET
    @Path("{orderStats: orders-by-status|orders-by-payment-type}")
    @RolesAllowed({"ADMIN", "SUPPORT"})
    @Operation(
      summary = "Get Orders by status",
      responses = { @ApiResponse(content = @Content(schema = @Schema(implementation = CategoryCountResponse.class)))}
    )
    public Response getOrdersByStatus(
      @Parameter(schema = @Schema(allowableValues = {"orders-by-status","orders-by-payment-type"}), example="orders-by-status")
      @PathParam("orderStats") String orderStats
    ) {
        CategoryCountResponse resp = new CategoryCountResponse();
        List<CategoryCountModel> categoryCountList;
        try {
            Session hbrSession = HibernateUtil.getSession();
            hbrSession.beginTransaction();
            if (orderStats.equals("orders-by-status")) {
                categoryCountList = StatsDao.getOrdersByStatus(hbrSession);
            } else {
                categoryCountList = StatsDao.getOrdersByPaymentType(hbrSession);
            }
            hbrSession.getTransaction().commit();
            resp.setSuccessMessage("Orders by status");
            resp.setList(categoryCountList);
            return Response.ok(resp).build();
        } catch (HibernateException | ConstraintViolationException e) {
            resp.setErrorMessage("Internal Error - " + e.getMessage() + ", " + (e.getCause()!=null? e.getCause().getMessage():""));
            return Response.ok(resp).build();
        }
    }
}
