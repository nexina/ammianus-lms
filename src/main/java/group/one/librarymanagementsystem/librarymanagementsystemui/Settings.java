package group.one.librarymanagementsystem.librarymanagementsystemui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class Settings {
    @FXML
    private TextField addressField;
    @FXML
    private TextField portField;
    @FXML
    private TextField currencyField;
    @FXML
    private Button saveButton;
    @FXML
    private Button cancelButton;
    @FXML
    private Label errorLabel;

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

        addressField.setText(DevTools.getConfig("address").toString());
        portField.setText(((Integer) DevTools.getConfig("port")).toString());
        currencyField.setText(DevTools.getConfig("currency").toString());
    }

    public void saveAction(ActionEvent event) throws IOException {
        String address = addressField.getText();
        int port = Integer.parseInt(portField.getText());
        String currency = currencyField.getText();

        if(db.testConnection(address,port)) {
            DevTools.setConfig("address", address);
            DevTools.setConfig("port", port);
            DevTools.setConfig("currency", currency);
            Utils.ShowMessage(errorLabel, "Saved Succesfully", 5.0, Color.GREEN);
        }else {
            Utils.ShowMessage(errorLabel, "Can not establish Connection!", 5.0, Color.RED);
        }
    }

    public void cancelAction(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
