package backEnd;

import javax.management.InvalidAttributeValueException;
import java.sql.*;

public class userInterface {
    private final Connection conn;
    int userID;
    private final sqlDB db;

    // Decide whether or nto to create new use that can only read
    public userInterface(sqlDB db) {
        this.db = db;
        this.conn = db.getConnection();
    }


    /* =====================================
          Connection Creation
    ===================================== */

//    private void openConnection() {
//        try {
//            // Create mySQL Connection
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskproject", "admin", "TestPassword1");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//    }

    public void closeConnection() {
        try {
            this.conn.close();
            System.out.println("Connection closed");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void enforceLogin() throws InvalidAttributeValueException {
        if (this.userID == -1) {
            throw new InvalidAttributeValueException("User not logged in. Login with login(name, pass)");
        }
    }

    // Updates user interface is accessing
    // Input is username and password
    // Output is True if login is successful, False if not
    public boolean login(String name, String pass) {
        int newID = db.login(name, pass);
        if (newID == -1) {
            return false;
        }
        this.userID = newID; // If the newID is not -1, update the userID
        return true;
    }

    // Get User's ID
    public int getUserID() { return this.userID; }

    // Gets Join Date of user
    public String getJoinDate() {
        try {
            this.enforceLogin();

            String query = "SELECT dateJoined FROM user WHERE userID = ?";
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            pstmnt.setInt(1, this.userID);

            ResultSet rset = pstmnt.executeQuery();
            rset.next();
            return rset.getString("dateJoined");
        } catch (Exception e) {
            System.out.println(e);
            return "Invalid User";
        }
    }

    // Gets Name of user
    public String getName() {
        try {
            this.enforceLogin();

            String query = "SELECT name FROM user WHERE userID = ?";
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            pstmnt.setInt(1, this.userID);

            ResultSet rset = pstmnt.executeQuery();
            rset.next();
            return rset.getString("name");
        } catch (Exception e) {
            System.out.println(e);
            return "Invalid User";
        }
    }

    // Gets email of a user
    public String getEmail() {
        try {
            this.enforceLogin();

            String query = "SELECT email FROM user WHERE userID = ?";
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            pstmnt.setInt(1, this.userID);

            ResultSet rset = pstmnt.executeQuery();
            rset.next();
            return rset.getString("email");
        } catch (Exception e) {
            System.out.println(e);
            return "Invalid User";
        }
    }

    // Gets password of a user
    // Should I keep or remove this?
    public String getPassword() {
        try {
            this.enforceLogin();

            String query = "SELECT password FROM user WHERE userID = ?";
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            pstmnt.setInt(1, this.userID);

            ResultSet rset = pstmnt.executeQuery();
            rset.next();
            return rset.getString("password");
        } catch (Exception e) {
            System.out.println(e);
            return "Invalid User";
        }
    }

    // Prints tasks assigned to user
    // todo: turn into generator for easier use by using .next()
    // todo: Maybe return RSet instead as its already a generator
    public void getTasks() {
        try {
            this.enforceLogin();

            String query = "SELECT * FROM task WHERE userID = ?";
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            pstmnt.setInt(1, this.userID);

            ResultSet rset = pstmnt.executeQuery();
            while (rset.next()) {
                System.out.println("listID = " + rset.getInt("listID") +
                        "\ntaskID = " + rset.getInt("taskID") +
                        "\ntaskName = " + rset.getString("taskName") +
                        "\nstatus = " + rset.getString("status") +
                        "\npriority = " + rset.getInt("priority") +
                        "\ndueDate = " + rset.getString("dueDate") +
                        "\ncolorCode = " + rset.getInt("colorCode"));
                System.out.print('\n');
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
