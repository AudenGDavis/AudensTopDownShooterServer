package com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses;

import com.AudensTopDownShooterServer.Main;
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
    private PlayerConnection playerConnection;
    private String response;


    public boolean stillConnected = true;
    public ServerReceiver(int PortNumber, Game Game, PlayerConnection PlayerConnection)
    {
        portNumber = PortNumber;
        game = Game;
        gson = new Gson();
        playerConnection = PlayerConnection;
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
        
        try {
            response = in.readLine();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        while(response != null)
        {
            try
            {
                synchronized(Main.synchronizedBulletsLock)
                {
                    game.fromClientPackage(gson.fromJson(response, ClientPackage.class),playerConnection.getPlayerID());
                }
                response = in.readLine();
                Thread.sleep(1);
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
        try 
        {
            serverSocket.close();
            clientSocket.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        stillConnected = false;
        
    }
}