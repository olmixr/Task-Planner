package org.example.task;

import javafx.collections.ObservableList;
import org.example.task.database.DatabaseConnection;

import java.sql.SQLException;
import java.time.LocalDate;

public class TaskService {

    public void startSql() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        DatabaseConnection.statement.executeUpdate("use taskplanner");

    }

}
