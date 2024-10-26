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
            Utils.ShowMessage(lirarian_bkntfnd_ere_lbl, "Book ID is empty!",5.0, Color.RED);
            return;
        }

        int bookId = Integer.parseInt(librarian_bookID_txtf.getText());
        String query = "SELECT * FROM books WHERE id =" + bookId;
        selcetedBook = db.queryView(query);

        if(selcetedBook.isEmpty())
        {
            Utils.ShowMessage(lirarian_bkntfnd_ere_lbl, "Book does not exist!",5.0, Color.RED);

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
        }

    }

    public void updateButton() {
        if (librarian_bookTitle.getText().isEmpty()) {
            Utils.ShowMessage(updatebooks_error_lbl, "Book Title is empty!", 5.0, Color.RED);
        } else if (librarian_bookAuthor.getText().isEmpty()) {
            Utils.ShowMessage(updatebooks_error_lbl, "Book Author is empty!", 5.0, Color.RED);
        } else if (librarian_bookPublisher.getText().isEmpty()) {
            Utils.ShowMessage(updatebooks_error_lbl, "Book Publisher is empty!", 5.0, Color.RED);
        } else if (librarian_bookCategory.getText().isEmpty()) {
            Utils.ShowMessage(updatebooks_error_lbl,"Book Category is empty!" , 5.0, Color.RED);
        } else if (librarian_bookshelfID.getText().isEmpty()) {
            Utils.ShowMessage(updatebooks_error_lbl, "Book Bookshelf is empty!", 5.0, Color.RED);
        } else if (librarian_bookShelfNo.getText().isEmpty()) {
            Utils.ShowMessage(updatebooks_error_lbl, "Book Shelf not found!", 5.0, Color.RED);
        }else
        {
            String title = librarian_bookTitle.getText();
            String author = librarian_bookAuthor.getText();
            String publisher = librarian_bookPublisher.getText();
            String category = librarian_bookCategory.getText();
            int available = librarian_bookAvailable.isSelected() ? 1 : 0;
            String bookshelf = librarian_bookshelfID.getText();
            int shelf = Integer.parseInt(librarian_bookShelfNo.getText());

            String updateQuery = "UPDATE books SET title = ?, author = ?, publisher = ?, category = ?, available = ?, bookshelf = ?, shelf = ? WHERE id = ?";
            int response = db.query(updateQuery, title, author, publisher, category, available, bookshelf, shelf, id);

            if(response == -1)
            {
                Utils.ShowMessage(updatebooks_error_lbl, "Book could not be added", 5.0, Color.RED);
            }else
            {
                Utils.ShowMessage(updatebooks_error_lbl, "Book updated succesfully! Refresh the List to see update !", 5.0, Color.GREEN);
            }
        }
    }

    public void cancelButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
