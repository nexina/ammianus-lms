package group.one.librarymanagementsystem.librarymanagementsystemui;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static group.one.librarymanagementsystem.librarymanagementsystemui.LibrarianUI.bookListViewItems;

public class AddBooks {
    DevTools dt = new DevTools();
    Database db = new Database();

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
    private Label addbooks_error_lbl;

    public void initialize()
    {
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

    public void addButton()
    {
        addbooks_error_lbl.setVisible(true);
        addbooks_error_lbl.setTextFill(Color.RED);


        if (librarian_bookTitle.getText().isEmpty()) {
            addbooks_error_lbl.setText("Book Title is empty!");
        } else if (librarian_bookAuthor.getText().isEmpty()) {
            addbooks_error_lbl.setText("Book Author is empty!");
        } else if (librarian_bookPublisher.getText().isEmpty()) {
            addbooks_error_lbl.setText("Book Publisher is empty!");
        } else if (librarian_bookCategory.getText().isEmpty()) {
            addbooks_error_lbl.setText("Book Category is empty!");
        } else if (librarian_bookShelfNo.getText().isEmpty()) {
            addbooks_error_lbl.setText("Shelf not found!");
        }else if (librarian_bookshelfID.getText().isEmpty()) {
            addbooks_error_lbl.setText("BookShelf not found!");
        }else
        {
            String title = librarian_bookTitle.getText();
            String author = librarian_bookAuthor.getText();
            String publisher = librarian_bookPublisher.getText();
            String category = librarian_bookCategory.getText();
            int available = librarian_bookAvailable.isSelected() ? 1 : 0;
            String bookshelf = librarian_bookshelfID.getText();
            int shelf = Integer.parseInt(librarian_bookShelfNo.getText());

            String insertQuery = "INSERT INTO books (title, author, publisher, category, available, bookshelf, shelf) " +
                    "VALUES ('" + title + "', '" + author + "', '" + publisher + "', '" + category + "', '" + available + "', '" + bookshelf + "', '" + shelf + "')";

            int response = db.query(insertQuery);
            if(response == -1)
            {
                addbooks_error_lbl.setText("Book could not be added");
            }else
            {
                addbooks_error_lbl.setText("Book added succesfully!");
                addbooks_error_lbl.setTextFill(Color.GREEN);
            }


            String findRowQuery = "SELECT id, borrowed FROM books WHERE title = '" + title + "' AND author = '" + author + "' AND publisher = '" + publisher + "' AND category = '" + category + "' AND available = " + available + " AND bookshelf = '" + bookshelf + "' AND shelf = '" + shelf + "'";
            List<Object[]> getRow = db.queryView(findRowQuery);

            DevTools.BookListItem bt = new DevTools.BookListItem();

            if (!getRow.isEmpty()) {
                bt.id = new SimpleStringProperty(getRow.get(0)[0].toString());
                String x = (String) getRow.get(0)[1];
                if(x==null || x.isEmpty())
                {
                    bt.borrowed = new SimpleStringProperty("--NONE--");
                }else
                {
                    bt.borrowed = new SimpleStringProperty(x);
                }
            }



            bt.title = new SimpleStringProperty(title);
            bt.author = new SimpleStringProperty(author);
            bt.publisher = new SimpleStringProperty(publisher);
            bt.category = new SimpleStringProperty(category);
            bt.available = new SimpleStringProperty((available == 1) ? "YES" : "NO");
            bt.bookshelf = new SimpleStringProperty(bookshelf);
            bt.shelf = new SimpleStringProperty(String.valueOf(shelf));
            bookListViewItems.add(bt);


            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    addbooks_error_lbl.setVisible(false);
                }
            }));
            timeline.play();
        }
    }

    public void cancelButton(ActionEvent event)
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
