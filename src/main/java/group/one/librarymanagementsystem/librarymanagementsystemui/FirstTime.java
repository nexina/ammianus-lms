package group.one.librarymanagementsystem.librarymanagementsystemui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.sql.*;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.stage.Stage;
import org.json.JSONObject;

public class FirstTime {
    @FXML
    public TextField addressField;
    @FXML
    public TextField portField;
    @FXML
    public TextField dbusernameField;
    @FXML
    public PasswordField dbpasswordField;
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField currencyField;
    @FXML
    private Label errorLabel;

    DevTools dt = new DevTools();
    Database db = new Database();

    public void initialize() {
        errorLabel.setVisible(false);

        Pattern digitPattern = Pattern.compile("\\d*");
        UnaryOperator<TextFormatter.Change> filter = change -> {
            if (digitPattern.matcher(change.getControlNewText()).matches()) {
                return change;
            } else {
                return null;
            }
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        portField.setTextFormatter(textFormatter);
    }

    public void save(ActionEvent event) throws IOException{
        if(addressField.getText().isEmpty() || portField.getText().isEmpty() || usernameField.getText().isEmpty() || passwordField.getText().isEmpty() || currencyField.getText().isEmpty() ||dbusernameField.getText().isEmpty() ||nameField.getText().isEmpty() || emailField.getText().isEmpty())
        {
            Utils.ShowMessage(errorLabel, "Fields can not be empty!", 5.0, Color.RED);
            return;
        }

        String address = addressField.getText();
        int port = Integer.parseInt(portField.getText());
        String db_name = "library";

        String test_url = "jdbc:mysql://"+address+":"+port+"/"+db_name;
        String insertQuery = "INSERT INTO users (role, name, email, username, password) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(test_url, dbusernameField.getText(), dbpasswordField.getText());
             PreparedStatement pstmt = connection.prepareStatement(insertQuery)) {

            pstmt.setString(1, "Librarian");
            pstmt.setString(2, nameField.getText());
            pstmt.setString(3, emailField.getText());
            pstmt.setString(4, usernameField.getText());
            pstmt.setString(5, passwordField.getText());

            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Task was successful. Rows affected: " + rowsAffected);

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("address", address);
                jsonObject.put("port", port);
                jsonObject.put("db-username", dbusernameField.getText());
                jsonObject.put("db-password", dbpasswordField.getText());
                jsonObject.put("currency", currencyField.getText());
                dt.createConfig(jsonObject);

                Utils.ShowMessage(errorLabel, "Successfully Configured", 15.0, Color.GREEN);

                dt.createNewUI("loginui.fxml", "Library Management System");

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.close();
            } else {
                Utils.ShowMessage(errorLabel, "Server could not be found", 15.0, Color.RED);
            }
        } catch (SQLException e) {
            Utils.ShowMessage(errorLabel, "Server could not be found", 15.0, Color.RED);
        }
    }


}
