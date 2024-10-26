package group.one.librarymanagementsystem.librarymanagementsystemui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static void ShowMessage(Label l, String s, Double d, Color c)
    {
        l.setTextFill(c);
        l.setText(s);
        l.setVisible(true);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(d), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                l.setVisible(false);
            }
        }));
        timeline.play();
    }

    public static boolean containsSpacesAndSpecialChars(String input) {
        String regex = ".*[\\s\\W].*"; // \s matches whitespace, \W matches non-word characters

        // Return true if the input matches the regex pattern
        return input != null && input.matches(regex);
    }

    public static boolean isValidEmail(String email) {
        String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (email == null) return false; // Check for null input
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
