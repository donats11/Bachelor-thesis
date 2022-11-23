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
            return DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASWORD);
        } catch (SQLException sqlException) {
            throw new BaseException("Failed to connect to database", 500);
        }
    }
}
