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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class DevTools {
    Database db = new Database();

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
//    @FXML
//    void createUI(ActionEvent event, String filename, String title, int w, int h) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(filename));
//        Scene scene = new Scene(fxmlLoader.load(), w, h);
//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        stage.setTitle(title);
//        stage.setScene(scene);
//        stage.show();
//    }

    ObservableList<BookListItem> convertBookToObservableList(List<Object[]> list) {
        ObservableList<BookListItem> observableList = FXCollections.observableArrayList();
        for (Object[] objArray : list) {
            BookListItem bl = new BookListItem();
            int i = 0;
            for (Object value : objArray) {
                switch(i)
                {
                    case 0:
//                        bl.setId((Integer) value);
                        bl.id = new SimpleStringProperty(value.toString());
                        break;
                    case 1:
//                        bl.setTitle((String) value);
                        bl.title = new SimpleStringProperty((String) value);
                        break;
                    case 2:
//                        bl.setAuthor((String) value);
                        bl.author = new SimpleStringProperty((String) value);
                        break;
                    case 3:
//                        bl.setPublisher((String) value);
                        bl.publisher = new SimpleStringProperty((String) value);
                        break;
                    case 4:
//                        bl.setCategory((String) value);
                        bl.category = new SimpleStringProperty((String) value);
                        break;
                    case 5:
//                        bl.setAvailable((Boolean)value);
                        bl.available = new SimpleStringProperty(((Boolean) value) ? "YES" : "NO");
                        break;
                    case 6:
//                        bl.setBorrowed((String)value);
                        if(value == null || (Integer)value == 0)
                        {
                            bl.borrowed = new SimpleStringProperty("-- NONE --");
                        }else
                        {
                            bl.borrowed = new SimpleStringProperty((String) value);
                        }

                        break;
                    case 7:
//                        bl.setBookshelf((String) value);
                        if(value == null || ((String) value).isEmpty())
                        {
                            bl.bookshelf = new SimpleStringProperty("-- NOT AVAILABLE --");
                        }else
                        {
                            bl.bookshelf = new SimpleStringProperty((String) value);
                        }

                        break;
                    case 8:
//                        bl.setShelf((Integer) value);
                        if(value == null || (Integer) value == 0)
                        {
                            bl.shelf = new SimpleStringProperty("-- NOT AVAILABLE --");
                        }else
                        {
                            bl.shelf = new SimpleStringProperty(value.toString());
                        }

                        break;
                }

                i++;

            }
            observableList.add(bl);
        }
        return observableList;
    }
    ObservableList<UserListItem> convertUserToObservableList(List<Object[]> list) {
        ObservableList<UserListItem> observableList = FXCollections.observableArrayList();
        for (Object[] objArray : list) {
            UserListItem ul = new UserListItem();
            int i = 0;
            for (Object value : objArray) {
                switch(i)
                {
                    case 0:
//                        bl.setId((Integer) value);
                        ul.id = new SimpleStringProperty(value.toString());
                        break;
                    case 1:
//                        bl.setTitle((String) value);
                        ul.role = new SimpleStringProperty((String) value);
                        break;
                    case 2:
//                        bl.setAuthor((String) value);
                        ul.name = new SimpleStringProperty((String) value);
                        break;
                    case 3:
//                        bl.setPublisher((String) value);
                        ul.email = new SimpleStringProperty((String) value);
                        break;
                    case 4:
//                        bl.setCategory((String) value);
                        ul.username = new SimpleStringProperty((String) value);
                        break;
                    case 5:
//                        bl.setAvailable((Boolean)value);
                        ul.password =  new SimpleStringProperty((String) value);
                        break;
                    case 6:
                        ul.fine =  new SimpleStringProperty(value.toString());
                        break;

                }

                i++;

            }
            observableList.add(ul);
        }
        return observableList;
    }

//    public class BookListItem {
//        private String id;
//        private String title;
//        private String author;
//        private String publisher;
//        private String category;
//        private String available;
//        private String borrowed;
//        private String bookshelf;
//        private String shelf;
//
//        // Getters
//        public String getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = String.valueOf(id);
//        }
//
//        public String getTitle() {
//            return title;
//        }
//
//        public void setTitle(String title) {
//            this.title = title;
//        }
//
//        public String getAuthor() {
//            return author;
//        }
//
//        public void setAuthor(String author) {
//            this.author = author;
//        }
//
//        public String getPublisher() {
//            return publisher;
//        }
//
//        public void setPublisher(String publisher) {
//            this.publisher = publisher;
//        }
//
//        public String getCategory() {
//            return category;
//        }
//
//        public void setCategory(String category) {
//            this.category = category;
//        }
//
//        public String isAvailable() {
//            return available;
//        }
//
//        public void setAvailable(Boolean available) {
//            if(available)
//            {
//                this.available = "YES";
//            }else
//            {
//                this.available = "NO";
//            }
//        }
//
//        public String getBorrowed() {
//            return borrowed;
//        }
//
//        public void setBorrowed(String borrowed) {
//            this.borrowed = borrowed;
//        }
//
//        public String getBookshelf() {
//            return bookshelf;
//        }
//
//        public void setBookshelf(String bookshelf) {
//            this.bookshelf = bookshelf;
//        }
//
//        public String getShelf() {
//            return shelf;
//        }
//
//        public void setShelf(int shelf) {
//            this.shelf = String.valueOf(shelf);
//        }
//
//
//    }
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
}


