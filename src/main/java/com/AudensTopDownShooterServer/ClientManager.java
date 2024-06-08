package com.AudensTopDownShooterServer;

import java.net.Socket;

import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Game;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Gun;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Player;
import com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses.JoinSocketThread;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList; 
import com.google.gson.Gson; 

import com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses.PlayerConnection;
import com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses.ServerCommunicator;
public class ClientManager
{
    private int joinPortNumber;
    private int numPlayers = 0;
    private int lastUsedPortNumber;
    private String ipAddress;
    private Gson gsonConverter = new Gson();
    private ServerCommunicator serverCommunicator;
    private Game game;


    public ClientManager(int JoinPortNumber, String IpAddress,Game Game)
    {
        game = Game;
        joinPortNumber = JoinPortNumber;
        lastUsedPortNumber = JoinPortNumber;
        ipAddress = IpAddress;
        JoinSocketThread joinSocketThread = new JoinSocketThread(this,joinPortNumber);
        new Thread(joinSocketThread).start();
    }

    public void connectPlayer(Socket clientSocket)
    {
        try 
        {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            
            PlayerConnection newPlayerConnection = new PlayerConnection(lastUsedPortNumber + 1, ipAddress,numPlayers + 1,game);
            lastUsedPortNumber += 1;
            numPlayers += 1;

            game.addPlayer(new Player(Gun.ar15(),50,750, 1,newPlayerConnection.getPlayerID()));
            

            serverCommunicator = new ServerCommunicator(newPlayerConnection.getPortNumber(),game,newPlayerConnection);
            new Thread(serverCommunicator).start();

            out.println(gsonConverter.toJson(newPlayerConnection));
        } 
        catch (IOException e) 
        {

        }
    }
}