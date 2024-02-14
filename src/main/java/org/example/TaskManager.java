package org.example;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskManager {
    //Database location
    private static final String conURL = "jdbc:postgresql://localhost:5432/postgres";

    //Exception for driver
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("PostgreSQL JDBC Driver not found. Include it in your project.", e);
        }
    }

    //Creating a connection with database
    // Method: connect
    // Description: Creates a connection with the PostgreSQL database.
    // Parameters: None
    // Returns: Connection object representing the connection to the database.
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(conURL, "postgres", "1");
    }

    // Method: addTask
    // Description: Adds a task to the database.
    // Parameters: Task object representing the task to be added.
    // Returns: Void
    public static void addTask(Task task) {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "INSERT INTO tasks (title, description, status) VALUES (?, ?, ?) RETURNING id")) {

            preparedStatement.setString(1, task.getTitle());
            preparedStatement.setString(2, task.getDescription());
            preparedStatement.setString(3, task.getStatus());

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                task.setId(resultSet.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method: getTasksByStatus
    // Description: Retrieves tasks from the database based on their status.
    // Parameters: String status representing the status of tasks to retrieve.
    // Returns: List of Task objects that match the provided status.
    public static List<Task> getTasksByStatus(String status) {
        List<Task> tasks = new ArrayList<>();
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, title, description, status FROM tasks WHERE status = ?")) {

            preparedStatement.setString(1, status);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Task task = new Task(resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getString("status"));
                task.setId(resultSet.getInt("id"));
                tasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    // Method: updateTaskStatus
    public static void updateTaskStatus(int taskId, String newStatus) {
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "UPDATE tasks SET status = ? WHERE id = ?")) {

            preparedStatement.setString(1, newStatus);
            preparedStatement.setInt(2, taskId);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}