package org.gstroke.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CustomerInactiveMapper
        implements ExceptionMapper<CustomerInactiveException> {

    @Override
    public Response toResponse(CustomerInactiveException e) {
        return Response.status(Response.Status.CONFLICT)
                .entity(e.getMessage())
                .build();
    }
}