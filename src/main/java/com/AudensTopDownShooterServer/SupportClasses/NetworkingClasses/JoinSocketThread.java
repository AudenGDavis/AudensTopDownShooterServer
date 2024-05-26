package com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses;

import java.net.ServerSocket;
import java.net.Socket;

import com.AudensTopDownShooterServer.ClientManager;

public class JoinSocketThread implements Runnable{

    private int portNumber;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ClientManager clientManager;

    public JoinSocketThread(ClientManager ClientManager, int PortNumber)
    { 
        clientManager = ClientManager;

        try 
        {
            portNumber = PortNumber;
            serverSocket = new ServerSocket(portNumber);
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    
    public void run() {
        while (true)
        {
            try 
            {
                clientSocket = serverSocket.accept();
                System.out.println("connected");
                clientManager.connectPlayer(clientSocket);
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
            
        }
    }
}
