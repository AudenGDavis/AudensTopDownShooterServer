package com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Game;
import com.google.gson.Gson;

public class ServerSender implements Runnable
{
    private int portNumber;
    private Game game;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Gson gson;

    public ServerSender(int PortNumber, Game Game)
    {
        portNumber = PortNumber;
        game = Game;
        gson = new Gson();
    }


    public void run() 
    {
		PrintWriter out = null;

        try 
        {
            serverSocket = new ServerSocket(portNumber);
            clientSocket = serverSocket.accept();
		    out = new PrintWriter(clientSocket.getOutputStream(),true);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        

        while(true)
        {
            try {
                out.println(gson.toJson(game,Game.class));
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
        
    }
}
