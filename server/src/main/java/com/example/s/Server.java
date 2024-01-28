package com.example.s;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import com.example.s.engine.Engine;
import com.example.s.engine.EngineFactory;
import com.example.s.engine.IEngine;
import com.example.s.logger.MyLogger;
import com.example.s.players.BotPlayer;
import com.example.s.players.IPlayer;
import com.example.s.players.Player;
import com.example.s.board.Board;

public class Server
{    
    public static void main(String[] args) 
    {
        MyLogger.loggerConfig();
        // System.out.println("XD\n\n\n\n\n\n\n\nXD");
        // DatabaseManager databaseManager = DatabaseManager.getInstance();
        // int indexBoard = databaseManager.addNewGame();
        // System.out.println("indexBoard: " + indexBoard);
        // databaseManager.addBoardString("Elo!", indexBoard);
        // for (String s : databaseManager.getGameSave(indexBoard))
        // {
        //     System.out.println(s);
        // }
        // for (String[] s : databaseManager.getGameList())
        // {
        //     System.out.println(s[0] + " " + s[1]);
        // }
        start();
    }

    private static void start()
    {
        try (ServerSocket serverSocket = new ServerSocket(8888)) 
        {
            EngineFactory engineFactory = new EngineFactory();
            System.out.println("Server is listening on port 8888");
 
            while (true) 
            {
                Socket playerSocket = serverSocket.accept();
                System.out.println("Dolaczyl nowy uzytkownik!");

                ClientInputReader inputReader = new ClientInputReader(playerSocket);

                // oczekujemy stringa w postaci: "pvp/bot 9x9/13x13/19x19" update: ZROBIONE I TAK SIE DZIEJE
                String userPreferences = inputReader.readInput();
                IPlayer player = new Player(playerSocket, inputReader);

                IEngine engine = engineFactory.getEngine(userPreferences, player);

                if (engine == null)
                {
                    continue;
                }

                engine.start();
            }
 
        } catch (IOException ex) {
            System.out.println("Serwer o takim adresie juz istnieje");
        }
    }
}