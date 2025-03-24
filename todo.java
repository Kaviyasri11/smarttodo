import java.io.*;
import java.util.*;

class Task implements Serializable {
    int id;
    String description;
    String priority;
    String deadline;
    boolean completed;

    Task(int id, String description, String priority, String deadline) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.deadline = deadline;
        this.completed = false;
    }

    public String toString() {
        return "ID: " + id + ", Task: " + description + ", Priority: " + priority + ", Deadline: " + deadline + ", Completed: " + (completed ? "Yes" : "No");
    }
}

public class SmartToDoList {
    private static final String FILE_NAME = "tasks.dat";

    public static void main(String[] args) {
        ArrayList<Task> tasks = loadTasks();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nSmart To-Do List");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Mark Task as Completed");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Task ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter Task Description: ");
                    String desc = scanner.nextLine();
                    System.out.print("Enter Priority (High/Medium/Low): ");
                    String priority = scanner.nextLine();
                    System.out.print("Enter Deadline (YYYY-MM-DD): ");
                    String deadline = scanner.nextLine();
                    tasks.add(new Task(id, desc, priority, deadline));
                    saveTasks(tasks);
                    System.out.println("Task added successfully!");
                    break;
                
                case 2:
                    System.out.println("\nTo-Do List:");
                    tasks.sort(Comparator.comparing(t -> t.deadline)); // Sort by deadline
                    for (Task t : tasks) {
                        System.out.println(t);
                    }
                    break;
                
                case 3:
                    System.out.print("Enter Task ID to mark as completed: ");
                    int taskId = scanner.nextInt();
                    boolean found = false;
                    for (Task t : tasks) {
                        if (t.id == taskId) {
                            t.completed = true;
                            saveTasks(tasks);
                            System.out.println("Task marked as completed!");
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Task not found!");
                    }
                    break;
                
                case 4:
                    System.out.println("Exiting program...");
                    break;
                
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 4);
        
        scanner.close();
    }

    private static void saveTasks(ArrayList<Task> tasks) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(tasks);
        } catch (IOException e) {
            System.out.println("Error saving tasks: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private static ArrayList<Task> loadTasks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (ArrayList<Task>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading tasks: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
