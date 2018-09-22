package com.app.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@ApiImplicitParams({
    @ApiImplicitParam(
        name="timezone-id",
        paramType="header",
        dataType="string",
        example="America/Los_Angeles",
        required=false,
        value="If provided, date/time will be converted to the given timezone, else date and time will be in GMT"
    ),
    @ApiImplicitParam(
        name="locale",
        paramType="header",
        dataType="string",
        example="en-US",
        required=false,
        value="if provided, this will override 'Accept-Language' header"
    )
})


public class BaseController {
    @Context
    protected HttpServletRequest req;

    @Context
    protected HttpServletResponse res;

    @Context
    protected ContainerRequestContext requestContext;

    @Context
    protected SecurityContext securityContext;

}
