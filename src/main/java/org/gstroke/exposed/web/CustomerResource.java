package org.gstroke.exposed.web;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.gstroke.entity.Customer;
import org.gstroke.response.dto.CustomerResponse;
import org.gstroke.service.CustomerService;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    CustomerService service;

    @GET
    @Path("/{id}")
    public Response getCustomerById(@PathParam("id") Long id) {

        if (id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Customer id must be greater than zero")
                    .build();
        }

        return service.getById(id)
                .map(this::toResponse)
                .map(Response::ok)
                .orElse(Response.status(Response.Status.NOT_FOUND))
                .build();
    }

    @GET
    @Path("/{id}/exists")
    public Response exists(@PathParam("id") Long id) {
        if (id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(false)
                    .build();
        }

        return Response.ok(service.customerExists(id)).build();
    }

    @GET
    @Path("/{id}/validate")
    public Response validate(@PathParam("id") Long id) {

        if (id == null || id <= 0) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Invalid customer id")
                    .build();
        }

        Customer customer = service.validateActiveCustomer(id);
        if (customer == null) {
            return Response.status(404).build();
        }

        if (!customer.status.equals("ACTIVE")) {
            return Response.status(409).entity("Customer inactive").build();
        }

        return Response.ok(toResponse(customer)).build();
    }

    private CustomerResponse toResponse(Customer c) {
        return new CustomerResponse(
                c.id,
                c.name,
                c.documentNumber,
                c.email,
                c.status
        );
    }
}