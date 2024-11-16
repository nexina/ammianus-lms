package group.one.librarymanagementsystem.librarymanagementsystemui;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdk.jshell.execution.Util;

import java.util.List;
import java.util.Objects;

import static group.one.librarymanagementsystem.librarymanagementsystemui.LibrarianUI.userListViewItems;

public class DeleteUsers {
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
    private Label deleteusers_error_lbl;
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
            Utils.ShowMessage(librarian_usrntfnd_ere_lbl, "User ID is empty!", 5.0, Color.RED);
            return;
        }

        userId = Integer.parseInt(librarian_userID_txtf.getText());
        String query = "SELECT * FROM users WHERE id =" + userId;
        selectedUser = db.queryView(query);

        if(selectedUser.isEmpty())
        {
            Utils.ShowMessage(librarian_usrntfnd_ere_lbl, "User does not exist!", 5.0, Color.RED);
            librarian_updateButton.setDisable(true);
        }else
        {
            librarian_updateButton.setDisable(false);
            if(Objects.equals(selectedUser.get(0)[1], "Librarian"))
            {
                librarian_userRole.getSelectionModel().select(0);
            }else if(Objects.equals(selectedUser.get(0)[1], "Library Staff"))
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

    public void deleteButton() {
        deleteusers_error_lbl.setVisible(true);
        deleteusers_error_lbl.setTextFill(Color.RED);
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
            deleteusers_error_lbl.setText("Full name is empty!");
        } else if (username.isEmpty()) {
            deleteusers_error_lbl.setText("Username is empty!");
        } else if (password.isEmpty()) {
            deleteusers_error_lbl.setText("Password is empty!");
        } else if (email.isEmpty()) {
            deleteusers_error_lbl.setText("E-mail is empty!");
        } else if (role.isEmpty()) {
            deleteusers_error_lbl.setText("Select a user Role!");
        }else
        {
            String deleteQuery = "DELETE FROM users WHERE id =  "+userId;

            int response = db.query(deleteQuery);
            if(response == -1)
            {
                Utils.ShowMessage(deleteusers_error_lbl, "User could not be deleted", 5.0, Color.RED);
            }else if(response == -1)
            {
                Utils.ShowMessage(deleteusers_error_lbl, "User does not exists", 5.0, Color.RED);
            }else {
                int i = 0;
                for (DevTools.UserListItem user : userListViewItems) {
                    if (user.idProperty().get().equals(String.valueOf(userId))) {
                        userListViewItems.remove(i);
                        break;
                    }
                    i++;
                }

                Utils.ShowMessage(deleteusers_error_lbl, "User deleted succesfully!", 5.0, Color.GREEN);
                librarian_updateButton.setDisable(true);
            }
        }
    }

    public void cancelButton(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
