package com.example.xd;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class GUIBoard{

    public GUIBoard(Stage stage, int size, String gameType) throws IOException{
        Parent parent = FXMLLoader.load(getClass().getResource("GUIBoard.fxml"));

        Scene scene = new Scene(parent);


        Pane pane = (Pane) scene.lookup("#MyPane");
        Button surrenderButton = (Button) scene.lookup("#surrender");
        Button passButton = (Button) scene.lookup("#pass");
        Button left = (Button) scene.lookup("#left");
        Button right = (Button) scene.lookup("#right");
        Button confirm = (Button) scene.lookup("#confirm");
        Text player1 = (Text) scene.lookup("#player1");
        Text player2 = (Text) scene.lookup("#player2");
        Text player11 = (Text) scene.lookup("#player11");
        Text player21 = (Text) scene.lookup("#player21");
        Text server = (Text) scene.lookup("#server");
        Text label = (Text) scene.lookup("#label");
        TextField area = (TextField) scene.lookup("#area");


        server.setText("ELO");
        player2.setText("dziala");
        

        Text[] texts = {player1, player2, server, label, player11, player21};
        Button[] buttons = {passButton, surrenderButton, left, right, confirm};
        Label[] labels = {};
        TextField[] textFields = {area};

        
        //new GamePane(pane, size, gameType, texts, buttons);
      if (gameType.equals("replay"))
      {
        ReplayConnection replayConnection = new ReplayConnection(pane, texts, buttons, labels, textFields, gameType, size);
        Thread thread = new Thread(replayConnection);
        thread.start();
      }
      else
      {
        ClientConnection clientConnection = new ClientConnection(pane, texts, buttons, labels, textFields, gameType, size);
        Thread thread = new Thread(clientConnection);
        thread.start();
      }

      stage.setOnCloseRequest(e ->{
        Platform.exit();
        System.exit(0);
      });

      stage.setTitle("GO");
      stage.setScene(scene);
      stage.show();

    }

}
