package org.example;
import java.util.List;
import java.util.Scanner;

public class KanbanApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //Main menu
        while (true) {
            System.out.println("Options:");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks (To Do)");
            System.out.println("3. View Tasks (In Progress)");
            System.out.println("4. View Tasks (Done)");
            System.out.println("5. Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    addTask(scanner);
                    break;
                case 2:
                    viewTasks("To Do");
                    break;
                case 3:
                    viewTasks("In Progress");
                    break;
                case 4:
                    viewTasks("Done");
                    break;
                case 5:
                    System.out.println("Exiting the Kanban. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    //Function to add the task
    private static void addTask(Scanner scanner) {
        System.out.print("Enter task title: ");
        String title = scanner.nextLine();

        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        System.out.println("Choose task status: ");
        System.out.println("1. To Do");
        System.out.println("2. In Progress");
        System.out.println("3. Done");
        System.out.print("Select an option: ");
        int statusOption = scanner.nextInt();
        scanner.nextLine();

        String status;
        switch (statusOption) {
            case 1:
                status = "To Do";
                break;
            case 2:
                status = "In Progress";
                break;
            case 3:
                status = "Done";
                break;
            default:
                status = "To Do"; //Default status is "To Do" for invalid options
        }

        Task task = new Task(title, description, status);
        TaskManager.addTask(task);
        System.out.println("Task added successfully!");
    }

    //Function to view tasks by status
    private static void viewTasks(String status) {
        System.out.println("Tasks in " + status + ":");
        List<Task> tasks = TaskManager.getTasksByStatus(status);
        for (Task task : tasks) {
            System.out.println(task);
        }
    }
}