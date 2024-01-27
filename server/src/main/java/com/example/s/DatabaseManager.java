
/*
Baza go_games

Tabela1 - game
id / date_column / index_gry


Tabela2 - board
id / index_gry / board 

*/
package com.example.s;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

final public class DatabaseManager implements IDatabase
{
    private static DatabaseManager instance;
    private final static String jdbcUrl = "jdbc:mariadb://localhost:3306/go_games?useSSL=false";
    private final static String username = "server";
    private final static String password = "GoGoPowerRangers";
    private static Connection connection = null;

    static {
        try {
            connection = DriverManager.getConnection(jdbcUrl, username, password);
            // Perform operations on the MariaDB database here
            System.out.println("Connected to MariaDB database!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static synchronized DatabaseManager getInstance()
    {
        if (instance == null)
        {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public ArrayList<String> getGameSave(int nb)
    {
        ArrayList<String> gameSave = new ArrayList<String>();
        String query = "SELECT board FROM board WHERE index_gry = ?;";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, nb);

            try{
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                {
                    gameSave.add(resultSet.getString("board"));
                    
                }
                return gameSave;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return new ArrayList<String>();
    }
    public ArrayList<String[]> getGameList()
    {
        ArrayList<String[]> gameList = new ArrayList<String[]>();
        String query = "SELECT index_gry, date_column FROM game;";
        try
        {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            try{
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next())
                {
                    String[] game = new String[2];
                    game[0] = resultSet.getString("index_gry");
                    game[1] = resultSet.getString("date_column");
                    gameList.add(game);
                    
                }
                return gameList;
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }

        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return new ArrayList<String[]>();
    }

    public int lasBoardIndex() {
        int ostatniIndex = -1;  // Domyślna wartość, jeśli nie można pobrać wyniku // Tworzenie zapytania SQL
            String sql = "SELECT MAX(index_gry) AS index_gry FROM board;";
            try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                
                // Wykonanie zapytania
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    
                    // Przetwarzanie wyników
                    if (resultSet.next()) {
                        ostatniIndex = resultSet.getInt("index_gry");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        return ostatniIndex;
    }
    public void addBoardString(String boardString, int index)
    {
        String sql = "INSERT INTO board (index_gry, board) VALUES (?, ?);";
        try  
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, index);  // index_gry
            preparedStatement.setString(2, boardString);  // board
            
            // Wykonanie zapytania
            int rowsAffected = preparedStatement.executeUpdate();
            
        }
        catch (SQLException e)
        {
        e.printStackTrace();
        }
    }
    public int addNewGame()
    {
        int gameIndex = lasBoardIndex() + 1;
        LocalDateTime now = LocalDateTime.now();
        String sql = "INSERT INTO game (date_column, index_gry) VALUES (?, ?);";
        try  
        {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setObject(1, now);  // index_gry
            preparedStatement.setInt(2,gameIndex);  // board
            
            // Wykonanie zapytania
            int rowsAffected = preparedStatement.executeUpdate();

            return gameIndex;
            
        }
        catch (SQLException e)
        {
        e.printStackTrace();
        }
        return -1;
    }


}