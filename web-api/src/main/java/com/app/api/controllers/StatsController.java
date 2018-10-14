package com.app.api.controllers;

import com.app.api.BaseController;
import com.app.dao.StatsDao;
import com.app.model.stats.DailySaleModel;
import com.app.model.stats.DailySaleModel.DailySaleResponse;
import com.app.util.HibernateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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





}
