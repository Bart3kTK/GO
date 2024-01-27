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
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GUIBoard{

    public GUIBoard(Stage stage, int size, String gameType) throws IOException{
        ClientConnection clientConnection;
        Parent parent = FXMLLoader.load(getClass().getResource("GUIBoard.fxml"));

        Scene scene = new Scene(parent);


        Pane pane = (Pane) scene.lookup("#MyPane");
        Button surrenderButton = (Button) scene.lookup("#surrender");
        Button passButton = (Button) scene.lookup("#pass");
        Button left = (Button) scene.lookup("#left");
        Button right = (Button) scene.lookup("#right");
        Text player1 = (Text) scene.lookup("#player1");
        Text player2 = (Text) scene.lookup("#player2");
        Text server = (Text) scene.lookup("#server");

        server.setText("ELO");
        player2.setText("dziala");

        Text[] texts = {player1, player2, server};
        Button[] buttons = {passButton, surrenderButton, left, right};

        
        //new GamePane(pane, size, gameType, texts, buttons);

        clientConnection = new ClientConnection(pane, texts, buttons, gameType, size);
        Thread thread = new Thread(clientConnection);
        thread.start();


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
