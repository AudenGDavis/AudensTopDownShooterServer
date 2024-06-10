package com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses;


import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Bullet;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Player;

import java.util.ArrayList;
import java.util.Map;

public class ServerPackage 
{
    private Map<Integer, com.AudensTopDownShooterServer.SupportClasses.GameClasses.Player> players;
    private ArrayList<Bullet> bullets;

    public ServerPackage(Map<Integer, Player> players, ArrayList<Bullet> bullets)
    {
        this.players = players;
        this.bullets = bullets;
    }

    public Map<Integer, Player> getPlayers(){
        return players;
    }

    public ArrayList<Bullet> getBullets()
    {
        return bullets;
    }
}

