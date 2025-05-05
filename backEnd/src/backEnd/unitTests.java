package backEnd;

import java.sql.*;
// test

public class unitTests {

    // Flushes all data in database
    public static void flushDB(sqlDB db) {
        Connection conn = db.getConnection();
        String query = "FLUSH TABLES calendar, collaboration, notification, task, tasklist, user;";
        try {
            Statement stmnt = conn.createStatement();
            stmnt.executeUpdate(query);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void testUserInterface(sqlDB db) {
        System.out.println("Testing User Interface");

        userInterface ui = new userInterface(db);
        boolean temp = ui.login("Jalan", "testpass");
        assert temp == true;

        System.out.println("User Interface Test Done");
        System.out.print("\n");

    }

    public static void getTasks(sqlDB db) {
        ResultSet rs = db.getAllTasks();

    }



    // Creates testing data
    /*
    todo: create testing data for all tables through function
     */

    public static void main(String[] args) {
        sqlDB db = new sqlDB();
        boolean useTest;

        useTest = true;
        if (useTest) {// check if login works correctly
            System.out.println("Login Test:");
            int id;
            id = db.login("Jalan", "testpass");
            System.out.println("ID: " + id);

            System.out.print('\n');
        }

        useTest = false;
        if (useTest) {
            System.out.println("task checker Test:");
            // check if task checker works correctly
            db.getTasks(1);

            System.out.print('\n');
        }

        testUserInterface(db);

        db.closeConnection();


    }

}
