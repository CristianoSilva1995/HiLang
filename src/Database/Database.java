package Database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author glukka, Arkadiusz Nowak
 */

public class Database {
    /**
     * Using sqlite driver, establish connection to sqlite database with a given name
     * @param dbName Name of the database
    */
    public static Connection getConnection(String dbName) {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:" + dbName;
            Connection con = DriverManager.getConnection(url);
            return con;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Trim and surround string with ' for sql query i.e. Sun becomes 'Sun'
     * @param str String to be modified
     */
    public static String sqlString(String str) {
        return str.trim().replace("'","''");
    }
}