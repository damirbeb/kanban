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
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(conURL, "postgres", "1");
    }

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

}