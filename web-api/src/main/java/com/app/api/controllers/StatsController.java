package com.app.api.controllers;

import com.app.api.BaseController;
import com.app.model.stats.DailySaleModel.DailySaleResponse;
import com.app.model.user.UserViewModel;
import com.app.util.Constants;
import com.app.util.HibernateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.FlushMode;
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
    @ApiOperation(value = "Get Orders by date", response = DailySaleResponse.class)
    @RolesAllowed({"ADMIN", "SUPPORT"})
    public Response getDailySale() {
        DailySaleResponse resp = new DailySaleResponse();

        String sql = "select sum( (unit_price * quantity) - discount) as count, order_date as date from  NORTHWIND.ORDER_DETAILS"
        +" where order_date > DATEADD(DAY, -100 , CURDATE())"
        +" group by DAY_OF_YEAR (order_date)";

        resp.setSuccessMessage("Success");
        return Response.ok(resp).build();

    }





}
