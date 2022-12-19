package loginApp.pakkeyManager.controller;

import io.smallrye.mutiny.Uni;
import loginApp.pakkeyManager.entities.Passkey;
import loginApp.pakkeyManager.entities.PasskeyDataWrapper;
import loginApp.pakkeyManager.entities.PasskeyRequest;
import loginApp.pakkeyManager.passkeyBO.PasskeyBO;
import loginApp.utils.Notification;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
@Path("/passkey")
public class PasskeyController {
    @Inject
    PasskeyBO passkeyBO;

    @Path("/getPasskeyNames")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<PasskeyDataWrapper> getPasskeyNames() {
        return passkeyBO.getPasskeyNames();
    }

    @Path("/insert")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Notification> insertOne(PasskeyRequest passkey) {
        return passkeyBO.insertOne(passkey);
    }
}
