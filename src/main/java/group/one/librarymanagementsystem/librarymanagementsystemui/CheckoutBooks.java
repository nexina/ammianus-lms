package group.one.librarymanagementsystem.librarymanagementsystemui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;

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
        error_lbl.setVisible(false);
        error_lbl.setTextFill(Color.RED);

        if(bookID.getText().isEmpty() ||pUserName.getText().isEmpty())
        {
            error_lbl.setText("The book ID and username can not be empty!");
            error_lbl.setVisible(true);
            return;
        }
            bid = bookID.getText();
            puser = pUserName.getText();

            List<Object[]> checkUser = db.queryView("SELECT * FROM users WHERE username = '"+puser+"';");
            if(checkUser.isEmpty())
            {
                error_lbl.setText("User does not exist!");
                error_lbl.setVisible(true);
                return;
            }

            String checkQuery = "SELECT available, borrowed FROM books WHERE id=" + Integer.parseInt(bid) + ";";
            List<Object[]> checkDB = db.queryView(checkQuery);
            if (checkDB.isEmpty() || !(Boolean) checkDB.get(0)[0]) {
                error_lbl.setText("The book is not available!");
                error_lbl.setVisible(true);
            } else {
                if (((String) checkDB.get(0)[1] != null && !Objects.equals((String) checkDB.get(0)[1], "-- NONE --")) && !Objects.equals((String) checkDB.get(0)[1], "")) {
                    error_lbl.setText("The book is borrowed by someone!");
                    error_lbl.setVisible(true);
                } else {
                    String updateQuery = "UPDATE books SET available = 0, borrowed='" + puser + "' WHERE id =" + bid + ";";
                    int res = db.query(updateQuery);
                    if (res == -1) {
                        error_lbl.setVisible(true);
                        error_lbl.setText("Something went wrong !");
                    } else if (res == 0) {
                        error_lbl.setVisible(true);
                        error_lbl.setText("No Book ID Found !");
                    } else {
                        error_lbl.setVisible(true);
                        error_lbl.setText("Book has been taken by " + puser + " !");
                        error_lbl.setTextFill(Color.GREEN);
                    }
                }
            }


    }
}
