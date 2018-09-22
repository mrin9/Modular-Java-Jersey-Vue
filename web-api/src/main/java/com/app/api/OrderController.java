package com.app.api;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import javax.annotation.security.PermitAll;
import io.swagger.annotations.*;



@Path("")
@Api(value = "Orders")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)

public class OrderController extends BaseController{

    @GET
    @Path("/orders")
    @PermitAll
    @ApiOperation(value = "Serach Orders ")
    public Response getOrders() {
        return Response.ok("Hello Orders API").build();
    }

}
