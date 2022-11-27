package loginApp.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static loginApp.utils.AppConstants.*;

public class DbHelper {
    public DbHelper() {
    }

    public static Connection getConnection() throws BaseException {
        try {
            return DriverManager.getConnection(getDatabaseUrl(), getDatabaseName(), getDatabasePassword());
        } catch (SQLException sqlException) {
            throw new BaseException("Failed to connect to database", 500);
        }
    }

    private static String getDatabaseUrl() {
        return getEnv(DATABASE_URL);
    }

    private static String getDatabaseName() {
        return getEnv(DATABASE_USERNAME);
    }

    private static String getDatabasePassword() {
        return getEnv(DATABASE_PASWORD);
    }

    private static String getEnv(String envVariable) {
        return System.getenv(envVariable);
    }
}
