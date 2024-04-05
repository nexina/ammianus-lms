module group.one.librarymanagementsystem.librarymanagementsystemui {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.sql;

    opens group.one.librarymanagementsystem.librarymanagementsystemui to javafx.fxml;
    exports group.one.librarymanagementsystem.librarymanagementsystemui;
}