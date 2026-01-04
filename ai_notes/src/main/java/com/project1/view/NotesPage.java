package com.project1.view;

import java.util.List;

import com.sun.glass.events.MouseEvent;
import com.sun.javafx.geom.Rectangle;
import com.sun.org.apache.xml.internal.security.keys.content.KeyValue;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class NotesPage {
    
    private VBox chatBox = new VBox(15);
    ScrollPane scrollPane = null;
    Scene notesScene;
    String userName;
    FormatController formatController = new FormatController();
    NotesController notesController = new NotesController();

    public void setScene(Scene notesScene) {
        this.notesScene = notesScene;
    }

    public void userName(String userName){
        this.userName = userName;
    }

    public Parent getView(Runnable back){
        scrollPane = new ScrollPane(chatBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(false);
        scrollPane.setOnScroll(event -> event.consume());

        Platform.runLater(() -> scrollPane.setVvalue(1.0));
        chatBox.setPadding(new Insets(10));
        chatBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        List<Note> notes = notesController.getAllNotesForUser(userName);
        System.out.println(notes);
        notes.forEach(note ->{

            //addUserBubble
            //addBotBubble

            chatBox.getChildren().add(createCard(note.getQuestion(), note.getAnswer()));
        });

        Label title = new Label("Notes");
        title.setStyle("-fx-text-fill: white");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color:rgb(25, 73, 109); -fx-text-fill: white; -fx-background-radius: 20; -fx-font-size: 15px");
        backButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                back.run();
            }
            
        });

        HBox titleBar = new HBox(50, backButton, title);
        titleBar.setPadding(new Insets(10));
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setPrefHeight(50);
        titleBar.setStyle("-fx-background-color:rgb(38, 38, 41); -fx-text-fill: white;");

        VBox root = new VBox(10, titleBar, scrollPane);
        root.setStyle("-fx-background-color: black; -fx-font-size: 20px; -fx-font-family: 'Segoe UI';");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        Rectangle clip = new Rectangle(300, 650);
        clip.setArcHeight(40);
        clip.setArcWidth(40);
        root.setClip(clip);

        return root;
    }

    private Pane createCard(String title, String expandedContent) {
        
        VBox card = new VBox();
        card.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
        card.setPadding(new Insets(10));
        card.setMaxWidth(Double.MAX_VALUE);
        card.setStyle("-fx-background-color:rgba(34, 17, 129, 0.42); -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0.5, 0.2);");
        card.setSpacing(10);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; ");

        // The expanded content area initially hidden
        Label contenLabel = new Label(expandedContent);
        contenLabel.setWrapText(true);
        contenLabel.setPadding(new Insets(10, 0, 0, 0));
        contenLabel.setMaxWidth(380);
        contenLabel.setVisible(false);
        contenLabel.setManaged(false);
        contenLabel.setStyle("-fx-font-size: 14px");

        card.getChildren().addAll(titleLabel, contenLabel);

        // Toggle expand/collapse on click
        card.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            boolean isVisible = contentLabel.isVisible();
            if(isVisible){
                collapse(contenLabel);
            } else {
                expand(contenLabel);
            }
        });

        return card;
    }

    private void expand(Label content){
        content.setVisible(true);
        content.setManaged(true);

        //Animate height grow
        content.setOpacity(0);
        Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(content.opacityProperty(), 0)), 
        new KeyFrame(Duration.millis(250), KeyValue(content.opacityProperty(), 1))
        );
        fadeIn.play();
    }

    private void collapse(Label content){

        // Animate height shrink
        Timeline fadeOut = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(content.opacityProperty(), 1)), 
        new KeyFrame(Duration.millis(250), KeyValue(content.opacityProperty(), 0))
        );
        fadeOut.setOnFinished(e -> {
            content.setVisible(false);
            content.setManaged(false);
        });
        fadeOut.play();
    }
}
