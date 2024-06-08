package com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

import com.AudensTopDownShooterServer.Main;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Game;
import com.google.gson.Gson;

public class ServerCommunicator implements Runnable
{
    private int portNumber;
    private Game game;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Gson gson;
    private PlayerConnection playerConnection;
    private String response = " ";

    public ServerCommunicator(int PortNumber, Game Game, PlayerConnection PlayerConnection)
    {
        portNumber = PortNumber;
        game = Game;
        gson = new Gson();
        playerConnection = PlayerConnection;
    }


    public void run() 
    {
		PrintWriter out = null;
        BufferedReader in = null;

        try 
        {
            serverSocket = new ServerSocket(portNumber);
            clientSocket = serverSocket.accept();
		    out = new PrintWriter(clientSocket.getOutputStream(),true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        try 
        {
            out.println(gson.toJson(game,Game.class));
            response = in.readLine();
        } 
        catch (Exception e) 
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
                    out.println(gson.toJson(game,Game.class));
                    response = in.readLine();
            
                }
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
        } 
        catch (IOException e) 
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
