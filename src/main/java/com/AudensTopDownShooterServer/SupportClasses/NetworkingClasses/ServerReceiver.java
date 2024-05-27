package com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses;

import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Game;
import com.google.gson.Gson;
import java.net.Socket;
import java.net.ServerSocket;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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
		PrintWriter out = null;

        try 
        {
            serverSocket = new ServerSocket(portNumber);
            clientSocket = serverSocket.accept();
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		    out = new PrintWriter(clientSocket.getOutputStream(),true);
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        

        while(true)
        {
            try {
                game.importGame(gson.fromJson(in.readLine(), Game.class));
                if(game.getPlayers().size() != 0){
                    System.out.println(game.getPlayers().get(0).getXPosition());
                }
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
        
    }
}