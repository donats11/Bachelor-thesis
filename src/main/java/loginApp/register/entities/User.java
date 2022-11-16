package loginApp.register.entities;

import io.quarkus.runtime.annotations.RegisterForReflection;

import static loginApp.utils.Authenticate.generateSalt;
import static loginApp.utils.Authenticate.hashPassword;
public class User {
    public String email;
    public String hashedPassword;
    public String salt;

    public User(String email) {
        this.email = email;
    }

    public static User generateUser(String email, String password) {
        User user = new User(email);
        user.salt = generateSalt();
        user.hashedPassword = hashPassword(password, user.salt);
        return user;
    }
}
