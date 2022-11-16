package loginApp.register.business;

import io.smallrye.mutiny.Uni;
import loginApp.register.entities.Request;
import loginApp.register.entities.User;
import loginApp.register.repository.AppRepo;
import loginApp.utils.Notification;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static loginApp.utils.ApplicationUtils.*;
import static loginApp.utils.Authenticate.hashPassword;

@ApplicationScoped
public class AppBO {
    @Inject
    AppRepo registerRepo;

    public Uni<Notification> insert(Request request) {
        return checkIfCredentialsAreValid(request).flatMap(
                aBoolean -> aBoolean ?
                        getUserByEmail(request.username)
                                .flatMap(foundUser -> {
                                    if (notBlank(foundUser.email)) {
                                        return uniItem(Notification.NotificationError("User exists in database"));
                                    }
                                    try {
                                        return registerRepo.insert(User.generateUser(request.username, request.password));
                                    } catch (Exception e) {
                                        return uniItem(Notification.NotificationError(e.getMessage()));
                                    }
                                }) : uniItem(Notification.NotificationError("Username/password is/are not given properly")));
    }

    public Uni<User> getUserByEmail(String email) {
        return registerRepo.getUserByEmail(email);
    }

    public Uni<Notification> login(Request request) {
        if (isBlank(request.username) || isBlank(request.password)) {
            return uniItem(Notification.NotificationError("Username/password is/are not given properly"));
        }
        return getUserByEmail(request.username)
                .flatMap(foundUser -> {
                    if (isBlank(foundUser)) {
                        return uniItem(Notification.NotificationError("Invalid credentials"));
                    }
                    if (Objects.equals(hashPassword(request.password, foundUser.salt), foundUser.hashedPassword)) {
                        return uniItem(Notification.NotificationOk("Login succesful"));
                    }
                    return uniItem(Notification.NotificationError("Invalid credentials"));
                });
    }

    private Uni<Boolean> checkIfCredentialsAreValid(Request loginRequest) {
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
        Pattern pattern = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$");
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    private boolean checkIfUsernameIsValid(String username) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
