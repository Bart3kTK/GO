package com.example.xd;

import javafx.application.Application;

import javafx.stage.Stage;
import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    public static void main(String[] args) {
        Application.launch(args);
      }
      @Override
      public void start(Stage primaryStage) throws IOException {
        new GameTypeChooser(primaryStage);
      }
    

}