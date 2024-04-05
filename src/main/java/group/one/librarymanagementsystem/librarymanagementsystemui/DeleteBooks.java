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
    private Label lirarian_bkntfnd_ere_lbl;

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

    public void DeleteButton() {
        updatebooks_error_lbl.setVisible(true);
        updatebooks_error_lbl.setTextFill(Color.RED);

        String deleteQuery = "DELETE FROM books WHERE id = " + id;

        int response = db.query(deleteQuery);
            if(response == -1)
            {
                updatebooks_error_lbl.setText("Book could not be removed");
            }else {
                int i = 0;
                for (DevTools.BookListItem book : bookListViewItems) {
                    if (book.idProperty().get().equals(String.valueOf(id))) {
                        bookListViewItems.remove(i);
                        break;
                    }
                    i++;
                }
                updatebooks_error_lbl.setText("Book removed succesfully!");
                updatebooks_error_lbl.setTextFill(Color.GREEN);
            }

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    updatebooks_error_lbl.setVisible(false);
                }
            }));
            timeline.play();

    }

    public void cancelButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
