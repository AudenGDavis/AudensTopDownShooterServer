package com.AudensTopDownShooterServer;

import java.net.Socket;
import com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses.JoinSocketThread;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class ClientManager
{
    public ClientManager()
    {
        JoinSocketThread joinSocketThread = new JoinSocketThread(this,42069);
        new Thread(joinSocketThread).start();
    }

    public void connectPlayer(Socket clientSocket)
    {
        try 
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            
            System.out.println(in.readLine());

            out.println("hello client, how are you");
        } 
        catch (IOException e) 
        {

        }
    }
}