module com.cs420.cs420 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.cs420.cs420 to javafx.fxml;
    exports com.cs420.cs420;
}