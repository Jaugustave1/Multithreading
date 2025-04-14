import java.sql.*;

public class sqlDB {
    private Connection conn;

    public sqlDB() {
        this.openConnection();
    }

    private void openConnection() {
        try {
            // Create mySQL Connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/taskproject", "admin", "TestPassword1");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void closeConnection() {
        try {
            this.conn.close();
            System.out.println("Connection closed");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Checks User/Password and returns userID if Valid
    // Returns -1 if Invalid
    public int checkLogin( String name, String pass) {
        try {
            String query = "Select userID FROM user WHERE name = ? AND PASSWORD = ?";
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            pstmnt.setString(1, name);
            pstmnt.setString(2, pass);

            ResultSet rset = pstmnt.executeQuery();
            return rset.getInt("userID");

        } catch (Exception e) {
            System.out.println(e);
        }
        // If no data is returned, return -1
        return -1;
    }


    // Get all tasks assigned to userID
    public void getTasks(int userID) {

        try {
            String query = "SELECT * FROM task WHERE userID = ? ";
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            pstmnt.setInt(1, userID);

            ResultSet rset = pstmnt.executeQuery("select * from task where userID = " + userID);
            while (rset.next()) {
                System.out.println("taskID = " + rset.getInt(8) +
                        "\nuserID = " + rset.getInt(1) +
                        "\nlistID = " + rset.getInt(2) +
                        "\ntaskname = " + rset.getString(3) +
                        "\nstatus = " + rset.getString(4) +
                        "\npriority = " + rset.getInt(5) +
                        "\ndueDate = " + rset.getString(6) + // This sketchy
                        "\ncolorCode = " + rset.getInt(7)
                );
                System.out.print('\n');
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}
