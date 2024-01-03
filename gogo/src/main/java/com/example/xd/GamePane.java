package com.example.xd;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class GamePane{

    private final Pane pane;
    private final int size;
    GUIPawn[][] pawnsGrid;
        private ClientConnection clientConnection;
    ArrayList<GUISquare> squares = new ArrayList<>();

    public GamePane(Pane pane, int size, Button okButton, TextField textField, Button passButton, Button surrenderButton) throws UnknownHostException, IOException
    {
        this.pane = pane;
        this.size = size;

        initBoard();
        clientConnection = new ClientConnection("localhost", 8888, pawnsGrid, pane, passButton, surrenderButton);
        Thread thread = new Thread(clientConnection);
        thread.start();

        // okButton.setOnMouseClicked(e -> {handleMouseClick(textField);});
        // passButton.setOnMouseClicked(e -> {
        //     System.out.println("pass");
        // });
        // surrenderButton.setOnMouseClicked(e -> {
        //     System.out.println("surrender");
        // });



    }




    private void initBoard()
    {

        pawnsGrid = new GUIPawn[size][size];

        for (int i = 0; i< size * size; i++)
        {
            GUISquare sq = new GUISquare(size);
            pane.getChildren().add(sq);
            squares.add(sq);
        }
        for (GUISquare sq : squares)
        {
            GUIPawn pawn = new GUIPawn(sq.getXpos(), sq.getYpos(), sq.getRow(), sq.getColumn());
            pawnsGrid[sq.getRow()][sq.getColumn()] = pawn;
            pane.getChildren().add(pawn);

        }
    }
    private void handleMouseClick(TextField textField){
            String text = textField.getText();
            String splitText[] = text.split(" ");
            pawnsGrid[Integer.valueOf(splitText[0])][Integer.valueOf(splitText[1])].setBlack();
            textField.setText("");
    }
    
}


