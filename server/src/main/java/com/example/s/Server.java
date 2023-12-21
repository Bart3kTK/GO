package com.example.s;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

import com.example.s.engine.Engine;
import com.example.s.players.IPlayer;
import com.example.s.players.Player;
import com.example.s.board.*;

public class Server
{    
    public static void main(String[] args) 
    {
        start();
    }

    private static void start()
    {
        Queue<IPlayer> queue = new LinkedList<IPlayer>();

        try (ServerSocket serverSocket = new ServerSocket(8888)) {
 
            System.out.println("Server is listening on port 8888");
 
            while (true) 
            {
                Socket playerSocket = serverSocket.accept();
                System.out.println("Dolaczyl nowy uzytkownik!");

                ClientInputReader inputReader = new ClientInputReader(playerSocket);

                // oczekujemy stringa w postaci: "pvp/bot 9x9/13x13/19/19
                String userPreferences = inputReader.readInput();
                System.out.println(userPreferences);
                String[] splitedUserPreferences = userPreferences.split(" ");

                if (splitedUserPreferences[0].equals("bot"))
                {
                    System.out.println("wybral bota");
                    // new bot_game(playerSocket)
                    //TU BYL KONFLIKT
                    // new bot_game(player)

                }

                else if (queue.isEmpty())
                {
                    IPlayer player = new Player(playerSocket, inputReader);
                    queue.add(player);
                }
                else
                {
                    IPlayer player1 = queue.poll();
                    IPlayer player2 = new Player(playerSocket, inputReader);

                    //nowy wątek dla pvp
                    if (true) //if player1.isConnected() && player2.isConnected()
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