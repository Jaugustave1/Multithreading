package backEnd;
import java.sql.*;
import java.util.logging.Logger;


/*
TODO: Figure out how to store multiple userIDs under collaboration
*/
// This class handles all table Queries/Creation of data

public class sqlDB {
    private Connection conn;

    public sqlDB() {
        this.openConnection();
    }

    /* =====================================
              Connection Creation
    ===================================== */

    // Could Potentially Use 2 accounts
    // One for reading only (Prevents SQL Injection to modify table)
    // Second for Read/Write
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

    // DELETE LATER, THIS SHOULD NOT PASS INTO PUBLIC BUILD
    // DELETE LATER, THIS SHOULD NOT PASS INTO PUBLIC BUILD
    // DELETE LATER, THIS SHOULD NOT PASS INTO PUBLIC BUILD
    // DELETE LATER, THIS SHOULD NOT PASS INTO PUBLIC BUILD
    public Connection getConnection() { return this.conn; }

    /* =====================================
              User Table Functions
    ===================================== */

    // Creates new User in database
    // Input: Username, password
    public void register(String username, String password) {
        try {
            String query = "INSERT INTO user (name, password, email) VALUES (?, ?, NULL)";
            // Prepared statement to prevent SQL Injection
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Overloading parameter if email is provided
    public void register(String username, String password, String email) {
        try {
            String query = "INSERT INTO user (name, password, email) VALUES (?, ?, ?)";
            // Prepared statement to prevent SQL Injection
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // Checks User/Password and returns userID if Valid
    // Input: name, password
    // Returns -1 if Invalid
    public int login( String name, String pass ) {
        try {
            String query = "Select userID FROM user WHERE name = ? AND PASSWORD = ?";
            // Prepared statement to prevent SQL Injection
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            pstmnt.setString(1, name);
            pstmnt.setString(2, pass);

            ResultSet rset = pstmnt.executeQuery();
            rset.next();
            return rset.getInt("userID");

        } catch (Exception e) {
            System.out.println(e);
        }
        // If no data is returned, return -1
        return -1;
    }

    // Updates name, password, and email for a given userID
    // Input: userID (int), name, password, email
    public void updateProfile(int userID, String name, String password, String email) {
        try {
            String query = "UPDATE user SET name = ?, password = ?, email = ? WHERE userID = ?";
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            pstmnt.setString(1, name);
            pstmnt.setString(2, password);
            pstmnt.setString(3, email);
            pstmnt.setInt(4, userID);
            pstmnt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /* =====================================
              Task Table Functions
    ===================================== */

    // Creates task in database
    // Input: userID, listID, taskName, priority, duedate YYYY-MM-DD HH:MM:SS, colorCode (Range of 0 - 16777215)
    // Remember to convert colorCode back into Hex later [0x000000 - 0xFFFFFF]
    public void createTask(int userID, int listID, String name, String status, String priority, String dueDate, int colorCode) {
        try {
            String query = "INSERT INTO task (userID, listID, taskName, status, priority, dueDate, colorCode) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            pstmnt.setInt(1, userID);
            pstmnt.setInt(2, listID);
            pstmnt.setString(3, name);
            pstmnt.setString(4, status);
            pstmnt.setString(5, priority);
            pstmnt.setString(6, dueDate);
            pstmnt.setInt(7, colorCode);
            pstmnt.executeUpdate();
        } catch ( Exception e ) {
            System.out.println(e);
        }
    }

    // Updates task with taskID with given information
    // Inputs: taskID, name, status, priority, dueDate, colorCode
    public void updateTask(int taskID, String name, String status, String priority, String dueDate, int colorCode) {
        try {
            String query = "UPDATE task SET taskName = ?, status = ?, priority = ?, dueDate = ?, colorCode = ? WHERE taskID = ?";
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            pstmnt.setString(1, name);
            pstmnt.setString(2, status);
            pstmnt.setString(3, priority);
            pstmnt.setString(4, dueDate);
            pstmnt.setInt(5, colorCode);
            pstmnt.setInt(6, taskID);
            pstmnt.executeUpdate();
        } catch ( Exception e ) {
            System.out.println(e);
        }

    }

    public ResultSet getAllTasks() {
        try {
            String query = "SELECT * FROM task";
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            return pstmnt.executeQuery();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


    /* =====================================
           Task List Table Functions
    ===================================== */

    // Create a new list with title "title" and list owner
    // Inputs: userID (Integer), String "title"
    public void createList(int ownerID, String title) {
        try {
            String query = "INSERT INTO tasklist (ownerID, title) VALUES (?, ?)";
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            pstmnt.setInt(1, ownerID);
            pstmnt.setString(2, title);
            pstmnt.executeUpdate();
        } catch ( Exception e ) {
            System.out.println(e);
        }

    }

    // Updates title of list
    // input: Integer listID, Integer OwnerID, String title
    // todo: Keep ability to switch the OwnerID?
    public void updateList(int ListID, int ownerID, String title) {
        try {
            String query = "UPDATE tasklist SET title = ?, ownerID = ? WHERE listID = ?";
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            pstmnt.setString(1, title);
            pstmnt.setInt(2, ownerID);
            pstmnt.setInt(3, ListID);
            pstmnt.executeUpdate();
        } catch ( Exception e ) {
            System.out.println(e);
        }
    }

    // Deletes list with specific list ID
    // input: Integer listID
    public void deleteList(int ListID) {
        try {
            String query = "DELETE FROM tasklist WHERE listID = ?";
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            pstmnt.setInt(1, ListID);
            pstmnt.executeUpdate();
        } catch ( Exception e ) {
            System.out.println(e);
        }
    }

    /*
    todo:
        AddCollaborator (Figure out how to make collaborations work)
        popTask (Figure out what exactly we're searching)
        searchTask (Same as above)
        iterateTasks (Same as above)
    */

    /* =====================================
          Collaboration Table Functions
    ===================================== */


    public void addCollaborator(int collaborationID, int userID) {
        String query = "INSERT INTO collabUsers (collaborationID, userID) VALUES (?, ?)"; // Adds Collaborators
        try (PreparedStatement ps = conn.prepareStatement(query)) { // Protects against SQL Injections
            ps.setInt(1, collaborationID); // CollabID is assigned to 1st ?/placeholder
            ps.setInt(2, userID);   // UserID is assigned to 2nd ?/placeholder
            ps.executeUpdate();     // both queries are inserted into collabUsers ~ links userID to Collaboration
        } catch (Exception e) {
            System.out.println("Error adding collaborator: " + e.getMessage());  // displays log if error/issue adding collaborator
        }
    }

    public void removeCollaborator(int collaborationID, int userID) {
        String query = "DELETE FROM collabUsers WHERE collaborationID = ? AND userID = ?";  // Deletes Collaborators
        try (PreparedStatement ps = conn.prepareStatement(query)) { // Protects against SQL Injections
            ps.setInt(1, collaborationID);
            ps.setInt(2, userID);
            ps.executeUpdate();     // deletes users Collaboration
        } catch (Exception e) {
            System.out.println("Error removing collaborator: " + e.getMessage()); // displays log if error/issue deleting collaborator
        }
    }












    /*
    todo:
        Figure out how exactly to make this work in MySQL
        https://stackoverflow.com/questions/18605669/how-to-store-multiple-values-in-single-column-where-use-less-memory
     */


    /* =====================================
          Notification Table Functions
    ===================================== */

    /*
    todo:
        Figure out how we're sending notifications
     */


    /* =====================================
          Calendar Table Functions
    ===================================== */

    /*
    todo:
        Create/Schedule notification
        Check upcoming events
     */


    /* =====================================
              Additional Functions
    ===================================== */

    // Get all tasks assigned to userID
    /*
    TODO: Modify output, return name/user/status/priority/etc
     Determine method of returning (Array, Tuple, Table, Iterable, etc.)
     Modify query to also search if task's taskList is in a collaboration user has access to
    */
    public ResultSet getTasks(int userID) {
        try { 
            String query = """
                SELECT t.*    -- selects /columns/tasks from the task table
                FROM task t    -- pulls from task
                JOIN tasklist tl ON t.listID = tl.listID    -- This will connect the tasks to their task list
                LEFT JOIN collaboration c ON tl.listID = c.listID -- this will link task lists to collaborations
                LEFT JOIN collabUsers cu ON c.collaborationID = cu.collaborationID -- this will connect collaborators to the task lists
                WHERE cu.userID = ? OR tl.ownerID = ?;        --  returns tasks to users listed as collaborators. and tasks are returned to the user that owns the tasklist 
            """;
    
            // Prepare statement to prevent SQL Injection
            PreparedStatement pstmnt = this.conn.prepareStatement(query);
            pstmnt.setInt(1, userID);
            pstmnt.setInt(2, userID);
    
            return pstmnt.executeQuery();
        } catch (Exception e) {
            System.out.println("Error fetching tasks: " + e.getMessage()); 
            return null;
        }
    }
}
