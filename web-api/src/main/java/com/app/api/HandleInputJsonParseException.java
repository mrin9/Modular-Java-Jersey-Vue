package com.app.api;


import com.app.model.BaseResponse;
import com.fasterxml.jackson.databind.JsonMappingException;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class HandleInputJsonParseException implements ExceptionMapper<JsonMappingException>{

    @Override
    public Response toResponse(JsonMappingException e){
        BaseResponse resp = new BaseResponse();
        resp.setErrorMessage(e.toString());
        return Response
            .status(Response.Status.BAD_REQUEST)
            .entity(resp)
            .type( MediaType.APPLICATION_JSON)
            .build();
    }

}
