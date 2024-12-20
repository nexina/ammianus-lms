package group.one.librarymanagementsystem.librarymanagementsystemui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import jdk.jshell.execution.Util;

import java.io.IOException;
import java.util.Objects;

public class LoginUI {
    @FXML
    private Label login_error_lbl;
    @FXML
    private ComboBox<String> login_des_cb;
    @FXML
    private TextField login_un_input;
    @FXML
    private PasswordField login_pw_input;
    DevTools dt = new DevTools();

    public void initialize() {
        login_des_cb.getItems().addAll("Librarian", "Library Staff", "Patron");
    }

    @FXML
    public void onLoginClick(ActionEvent actionEvent) throws IOException {

        int des_ind = login_des_cb.getSelectionModel().getSelectedIndex();
        String username_input = login_un_input.getText();
        String password_input = login_pw_input.getText();
        if(des_ind == -1 || Objects.equals(username_input, "") || Objects.equals(password_input, ""))
        {
            Utils.ShowMessage(login_error_lbl, "Role, Username or Password can not be empty !", 5.0, Color.RED);
            return;
        }

        String username = dt.LoginScreen(des_ind, username_input, password_input);

        if( username != null && !username.isEmpty())
        {
            String des_filepath = "";
            String des_title = "";

            FXMLLoader fxmlLoader;
            Scene scene;
            LibrarianUI librarianController;
            LibraryStaffUI librarystaffController;
            PatronUI patronController;
            Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

            switch (des_ind)
            {
                case 0:
                    des_filepath = "librarian.fxml";
                    des_title = "Librarian Dashboard";
                    fxmlLoader = new FXMLLoader(getClass().getResource(des_filepath));
                    scene = new Scene(fxmlLoader.load(), 1200, 720);
                    librarianController = fxmlLoader.getController();
                    librarianController.initData(username);
                    stage.setTitle(des_title);
                    stage.setScene(scene);
                    stage.show();
                    break;
                case 1:
                    des_filepath = "library_staff.fxml";
                    des_title = "Library Staff Dashboard";
                    fxmlLoader = new FXMLLoader(getClass().getResource(des_filepath));
                    scene = new Scene(fxmlLoader.load(), 1200, 720);
                    librarystaffController = fxmlLoader.getController();
                    librarystaffController.initData(username);
                    stage.setTitle(des_title);
                    stage.setScene(scene);
                    stage.show();
                    break;
                case 2:
                    des_filepath = "patron.fxml";
                    des_title = "Patron Dashboard";
                    fxmlLoader = new FXMLLoader(getClass().getResource(des_filepath));
                    scene = new Scene(fxmlLoader.load(), 1200, 720);
                    patronController = fxmlLoader.getController();
                    patronController.initData(username);
                    stage.setTitle(des_title);
                    stage.setScene(scene);
                    stage.show();
                    break;
                default:
                    System.exit(0);
            }
        }else {
            Utils.ShowMessage(login_error_lbl, "Username and Password doesn't match or user couldn't be found. Contact Administrator", 5.0, Color.RED);
        }

    }
}