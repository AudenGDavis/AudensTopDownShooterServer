package com.AudensTopDownShooterServer;

import java.util.ArrayList;

import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Bullet;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Game;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Player;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Wall;
import com.AudensTopDownShooterServer.SupportClasses.PhysicsClasses.Vector2;
import javax.swing.JFrame;

public class GameEngine extends JFrame
{
    Game game;
    public ClientManager clientManager;
    GamePanel gamePanel;
    GameManager gameManager;

    public GameEngine()
    {
        this.setTitle("Server");
        this.setSize(600, 600);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        game = new Game(new ArrayList<Player>(), new ArrayList<Wall>(), new ArrayList<Bullet>());
        
        game.getWalls().add(new Wall(new Vector2(0,0), new Vector2(0,1500)));

        clientManager = new ClientManager(42069,"127.0.0.1",game);

        gamePanel = new GamePanel(game, this);
        this.add(gamePanel);

        gameManager = new GameManager(game, gamePanel);
        new Thread(gameManager).start();   
    }


}
