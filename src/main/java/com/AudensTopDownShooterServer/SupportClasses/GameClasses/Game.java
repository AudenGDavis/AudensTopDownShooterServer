package com.AudensTopDownShooterServer.SupportClasses.GameClasses;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.AudensTopDownShooterServer.SupportClasses.NetworkingClasses.ClientPackage;

public class Game {
    private Map<Integer, Player> players;
    private ArrayList<Wall> walls;
    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> localBullets;
    
    public Game()
    {
        players = new HashMap<Integer, Player>();
        walls = new ArrayList<Wall>();
        bullets = new ArrayList<Bullet>();
        localBullets = new ArrayList<Bullet>();
    }

    public Game(ArrayList<Wall> Walls ,ArrayList<Bullet> Bullets){
        players = new HashMap<>();
        walls = Walls;
        bullets = Bullets;
        localBullets = new ArrayList<Bullet>();
    }

    public synchronized void addPlayer(Player Player) {
        players.put(Player.getPlayerID(), Player);
    }
    public synchronized Map<Integer, Player> getPlayers() {
        return players;
    }
    public synchronized ArrayList<Wall> getWalls() {
        return walls;
    }
    public synchronized void setWalls(ArrayList<Wall> walls) {
        this.walls = walls;
    }
    public synchronized ArrayList<Bullet> getBullets() {
        return bullets;
    }
    public synchronized void setBullets(ArrayList<Bullet> bullets) {
        this.bullets = bullets;
    }
    public synchronized ArrayList<Bullet> getLocalBullets() {
        return localBullets;
    }
    public synchronized void setLocalBullets(ArrayList<Bullet> LocoalBullets) {
        this.localBullets = LocoalBullets;
    }

    public synchronized void importGame(Game importedGame)
    {
        this.players = importedGame.getPlayers();
        this.walls = importedGame.getWalls();
        this.bullets = importedGame.getBullets();
    }

    public synchronized void fromClientPackage(ClientPackage clientPackage, int playerID)
    {
        clientPackage.getLocalPlayer().setHealth(players.get(playerID).getHealth());
        players.put(playerID, clientPackage.getLocalPlayer());
        for(int i = 0; i < clientPackage.getBullets().size(); i++)
        {
            bullets.add(clientPackage.getBullets().get(i));
        }
    }

    public String toString()
    {
        String returnString = "Game \n";
        
        returnString += "   |- Players \n";
        for (Map.Entry<Integer, Player> serverEntry : players.entrySet()) 
        {
            returnString += "   |   |- " + serverEntry.getKey() + " = " + serverEntry.getValue() + "\n";
            
        }

        returnString += "   |\n";
        returnString += "   |- Walls \n";
        for(Wall wall : walls)
        {
            returnString += "   |   |- [" + wall.getStart().toString() + "  " + wall.getEnd().toString() + "]\n";
        }

        returnString += "   |\n";
        returnString += "   |- Bullets \n";
        for(Bullet bullet : bullets)
        {
            returnString += "   |   |- [" + bullet + "]\n";
        }

        returnString += "   |\n";
        returnString += "   |- Local Bullets \n";
        for(Bullet bullet : localBullets)
        {
            returnString += "   |   |- [" + bullet + "]\n";
        }

        return returnString;
    }
}

//steve parr