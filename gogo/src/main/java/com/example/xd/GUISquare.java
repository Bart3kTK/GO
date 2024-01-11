package com.example.xd;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
//wiersze kolumny
public class GUISquare extends Rectangle {
    private static int number = 0;
    private final int index;
    private int side = 50;
    private final int size;
    private final int column;
    private final int row;
    public GUISquare(int size){
        this.size = size;
        index = number;
        number ++;
        setWidth(side);
        setHeight(side);
        setStroke(Color.BLACK);
        setFill(Color.WHITE);
        setStrokeWidth(1);
        column = index / size;
        row = index % size;
        setX(50+ side * column);
        setY(50+ side * row);
        if(index / size == size - 1)
        {
            setVisible(false);
        }
        if(index % size == size - 1)
        {
            setVisible(false);
        }
    }
    public double getXpos()
    {
        return getX();
    }
    public double getYpos()
    {
        return getY();
    }
    public int getColumn()
    {
        return column;
    }
    public int getRow()
    {
        return row;
    }

}