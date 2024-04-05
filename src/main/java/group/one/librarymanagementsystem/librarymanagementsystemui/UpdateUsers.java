package group.one.librarymanagementsystem.librarymanagementsystemui;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.Objects;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import static group.one.librarymanagementsystem.librarymanagementsystemui.LibrarianUI.userListViewItems;

public class UpdateUsers {
    DevTools dt = new DevTools();
    Database db = new Database();

    @FXML
    private TextField librarian_userID_txtf;
    @FXML
    private TextField librarian_fullName;
    @FXML
    private TextField librarian_userName;
    @FXML
    private PasswordField librarian_userPassword;
    @FXML
    private TextField librarian_userEmail;
    @FXML
    private ComboBox librarian_userRole;
    @FXML
    private Label updateusers_error_lbl;
    @FXML
    private Label librarian_usrntfnd_ere_lbl;

    @FXML
    private VBox librarian_info;

    @FXML
    private Button librarian_updateButton;

    int userId;

    List<Object[]> selectedUser;

    public void initialize() {
        librarian_userRole.getItems().addAll("Librarian", "Library Staff", "Patron");
    }

    public void checkBook() {
        if(librarian_userID_txtf.getText().isEmpty())
        {
            showbkntfndAnim("User ID is empty!");
        }else
        {
            userId = Integer.parseInt(librarian_userID_txtf.getText());
            String query = "SELECT * FROM users WHERE id =" + userId;
            selectedUser = db.queryView(query);

            if(selectedUser.isEmpty())
            {
                showbkntfndAnim("User does not exist!");

                librarian_info.setDisable(true);
                librarian_updateButton.setDisable(true);
            }else
            {
                librarian_info.setDisable(false);
                librarian_updateButton.setDisable(false);

                System.out.println((String) selectedUser.get(0)[1]);

                if(Objects.equals((String) selectedUser.get(0)[1], "Librarian"))
                {
                    librarian_userRole.getSelectionModel().select(0);
                }else if(Objects.equals((String) selectedUser.get(0)[1], "Library Staff"))
                {
                    librarian_userRole.getSelectionModel().select(1);
                }else
                {
                    librarian_userRole.getSelectionModel().select(2);
                }

                librarian_fullName.setText((String) selectedUser.get(0)[2]);
                librarian_userEmail.setText((String) selectedUser.get(0)[3]);
                librarian_userName.setText((String) selectedUser.get(0)[4]);
                librarian_userPassword.setText((String) selectedUser.get(0)[5]);
            }
        }
    }

    private void showbkntfndAnim(String text) {
        librarian_usrntfnd_ere_lbl.setVisible(true);
        librarian_usrntfnd_ere_lbl.setText(text);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                librarian_usrntfnd_ere_lbl.setVisible(false);
            }
        }));
        timeline.play();
    }

    public void updateButton() {
        updateusers_error_lbl.setVisible(true);
        updateusers_error_lbl.setTextFill(Color.RED);
        String fullname = librarian_fullName.getText();
        String username = librarian_userName.getText();
        String password = librarian_userPassword.getText();
        String email = librarian_userEmail.getText();
        String role;

        switch(librarian_userRole.getSelectionModel().getSelectedIndex())
        {
            case 0:
                role = "Librarian";
                break;
            case 1:
                role = "Library Staff";
                break;
            case 2:
                role = "Patron";
                break;
            default:
                role = "";
        }

        if (fullname.isEmpty()) {
            updateusers_error_lbl.setText("Full name is empty!");
        } else if (username.isEmpty()) {
            updateusers_error_lbl.setText("Username is empty!");
        } else if (password.isEmpty()) {
            updateusers_error_lbl.setText("Password is empty!");
        } else if (email.isEmpty()) {
            updateusers_error_lbl.setText("E-mail is empty!");
        } else if (role.isEmpty()) {
            updateusers_error_lbl.setText("Select a user Role!");
        }else
        {
            String updateQuery = "UPDATE users SET role = '" + role + "', name = '" + fullname + "', email = '" + email + "', username = '" + username + "', password = '" + password + "' WHERE id =  "+userId+"";

            int response = db.query(updateQuery);
            if(response == -1)
            {
                updateusers_error_lbl.setText("User could not be updated");
            }else {
                updateusers_error_lbl.setText("User updated succesfully! Refresh the list to see update !");
                updateusers_error_lbl.setTextFill(Color.GREEN);
            }

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {

                    updateusers_error_lbl.setVisible(false);
                }
            }));
            timeline.play();
        }
    }

    public void cancelButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
