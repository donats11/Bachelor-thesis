package loginApp.register.repository;

import io.smallrye.mutiny.Uni;
import loginApp.register.entities.User;
import loginApp.utils.BaseException;
import loginApp.utils.Notification;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static loginApp.utils.ApplicationUtils.uniFail;
import static loginApp.utils.ApplicationUtils.uniItem;

@ApplicationScoped
public class AppRepo {

    public Uni<Notification> insert(User user) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginmanager", "root", "root");
        String query = "INSERT INTO User VALUES (?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, user.email);
        stmt.setString(2, user.hashedPassword);
        stmt.setString(3, user.salt);
        if (stmt.executeUpdate() != 1) {
            conn.close();
            return uniItem(Notification.NotificationError("User not inserted"));
        } else {
            conn.close();
            return uniItem(Notification.NotificationOk("User inserted"));
        }
    }

    public Uni<User> getUserByEmail(String email) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/loginmanager", "root", "root");
            String query = "SELECT * FROM User where email = ?";
            PreparedStatement preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            return !resultSet.next() ? uniItem(new User()) : uniItem(User.from(resultSet));
        } catch (Exception e) {
            return uniFail(new BaseException("Server is down", 500));

        }
    }
}
