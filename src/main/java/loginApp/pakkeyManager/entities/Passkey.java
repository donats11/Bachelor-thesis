package loginApp.pakkeyManager.entities;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Passkey {
    public String passkeyName;

    public Passkey(String passkeyName) {
        this.passkeyName = passkeyName;
    }

    public static Passkey from(ResultSet resultSet) throws SQLException {
        return new Passkey(resultSet.getString(1));
    }
}
