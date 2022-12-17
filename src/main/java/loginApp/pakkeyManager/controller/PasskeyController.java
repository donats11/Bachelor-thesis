package loginApp.pakkeyManager.controller;

import io.smallrye.mutiny.Uni;
import loginApp.pakkeyManager.entities.Passkey;
import loginApp.pakkeyManager.passkeyBO.PasskeyBO;
import loginApp.utils.Notification;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("/passkey")
public class PasskeyController {

    @Inject
    PasskeyBO passkeyBO;

    @GET
    public Uni<List<String>> getPasskeyNames() {
        return passkeyBO.getPasskeyNames();
    }

    @POST
    public Uni<Notification> insertOne(Passkey passkey) {
        return passkeyBO.insertOne(passkey);
    }
}
