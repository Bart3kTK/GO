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



public class BoardTypeChooser{

    private String gameType;

    public BoardTypeChooser(Stage stage, String gameType) throws IOException{

        if (gameType.equals("replay"))
        {
            new GUIBoard(stage, 0, gameType);
        }
        else
        {
            Parent parent = FXMLLoader.load(getClass().getResource("BoardTypeChooser.fxml"));


            Scene scene = new Scene(parent);

            Button button9 = (Button) scene.lookup("#button9");
            Button button13 = (Button) scene.lookup("#button13");
            Button button19 = (Button) scene.lookup("#button19");



            button9.setOnMouseClicked(e -> {
                try {
                    new GUIBoard(stage, 9, gameType);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
            button13.setOnMouseClicked(e -> {
                try {
                    new GUIBoard(stage, 13, gameType);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            });
            button19.setOnMouseClicked(e -> {
                try {
                    new GUIBoard(stage, 19, gameType);
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
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

    }

    public String getMessage()
    {
      return gameType;
    }
}
