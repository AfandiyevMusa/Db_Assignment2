import java.sql.Connection;
import java.sql.DriverManager;

public class DbFunctions {
    private static final String dbname = "bookstore";
    private static final String user = "postgres";
    private static final String pass = "pg_strong_password";


    public Connection connect_to_db() {
        Connection conn = null;
        try {
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + dbname, user, pass);
            if (conn != null) {
                System.out.println("Connection Established");
            } else {
                System.out.println("Connection Failed");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }
}