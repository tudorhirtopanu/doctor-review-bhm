module uk.ac.brunel {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens uk.ac.brunel to javafx.fxml;
    
    exports uk.ac.brunel;
}