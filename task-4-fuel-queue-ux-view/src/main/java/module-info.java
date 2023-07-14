module com.example.testux {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.testux to javafx.fxml;
    exports com.example.testux;
}

//