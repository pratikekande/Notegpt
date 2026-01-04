package com.project1.utilities;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.SequentialTransition;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Snackbar {
    
    public static void show(Stage stage, String message) {
        Label label = new Label(message);
        label.setStyle("-fx-background-radius: 20; -fx-background-color: #323232; -fx-text-fill: white; -fx-padding: 10 20 10 20;");
        label.setOpacity(0);

        Popup popup = new Popup();
        popup.getContent().add(label);
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);

        // Calculate position: bottom center of stage
        double x = stage.getX() + (stage.getWidth() / 2) - 100;
        double y = stage.getY() + stage.getHeight() - 100;
        popup.show(stage, x, y);

        // Fade in
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), label);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        // Wait
        PauseTransition wait = new PauseTransition(Duration.seconds(2));

        // Fade out
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), label);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);
        fadeOut.setOnFinished(e -> popup.hide());

        // Play all
        SequentialTransition seq = new SequentialTransition(fadeIn, wait, fadeOut);
        seq.play();

    }
}
