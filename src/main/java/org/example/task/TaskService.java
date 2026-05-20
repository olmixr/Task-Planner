package org.example.task;

import org.example.task.database.DatabaseConnection;

import java.sql.SQLException;

public class TaskService {

    public void startSql() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        DatabaseConnection.statement.executeUpdate("use taskplanner");

    }


}
