package com.app.filters;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.*;
import java.security.Principal;
import jakarta.annotation.Priority;
import jakarta.annotation.security.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.ext.Provider;
import jakarta.ws.rs.Priorities;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;
import com.app.model.user.*;
import com.app.model.BaseResponse;
import com.app.model.BaseResponse.MessageTypeEnum;
import com.app.util.TokenUtil;
import com.app.util.Constants.UserRoleConstants;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthorizationRequestFilter implements ContainerRequestFilter {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(AuthorizationRequestFilter.class);

    @Context
    HttpServletRequest request;

    @Context
    ResourceInfo resourceInfo;

    @Override
    public void filter(ContainerRequestContext reqContext ) throws IOException {
        Method method = resourceInfo.getResourceMethod();
        String className = method.getDeclaringClass().getName();
        String path = reqContext.getUriInfo().getPath();
        BaseResponse resp = new BaseResponse();
        final UserViewModel userView;

        //Allow Access for @PermitAll or OpenAPI Spec
        if (reqContext.getMethod().equalsIgnoreCase("OPTIONS") || method.isAnnotationPresent(PermitAll.class) || className.equals("io.swagger.v3.jaxrs2.integration.resources.OpenApiResource")) {
            return;
        }

        String jwtToken = reqContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isBlank(jwtToken)) {
            resp.setTypeAndMessage(MessageTypeEnum.BAD_TOKEN, "Empty Token" );
            reqContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON).entity(resp).build());
            return;
        }

        try {
            userView = TokenUtil.getUserFromToken(jwtToken);
            if (userView==null) {
                resp.setTypeAndMessage(MessageTypeEnum.BAD_TOKEN, "Invalid Token" );
                reqContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON).entity(resp).build());
                return;
            }
        } catch (Exception e) {
            log.error("Invalid user token " + e.getMessage());
            resp.setTypeAndMessage(MessageTypeEnum.BAD_TOKEN, "Invalid User" );
            reqContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).type(MediaType.APPLICATION_JSON).entity(resp).build());
            return;
        }

        // Store Account in the SecurityContext
        final SecurityContext securityContext = reqContext.getSecurityContext();
        reqContext.setSecurityContext(new SecurityContext() {
            @Override
            public Principal getUserPrincipal() {return userView;}

            @Override
            public boolean isUserInRole(String role) {return securityContext.isUserInRole(role);}

            @Override
            public boolean isSecure() {return securityContext.isSecure();}

            @Override
            public String getAuthenticationScheme() {return "Token-Based-Auth-Scheme";}
        });

        // Everything is permitted for the role "admin"
        if (userView.getRole().equalsIgnoreCase(UserRoleConstants.ROLE_ADMIN)){
            return;
        }

        //check for roles
        if(method.isAnnotationPresent(RolesAllowed.class)) {
            RolesAllowed rolesAnnotation = method.getAnnotation(RolesAllowed.class);
            Set<String> allowedRoleSet = new HashSet<>(Arrays.asList(rolesAnnotation.value() ));
            if (allowedRoleSet.contains(userView.getRole())){
                return;
            }
            if (allowedRoleSet.contains(UserRoleConstants.ROLE_CUSTOMER)){
                // Any endpoint for role "CUSTOMER" is available to all authenticated users (ADMIN, SUPPORT)
                return;
            } else {
                resp.setTypeAndMessage(MessageTypeEnum.NO_ACCESS, "Unauthorized for " +  userView.getRole() + " role");
                reqContext.abortWith(Response.status(Response.Status.FORBIDDEN)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(resp).build());
                return;
            }
        }
    }
}



