package com.example.xd;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import com.example.xd.pawns.BlackPawn;
import com.example.xd.pawns.WhitePawn;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();       //no comment
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        Board board = new Board(9, 9);
        board.putPawn(0, 0, new BlackPawn());
        board.putPawn(5, 8, new WhitePawn());

        System.out.println(board);

        board.removePawn(5, 8);
        System.out.println(board);
        launch();
        //elo
    }

}