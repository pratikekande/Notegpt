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
    
    Stage ai_primaryStage;
    String ai_userName;
    Scene ai_homePageScene, ai_searchPageScene, ai_notesPageScene;

    public void setScene(Scene ai_homePagScene) {
        this.ai_homePageScene = ai_homePagScene;
    }

    public void setStage(Stage ai_stage){
        this.ai_primaryStage = ai_stage;
    }

    public void setUserName(String ai_userName){
        this.ai_userName = ai_userName;
    }

    public Parent getView(Runnable ai_logout) {
        Circle ai_search_circle = new Circle();
        ai_search_circle.setRadius(70);
        ai_search_circle.setFill(Color.WHITE);

        Circle ai_notes_circle = new Circle();
        ai_notes_circle.setRadius(70);
        ai_notes_circle.setFill(Color.WHITE);

        Text ai_label_search = new Text("Search");
        ai_label_search.setFill(Color.BLACK);
        ai_label_search.setFont(Font.font(20));

        Text ai_label_note = new Text("Search");
        ai_label_note.setFill(Color.BLACK);
        ai_label_note.setFont(Font.font(20));

        StackPane ai_stack_search_circle = new StackPane(ai_search_circle, ai_label_search);
        ai_stack_search_circle.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event arg0) {
                openSearchPage();
                System.out.println("called");
            }
            
        });

        StackPane ai_stack_notes_circle = new StackPane(ai_notes_circle, ai_label_note);
        ai_stack_notes_circle.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event arg0) {
                openNotesPage();
                System.out.println("called");
            }
            
        });  
        
        VBox ai_vb = new VBox(60, ai_stack_search_circle, ai_stack_notes_circle);
        ai_vb.setStyle("-fx-alignment : center");

        Label ai_title = new Label("Chat App");
        ai_title.setStyle("-fx-text-fill : white");

        Button ai_backButton = new Button("Back");
        ai_backButton.setStyle("-fx-background-color:rgb(25, 73, 109); -fx-text-fill: white; -fx-background-radius: 20; -fx-font-size: 15px");
        ai_backButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                ai_logout.run();
            }
            
        });

        HBox ai_titleBar = new HBox(50, ai_backButton, ai_title);
        ai_titleBar.setPadding(new Insets(10));
        ai_titleBar.setAlignment(Pos.CENTER_LEFT);
        ai_titleBar.setStyle("-fx-background-color:rgb(38, 38, 41); -fx-text-fill: white;");

        VBox ai_root = new VBox(120, ai_titleBar, ai_vb);
        ai_root.setStyle("-fx-background-color: black; -fx-alignment: top center; -fx-pref-width: 500; -fx-pref-height: 650; -fx-font-size: 20px; -fx-font-family: 'Segoe UI;");

        Rectangle ai_clip = new Rectangle(300, 650);
        ai_clip.setArcWidth(40);
        ai_clip.setArcHeight(40);
        ai_root.setClip(ai_clip);

        return ai_root;
        
    }

    public void openSearchPage() {
        SearchPage ai_searchPage = new SearchPage();
        ai_searchPage.set_ai_userName(ai_userName);
        ai_searchPage.set_ai_stage(ai_primaryStage);
        ai_searchPageScene = new Scene(ai_searchPage.getView(this::backToHomePage), 300, 650);
        ai_searchPageScene.setFill(Color.TRANSPARENT);
        ai_searchPage.setScene(ai_searchPageScene);
        ai_primaryStage.setScene(ai_searchPageScene);
    }

    public void openNotesPage() {
        NotesPage ai_notePage = new NotesPage();
        ai_notePage.set_ai_userName(ai_userName);
        ai_notesPageScene = new Scene(ai_notePage.getView(this::backToHomePage), 300, 650);
        ai_notesPageScene.setFill(Color.TRANSPARENT);
        ai_notePage.setScene(ai_notesPageScene);
        ai_primaryStage.setScene(ai_notesPageScene);
    }

    public void backToHomePage() {
        ai_primaryStage.setScene(ai_homePageScene);
    }
}
