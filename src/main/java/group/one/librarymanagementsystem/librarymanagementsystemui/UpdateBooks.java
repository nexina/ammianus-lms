package group.one.librarymanagementsystem.librarymanagementsystemui;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static group.one.librarymanagementsystem.librarymanagementsystemui.LibrarianUI.bookListViewItems;

public class UpdateBooks {
    DevTools dt = new DevTools();
    Database db = new Database();

    @FXML
    private TextField librarian_bookID_txtf;

    @FXML
    private TextField librarian_bookTitle;
    @FXML
    private TextField librarian_bookAuthor;
    @FXML
    private TextField librarian_bookPublisher;
    @FXML
    private TextField librarian_bookCategory;
    @FXML
    private CheckBox librarian_bookAvailable;
    @FXML
    private TextField librarian_bookshelfID;
    @FXML
    private TextField librarian_bookShelfNo;
    @FXML
    private Label updatebooks_error_lbl;
    @FXML
    private Label lirarian_bkntfnd_ere_lbl;

    @FXML
    private VBox librarian_info;

    @FXML
    private Button librarian_updateButton;

    int id;

    List<Object[]> selcetedBook;

    public void initialize() {
        Pattern digitPattern = Pattern.compile("\\d*");
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (digitPattern.matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        librarian_bookShelfNo.setTextFormatter(textFormatter);
    }

    public void checkBook() {
        if(librarian_bookID_txtf.getText().isEmpty())
        {
            showbkntfndAnim("Book ID is empty!");
        }else
        {
            int bookId = Integer.parseInt(librarian_bookID_txtf.getText());
            String query = "SELECT * FROM books WHERE id =" + bookId;
            selcetedBook = db.queryView(query);

            if(selcetedBook.isEmpty())
            {
                showbkntfndAnim("Book does not exist!");

                librarian_info.setDisable(true);
                librarian_updateButton.setDisable(true);
            }else
            {
                librarian_info.setDisable(false);
                librarian_updateButton.setDisable(false);

                id = Integer.parseInt(librarian_bookID_txtf.getText());

                librarian_bookTitle.setText((String) selcetedBook.get(0)[1]);
                librarian_bookAuthor.setText((String) selcetedBook.get(0)[2]);
                librarian_bookPublisher.setText((String) selcetedBook.get(0)[3]);
                librarian_bookCategory.setText((String) selcetedBook.get(0)[4]);
                librarian_bookAvailable.setSelected((Boolean) selcetedBook.get(0)[5]);
                librarian_bookshelfID.setText((String) selcetedBook.get(0)[7]);
                librarian_bookShelfNo.setText(selcetedBook.get(0)[8].toString());
                System.out.println(selcetedBook.get(0)[8].toString());
            }
        }
    }

    private void showbkntfndAnim(String text) {
        lirarian_bkntfnd_ere_lbl.setVisible(true);
        lirarian_bkntfnd_ere_lbl.setText(text);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                lirarian_bkntfnd_ere_lbl.setVisible(false);
            }
        }));
        timeline.play();
    }

    public void updateButton() {
        updatebooks_error_lbl.setVisible(true);
        updatebooks_error_lbl.setTextFill(Color.RED);


        if (librarian_bookTitle.getText().isEmpty()) {
            updatebooks_error_lbl.setText("Book Title is empty!");
        } else if (librarian_bookAuthor.getText().isEmpty()) {
            updatebooks_error_lbl.setText("Book Author is empty!");
        } else if (librarian_bookPublisher.getText().isEmpty()) {
            updatebooks_error_lbl.setText("Book Publisher is empty!");
        } else if (librarian_bookCategory.getText().isEmpty()) {
            updatebooks_error_lbl.setText("Book Category is empty!");
        } else if (librarian_bookshelfID.getText().isEmpty()) {
            updatebooks_error_lbl.setText("Book Bookshelf is empty!");
        } else if (librarian_bookShelfNo.getText().isEmpty()) {
            updatebooks_error_lbl.setText("Book Shelf not found!");
        }else
        {
            String title = librarian_bookTitle.getText();
            String author = librarian_bookAuthor.getText();
            String publisher = librarian_bookPublisher.getText();
            String category = librarian_bookCategory.getText();
            int available = librarian_bookAvailable.isSelected() ? 1 : 0;
            String bookshelf = librarian_bookshelfID.getText();
            int shelf = Integer.parseInt(librarian_bookShelfNo.getText());

            String updateQuery = "UPDATE books SET title = '" + title + "', author = '" + author + "', publisher = '" + publisher + "', category = '" + category + "', available = '" + available + "', bookshelf = '" + bookshelf + "', shelf = '" + shelf + "' WHERE id = " + id;

            int response = db.query(updateQuery);
            if(response == -1)
            {
                updatebooks_error_lbl.setText("Book could not be added");
            }else
            {
                updatebooks_error_lbl.setText("Book updated succesfully! Refresh the List to see update !");
                updatebooks_error_lbl.setTextFill(Color.GREEN);
            }

//            int i = 0;
//            for (DevTools.BookListItem book : bookListViewItems) {
//                if (book.idProperty().get().equals(String.valueOf(id))) {
//                    bookListViewItems.get(i).title = new SimpleStringProperty(title);
//                    bookListViewItems.get(i).author = new SimpleStringProperty(author);
//                    bookListViewItems.get(i).publisher = new SimpleStringProperty(publisher);
//                    bookListViewItems.get(i).category = new SimpleStringProperty(category);
//                    bookListViewItems.get(i).available = new SimpleStringProperty((available == 1) ? "YES" : "NO");
//                    bookListViewItems.get(i).bookshelf = new SimpleStringProperty(bookshelf);
//                    bookListViewItems.get(i).shelf = new SimpleStringProperty(String.valueOf(shelf));
//                    break;
//                }
//                i++;
//            }

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    updatebooks_error_lbl.setVisible(false);
                }
            }));
            timeline.play();
        }
    }

    public void cancelButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
