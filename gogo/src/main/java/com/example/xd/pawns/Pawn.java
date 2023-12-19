package com.example.xd.pawns;


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

    public String getColor()
    {
        return this.color;
    }

    
}
