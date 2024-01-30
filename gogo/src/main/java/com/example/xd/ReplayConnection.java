package com.example.xd;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ReplayConnection implements Runnable{
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private GUIPawn[][] pawnsGrid;
    private Pane pane;
    private Button passButton;
    private Button surrenderButton;
    private Button left;
    private Button right;
    private Button confirm;
    private TextField area;
    private Text server;
    private Text label;
    private boolean isMyTurn = false;
    private boolean isRunning = true;
    private int size;
    private String gameType;
    

    public ReplayConnection(Pane pane, Text[] texts, Button[] buttons,Label[] labels, TextField[] textFields, String gameType, int size) throws UnknownHostException, IOException {
        this.pane = pane;
        this.left = buttons[2];
        this.right = buttons[3];
        this.confirm = buttons[4];
        this.server = texts[2];
        this.area = textFields[0];
        this.label = texts[3];
        this.size = size;
        this.gameType = gameType;

        socket = new Socket("localhost", 8888); 
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        buttons[0].setVisible(false);
        buttons[1].setVisible(false);
        texts[0].setVisible(false);
        texts[1].setVisible(false);
        texts[4].setVisible(false);
        texts[5].setVisible(false);
        left.setVisible(false);
        right.setVisible(false);
        events();
        out.println(gameType + " " + 888);

    }
    



    private void events()
    {
        left.setOnMouseClicked(e -> {
            if (isMyTurn)
            {
                out.println("0 1");
                isMyTurn = false;
            }
        });
        right.setOnMouseClicked(e -> {
            if (isMyTurn)
            {
                out.println("1 0");
                isMyTurn = false;
            }
        });
        confirm.setOnMouseClicked(e -> {
            if (isMyTurn)
            {
                try
                {
                    int number = Integer.parseInt(area.getText());
                    out.println("2 " + number);
                    isMyTurn = false;
                }
                catch (NumberFormatException exception)
                {
                    area.setText("It have to be a number!");
                }
            }
        });

    }

    @Override
    public void run() {
        try
        {
            while(isRunning)
            {
                String mess = in.readLine();
                if (mess != null)
                {   
                        handleReplayCommand(mess);
                }
                else
                {
                    stop();
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            closeSocket();
        }

    }

    public void stop(){
        isRunning = false;
        closeSocket();
        Platform.runLater(() -> {
            server.setVisible(true);
            server.setText("Server is down!");
        });
    }

    private void closeSocket()
    {
        try 
        {
            if (socket != null && !socket.isClosed())
            {
                socket.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    private void handleReplayCommand(String command)
    {
        String[] splitedString = command.split(";");
        System.out.println("Dostałem: " + command  );

        if (splitedString[0].equals("data"))
        {
            for (String s : splitedString)
            {
                if(s.equals("data"))
                {
                    continue;
                }
                System.out.println(s);
                label.setText(label.getText() + "\n" + s);
            }            
        }
        else if (splitedString[0].equals("done"))
        {
            isMyTurn = true;
        }
        else if(splitedString[0].equals("prepare"))
        {
            confirm.setVisible(false);
            area.setVisible(false);
            label.setVisible(false);
            left.setVisible(true);
            right.setVisible(true);
            size = Integer.parseInt(splitedString[1]);
            initBoard();
        }
        else if(splitedString[0].equals("board"))
        {
            System.out.println(1);
            String board = splitedString[1];
            System.out.println(2);
            String[] splitedBoard = board.split("/");
            Platform.runLater(() -> {
                for (int i = 0; i < size; i++)
                {
                    String[] splitedColumn = splitedBoard[i].split(" ");
                    for (int j = 0; j < size; j++)
                    {
                        if (splitedColumn[j].equals("white"))
                        {
                            pawnsGrid[i][j].setWhite();
                        }
                        else if (splitedColumn[j].equals("black"))
                        {
                            pawnsGrid[i][j].setBlack();
                        }
                        else
                        {
                            pawnsGrid[i][j].setClear();
                        }
                        
                    }
                }    
            });


        }

    }
    private void initBoard()
    {

        pawnsGrid = new GUIPawn[size][size];
        Platform.runLater(() -> {
            for (int i = 0; i< size * size; i++)
            {
                GUISquare sq = new GUISquare(size);
                pane.getChildren().add(sq);
                GUIPawn pawn = new GUIPawn(sq.getXpos(), sq.getYpos(), sq.getRow(), sq.getColumn());
                pawnsGrid[sq.getRow()][sq.getColumn()] = pawn;
                pane.getChildren().add(pawn);
    
            }
        });
    }
}
