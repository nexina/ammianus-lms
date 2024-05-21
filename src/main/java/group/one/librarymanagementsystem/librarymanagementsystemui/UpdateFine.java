package group.one.librarymanagementsystem.librarymanagementsystemui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class UpdateFine {

    DevTools dt = new DevTools();
    Database db = new Database();

    @FXML
    private TextField fine_usernameTF;
    @FXML
    private TextField fine_payAmount;
    @FXML
    private TextField fine_addFine;

    @FXML
    private Label fine_userAmount;
    @FXML
    private Label fine_previousFine;
    @FXML
    private Label fine_finePaid;
    @FXML
    private Label fine_fineAdded;
    @FXML
    private Label fine_totalFine;
    @FXML
    private Label fine_usernameError;
    @FXML
    private Label userfine_lbl;
    @FXML
    private VBox fine_entryBox;

    List<Object[]> user;
    BigInteger currentFine;
    BigInteger newFine;
    String username;

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
        fine_addFine.setTextFormatter(textFormatter);
        fine_payAmount.setTextFormatter(new TextFormatter<>(filter));

    }

    public void checkFine()
    {
        String getFineQuery = "SELECT * FROM users WHERE username ='" + fine_usernameTF.getText() +"';";
        user = db.queryView(getFineQuery);

        if(user.isEmpty())
        {
            showusrntfndAnim("User not found !");
        }else
        {
            fine_entryBox.setDisable(false);
            currentFine = (BigInteger) user.get(0)[user.get(0).length-1];
            newFine = currentFine;
            fine_userAmount.setText(currentFine.toString() + " TK");

            fine_payAmount.setText("0");
            fine_addFine.setText("0");

            username = fine_usernameTF.getText();
            userfine_lbl.setText(username + "'s Fine: ");
        }
    }

    private void showusrntfndAnim(String text) {
        fine_usernameError.setVisible(true);
        fine_usernameError.setText(text);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                fine_usernameError.setVisible(false);
            }
        }));
        timeline.play();
    }

    public void updateFine()
    {
        addFine();
        payFine();

        String updateQuery = "UPDATE users SET fine = "+newFine+" WHERE username='"+username+"' ;";
        int res = db.query(updateQuery);

        if(res == 1)
        {
            showusrntfndAnim("Successfully Updated "+username+" Fine! Refresh list to check update!");
            currentFine = newFine;
            fine_userAmount.setText(currentFine.toString() + " TK");

            fine_payAmount.setText("0");
            fine_addFine.setText("0");
            fine_finePaid.setText("Fine Paid");
            fine_fineAdded.setText("Add Fine");
            fine_totalFine.setText("Total");
            fine_previousFine.setText("Previous Fine");

        }else
        {
            showusrntfndAnim("Failed to Update the fine");
        }

    }

    public void changeFineView()
    {
        if (fine_addFine.getText().isEmpty())
        {
            fine_addFine.setText("0");
        }

        if (fine_payAmount.getText().isEmpty())
        {
            fine_payAmount.setText("0");
        }

        BigInteger x = new BigInteger(fine_addFine.getText());
        BigInteger y = new BigInteger(fine_payAmount.getText());

        if (y.compareTo(newFine) > 0)
        {
            showusrntfndAnim("Paid fine amount is greater than current fine !");
            fine_finePaid.setText("0 TK");
            fine_payAmount.setText("0");
            y = BigInteger.valueOf(0);

        }else{
            fine_finePaid.setText(y.toString() + "TK");
        }

        fine_fineAdded.setText(x.toString() + "TK");


        BigInteger res = new BigInteger(currentFine.toString());
        res = res.add(x);
        res = res.subtract(y);
        fine_totalFine.setText(res + "TK");
        fine_previousFine.setText(currentFine.toString() + " TK");
    }

    public void addFine()
    {


        BigInteger x = new BigInteger(fine_addFine.getText());
        newFine = newFine.add(x);
    }

    public void payFine() {


        BigInteger x = new BigInteger(fine_payAmount.getText());

        if (x.compareTo(newFine) > 0)
        {
            showusrntfndAnim("Paid fine amount is greater than current fine !");
            fine_finePaid.setText("0 TK");
        }else
        {
            newFine = newFine.subtract(x);
        }



    }

}




