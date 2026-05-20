module org.example.task {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.task to javafx.fxml, javafx.base;
    exports org.example.task;
}
