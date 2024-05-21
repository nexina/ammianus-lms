package group.one.librarymanagementsystem.librarymanagementsystemui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;

import javafx.scene.Node;
import javafx.scene.control.*;

import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

import java.util.List;

public class LibrarianUI {
    String username;
    Database db = new Database();
    DevTools dt = new DevTools();

    List<Object[]> userList;
    List<Object[]> bookList;
    public static ObservableList<DevTools.BookListItem> bookListViewItems;
    public static ObservableList<DevTools.UserListItem> userListViewItems;

    @FXML
    private TableView librarian_bookList;
    @FXML
    private TableView librarian_userList;
    @FXML
    private Label librarian_bookCount;
    @FXML
    private Label librarian_userCount;
    @FXML
    private Label librarian_fineCount;
    @FXML
    private Label librarian_username;
    @FXML
    private Label librarian_timeanddate;
    @FXML
    private TextField searchField;
    @FXML
    private TextField userSearchField;
    @FXML
    private Button clearSearchField;
    @FXML
    private Button clearUserSearchField;
    @FXML
    private ComboBox librarian_searchTopic;
    @FXML
    private ComboBox librarian_userSearchTopic;

    @FXML
    private TableColumn<DevTools.BookListItem, String> idColumn;
    @FXML
    private TableColumn<DevTools.BookListItem, String> titleColumn;
    @FXML
    private TableColumn<DevTools.BookListItem, String> authorColumn;
    @FXML
    private TableColumn<DevTools.BookListItem, String> publisherColumn;
    @FXML
    private TableColumn<DevTools.BookListItem, String> categoryColumn;
    @FXML
    private TableColumn<DevTools.BookListItem, String> availableColumn;
    @FXML
    private TableColumn<DevTools.BookListItem, String> borrowedColumn;
    @FXML
    private TableColumn<DevTools.BookListItem, String> bookshelfColumn;
    @FXML
    private TableColumn<DevTools.BookListItem, String> shelfColumn;

    @FXML
    private TableColumn<DevTools.UserListItem, String> iduColumn;
    @FXML
    private TableColumn<DevTools.UserListItem, String> roleColumn;
    @FXML
    private TableColumn<DevTools.UserListItem, String> nameColumn;
    @FXML
    private TableColumn<DevTools.UserListItem, String> emailColumn;
    @FXML
    private TableColumn<DevTools.UserListItem, String> usernameColumn;
    @FXML
    private TableColumn<DevTools.UserListItem, String> passwordColumn;
    @FXML
    private TableColumn<DevTools.UserListItem, String> fineColumn;

    public void initData(String name) {
        username = name;
        librarian_username.setText(username);
    }

    void getUserList() {
        userList = db.queryView("SELECT * FROM users");
        userListViewItems = dt.convertUserToObservableList(userList);
    }

    void getBookList() {
        bookList = db.queryView("SELECT * FROM books");
        bookListViewItems = dt.convertBookToObservableList(bookList);
    }

