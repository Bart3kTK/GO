package com.example.s;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;

import com.example.s.engine.Engine;
import com.example.s.logger.MyLogger;
import com.example.s.players.IPlayer;
import com.example.s.players.Player;
import com.example.s.board.Board;

public class Server
{    
    public static void main(String[] args) 
    {
        MyLogger.loggerConfig();
        MyLogger.logger.log(Level.INFO, "Huj");
        start();
    }

    private static void start()
    {
        Queue<IPlayer> queue9x9 = new LinkedList<IPlayer>();
        Queue<IPlayer> queue13x13 = new LinkedList<IPlayer>();
        Queue<IPlayer> queue19x19 = new LinkedList<IPlayer>();

        try (ServerSocket serverSocket = new ServerSocket(8888)) {
 
            System.out.println("Server is listening on port 8888");
 
            while (true) 
            {
                Socket playerSocket = serverSocket.accept();
                System.out.println("Dolaczyl nowy uzytkownik!");

                ClientInputReader inputReader = new ClientInputReader(playerSocket);

                // oczekujemy stringa w postaci: "pvp/bot 9x9/13x13/19x19" update: ZROBIONE I TAK SIE DZIEJE
                String userPreferences = inputReader.readInput();
                System.out.println(userPreferences);
                String[] splitedUserPreferences = userPreferences.split(" ");

                if (splitedUserPreferences[0].equals("bot"))
                {
                    System.out.println("wybral bota");
                    // new bot_game(playerSocket)

                }                       // tutaj będzie builder

                else if (splitedUserPreferences[1].equals("19"))
                {
                    if (queue19x19.isEmpty())
                    {
                        IPlayer player = new Player(playerSocket, inputReader);
                        queue19x19.add(player);
                    }
                    else
                    {
                        IPlayer player1 = queue19x19.poll();
                        IPlayer player2 = new Player(playerSocket, inputReader);

                        //nowy wątek dla pvp
                        if (player1.isConnected() && player2.isConnected()) //if player1.isConnected() && player2.isConnected()
                        {
                            Engine engine = new Engine(player1, player2, new Board(19, 19));
                            engine.start();
                        }
                    }
                }

                else if (splitedUserPreferences[1].equals("13"))
                {
                    if (queue13x13.isEmpty())
                    {
                        IPlayer player = new Player(playerSocket, inputReader);
                        queue13x13.add(player);
                    }
                    else
                    {
                        IPlayer player1 = queue13x13.poll();
                        IPlayer player2 = new Player(playerSocket, inputReader);

                        //nowy wątek dla pvp
                        if (player1.isConnected() && player2.isConnected()) //if player1.isConnected() && player2.isConnected()
                        {
                            Engine engine = new Engine(player1, player2, new Board(13, 13));
                            engine.start();
                        }
                    }
                }

                else if (queue9x9.isEmpty())
                {
                    IPlayer player = new Player(playerSocket, inputReader);
                    queue9x9.add(player);
                }
                else
                {
                    IPlayer player1 = queue9x9.poll();
                    IPlayer player2 = new Player(playerSocket, inputReader);

                    //nowy wątek dla pvp
                    if (player1.isConnected() && player2.isConnected()) //if player1.isConnected() && player2.isConnected()
                    {
                        Engine engine = new Engine(player1, player2, new Board(9, 9));
                        engine.start();
                    }
                }
                // TODO: Tu trzeba wymyslic thread ktory uruchamai sie po dalaczeniu kazdego gracza
                // TODO: w ktorym bedzie jakas kolejka dolaczania do graczy
            }
 
        } catch (IOException ex) {
            System.out.println("Serwer o takim adresie juz istnieje");
        }
    }
}