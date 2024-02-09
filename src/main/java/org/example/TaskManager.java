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
}