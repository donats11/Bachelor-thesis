package loginApp.register.controller;

import io.smallrye.mutiny.Uni;
import loginApp.register.entities.Request;
import loginApp.utils.Notification;
import loginApp.register.business.AppBO;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class AppController {
    @Inject
    AppBO registerBO;

    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Notification> registerUser(Request loginRequest) {
        return registerBO.insert(loginRequest);
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Notification> login(Request loginRequest) {
        return registerBO.login(loginRequest);
    }
}
