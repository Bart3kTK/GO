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


public class GUIBoard{

    public GUIBoard(Stage stage) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("GUIBoard.fxml"));

        Scene scene = new Scene(parent);


        Pane pane = (Pane) scene.lookup("#MyPane");
        Button surrenderButton = (Button) scene.lookup("#surrender");
        Button passButton = (Button) scene.lookup("#pass");
        Button okButton = (Button) scene.lookup("#testok");
        TextField textfield = (TextField) scene.lookup("#testarea");


        new GamePane(pane, 10, okButton, textfield, passButton, surrenderButton);


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
}
