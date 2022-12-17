package loginApp.pakkeyManager.passkeyBO;

import io.smallrye.mutiny.Uni;
import loginApp.pakkeyManager.entities.Passkey;
import loginApp.pakkeyManager.repo.PasskeyRepo;
import loginApp.utils.Notification;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class PasskeyBO {
    @Inject
    PasskeyRepo passkeyRepo;

    public Uni<List<String>> getPasskeyNames() {
        return passkeyRepo.getPasskeyNames();
    }

    public Uni<Notification> insertOne(Passkey passkey) {
        return passkeyRepo.insertOne(passkey);
    }
}
