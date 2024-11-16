package group.one.librarymanagementsystem.librarymanagementsystemui;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;
import static group.one.librarymanagementsystem.librarymanagementsystemui.LibrarianUI.userListViewItems;

public class AddUsers {
    DevTools dt = new DevTools();
    Database db = new Database();

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
    private Label addusers_error_lbl;

    public void initialize() {
        librarian_userRole.getItems().addAll("Librarian", "Library Staff", "Patron");
    }

    public void addButton()
    {
        addusers_error_lbl.setVisible(true);
        addusers_error_lbl.setTextFill(Color.RED);
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
            addusers_error_lbl.setText("Full name is empty!");
        } else if (username.isEmpty()) {
            addusers_error_lbl.setText("Username is empty!");
        } else if (password.isEmpty()) {
            addusers_error_lbl.setText("Password is empty!");
        } else if (email.isEmpty()) {
            addusers_error_lbl.setText("E-mail is empty!");
        } else if (role.isEmpty()) {
            addusers_error_lbl.setText("Select a user Role!");
        }else if (Utils.containsSpacesAndSpecialChars(username)) {
            addusers_error_lbl.setText("Username must not contain space or special character");
        } else if(!Utils.isValidEmail(email)) {
            addusers_error_lbl.setText("The email is not valid");
        }else {
            String insertQuery = "INSERT INTO users (role, name, email, username, password) VALUES (?, ?, ?, ?, ?)";
            int response = db.query(insertQuery, role, fullname, email, username, password);
            if(response == -1)
            {
                Utils.ShowMessage(addusers_error_lbl, "User could not be added", 5.0, Color.RED);
            }else
            {
                Utils.ShowMessage(addusers_error_lbl, "User added succesfully!", 5.0, Color.GREEN);

                String findRowQuery = "SELECT id FROM users WHERE username = '" + username + "'";
                List<Object[]> getRow = db.queryView(findRowQuery);

                DevTools.UserListItem ut = new DevTools.UserListItem();

                if (!getRow.isEmpty()) {
                    ut.id = new SimpleStringProperty(getRow.get(0)[0].toString());
                }

                ut.name = new SimpleStringProperty(fullname);
                ut.username = new SimpleStringProperty(username);
                ut.password = new SimpleStringProperty(password);
                ut.email = new SimpleStringProperty(email);
                ut.role = new SimpleStringProperty(role);
                ut.fine = new SimpleStringProperty("0");
                userListViewItems.add(ut);
            }
        }
    }

    public void cancelButton(ActionEvent event)
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
