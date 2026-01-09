package com.project1.view;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HomePage {
    
    Stage primaryStage;
    String userName;
    Scene homePageScene, searchPageScene, notesPageScene;

    public void setScene(Scene homePagScene) {
        this.homePageScene = homePagScene;
    }

    public void setStage(Stage stage){
        this.primaryStage = stage;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public Parent getView(Runnable logout) {
        Circle search_circle = new Circle();
        search_circle.setRadius(70);
        search_circle.setFill(Color.WHITE);

        Circle notes_circle = new Circle();
        notes_circle.setRadius(70);
        notes_circle.setFill(Color.WHITE);

        Text label_search = new Text("Search");
        label_search.setFill(Color.BLACK);
        label_search.setFont(Font.font(20));

        Text label_note = new Text("Search");
        label_note.setFill(Color.BLACK);
        label_note.setFont(Font.font(20));

        StackPane stack_search_circle = new StackPane(search_circle, label_search);
        stack_search_circle.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event arg0) {
                openSearchPage();
                System.out.println("called");
            }
            
        });

        StackPane stack_notes_circle = new StackPane(notes_circle, label_note);
        stack_notes_circle.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event arg0) {
                openNotesPage();
                System.out.println("called");
            }
            
        });  
        
        VBox vb = new VBox(60, stack_search_circle, stack_notes_circle);
        vb.setStyle("-fx-alignment : center");

        Label title = new Label("Chat App");
        title.setStyle("-fx-text-fill : white");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color:rgb(25, 73, 109); -fx-text-fill: white; -fx-background-radius: 20; -fx-font-size: 15px");
        backButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                logout.run();
            }
            
        });

        HBox titleBar = new HBox(50, backButton, title);
        titleBar.setPadding(new Insets(10));
        titleBar.setAlignment(Pos.CENTER_LEFT);
        titleBar.setStyle("-fx-background-color:rgb(38, 38, 41); -fx-text-fill: white;");

        VBox root = new VBox(120, titleBar, vb);
        root.setStyle("-fx-background-color: black; -fx-alignment: top center; -fx-pref-width: 500; -fx-pref-height: 650; -fx-font-size: 20px; -fx-font-family: 'Segoe UI;");

        Rectangle clip = new Rectangle(300, 650);
        clip.setArcWidth(40);
        clip.setArcHeight(40);
        root.setClip(clip);

        return root;
        
    }

    public void openSearchPage() {
        SearchPage searchPage = new SearchPage();
        searchPage.set_userName(userName);
        searchPage.setStage(primaryStage);
        searchPageScene = new Scene(searchPage.getView(this::backToHomePage), 300, 650);
        searchPageScene.setFill(Color.TRANSPARENT);
        searchPage.setScene(searchPageScene);
        primaryStage.setScene(searchPageScene);
    }

    public void openNotesPage() {
        NotesPage notePage = new NotesPage();
        notePage.userName(userName);
        notesPageScene = new Scene(notePage.getView(this::backToHomePage), 300, 650);
        notesPageScene.setFill(Color.TRANSPARENT);
        notePage.setScene(notesPageScene);
        primaryStage.setScene(notesPageScene);
    }

    public void backToHomePage() {
        primaryStage.setScene(homePageScene);
    }
}
