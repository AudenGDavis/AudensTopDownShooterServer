package com.AudensTopDownShooterServer;

import java.net.Socket;

import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Game;
import com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses.JoinSocketThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList; 
import com.google.gson.Gson; 

import com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses.PlayerConnection;
import com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses.ServerReceiver;
import com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses.ServerSender;
public class ClientManager
{
    private int joinPortNumber;
    private int numPlayers = 0;
    private ArrayList<PlayerConnection> playerConnections = new ArrayList<PlayerConnection>();
    private int lastUsedPortNumber;
    private String ipAddress;
    private Gson gsonConverter = new Gson();
    private ServerReceiver serverReceiver;
    private ServerSender serverSender;
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
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            
            PlayerConnection newPlayerConnection = new PlayerConnection(lastUsedPortNumber + 1, lastUsedPortNumber + 2, ipAddress,numPlayers + 1);
            lastUsedPortNumber += 2;
            numPlayers += 1;

            out.println(gsonConverter.toJson(newPlayerConnection));
            serverSender = new ServerSender(newPlayerConnection.getClientRecieverPortNumber(),game);
            new Thread(serverSender).start();

            serverReceiver = new ServerReceiver(newPlayerConnection.getClientSenderPortNumber(),game);
            new Thread(serverReceiver).start();
        } 
        catch (IOException e) 
        {

        }
    }
}