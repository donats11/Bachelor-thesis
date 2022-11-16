package loginApp.register.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public User() {
    }

    public User(String email, String hashedPassword, String salt) {
        this.email = email;
        this.hashedPassword = hashedPassword;
        this.salt = salt;
    }

    public static User from(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
    }
}
