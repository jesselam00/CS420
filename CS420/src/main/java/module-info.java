module com.cs420.cs420 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.bytedeco.javacv;


    opens com.cs420.cs420 to javafx.fxml;
    exports com.cs420.cs420;
}