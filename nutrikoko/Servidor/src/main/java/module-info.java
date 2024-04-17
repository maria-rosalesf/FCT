module org.example.servidor {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.example.servidor to javafx.fxml;
    exports org.example.servidor;
}