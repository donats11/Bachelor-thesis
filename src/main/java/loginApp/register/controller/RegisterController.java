package loginApp.register.controller;

import io.smallrye.mutiny.Uni;
import loginApp.register.entities.LoginRequest;
import loginApp.utils.Notification;
import loginApp.register.business.RegisterBO;
import loginApp.register.entities.User;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/api")
public class RegisterController {
    @Inject
    RegisterBO registerBO;

    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Notification> registerUser(User user) {
        return registerBO.insert(user);
    }

    @Path("/login")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Notification> login(LoginRequest loginRequest) {
        return registerBO.login(loginRequest);
    }
}
