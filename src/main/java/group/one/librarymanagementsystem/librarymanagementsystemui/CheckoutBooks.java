package group.one.librarymanagementsystem.librarymanagementsystemui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class CheckoutBooks {
    @FXML
    private TextField bookID;
    @FXML
    private TextField pUserName;
    @FXML
    private Label error_lbl;

    Database db = new Database();
    DevTools dt = new DevTools();

    public void initialize()
    {
        error_lbl.setVisible(false);
        Pattern digitPattern = Pattern.compile("\\d*");
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (digitPattern.matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        };
        bookID.setTextFormatter(new TextFormatter<>(filter));
    }

    public void Checkout()
    {
        String bid;
        String puser;

        if(bookID.getText().isEmpty() || pUserName.getText().isEmpty())
        {
            Utils.ShowMessage(error_lbl, "The book ID and username can not be empty!", 5.0, Color.RED);
            return;
        }
            bid = bookID.getText();
            puser = pUserName.getText();

            List<Object[]> checkUser = db.queryView("SELECT * FROM users WHERE username = '"+puser+"';");
            if(checkUser.isEmpty())
            {
                Utils.ShowMessage(error_lbl, "User does not exists !", 5.0, Color.RED);
                return;
            }

            String checkQuery = "SELECT available, borrowed FROM books WHERE id=" + Integer.parseInt(bid) + ";";
            List<Object[]> checkDB = db.queryView(checkQuery);
            if (checkDB.isEmpty() || !(Boolean) checkDB.get(0)[0]) {
                Utils.ShowMessage(error_lbl, "The book is not available!", 5.0, Color.RED);
            } else {
                if (((String) checkDB.get(0)[1] != null && !Objects.equals((String) checkDB.get(0)[1], "-- NONE --")) && !Objects.equals((String) checkDB.get(0)[1], "")) {
                    Utils.ShowMessage(error_lbl, "The book is borrowed by someone!", 5.0, Color.RED);
                } else {
                    String updateQuery = "UPDATE books SET available = 0, borrowed='" + puser + "' WHERE id =" + bid + ";";
                    int res = db.query(updateQuery);
                    if (res == -1) {
                        Utils.ShowMessage(error_lbl, "Something went wrong !", 5.0, Color.RED);
                    } else if (res == 0) {
                        Utils.ShowMessage(error_lbl, "No Book ID Found !", 5.0, Color.RED);
                    } else {
                        Utils.ShowMessage(error_lbl, "Book has been taken by " + puser + " !", 5.0, Color.GREEN);
                    }
                }
            }
    }

    public void Cancel(ActionEvent event)
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
