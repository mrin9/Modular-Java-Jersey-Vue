package com.app.filters;

import java.io.IOException;
import jakarta.annotation.Priority;
import jakarta.ws.rs.container.*;
import jakarta.ws.rs.core.MultivaluedMap;

@Priority(0)
public class CORSResponseFilter  implements ContainerResponseFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        MultivaluedMap<String, Object> respHeaderMap = responseContext.getHeaders();
        String reqHeaderString = requestContext.getHeaderString("Access-Control-Request-Headers");

        respHeaderMap.add("Access-Control-Allow-Origin", "*");
        respHeaderMap.add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        respHeaderMap.add("Access-Control-Allow-Credentials", "true");
        respHeaderMap.add("Access-Control-Allow-Headers","Origin, X-Requested-With, Content-Type, Accept, Accept-Encoding, Accept-Language, Host, Referer, Connection, User-Agent, authorization, timezone-id");
        //respHeaderMap.add("Access-Control-Allow-Headers", reqHeaderString);
        respHeaderMap.add("X-Powered-By", "Mrin-Order-API");
    }
}
