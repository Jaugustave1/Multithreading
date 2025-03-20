public class MultiThreadedMergeSortTest {
    public static void main(String[] args) {
        Task[] tasks = {
            new Task("Task1", 3),
            new Task("Task2", 1),
            new Task("Task3", 4),
            new Task("Task4", 2)
        };

        MergeSortParallel sorter = new MergeSortParallel(tasks, 0, tasks.length - 1);
        sorter.run();

        System.out.println("Sorted Tasks by Priority:");
        for (Task task : tasks) {
            System.out.println(task.getName() + " - Priority: " + task.getPriority());
        }
    }
}
