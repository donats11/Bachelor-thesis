package loginApp.utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbHelper {

    private static Connection conn = null;

    public DbHelper() {
    }

    public static Connection getConnection() throws Exception {
        if (conn == null || conn.isClosed()) {
            conn = DriverManager.getConnection(AppConfig.get().getConnectionString());
        }
        return conn;
    }
}
