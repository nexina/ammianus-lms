package group.one.librarymanagementsystem.librarymanagementsystemui;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MainUI extends Application {

    DevTools devTools = new DevTools();
    @Override
    public void start(Stage stage) throws IOException {
        if(DevTools.configFileExists())
        {
            devTools.createMainUI(stage, "loginui.fxml", "Library Management System");
        }else
        {
            devTools.createNewUI("firsttime.fxml", "First Time on Ammianus", 600, 662);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}