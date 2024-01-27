package com.example.xd;
import java.io.IOException;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;



public class GameTypeChooser{

    private String gameType;

    public GameTypeChooser(Stage stage) throws IOException{

        Parent parent = FXMLLoader.load(getClass().getResource("GameTypeChooser.fxml"));

        Scene scene = new Scene(parent);

        Button botButton = (Button) scene.lookup("#botButton");
        Button multiButton = (Button) scene.lookup("#multiButton");
        Button replayButton = (Button) scene.lookup("#replays");


        botButton.setOnMouseClicked(e -> {
            try {
              new BoardTypeChooser(stage, "bot");
            } catch (IOException e1) {
              e1.printStackTrace();
            }
        });

        multiButton.setOnMouseClicked(e -> {
            try {
              new BoardTypeChooser(stage, "pvp");
            } catch (IOException e1) {
              e1.printStackTrace();
            }
        });
        replayButton.setOnMouseClicked(e -> {
            try {
              new BoardTypeChooser(stage, "replay");
            } catch (IOException e1) {
              e1.printStackTrace();
            }
        });




    //   //pane.setClip(new Rectangle(0,0, Settings.getWindowWdth(), Settings.getWindowHeight()));

    // //   stage.setHeight(Settings.getWindowHeight());
    // //   stage.setWidth(Settings.getWindowWdth());
      stage.setOnCloseRequest(e ->{
        Platform.exit();
        System.exit(0);
      });

      stage.setTitle("GO");
      stage.setScene(scene);
      stage.show();

    }

    public String getMessage()
    {
      return gameType;
    }
}
