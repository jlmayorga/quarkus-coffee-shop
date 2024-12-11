package com.redhat.demo.resource;

import com.redhat.demo.model.Coffee;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.jboss.logging.Logger;
import java.util.List;

@Path("/coffee")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoffeeResource {
    private static final Logger LOG = Logger.getLogger(CoffeeResource.class);

    @GET
    @Operation(summary = "Get all coffee products")
    public List<Coffee> getAllCoffee() {
        return Coffee.listAll();
    }

    @GET
    @Path("/lowStock")
    @Operation(summary = "Get coffee products that need restocking")
    public List<Coffee> getLowStockCoffee() {
        return Coffee.findLowStock();
    }

    @POST
    @Transactional
    @Operation(summary = "Add a new coffee product")
    public Response addCoffee(Coffee coffee) {
        coffee.persist();
        LOG.infof("Created new coffee: %s", coffee.name);
        return Response.status(201).entity(coffee).build();
    }
}