package com.example.s;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
// kalsa do testow docelowo jej tu nie bedzie
public class ClientTest {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8888); 
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out.println("bot elo");

        while(true)
        {

        }
        

    }
}
