package loginApp.register.business;

import io.smallrye.mutiny.Uni;
import loginApp.register.entities.LoginRequest;
import loginApp.utils.Notification;
import loginApp.register.entities.User;
import loginApp.register.repository.RegisterRepo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;

import static loginApp.utils.ApplicationUtils.*;
import static loginApp.utils.Authenticate.generateSalt;
import static loginApp.utils.Authenticate.hashPassword;

@ApplicationScoped
public class RegisterBO {

    @Inject
    RegisterRepo registerRepo;

    public Uni<Notification> insert(User user) {
        return getUserByEmail(user.email)
                .flatMap(foundUser -> {
                    if (notBlank(foundUser)) {
                        return uniItem(Notification.NotificationError("User exists in database"));
                    }
                    user.salt = generateSalt();
                    user.hashedPassword = hashPassword(user.hashedPassword, user.salt);
                    return insert(user);
                });
    }

    public Uni<User> getUserByEmail(String email) {
        return registerRepo.getUserByEmail(email);
    }

    public Uni<Notification> login(LoginRequest loginRequest) {
        return getUserByEmail(loginRequest.username)
                .flatMap(foundUser -> {
                    if (isBlank(foundUser)) {
                        return uniItem(Notification.NotificationError("Invalid credentials"));
                    }
                    if (Objects.equals(hashPassword(loginRequest.password, foundUser.salt), foundUser.hashedPassword)) {
                        return uniItem(Notification.NotificationOk("Login succesful"));
                    }
                    return uniItem(Notification.NotificationError("Invalid credentials"));
                });
    }
}
