package group.one.librarymanagementsystem.librarymanagementsystemui;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class reEntryBooks {
    @FXML
    private TextField bookid;
    @FXML
    private TextField bookshelfid;
    @FXML
    private TextField shelfno;
    @FXML
    private TextField busername;
    @FXML
    private CheckBox available;
    @FXML
    private VBox readd_vbox;

    @FXML
    private Label usrerror;

    @FXML
    private Label updateerror;

    Database db = new Database();
    DevTools dt = new DevTools();

    public void initialize()
    {
        available.setSelected(true);
        readd_vbox.setVisible(false);
        usrerror.setVisible(false);
        updateerror.setVisible(false);
        Pattern digitPattern = Pattern.compile("\\d*");
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (digitPattern.matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        };
        bookid.setTextFormatter(new TextFormatter<>(filter));
        shelfno.setTextFormatter(new TextFormatter<>(filter));
    }

    List<Object[]> x;
    int bid;
    String buser;
    Boolean prev_ava;

    String prev_bookshelf;
    String prev_shelf;
    public void checkBook()
    {
        if(bookid.getText().isEmpty() || busername.getText().isEmpty())
        {
            Utils.ShowMessage(usrerror, "Fields can not be empty!", 5.0, Color.RED);
            return;
        }
        bid = Integer.parseInt(bookid.getText());
        buser = busername.getText();
        prev_ava = false;

        x = db.queryView("SELECT available, bookshelf, shelf FROM books WHERE id ="+bid+" AND borrowed='"+buser+"';");
        if(x.isEmpty())
        {
            Utils.ShowMessage(usrerror, "The user may not exists or the borrower does not have that book !", 5.0, Color.RED);
        }else
        {
            Utils.ShowMessage(usrerror, "User has that book !", 5.0, Color.GREEN);
            for(Object[] item: x)
            {
                prev_ava = (Boolean) item[0];
                prev_bookshelf = (String) item[1];
                prev_shelf = item[2].toString();

            }
            available.setSelected(prev_ava);
            bookshelfid.setText(prev_bookshelf);
            shelfno.setText(prev_shelf);

            readd_vbox.setVisible(true);
        }
    }

    public void Reset()
    {
        available.setSelected(prev_ava);
        bookshelfid.setText(prev_bookshelf);
        shelfno.setText(prev_shelf);
    }

    public void updateBook()
    {
        String bshelf;
        String shelf;
        Boolean ava;
        if(bookshelfid.getText().isEmpty())
            bshelf = prev_bookshelf;
        else
            bshelf = bookshelfid.getText();

        if(shelfno.getText().isEmpty())
        {
            shelf = prev_shelf;
        }else
        {
            shelf = shelfno.getText();
        }

        ava= available.isSelected();

        int res = db.query("UPDATE books SET available="+ava+", bookshelf='"+bshelf+"', shelf="+Integer.parseInt(shelf)+",borrowed='"+"-- NONE --"+"' WHERE id ="+bid+";");
        if(res == -1)
        {
            Utils.ShowMessage(updateerror, "Something went wrong!", 5.0, Color.RED);
        }else if(res == 0)
        {
            Utils.ShowMessage(updateerror, "No Book ID Found !", 5.0, Color.RED);
        }else
        {
            Utils.ShowMessage(updateerror, "Book has been added !", 5.0, Color.GREEN);

            prev_ava = ava;
            prev_bookshelf = bshelf;
            prev_shelf = shelf;
        }
    }

}
