package com.project1.view;

import org.commonmark.parser.Parser; 

import org.commonmark.renderer.text.TextContentRenderer;
import org.fxmisc.richtext.InlineCssTextArea;

import com.project1.controller.AiApiController;
import com.project1.controller.FormatController;
import com.project1.controller.NotesController;
import com.project1.model.Note;
import com.project1.utilities.Snackbar;
import javafx.scene.shape.Rectangle; 
import javafx.scene.paint.Color; 

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class SearchPage {
    
    Scene searchScene;
    Stage stage;
    private VBox chatBox = new VBox(15);
    ScrollPane scrollPane = null;
    String userName;

    private Parser markdownParser = Parser.builder().build();
    private TextContentRenderer renderer = TextContentRenderer.builder().build();

    AiApiController controller = new AiApiController();
    FormatController formatController = new FormatController();
    NotesController notesController = new NotesController();

    public void setScene(Scene searchScene){
        this.searchScene = searchScene;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    public void set_userName(String userName){
        this.userName = userName;
    }

    public Parent getView(Runnable back){
        TextField inputField = new TextField();
        inputField.setPromptText("Ask something...");
        inputField.setStyle("-fx-background-radius: 20");
        inputField.setFont(Font.font("Segoe UI", 14));
        inputField.setPrefHeight(35);
        inputField.setPrefWidth(200);

        Button sendBtn = new Button("Send");
        sendBtn.setFont(Font.font("Segoe UI Semibold", 14));
        sendBtn.setStyle("-fx-background-color: #0078D4; -fx-text-fill: white; -fx-background-radius: 20;");
        sendBtn.setPrefHeight(35);
        sendBtn.setDefaultButton(true);

        sendBtn.setOnAction(e -> {
            String question = inputField.getText();

            if(!question.isBlank()){
                addUserBubble(question);
                String response = controller.callGeminiAPI(question);
                System.out.println(response);
                addBotBubble(question, response);
                inputField.clear();
            }
        });

        scrollPane = new ScrollPane(chatBox);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent");
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(false);
        
        scrollPane.setOnScroll(event -> event.consume());
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
        chatBox.setBackground(new Background(new BackgroundFill(Color.TRANSPARENT, CornerRadii.EMPTY, Insets.EMPTY)));

        Label title = new Label("Search");
        title.setStyle("-fx-text-fill: white;");

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color:rgb(25, 73, 109); -fx-text-fill: white; -fx-background-radius: 20; -fx-font-size: 15px;");
        
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

        HBox inputBar = new HBox(10, inputField, sendBtn);
        inputBar.setPadding(new Insets(10, 10, 20, 10));
        inputBar.setAlignment(Pos.CENTER);
        inputBar.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));

        VBox root = new VBox(10, titleBar, scrollPane, inputBar);
        root.setStyle("-fx-background-color: black; -fx-font-size: 20px; -fx-font-family: 'Segoe UI';");
        VBox.setVgrow(scrollPane, Priority.ALWAYS);
        Rectangle clip = new Rectangle(300, 650);
        clip.setArcHeight(40);
        clip.setArcWidth(40);
        root.setClip(clip);
         
        return root;
    }

    private void addUserBubble(String text){

        Label label = new Label(text);
        label.setWrapText(true);
        label.setStyle("-fx-background-color:rgba(237, 66, 94, 0.42); -fx-padding: 10; -fx-background-radius: 10; -fx-font-size: 14px; -fx-text-fill: white");

        HBox container = new HBox(label);
        container.setAlignment(Pos.CENTER_RIGHT);
        container.setPadding(new Insets(2));
        chatBox.getChildren().add(container);
    }

    private void addBotBubble(String question, String markdown){
        
        String plainText = renderer.render(markdownParser.parse(markdown));
        
        InlineCssTextArea area = new InlineCssTextArea();
        area.replaceText(plainText);
        area.setWrapText(true);
        area.setEditable(false);
        area.setPrefWidth(600);
        area.setStyle("-fx-background-color:rgba(34, 17, 129, 0.42);" + "-fx-font-family: 'Segoe UI';" + "-fx-font-size: 14px;" + "-fx-padding: 10px;" + "-fx-background-radius: 12;");

        formatController.formatAndDisplayAIResponse(area, plainText);
        Platform.runLater(() -> area.scrollYToPixel(0));
        area.moveTo(0); // Move caret to start
        Platform.runLater(() -> area.requestFollowCaret());

        area.textProperty().addListener((obs, old, val) -> {
            area.setPrefHeight(area.getTotalHeightEstimate());
        });

        HBox container = new HBox(area);
        container.setAlignment(Pos.CENTER_LEFT);
        
        Label add = new Label("Add to Notes");
        add.setStyle("-fx-font-Size: 10px");
        VBox vb = new VBox(10, container, add);
        add.setOnMouseClicked(new EventHandler<Event>() {

            @Override
            public void handle(Event arg0) {
                Note note = new Note();
                note.setQuestion(question);
                note.setAnswer(plainText);
                note.setUserName(userName);
                notesController.addNote(note);
                Snackbar.show(stage, "Note Successfully Added");
                vb.getChildren().remove(1);
            }
            
        });

        vb.setAlignment(Pos.CENTER_LEFT);
        vb.setPadding(new Insets(2));

        chatBox.getChildren().add(vb);
        Platform.runLater(() -> scrollPane.setVvalue(1.0));
    }


}
