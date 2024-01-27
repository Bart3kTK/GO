package com.example.s;

import java.util.ArrayList;

public interface IDatabase {
    public ArrayList<String> getGameSave(int nb);
    public ArrayList<String[]> getGameList();

    public int lasBoardIndex();
    public void addBoardString(String board, int index);
    public int addNewGame();

}
