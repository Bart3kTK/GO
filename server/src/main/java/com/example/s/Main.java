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
<<<<<<< HEAD
=======

>>>>>>> main

public class Main {    
    public static void main(String[] args) 
    {

        Queue<Socket> queue = new LinkedList<Socket>();

        try (ServerSocket serverSocket = new ServerSocket(8888)) {
 
            System.out.println("Server is listening on port 8888");
 
            while (true) 
            {
                Socket player = serverSocket.accept();
                System.out.println("Dolaczyl nowy uzytkownik!");

                InputStream playerInput = player.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(playerInput));

                // oczekujemy stringa w postaci: "pvp/bot 9x9/13x13/19/19"
                String userPreferences = bufferedReader.readLine();
                String[] splitedUserPreferences = userPreferences.split(" ");

                if (splitedUserPreferences[0].equals("bot"))
                {
                    System.out.println("wybral bota");
                    // new bot_game(player)

                }

                else if(queue.isEmpty())
                {
                    queue.add(player);
                }
                else
                {
                    Socket player2 = queue.poll();
                    //nowy wątek dla pvp
                    if (player.isConnected() && player2.isConnected())
                    {
                        // Engine engine = new Engine(player, player2);
                        // engine.run();
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