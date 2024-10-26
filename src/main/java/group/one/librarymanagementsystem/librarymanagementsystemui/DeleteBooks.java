package group.one.librarymanagementsystem.librarymanagementsystemui;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

public class DeleteBooks {
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
    private Label librarian_bkntfnd_ere_lbl;

    @FXML
    private VBox librarian_info;

    @FXML
    private Button librarian_updateButton;

    private int id;

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

        updatebooks_error_lbl.setVisible(false);
        librarian_bkntfnd_ere_lbl.setVisible(false);
    }

    public void checkBook() {
        if(librarian_bookID_txtf.getText().isEmpty())
        {
            Utils.ShowMessage(librarian_bkntfnd_ere_lbl,"Book ID is empty!", 5.0, Color.RED);
            return;
        }

        int bookId = Integer.parseInt(librarian_bookID_txtf.getText());
        String query = "SELECT * FROM books WHERE id =" + bookId;
        selcetedBook = db.queryView(query);

        if(selcetedBook.isEmpty())
        {
            Utils.ShowMessage(librarian_bkntfnd_ere_lbl,"Book does not exist!", 5.0, Color.RED);
            librarian_updateButton.setDisable(true);
        }else
        {
            librarian_updateButton.setDisable(false);

            librarian_bookTitle.setText((String) selcetedBook.get(0)[1]);
            librarian_bookAuthor.setText((String) selcetedBook.get(0)[2]);
            librarian_bookPublisher.setText((String) selcetedBook.get(0)[3]);
            librarian_bookCategory.setText((String) selcetedBook.get(0)[4]);
            librarian_bookAvailable.setSelected((Boolean) selcetedBook.get(0)[5]);
            librarian_bookshelfID.setText((String) selcetedBook.get(0)[7]);
            librarian_bookShelfNo.setText(selcetedBook.get(0)[8].toString());
            System.out.println(selcetedBook.get(0)[8].toString());

            id = Integer.parseInt(librarian_bookID_txtf.getText());

            librarian_info.setVisible(true);
        }

    }

    public void DeleteButton() {
        String deleteQuery = "DELETE FROM books WHERE id = " + id;

        int response = db.query(deleteQuery);
            if(response == -1)
            {
                Utils.ShowMessage(updatebooks_error_lbl,"Book could not be removed", 5.0, Color.RED);
            }else {
                int i = 0;
                for (DevTools.BookListItem book : bookListViewItems) {
                    if (book.idProperty().get().equals(String.valueOf(id))) {
                        bookListViewItems.remove(i);
                        break;
                    }
                    i++;
                }

                Utils.ShowMessage(updatebooks_error_lbl,"Book removed succesfully!", 5.0, Color.GREEN);
                librarian_updateButton.setDisable(true);
            }

        librarian_bookTitle.clear();
        librarian_bookAuthor.clear();
        librarian_bookPublisher.clear();
        librarian_bookCategory.clear();
        librarian_bookAvailable.setSelected(false);
        librarian_bookshelfID.clear();
        librarian_bookShelfNo.clear();

        librarian_info.setVisible(false);

    }

    public void cancelButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
