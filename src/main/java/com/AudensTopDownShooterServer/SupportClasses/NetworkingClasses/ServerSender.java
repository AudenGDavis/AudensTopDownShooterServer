package com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.print.attribute.standard.Severity;

import java.net.ServerSocket;

import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Game;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ServerSender implements Runnable
{
    private int portNumber;
    private Game game;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private Gson gsonConverter = new GsonBuilder().create();

    public ServerSender(int PortNumber, Game Game)
    {
        portNumber = PortNumber;
        game = Game;
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
                out.println(gsonConverter.toJson(game,Game.class));
                System.out.println(gsonConverter.toJson(game,Game.class));
                System.out.println(game.getWalls());
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
        
    }
}
