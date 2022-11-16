package loginApp.register.business;

import io.smallrye.mutiny.Uni;
import loginApp.register.entities.LoginRequest;
import loginApp.register.entities.User;
import loginApp.register.repository.RegisterRepo;
import loginApp.utils.Notification;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;
import java.util.function.Supplier;

import static loginApp.utils.ApplicationUtils.*;
import static loginApp.utils.Authenticate.hashPassword;

@ApplicationScoped
public class RegisterBO {
    @Inject
    RegisterRepo registerRepo;

    public Uni<Notification> insert(LoginRequest loginRequest) {
        return getUserByEmail(loginRequest.username)
                .flatMap(foundUser -> {
                    if (notBlank(foundUser)) {
                        return uniItem(Notification.NotificationError("User exists in database"));
                    }
                    try {
                        return registerRepo.insert(User.generateUser(loginRequest.username, loginRequest.password));
                    } catch (Exception e) {
                        return uniItem(Notification.NotificationError("User not inserted"));
                    }
                });
    }

    public Uni<User> getUserByEmail(String email) {
        return registerRepo.getUserByEmail(email);
    }

    public Uni<Notification> login(LoginRequest loginRequest) {
        return checkIfCredentialsAreValid(loginRequest).flatMap(aBoolean -> {
            try {
                return aBoolean ?
                        getUserByEmail(loginRequest.username)
                                .flatMap(foundUser -> {
                                    if (isBlank(foundUser)) {
                                        return uniItem(Notification.NotificationError("Invalid credentials"));
                                    }
                                    if (Objects.equals(hashPassword(loginRequest.password, foundUser.salt), foundUser.hashedPassword)) {
                                        return uniItem(Notification.NotificationOk("Login succesful"));
                                    }
                                    return uniItem(Notification.NotificationError("Invalid credentials"));
                                }) :
                        uniItem(Notification.NotificationError("Username/password is/are not given properly"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private Uni<Boolean> checkIfCredentialsAreValid(LoginRequest loginRequest) {
        return (isBlank(loginRequest.password) || isBlank(loginRequest.username)) ?
                uniItem(false) : (checkIfUsernameIsValid(loginRequest.username) && checkIfPasswordIsValid(loginRequest.password)) ?
                uniItem(true) : uniItem(false);
    }

    //Must have at least one numeric character
//Must have at least one lowercase character
//Must have at least one uppercase character
//Must have at least one special symbol among @#$%
//Password length should be between 8 and 20
    private boolean checkIfPasswordIsValid(String password) {
        return password.matches("/-^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$/-");
    }

    private boolean checkIfUsernameIsValid(String username) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(username);
        return m.matches();
    }
}
