package com.app.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

// These Request parameters will appear for all the controllers which extends from base controller
@ApiImplicitParams({
    @ApiImplicitParam(name="locale", paramType="header", dataType="string",  example="en-US", required=false,
        value="when provided, this will given more preference than 'Accept-Language' header from browser"
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
