package loginApp.register.repository;

import io.smallrye.mutiny.Uni;
import loginApp.utils.Notification;
import loginApp.register.entities.User;

import javax.enterprise.context.ApplicationScoped;

import static loginApp.utils.ApplicationUtils.uniItem;

@ApplicationScoped
public class RegisterRepo {

    public Uni<Notification> insert(User user) {
        return uniItem(Notification.NotificationOk(""));
    }

    public Uni<User> getUserByEmail(String email) {
        return uniItem(new User());
    }
}
