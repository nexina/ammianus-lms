package group.one.librarymanagementsystem.librarymanagementsystemui;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainUI extends Application {

    DevTools devTools = new DevTools();
    @Override
    public void start(Stage stage) throws IOException {
       devTools.createMainUI(stage, "loginui.fxml", "Library Management System");
    }

    public static void main(String[] args) {
        launch(args);
    }
}