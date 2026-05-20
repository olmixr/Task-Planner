package org.example.task;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class TaskApp extends Application {

    private static final double FORM_WIDTH = 280;
    private static final double BUTTON_HEIGHT = 38;
    private static final String ROOT_STYLE = "-fx-background-color: #f4f6f8;";
    private static final String PANEL_STYLE = "-fx-background-color: white; -fx-border-color: #d8dde3; -fx-border-width: 0 0 0 1;";
    private static final String TITLE_STYLE = "-fx-font-size: 20px; -fx-font-weight: bold;";
    private static final String LABEL_STYLE = "-fx-font-size: 13px; -fx-font-weight: bold;";

    private final TaskService taskService = new TaskService();

    @Override
    public void start(Stage stage) throws IOException, SQLException, ClassNotFoundException {
        taskService.startSql();

        ObservableList<Task> tasks = FXCollections.observableArrayList();
        tasks.add(new Task(1, "Learn JDBC", "Learn how to use JDBC", true, LocalDate.of(2025, 5, 20)));

        TableView<Task> tableView = createTaskTable(tasks);
        VBox tasksSection = createTasksSection(tableView);
        VBox detailsSection = createDetailsSection(stage);

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(16));
//        root.setStyle(ROOT_STYLE);
        root.setCenter(tasksSection);
        root.setRight(detailsSection);
        BorderPane.setMargin(detailsSection, new Insets(0, 0, 0, 16));

        Scene scene = new Scene(root, 1000, 720);
        stage.setTitle("Task Planner");
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.setScene(scene);
        stage.show();
    }

    private TableView<Task> createTaskTable(ObservableList<Task> tasks) {
        TableColumn<Task, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        idColumn.setPrefWidth(60);

        TableColumn<Task, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setPrefWidth(170);

        TableColumn<Task, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        descriptionColumn.setPrefWidth(280);

        TableColumn<Task, Boolean> statusColumn = new TableColumn<>("Status");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("completed"));
        statusColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Boolean completed, boolean empty) {
                super.updateItem(completed, empty);
                setText(empty || completed == null ? null : completed ? "Completed" : "Not completed");
            }
        });
        statusColumn.setPrefWidth(130);

        TableColumn<Task, LocalDate> deadlineColumn = new TableColumn<>("Deadline");
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("deadline"));
        deadlineColumn.setPrefWidth(120);

        TableView<Task> tableView = new TableView<>(tasks);
        tableView.getColumns().addAll(idColumn, titleColumn, descriptionColumn, statusColumn, deadlineColumn);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        tableView.setPlaceholder(new Label("No tasks yet"));

        return tableView;
    }

    private VBox createTasksSection(TableView<Task> tableView) {
        Label sectionTitle = new Label("Tasks");
        sectionTitle.setStyle(TITLE_STYLE);

        Button allTasks = createFilterButton("All tasks");
        Button completedTasks = createFilterButton("Completed tasks");
        Button notCompletedTasks = createFilterButton("Not completed tasks");

        HBox filters = new HBox(10, allTasks, completedTasks, notCompletedTasks);
        filters.setAlignment(Pos.CENTER_LEFT);

        VBox section = new VBox(12, sectionTitle, filters, tableView);
        section.setAlignment(Pos.TOP_LEFT);
        section.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        VBox.setVgrow(tableView, Priority.ALWAYS);

        return section;
    }

    private VBox createDetailsSection(Stage stage) {
        Label sectionTitle = new Label("Task Details");
        sectionTitle.setStyle(TITLE_STYLE);

        Label titleLabel = createFieldLabel("Title");
        TextField titleField = new TextField();
        titleField.setPromptText("Task title");
        stretchControl(titleField);

        Label descriptionLabel = createFieldLabel("Description");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Task description");
        descriptionArea.setWrapText(true);
        descriptionArea.setPrefRowCount(5);
        stretchControl(descriptionArea);

        CheckBox completedCheckBox = new CheckBox("Task is completed");

        Label deadlineLabel = createFieldLabel("Deadline");
        DatePicker deadlinePicker = new DatePicker(LocalDate.now());
        stretchControl(deadlinePicker);

        Button createTask = createActionButton("Create task", "#2f9e44");
        Button updateTask = createActionButton("Update task", "#1c7ed6");
        Button deleteTask = createActionButton("Delete task", "#e03131");
        Button clearFields = createActionButton("Clear fields", "#868e96");
        Button exitButton = createActionButton("Exit", "#495057");

        clearFields.setOnAction(event -> {
            titleField.clear();
            descriptionArea.clear();
            completedCheckBox.setSelected(false);
            deadlinePicker.setValue(LocalDate.now());
        });
        exitButton.setOnAction(event -> stage.close());

        VBox section = new VBox(
                10,
                sectionTitle,
                titleLabel,
                titleField,
                descriptionLabel,
                descriptionArea,
                createFieldLabel("Status"),
                completedCheckBox,
                deadlineLabel,
                deadlinePicker,
                createTask,
                updateTask,
                deleteTask,
                clearFields,
                exitButton
        );
        section.setAlignment(Pos.TOP_LEFT);
        section.setPadding(new Insets(16));
        section.setPrefWidth(FORM_WIDTH);
        section.setMinWidth(FORM_WIDTH);
        section.setMaxWidth(FORM_WIDTH);
        section.setStyle(PANEL_STYLE);

        return section;
    }

    private Label createFieldLabel(String text) {
        Label label = new Label(text);
        label.setStyle(LABEL_STYLE);
        return label;
    }

    private Button createFilterButton(String text) {
        Button button = new Button(text);
        button.setMinHeight(32);
        button.setStyle("-fx-font-size: 13px;");
        return button;
    }

    private Button createActionButton(String text, String color) {
        Button button = new Button(text);
        button.setPrefHeight(BUTTON_HEIGHT);
        button.setMaxWidth(Double.MAX_VALUE);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-font-size: 14px;");
        return button;
    }

    private void stretchControl(Control control) {
        control.setMaxWidth(Double.MAX_VALUE);
    }

    public static void main(String[] args) {
        launch();
    }
}
