package com.app.api;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;
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
