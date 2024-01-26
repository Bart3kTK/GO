package com.example.s.board;

public class BoardFactory 
{
    public Board createBoard(String stringSize)
    {
        int size = Integer.parseInt(stringSize);

        if (size == 9 || size == 13 || size == 19)
        {
            return new Board(size, size);
        }
        else
        {
            return null;
        }
    }
}
