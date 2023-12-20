package com.example.xd;

import java.util.ArrayList;
import java.util.Scanner;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class GamePane {

    private final Pane pane;
    private final int size;

    public GamePane(Pane pane, int size, Button okButton, TextField textField)
    {
        this.pane = pane;
        this.size = size;

        ArrayList<GUISquare> squares = new ArrayList<>();
        GUIPawn[][] pawnsGrid = new GUIPawn[size][size];

        for (int i = 0; i< size * size; i++)
        {
            GUISquare sq = new GUISquare(size);
            pane.getChildren().add(sq);
            squares.add(sq);
        }
        for (GUISquare sq : squares)
        {
            GUIPawn pawn = new GUIPawn(sq.getXpos(), sq.getYpos());
            pawnsGrid[sq.getRow()][sq.getColumn()] = pawn;
            pane.getChildren().add(pawn);

        }

        okButton.setOnMouseClicked(e -> {
            String text = textField.getText();
            String splitText[] = text.split(" ");
            pawnsGrid[Integer.valueOf(splitText[0])][Integer.valueOf(splitText[1])].setBlack();
            textField.setText("");
        });



    }
    
}


