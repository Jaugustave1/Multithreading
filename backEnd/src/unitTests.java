import java.sql.*;

public class unitTests {

    // Flushes all data in database
    public void flushDB(sqlDB db) {
        Connection conn = db.getConnection();
        String query = "FLUSH TABLES calendar, collaboration, notification, task, tasklist, user;";
        try {
            Statement stmnt = conn.createStatement();
            stmnt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Creates testing data

    public static void main(String[] args) {
        sqlDB db = new sqlDB();

        // check if login works correctly
        int id;
        id = db.login("Jalan", "testpass");
        System.out.println("ID: " + id);

        System.out.print('\n');
        // check if task checker works correctly
        db.getTasks(1);

        db.closeConnection();


    }

}
