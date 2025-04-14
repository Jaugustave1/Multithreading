public class Main {

    public static void main(String[] args) {
        sqlDB db = new sqlDB();

        // check if login works correctly
        int id;
        id = db.checkLogin("Jalan", "testpass");
        System.out.println("ID: " + id);

        System.out.print('\n');
        // check if task checker works correctly
        db.getTasks(1);

        // Check if get tasks works correctly
        db.closeConnection();


    }

}
