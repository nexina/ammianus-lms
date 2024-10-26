package group.one.librarymanagementsystem.librarymanagementsystemui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.List;

public class MoveBook {

    @FXML
    private TextField bid;
    @FXML
    private TextField bookshelfid;
    @FXML
    private TextField shelfno;
    @FXML
    private TextField exbookshelfid;
    @FXML
    private TextField exshelfno;
    @FXML
    private Label bookexists_lbl;
    @FXML
    private Label success;
    @FXML
    private VBox bookexist_vbox;

    Database db = new Database();
    DevTools dt = new DevTools();


    public void initialize()
    {
        bookexist_vbox.setVisible(false);
        success.setVisible(false);
    }

    List<Object[]> noBookinPosition;

    int current_bookid;
    int found_bookid;
    String new_bookshelf;
    int new_shelfno;

    public void move()
    {
        if (bid.getText().isEmpty() ||  bookshelfid.getText().isEmpty() || shelfno.getText().isEmpty()){
            Utils.ShowMessage(success, "Fields can not be empty!", 5.0, Color.RED);
            return;
        }

        current_bookid = Integer.parseInt(bid.getText());
        new_bookshelf = bookshelfid.getText();
        new_shelfno = Integer.parseInt(shelfno.getText());

        String query = "SELECT id, bookshelf, shelf FROM books WHERE bookshelf='"+ new_bookshelf +"' AND shelf="+ new_shelfno +";";
        noBookinPosition = db.queryView(query);

        query = "SELECT id FROM books WHERE id=" + current_bookid;
        List<Object[]> booksexists = db.queryView(query);

        if(booksexists.isEmpty())
        {
            Utils.ShowMessage(success, "Book does not exist!", 5.0, Color.RED);
            return;
        }

        if(noBookinPosition.isEmpty())
        {
            Utils.ShowMessage(success, "Book Position has been updated! Refresh the list to see update !", 5.0, Color.GREEN);
            String updateQuery = "UPDATE books SET bookshelf = '"+ new_bookshelf +"', shelf="+ new_shelfno +" WHERE id="+current_bookid+";";
            db.query(updateQuery);
            bookexist_vbox.setVisible(false);
        }else{
            found_bookid = (Integer) noBookinPosition.get(0)[0];

            bookexists_lbl.setText("Book " +found_bookid+ " already exists at that place" );
            bookexists_lbl.setVisible(true);

            exbookshelfid.setText((String) noBookinPosition.get(0)[1]);
            int s = (int) noBookinPosition.get(0)[2];
            exshelfno.setText(String.valueOf(s));
            bookexist_vbox.setVisible(true);
        }

    }

    public void exchangeandmove()
    {
        String currentBookQuery = "SELECT id, bookshelf, shelf FROM books WHERE id="+current_bookid+";";
        List<Object[]> currentBook = db.queryView(currentBookQuery);

        String updateQuery = "UPDATE books SET bookshelf='"+(String)currentBook.get(0)[1]+"', shelf="+(int)currentBook.get(0)[2]+" WHERE id="+found_bookid+";";
        db.query(updateQuery);

        updateQuery = "UPDATE books SET bookshelf='"+(String) noBookinPosition.get(0)[1]+"', shelf="+(int) noBookinPosition.get(0)[2]+" WHERE id="+current_bookid+";";
        db.query(updateQuery);

        Utils.ShowMessage(success, "Book Position has been updated! Refresh the list to see update !", 5.0, Color.GREEN);
        bookexist_vbox.setVisible(false);
    }

}
