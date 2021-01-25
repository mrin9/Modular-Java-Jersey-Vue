package com.app.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;
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
