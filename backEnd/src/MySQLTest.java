import java.sql.*;

public class MySQLTest {

    public static void main(String[] args) {

        try {
            // Create mySQL connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskproject", "admin", "TestPassword1");
            Statement stmt = conn.createStatement();

            // Query
            ResultSet rs = stmt.executeQuery("DESCRIBE user");

            // get results
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }

            // Close connection
            conn.close();

        } catch ( Exception e ) {
            System.out.println(e);
        }


    }

}
