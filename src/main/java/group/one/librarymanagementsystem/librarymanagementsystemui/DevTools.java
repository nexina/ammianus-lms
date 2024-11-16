package group.one.librarymanagementsystem.librarymanagementsystemui;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;

public class DevTools {
    Database db = new Database();

    public static String CONFIG_LOCATION =  Paths.get("").toAbsolutePath() + "/config.json";

    public static boolean configFileExists() {
        File configFile = new File(CONFIG_LOCATION);

        return configFile.exists();
    }

    String LoginScreen(int x, String username, String password)
    {
        List<Object[]> res = null;
        String role;

        if(x == 0)
        {
            role = "Librarian";

        }else if(x == 1)
        {
            role = "Library Staff";
        }else
        {
            role = "Patron";
        }

        try
        {
            String selectQuery = "SELECT * "
                    + "FROM users "
                    + "WHERE role = '" + role + "' "
                    + "AND username = '" + username + "' "
                    + "AND password = '" + password + "';";
            res = db.queryView(selectQuery);
        }catch (Exception e)
        {
            System.out.println(e.getMessage());
            return "";
        }

        if (res == null || res.isEmpty()) {
            return "";
        }

        return username;
    }

    void createMainUI(Stage stage, String filename, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filename));
        Scene scene = new Scene(fxmlLoader.load(), 960, 560);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void createNewUI(String filename, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filename));
        Scene scene = new Scene(fxmlLoader.load(), 960, 560);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void createNewUI(String filename, String title, int w, int h) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filename));
        Scene scene = new Scene(fxmlLoader.load(), w, h);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    ObservableList<BookListItem> convertBookToObservableList(List<Object[]> list) {
        ObservableList<BookListItem> observableList = FXCollections.observableArrayList();
        for (Object[] objArray : list) {
            BookListItem bl = new BookListItem();
            int i = 0;
            bl.id = new SimpleStringProperty(objArray[0].toString());
            bl.title = new SimpleStringProperty((String) objArray[1]);
            bl.author = new SimpleStringProperty((String) objArray[2]);
            bl.publisher = new SimpleStringProperty((String) objArray[3]);
            bl.category = new SimpleStringProperty((String) objArray[4]);
            bl.available = new SimpleStringProperty(((Boolean) objArray[5]) ? "YES" : "NO");
            if(objArray[6] == null)
            {
                bl.borrowed = new SimpleStringProperty("-- NONE --");
            }else
            {
                bl.borrowed = new SimpleStringProperty((String) objArray[6]);
            }

            if(objArray[7] == null || ((String) objArray[7]).isEmpty())
            {
                bl.bookshelf = new SimpleStringProperty("-- NOT ASSIGNED --");
            }else
            {
                bl.bookshelf = new SimpleStringProperty((String) objArray[7]);
            }

            if(objArray[8] == null || (Integer) objArray[8] == 0)
            {
                bl.shelf = new SimpleStringProperty("-- NOT ADDED --");
            }else
            {
                bl.shelf = new SimpleStringProperty(objArray[8].toString());
            }

            observableList.add(bl);
        }
        return observableList;
    }
    ObservableList<UserListItem> convertUserToObservableList(List<Object[]> list) {
        ObservableList<UserListItem> observableList = FXCollections.observableArrayList();
        for (Object[] objArray : list) {
            UserListItem ul = new UserListItem();
            ul.id = new SimpleStringProperty(objArray[0].toString());
            ul.role = new SimpleStringProperty((String) objArray[1]);
            ul.name = new SimpleStringProperty((String) objArray[2]);
            ul.email = new SimpleStringProperty((String) objArray[3]);
            ul.username = new SimpleStringProperty((String) objArray[4]);
            ul.password =  new SimpleStringProperty((String) objArray[5]);
            ul.fine =  new SimpleStringProperty(objArray[6].toString());

            observableList.add(ul);
        }
        return observableList;
    }

    public static class BookListItem {
        StringProperty id = new SimpleStringProperty("");
        StringProperty title = new SimpleStringProperty("");
        StringProperty author = new SimpleStringProperty("");
        StringProperty publisher = new SimpleStringProperty("");
        StringProperty category = new SimpleStringProperty("");
        StringProperty available = new SimpleStringProperty("");
        StringProperty borrowed = new SimpleStringProperty("");
        StringProperty bookshelf = new SimpleStringProperty("");
        StringProperty shelf = new SimpleStringProperty("");

        // Getters for JavaFX properties
        public StringProperty idProperty() {
            return id;
        }

        public StringProperty titleProperty() {
            return title;
        }

        public StringProperty authorProperty() {
            return author;
        }

        public StringProperty publisherProperty() {
            return publisher;
        }

        public StringProperty categoryProperty() {
            return category;
        }

        public StringProperty availableProperty() {
            return available;
        }

        public StringProperty borrowedProperty() {
            return borrowed;
        }

        public StringProperty bookshelfProperty() {
            return bookshelf;
        }

        public StringProperty shelfProperty() {
            return shelf;
        }
    }

    public static class UserListItem {
        StringProperty id = new SimpleStringProperty("");
        StringProperty role = new SimpleStringProperty("");
        StringProperty name = new SimpleStringProperty("");
        StringProperty email = new SimpleStringProperty("");

        StringProperty username = new SimpleStringProperty("");
        StringProperty password = new SimpleStringProperty("");
        StringProperty fine = new SimpleStringProperty("");

        // Getters for JavaFX properties
        public StringProperty idProperty() {
            return id;
        }

        public StringProperty roleProperty() {
            return role;
        }

        public StringProperty nameProperty() {
            return name;
        }

        public StringProperty emailProperty() {
            return email;
        }

        public StringProperty usernameProperty() {
            return username;
        }

        public StringProperty passwordProperty() {
            return password;
        }

        public StringProperty fineProperty() {
            return fine;
        }
    }

    public void updateTime(Label text)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM yyyy | hh:mm a");
        LocalDateTime now = LocalDateTime.now();
        String formattedDateTime = now.format(formatter);
        text.setText(formattedDateTime);
    }

    public int createConfig(JSONObject jo)
    {
        try (FileWriter file = new FileWriter(CONFIG_LOCATION)) {
            System.out.println(Paths.get("").toAbsolutePath());
            file.write(jo.toString(4));
            file.flush();
            return 1;
        } catch (IOException e) {
            return -1;
        }
    }

    public static Object getConfig(String s){
        try {
            String content = new String(Files.readAllBytes(Paths.get(CONFIG_LOCATION)));
            JSONObject configObj = new JSONObject(content);

            return configObj.get(s);
        }catch (IOException e)
        {
            return null;
        }
    }

    public static void setConfig(String key,  String value){
        try {
            String content = new String(Files.readAllBytes(Paths.get(CONFIG_LOCATION)));
            JSONObject configObj = new JSONObject(content);

            configObj.put(key, value);

            Files.write(Paths.get(CONFIG_LOCATION), configObj.toString(4).getBytes());


        }catch (IOException e)
        {

        }
    }

    public static void setConfig(String key,  int value){
        try {
            String content = new String(Files.readAllBytes(Paths.get(CONFIG_LOCATION)));
            JSONObject configObj = new JSONObject(content);

            configObj.put(key, value);

            Files.write(Paths.get(CONFIG_LOCATION), configObj.toString(4).getBytes());


        }catch (IOException e)
        {

        }
    }
}


