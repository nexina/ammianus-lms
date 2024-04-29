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
        bid = bookID.getText();
        puser = pUserName.getText();

        String checkQuery = "SELECT available, borrowed FROM books WHERE id="+Integer.parseInt(bid)+";";
        List<Object[]> checkDB = db.queryView(checkQuery);
        if(!(Boolean) checkDB.get(0)[0])
        {
            error_lbl.setText("The book is not available!");
            error_lbl.setVisible(true);
        }else
        {
            if((String) checkDB.get(0)[1] != null && !Objects.equals((String) checkDB.get(0)[1], "-- NONE --"))
            {
                error_lbl.setText("The book is borrowed by someone!");
                error_lbl.setVisible(true);
            }else
            {
                String updateQuery = "UPDATE books SET available = 0, borrowed='"+puser+"' WHERE id ="+bid+";";
                int res = db.query(updateQuery);
                if(res == -1)
                {
                    error_lbl.setVisible(true);
                    error_lbl.setText("Something went wrong !");
                }else if(res == 0)
                {
                    error_lbl.setVisible(true);
                    error_lbl.setText("No Book ID Found !");
                }else {
                    error_lbl.setVisible(true);
                    error_lbl.setText("Book has been taken by "+puser+" !");
                    error_lbl.setTextFill(Color.GREEN);
                }
            }
        }


    }
}
