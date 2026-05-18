package org.example.task;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;

public class TaskApp extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        BorderPane borderPane = new BorderPane();

        TableView<Task> tableView = new TableView<>();

        TableColumn<Task, Integer> idColumn = new TableColumn<>("ID");


        Text titleText = new Text("Task Planner");

        TextField textField = new TextField();
        Text description = new Text("Description");
        TextField textFieldDescription = new TextField();

        Text textComplete = new Text("Completed");
        CheckBox checkBox = new CheckBox("Включить уведомления");
//        checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
//            if (newValue) {
//                System.out.println("Галочка установлена!");
//            } else {
//                System.out.println("Галочка снята.");
//            }
//        });

        DatePicker datePicker = new DatePicker();
        // Устанавливаем дату по умолчанию (например, текущую)
        datePicker.setValue(LocalDate.now());

//        @Override
//        public void updateItem(LocalDate date, boolean empty) {
//            super.updateItem(date, empty);
//            // Отключаем дни, которые идут до сегодняшнего
//            if (date.isBefore(LocalDate.now())) {
//                setDisable(true);
//                setStyle("-fx-background-color: #ffc0c0;"); // Подсветка недоступных дат
//            }
//        }
//    });
//
//    // 3. Создаем кнопку и обработчик события
//    Label label = new Label("Выберите дату:");
//    Button button = new Button("Показать выбранное");
//        button.setOnAction(e -> {
//        LocalDate selectedDate = datePicker.getValue();
//        if (selectedDate != null) {
//            label.setText("Вы выбрали: " + selectedDate.toString());
//            System.out.println("Выбранная дата: " + selectedDate);
//        } else {
//            label.setText("Дата не выбрана");
//        }
//    });

        Button createTask = new Button("Create new task");
        Button updateTask = new Button("Update task");
        Button deleteTask = new Button("Delete task");
        Button clearFields = new Button("Clear fields");
        Button exitButton = new Button("Exit");

        Button[] buttons = {
                createTask,
                updateTask,
                deleteTask,
                clearFields,
                exitButton
        };

        for (Button button : buttons) {
            button.setPrefWidth(150);
        }


        VBox rootLayout = new VBox(10);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.getChildren().addAll(titleText,
                textField,
                description,
                textFieldDescription,
                textComplete,
                checkBox,
                datePicker,
                createTask,
                updateTask,
                deleteTask,
                clearFields,
                exitButton);

        borderPane.setRight(rootLayout);
        borderPane.setLeft(tableView);






        exitButton.setOnAction(event -> stage.close());


        Scene scene = new Scene(borderPane, 1000, 720);
        stage.setTitle("BANK SYSTEM");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch();

  }
}