    public void initialize() {
        refreshList();

        librarian_searchTopic.getItems().addAll("Book ID", "Title", "Author", "Publisher", "Category", "Available", "Borrower", "Bookshelf", "Shelf No");
        librarian_userSearchTopic.getItems().addAll("User ID", "Role", "Name", "E-mail", "Username");

        dt.updateTime(librarian_timeanddate);
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(10), event -> dt.updateTime(librarian_timeanddate))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        idColumn.setCellValueFactory(data -> data.getValue().idProperty());
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());
        authorColumn.setCellValueFactory(data -> data.getValue().authorProperty());
        publisherColumn.setCellValueFactory(data -> data.getValue().publisherProperty());
        categoryColumn.setCellValueFactory(data -> data.getValue().categoryProperty());
        availableColumn.setCellValueFactory(data -> data.getValue().availableProperty());
        borrowedColumn.setCellValueFactory(data -> data.getValue().borrowedProperty());
        bookshelfColumn.setCellValueFactory(data -> data.getValue().bookshelfProperty());
        shelfColumn.setCellValueFactory(data -> data.getValue().shelfProperty());

        iduColumn.setCellValueFactory(data -> data.getValue().idProperty());
        roleColumn.setCellValueFactory(data -> data.getValue().roleProperty());
        nameColumn.setCellValueFactory(data -> data.getValue().nameProperty());
        emailColumn.setCellValueFactory(data -> data.getValue().emailProperty());
        usernameColumn.setCellValueFactory(data -> data.getValue().usernameProperty());
        passwordColumn.setCellValueFactory(data -> data.getValue().passwordProperty());
        fineColumn.setCellValueFactory(data -> data.getValue().fineProperty());

        userListViewItems.addListener((ListChangeListener<DevTools.UserListItem>) change -> {
            librarian_userCount.setText(String.valueOf(userListViewItems.size()));
        });
    }

    public void onfindBookTopicSelected(ActionEvent event) {
        clearSearchField.setDisable(false);
        searchField.setDisable(false);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            librarian_bookList.setItems(bookListViewItems.filtered((book) -> {
                switch (librarian_searchTopic.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        return book.idProperty().get().contains(newValue);
                    case 1:
                        return book.titleProperty().get().toLowerCase().contains(newValue.toLowerCase());
                    case 2:
                        return book.authorProperty().get().toLowerCase().contains(newValue.toLowerCase());
                    case 3:
                        return book.publisherProperty().get().toLowerCase().contains(newValue.toLowerCase());
                    case 4:
                        return book.categoryProperty().get().toLowerCase().contains(newValue.toLowerCase());
                    case 5:
                        return book.availableProperty().get().toLowerCase().contains(newValue.toLowerCase());
                    case 6:
                        return book.borrowedProperty().get().toLowerCase().contains(newValue.toLowerCase());
                    case 7:
                        return book.bookshelfProperty().get().toLowerCase().contains(newValue.toLowerCase());
                    case 8:
                        return book.shelfProperty().get().contains(newValue);
                    default:
                        return false;
                }
            }));
        });
    }

    public void onfindUserTopicSelected(ActionEvent event) {
        clearUserSearchField.setDisable(false);
        userSearchField.setDisable(false);

        userSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            librarian_userList.setItems(userListViewItems.filtered((user) -> {
                switch (librarian_userSearchTopic.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        return user.idProperty().get().contains(newValue);
                    case 1:
                        return user.roleProperty().get().toLowerCase().contains(newValue.toLowerCase());
                    case 2:
                        return user.nameProperty().get().toLowerCase().contains(newValue.toLowerCase());
                    case 3:
                        return user.emailProperty().get().toLowerCase().contains(newValue.toLowerCase());
                    case 4:
                        return user.usernameProperty().get().toLowerCase().contains(newValue.toLowerCase());
                    default:
                        return false;
                }
            }));
        });
    }

    public void doClearSearchField()
    {
        searchField.setText("");
    }

    public void doUserClearSearchField()
    {
        userSearchField.setText("");
    }

    public void refreshList() {
        getUserList();
        getBookList();

        doClearSearchField();
        doUserClearSearchField();

        librarian_bookCount.setText(String.valueOf(bookListViewItems.size()));
        librarian_userCount.setText(String.valueOf(userListViewItems.size()));
        bookListViewItems.addListener((ListChangeListener<DevTools.BookListItem>) change -> {
            librarian_bookCount.setText(String.valueOf(bookListViewItems.size()));
        });

        String sumQuery = "SELECT SUM(fine) AS totalFine FROM users";
        List<Object[]> sum = db.queryView(sumQuery);
        BigDecimal totalFine = (BigDecimal) sum.get(0)[0];
        librarian_fineCount.setText(String.valueOf(totalFine));

        userListViewItems.addListener((ListChangeListener<DevTools.UserListItem>) change -> {
            List<Object[]> v = db.queryView(sumQuery);
            BigDecimal x = (BigDecimal) v.get(0)[0];
            librarian_fineCount.setText(String.valueOf(x));
        });

        librarian_userList.setItems(userListViewItems);
        librarian_bookList.setItems(bookListViewItems);
    }

    public void signOut(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        dt.createNewUI("loginui.fxml", "Library Management System");
    }

    public void addBookUI(ActionEvent event) throws IOException{
        dt.createNewUI("add_books.fxml", "Add Books", 520, 310);
    }

    public void updateBookUI(ActionEvent event) throws IOException{
        dt.createNewUI("update_books.fxml", "Update Books", 520, 540);
    }

    public void removeBookUI(ActionEvent event) throws IOException{
        dt.createNewUI("remove_books.fxml", "Remove Books", 520, 540);
    }

    public void addUserUI(ActionEvent event) throws IOException{
        dt.createNewUI("add_users.fxml", "Add Users", 520, 310);
    }

    public void updateUserUI(ActionEvent event) throws IOException{
        dt.createNewUI("update_users.fxml", "Update Users", 520, 540);
    }

    public void updateUserFineUI(ActionEvent event) throws IOException{
        dt.createNewUI("fine_info.fxml", "Update Fine", 520, 560);
    }

    public void removeUserUI(ActionEvent event) throws IOException{
        dt.createNewUI("remove_users.fxml", "Remove Users", 580, 640);
    }

    public void movebookUI(ActionEvent event) throws IOException{
        dt.createNewUI("move_books.fxml", "Move Book", 600, 580);
    }
}
