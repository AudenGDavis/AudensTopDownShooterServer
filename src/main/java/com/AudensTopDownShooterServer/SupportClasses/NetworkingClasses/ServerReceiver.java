package com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses;

import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Game;
import com.google.gson.Gson;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ServerReceiver implements Runnable
{
    private int portNumber;
    private Game game;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Gson gson;

    public ServerReceiver(int PortNumber, Game Game)
    {
        portNumber = PortNumber;
        game = Game;
        gson = new Gson();
    }


    public void run() 
    {
        BufferedReader in = null;

        try 
        {
            serverSocket = new ServerSocket(portNumber);
            clientSocket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        

        while(true)
        {
            try {
                game.importGame(gson.fromJson(in.readLine(), Game.class));
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
        
    }
}