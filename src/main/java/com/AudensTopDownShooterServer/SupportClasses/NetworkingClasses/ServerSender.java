package com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.ServerSocket;

import com.AudensTopDownShooterServer.Main;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Game;
import com.google.gson.Gson;

public class ServerSender implements Runnable
{
    private int portNumber;
    private Game game;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Gson gson;
    private PlayerConnection playerConnection;
    private String response;
    private ServerReceiver receiver;

    public ServerSender(int PortNumber, Game Game, PlayerConnection PlayerConnection,ServerReceiver Receiver)
    {
        portNumber = PortNumber;
        game = Game;
        gson = new Gson();
        playerConnection = PlayerConnection;
        receiver = Receiver;
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
        

        while(receiver.stillConnected)
        {
            try 
            {
                synchronized(Main.synchronizedBulletsLock)
                {
                    out.println(gson.toJson(game,Game.class));
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
