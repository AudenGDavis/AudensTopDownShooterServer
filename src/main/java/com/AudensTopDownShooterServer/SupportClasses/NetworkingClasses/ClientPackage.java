package com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses;

import java.util.ArrayList;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Bullet; 

import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Player;

public class ClientPackage
{
    private Player localPlayer;
    private ArrayList<Bullet> bullets;

    // Synchronized getter for localPlayer
    public synchronized Player getLocalPlayer() {
        return localPlayer;
    }

    // Synchronized setter for localPlayer
    public synchronized void setLocalPlayer(Player localPlayer) {
        this.localPlayer = localPlayer;
    }

    // Synchronized getter for bullets
    public synchronized ArrayList<Bullet> getBullets() {
        return bullets;
    }

    // Synchronized setter for bullets
    public synchronized void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }
}