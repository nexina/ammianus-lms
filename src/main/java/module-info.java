module group.one.librarymanagementsystem.librarymanagementsystemui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;
    requires jdk.jshell;
    requires org.json;

    opens group.one.librarymanagementsystem.librarymanagementsystemui to javafx.fxml;
    exports group.one.librarymanagementsystem.librarymanagementsystemui;
}