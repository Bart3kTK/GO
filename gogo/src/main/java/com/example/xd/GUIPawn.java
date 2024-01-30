package com.example.xd;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class GUIPawn extends Circle{
    private boolean isClicked = false;
    private int row;
    private int column;
    private boolean isLocked = true;

    public GUIPawn(double x, double y, int row, int column)
    {
        this.row = row;
        this.column = column;
        setCenterX(x);
        setCenterY(y);
        setRadius(20);
        setPickOnBounds(true);
        setClear();

        setOnMouseClicked(e -> {
            if (!isLocked){
                isClicked = true;
            }

            
            

        });
    }

    public void setClear()
    {
        setFill(Color.TRANSPARENT);
        setStroke(Color.TRANSPARENT);
        isClicked = false;
    }
    public void setBlack()
    {
        setFill(Color.BLACK);
        setStroke(Color.BLACK);
        isClicked = false;
        isLocked = true;
    }
    public void setWhite()
    {
        setFill(Color.WHITE);
        setStroke(Color.BLACK);
        isClicked = false;
        isLocked = true;
    }
    public boolean isClicked()
    {
        return isClicked;
    }
    public void setClicked(boolean isClicked)
    {
        this.isClicked = isClicked;
    }
    public int getColumn()
    {
        return column;
    }
    public int getRow()
    {
        return row;
    }
    public void lock()
    {
        isLocked = true;
    }
    public void unlock()
    {
        isLocked = false;
    }
    public boolean isLocked()
    {
        return isLocked;
    }


    

}

