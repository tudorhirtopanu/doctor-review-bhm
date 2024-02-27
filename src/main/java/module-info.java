module uk.ac.brunel {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens uk.ac.brunel to javafx.fxml;
    opens uk.ac.brunel.controllers to javafx.fxml;
    
    exports uk.ac.brunel;
}