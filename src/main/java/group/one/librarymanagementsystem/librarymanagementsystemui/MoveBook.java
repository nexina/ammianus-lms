package group.one.librarymanagementsystem.librarymanagementsystemui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

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

    List<Object[]> existingBook;

    int current_bookid;
    int found_bookid;
    String new_bookshelf;
    int new_shelfno;

    public void move()
    {
        current_bookid = Integer.parseInt(bid.getText());
        new_bookshelf = bookshelfid.getText();
        new_shelfno = Integer.parseInt(shelfno.getText());

        String query = "SELECT id, bookshelf, shelf FROM books WHERE bookshelf='"+ new_bookshelf +"' AND shelf="+ new_shelfno +";";
        existingBook = db.queryView(query);

        if(existingBook.isEmpty())
        {
            success.setVisible(true);
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    success.setVisible(false);
                }
            }));
            timeline.play();
            String updateQuery = "UPDATE books SET bookshelf = '"+ new_bookshelf +"', shelf="+ new_shelfno +" WHERE id="+current_bookid+";";
            db.query(updateQuery);
            bookexist_vbox.setVisible(false);
        }else{
            success.setVisible(false);
            found_bookid = (Integer)existingBook.get(0)[0];
            bookexists_lbl.setText("Book " +found_bookid+ " exists already at that place" );
            bookexists_lbl.setVisible(true);
            exbookshelfid.setText((String) existingBook.get(0)[1]);
            int s = (int)existingBook.get(0)[2];
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
        updateQuery = "UPDATE books SET bookshelf='"+(String)existingBook.get(0)[1]+"', shelf="+(int)existingBook.get(0)[2]+" WHERE id="+current_bookid+";";
        db.query(updateQuery);

        success.setVisible(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                success.setVisible(false);
            }
        }));
        timeline.play();
        bookexist_vbox.setVisible(false);
    }

}
