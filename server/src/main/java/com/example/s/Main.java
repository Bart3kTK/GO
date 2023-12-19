package com.example.s;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {    
    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(8888)) {
 
            System.out.println("Server is listening on port 8888");
 
            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Dolaczyl nowy uzytkownik!");

                
                
                // TODO: Tu trzeba wymyslic thread ktory uruchamai sie po dalaczeniu kazdego gracza
                // TODO: w ktorym bedzie jakas kolejka dolaczania do graczy
            }
 
        } catch (IOException ex) {
            System.out.println("Serwer o takim adresie juz istnieje");
        }
    }
}