package com.project1.view;

import java.util.List;

import com.project1.controller.FormatController;
import com.project1.controller.NotesController;
import com.project1.model.Note;
import javafx.scene.input.MouseEvent; 
import javafx.scene.shape.Rectangle; 


import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
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

    private Pane createCard(String title, String expandedcontentt) {
        
        VBox card = new VBox();
        card.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(8), Insets.EMPTY)));
        card.setPadding(new Insets(10));
        card.setMaxWidth(Double.MAX_VALUE);
        card.setStyle("-fx-background-color:rgba(34, 17, 129, 0.42); -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0.5, 0.2);");
        card.setSpacing(10);

        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; ");

        // The expanded contentt area initially hidden
        Label contentLabel = new Label(expandedcontentt);
        contentLabel.setWrapText(true);
        contentLabel.setPadding(new Insets(10, 0, 0, 0));
        contentLabel.setMaxWidth(380);
        contentLabel.setVisible(false);
        contentLabel.setManaged(false);
        contentLabel.setStyle("-fx-font-size: 14px");

        card.getChildren().addAll(titleLabel, contentLabel);

        // Toggle expand/collapse on click
        card.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            boolean isVisible = contentLabel.isVisible();
            if(isVisible){
                collapse(contentLabel);
            } else {
                expand(contentLabel);
            }
        });

        return card;
    }

    private void expand(Label contentt){
        contentt.setVisible(true);
        contentt.setManaged(true);

        //Animate height grow
        contentt.setOpacity(0);
        Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(contentt.opacityProperty(), 0)), 
        new KeyFrame(Duration.millis(250),new KeyValue(contentt.opacityProperty(), 1))
        );
        fadeIn.play();
    }

    private void collapse(Label contentt){

        // Animate height shrink
        Timeline fadeOut = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(contentt.opacityProperty(), 1)), 
        new KeyFrame(Duration.millis(250),new KeyValue(contentt.opacityProperty(), 0))
        );
        fadeOut.setOnFinished(e -> {
            contentt.setVisible(false);
            contentt.setManaged(false);
        });
        fadeOut.play();
    }
}
