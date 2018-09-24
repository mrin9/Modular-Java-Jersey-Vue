package com.app.api;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;


import com.app.model.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * If Rest API controller or Jersey framework throws any exception then this ExceptionMapper
 * class will catch the exception and returns JSON error response. (instead of sending some web-server generated html content)
 *
 */
@Provider
public class RelayRuntimeExceptionInResponse implements ExceptionMapper<RuntimeException> {
    private static Logger log = LoggerFactory.getLogger(RelayRuntimeExceptionInResponse.class);

    @Override
    public Response toResponse(RuntimeException e) {
        BaseResponse resp = new BaseResponse();
        String stackTrace="";
        //stackTrace = Stream.of(e.getStackTrace()).map((a) -> a.toString()).collect(Collectors.joining("' , '",  "[ '",  "' ]"));

        StringWriter exceptions = new StringWriter();
        e.printStackTrace(new PrintWriter(exceptions));
        stackTrace = exceptions.toString();

        log.error(stackTrace);

        resp.setErrorMessage( (e.getClass().getCanonicalName() +":-")  +  (e.getMessage()!=null?e.getMessage():e.toString()));
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(resp)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }


}
