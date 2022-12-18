package loginApp.pakkeyManager.repo;

import io.smallrye.mutiny.Uni;
import loginApp.pakkeyManager.entities.Passkey;
import loginApp.pakkeyManager.entities.PasskeyRequest;
import loginApp.utils.BaseException;
import loginApp.utils.DbHelper;
import loginApp.utils.Notification;

import javax.enterprise.context.ApplicationScoped;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PasskeyRepo {
    public Uni<List<String>> getPasskeyNames() {
        return Uni.createFrom().emitter(uniEmitter -> {
            try {
                List<Passkey> passkeys = new ArrayList<>();
                Connection conn = DbHelper.getConnection();
                String query = "SELECT * FROM remotePasskey";
                PreparedStatement preparedStatement = conn.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    passkeys.add(Passkey.from(resultSet));
                }
                uniEmitter.complete(passkeys.stream().map(passkey -> passkey.passkeyName).collect(Collectors.toList()));
            } catch (Exception | BaseException e) {
                uniEmitter.fail(new BaseException("Failed to process get user by email due to server error encounter", 500));
            }
        });
    }

    public Uni<Notification> insertOne(PasskeyRequest passkey) {
        return Uni.createFrom().emitter(uniEmitter -> {
            try {
                Connection conn = DbHelper.getConnection();
                String query = "INSERT INTO remotePasskey VALUES (?)";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1, passkey.passkeyName);
                if (stmt.executeUpdate() != 1) {
                    conn.close();
                    uniEmitter.complete(Notification.NotificationError("Passkey not inserted"));
                } else {
                    conn.close();
                    uniEmitter.complete(Notification.NotificationOk("Passkey inserted"));
                }
            } catch (Exception | BaseException e) {
                uniEmitter.fail(new BaseException("Could not insert passkey due to server error", 500));
            }
        });
    }
}
