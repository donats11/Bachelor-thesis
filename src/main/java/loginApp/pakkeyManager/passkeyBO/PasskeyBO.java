package loginApp.pakkeyManager.passkeyBO;

import io.smallrye.mutiny.Uni;
import loginApp.pakkeyManager.entities.Passkey;
import loginApp.pakkeyManager.entities.PasskeyDataWrapper;
import loginApp.pakkeyManager.entities.PasskeyRequest;
import loginApp.pakkeyManager.repo.PasskeyRepo;
import loginApp.utils.Notification;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PasskeyBO {
    @Inject
    PasskeyRepo passkeyRepo;

    public Uni<PasskeyDataWrapper> getPasskeyNames() {
        return passkeyRepo.getPasskeyNames();
    }

    public Uni<Notification> insertOne(PasskeyRequest passkey) {
        return passkeyRepo.insertOne(passkey);
    }
}
