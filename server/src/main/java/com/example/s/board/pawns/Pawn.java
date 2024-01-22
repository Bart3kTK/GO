package com.example.s.board.pawns;


/*
 *  @brief The class represents default Pawn (pionek xd) 
 *  The Black and white pawn extends from this class. It allows to use factory pattern.
 * 
 *      TO DOs:
 *      - implement composit pattern to store neighbors
 *      - implement factory method pattern to make proper pawns 
 */
public abstract class Pawn 
{
    protected String color;
    protected int row;
    protected int column;
    protected boolean isChecked = false;
    protected Pawn[] neighbours = new Pawn[4];

    public String getColor()
    {
        return this.color;
    }
    public Pawn[] getNeighbours()
    {
        return this.neighbours;
    }
    public void setNeighbours(Pawn[] neighbours)
    {
        this.neighbours = neighbours;
    }

    public int getRow()
    {
        return row;
    }

    public int getColumn()
    {
        return column;
    }

    public void setIsChecked(boolean isChecked)
    {
        this.isChecked = isChecked;
    }

    public boolean getChecked()
    {
        return isChecked;
    }

    public String toString()
    {
        return Integer.toString(row) + " " + Integer.toString(column) + " " + color;
    }

    public String toStringClearMessage()
    {
        return Integer.toString(row) + " " + Integer.toString(column) + " clear";
    }
}
