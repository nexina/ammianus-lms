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
        bid = Integer.parseInt(bookid.getText());
        buser = busername.getText();
        prev_ava = false;
        System.out.println(bid);
        System.out.println(buser);

        x = db.queryView("SELECT available, bookshelf, shelf FROM books WHERE id ="+bid+" AND borrowed='"+buser+"';");
        if(x.isEmpty())
        {
            usrerror.setVisible(true);
        }else
        {
            usrerror.setVisible(false);
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
        updateerror.setTextFill(Color.RED);
        updateerror.setVisible(false);
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

        int res = db.query("UPDATE books SET available="+ava+", bookshelf='"+bshelf+"', shelf="+Integer.parseInt(shelf)+",borrowed='' WHERE id ="+bid+";");
        if(res == -1)
        {
            updateerror.setVisible(true);
        }else if(res == 0)
        {
            updateerror.setVisible(true);
            updateerror.setText("No Book ID Found !");
        }else
        {
            updateerror.setVisible(true);
            updateerror.setText("Book has been added !");
            updateerror.setTextFill(Color.GREEN);

            prev_ava = ava;
            prev_bookshelf = bshelf;
            prev_shelf = shelf;

        }
    }

}
