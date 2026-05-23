package org.example.task;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.task.database.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;

public class TaskService {
    private static final String URL = "jdbc:mysql://localhost:3306/taskplanner";
    private static final String USER_NAME = "root";
    private static final String PASSWORD = "root";

    public static boolean MessageDelete = false;
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER_NAME, PASSWORD);
    }
    public ObservableList<Task> getAllTasks() throws SQLException {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, title, description, completed, deadline FROM tasks";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                boolean completed = resultSet.getBoolean("completed");
                Date deadlineDate = resultSet.getDate("deadline");
                LocalDate deadline = deadlineDate == null ? null : deadlineDate.toLocalDate();

                Task task = new Task(id, title, description, completed, deadline);

                tasks.add(task);

            }
        }
        return tasks;
    }

    public ObservableList<Task> CompletedTask() throws SQLException {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, title, description, completed, deadline FROM tasks where completed = 1 ";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                boolean completed = resultSet.getBoolean("completed");
                Date deadlineDate = resultSet.getDate("deadline");
                LocalDate deadline = deadlineDate == null ? null : deadlineDate.toLocalDate();

                Task task = new Task(id, title, description, completed, deadline);

                tasks.add(task);

            }
        }
        return tasks;
    }

    public ObservableList<Task> NotCompletedTask() throws SQLException {
        ObservableList<Task> tasks = FXCollections.observableArrayList();
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT id, title, description, completed, deadline FROM tasks where completed = 0 ";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String description = resultSet.getString("description");
                boolean completed = resultSet.getBoolean("completed");
                Date deadlineDate = resultSet.getDate("deadline");
                LocalDate deadline = deadlineDate == null ? null : deadlineDate.toLocalDate();

                Task task = new Task(id, title, description, completed, deadline);

                tasks.add(task);

            }
        }
        return tasks;
    }


    public void createTaskSQL(String title,String description,boolean completed,LocalDate localDate) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "insert into tasks (title, description, completed, deadline) values (?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1,title);
            preparedStatement.setString(2,description);
            preparedStatement.setBoolean(3,completed);
            preparedStatement.setDate(4, Date.valueOf(localDate));

            preparedStatement.executeUpdate();
        }
    }
    public void deleteTaskSQL(int id) throws SQLException {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM tasks WHERE id = ?;";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setInt(1,id);

            int affectedRows = preparedStatement.executeUpdate();

            MessageDelete = false;
            if (affectedRows >= 1){
                MessageDelete = true;
            }
        }
    }

    public boolean deleteMessage(){
        return MessageDelete;
    }

    public void updateTaskSQL(String title,String description,boolean completed,LocalDate localDate, int id) {

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE tasks SET title = ?,description = ?,completed = ?,deadline = ?  WHERE id = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);


            preparedStatement.setString(1,title);
            preparedStatement.setString(2,description);
            preparedStatement.setBoolean(3,completed);
            preparedStatement.setDate(4, Date.valueOf(localDate));

            preparedStatement.setInt(5,id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}
