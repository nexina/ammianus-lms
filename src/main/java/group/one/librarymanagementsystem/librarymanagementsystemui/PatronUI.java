package group.one.librarymanagementsystem.librarymanagementsystemui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import java.util.List;

public class PatronUI {
    String username;
    Database db = new Database();
    DevTools dt = new DevTools();

    List<Object[]> bookList;
    public static ObservableList<DevTools.BookListItem> bookListViewItems;

    @FXML
    private TableView librarian_bookList;
    @FXML
    private Label librarian_bookCount;
    @FXML
    private Label librarian_fineCount;
    @FXML
    private Label librarian_username;
    @FXML
    private Label librarian_timeanddate;
    @FXML
    private TextField searchField;
    @FXML
    private Button clearSearchField;

    @FXML
    private ComboBox librarian_searchTopic;

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
    private TableColumn<DevTools.BookListItem, String> bookshelfColumn;
    @FXML
    private TableColumn<DevTools.BookListItem, String> shelfColumn;

    String patronRoleName = "Patron";

    public void initData(String name) {
        username = name;
        librarian_username.setText(username);
        refreshList();
    }

    void getBookList() {
        bookList = db.queryView("SELECT * FROM books");
        bookListViewItems = dt.convertBookToObservableList(bookList);
    }

    public void initialize() {

        librarian_searchTopic.getItems().addAll("Book ID", "Title", "Author", "Publisher", "Category", "Bookshelf", "Shelf No");

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
        bookshelfColumn.setCellValueFactory(data -> data.getValue().bookshelfProperty());
        shelfColumn.setCellValueFactory(data -> data.getValue().shelfProperty());
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
                    case 6:
                        return book.bookshelfProperty().get().toLowerCase().contains(newValue.toLowerCase());
                    case 7:
                        return book.shelfProperty().get().contains(newValue);
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


    public void refreshList() {
        getBookList();
        doClearSearchField();

        librarian_bookCount.setText(String.valueOf(bookListViewItems.size()));
        bookListViewItems.addListener((ListChangeListener<DevTools.BookListItem>) change -> {
            librarian_bookCount.setText(String.valueOf(bookListViewItems.size()));
        });

        String sumQuery = "SELECT SUM(fine) AS totalFine FROM users WHERE role ='" + patronRoleName+"' AND username = '" +username+ "'";
        List<Object[]> sum = db.queryView(sumQuery);
        BigDecimal totalFine = (BigDecimal) sum.get(0)[0];
        librarian_fineCount.setText(String.valueOf(totalFine));

        librarian_bookList.setItems(bookListViewItems);
    }

    public void signOut(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
        dt.createNewUI("loginui.fxml", "Library Management System");
    }

    public void myBorrowedBooks(ActionEvent event)
    {
        librarian_bookList.setItems(bookListViewItems.filtered((book) -> {
            return book.borrowedProperty().get().toLowerCase().contains(username.toLowerCase());
        }));
        clearSearchField.setDisable(false);
    }
}
