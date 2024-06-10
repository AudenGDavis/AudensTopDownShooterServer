package com.AudensTopDownShooterServer;

import java.util.ArrayList;
import java.util.Dictionary;

import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Bullet;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Game;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Gun;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Player;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Wall;
import com.AudensTopDownShooterServer.SupportClasses.PhysicsClasses.Vector2;
import com.google.gson.Gson;
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

        game = new Game(new ArrayList<Wall>(), new ArrayList<Bullet>());
        
        game.getWalls().add(new Wall(new Vector2(0,0), new Vector2(0,1500)));
        
        // //world border
        game.getWalls().add(new Wall(new Vector2(0,0), new Vector2(0,1500)));
        game.getWalls().add(new Wall(new Vector2(0,1500), new Vector2(2300,1500)));
        game.getWalls().add(new Wall(new Vector2(2300,1500), new Vector2(2300,0)));
        game.getWalls().add(new Wall(new Vector2(2300,0), new Vector2(0,0)));

        // //spawn house
        game.getWalls().add(new Wall(new Vector2(0,600), new Vector2(200,600)));
        game.getWalls().add(new Wall(new Vector2(0,900), new Vector2(200,900)));
        game.getWalls().add(new Wall(new Vector2(200,600), new Vector2(200,700)));
        game.getWalls().add(new Wall(new Vector2(200,900), new Vector2(200,800)));
        
        // //house 1
        game.getWalls().add(new Wall(new Vector2(100,0), new Vector2(100,400)));
        game.getWalls().add(new Wall(new Vector2(100,400), new Vector2(400,400)));
        game.getWalls().add(new Wall(new Vector2(500,400), new Vector2(700,400)));
        game.getWalls().add(new Wall(new Vector2(700,400), new Vector2(700,0)));
        game.getWalls().add(new Wall(new Vector2(400,0), new Vector2(400,200)));
        game.getWalls().add(new Wall(new Vector2(400,300), new Vector2(400,400)));
        game.getWalls().add(new Wall(new Vector2(500,0), new Vector2(500,200)));
        game.getWalls().add(new Wall(new Vector2(500,300), new Vector2(500,400)));

        // //house 2
        game.getWalls().add(new Wall(new Vector2(100,1100), new Vector2(100,1500)));
        game.getWalls().add(new Wall(new Vector2(100,1100), new Vector2(400,1100)));
        game.getWalls().add(new Wall(new Vector2(500,1100), new Vector2(700,1100)));
        game.getWalls().add(new Wall(new Vector2(700,1100), new Vector2(700,1500)));
        game.getWalls().add(new Wall(new Vector2(300,1100), new Vector2(300,1300)));
        game.getWalls().add(new Wall(new Vector2(300,1400), new Vector2(300,1500)));
        
        
        // //big house 1
        game.getWalls().add(new Wall(new Vector2(900,0), new Vector2(900,700)));
        game.getWalls().add(new Wall(new Vector2(900,700), new Vector2(1300,700)));
        game.getWalls().add(new Wall(new Vector2(1400,700), new Vector2(1400,0)));
        game.getWalls().add(new Wall(new Vector2(1300,700), new Vector2(1300,600)));
        game.getWalls().add(new Wall(new Vector2(1200,500), new Vector2(1400,500)));
        game.getWalls().add(new Wall(new Vector2(1100,0), new Vector2(1100,300)));
        game.getWalls().add(new Wall(new Vector2(1100,400), new Vector2(1100,700)));
        game.getWalls().add(new Wall(new Vector2(1000,100), new Vector2(1100,100)));
        game.getWalls().add(new Wall(new Vector2(1000,200), new Vector2(1100,200)));
        game.getWalls().add(new Wall(new Vector2(1000,300), new Vector2(1100,300)));
        game.getWalls().add(new Wall(new Vector2(1000,400), new Vector2(1100,400)));
        game.getWalls().add(new Wall(new Vector2(1000,500), new Vector2(1100,500)));
        game.getWalls().add(new Wall(new Vector2(1000,600), new Vector2(1100,600)));
        game.getWalls().add(new Wall(new Vector2(1200,500), new Vector2(1200,400)));
        game.getWalls().add(new Wall(new Vector2(1200,300), new Vector2(1200,0)));
        
        // //big house 2
        game.getWalls().add(new Wall(new Vector2(900,800), new Vector2(900,1500)));
        game.getWalls().add(new Wall(new Vector2(900,800), new Vector2(1200,800)));
        game.getWalls().add(new Wall(new Vector2(1300,800), new Vector2(1400,800)));
        game.getWalls().add(new Wall(new Vector2(1300,800), new Vector2(1400,800)));
        game.getWalls().add(new Wall(new Vector2(1400,800), new Vector2(1400,1400)));
        game.getWalls().add(new Wall(new Vector2(1100,800), new Vector2(1100,1200)));
        game.getWalls().add(new Wall(new Vector2(1100,1300), new Vector2(1100,1500)));
        game.getWalls().add(new Wall(new Vector2(1200,1000), new Vector2(1300,1000)));
        game.getWalls().add(new Wall(new Vector2(1200,1000), new Vector2(1200,1100)));
        game.getWalls().add(new Wall(new Vector2(1200,1100), new Vector2(1300,1100)));
        game.getWalls().add(new Wall(new Vector2(1400,1400), new Vector2(1200,1400)));
        game.getWalls().add(new Wall(new Vector2(1200,1400), new Vector2(1200,1200)));
    
        // //court yard 
        game.getWalls().add(new Wall(new Vector2(1500,300), new Vector2(1500,100)));
        game.getWalls().add(new Wall(new Vector2(1500,100), new Vector2(1700,100)));
        game.getWalls().add(new Wall(new Vector2(1500,300), new Vector2(1700,100)));

        game.getWalls().add(new Wall(new Vector2(2000,100), new Vector2(2200,100)));
        game.getWalls().add(new Wall(new Vector2(2200,100), new Vector2(2200,300)));
        game.getWalls().add(new Wall(new Vector2(2000,100), new Vector2(2200,300)));

        game.getWalls().add(new Wall(new Vector2(1500,1200), new Vector2(1500,1400)));
        game.getWalls().add(new Wall(new Vector2(1500,1400), new Vector2(1700,1400)));
        game.getWalls().add(new Wall(new Vector2(1500,1200), new Vector2(1500,1400)));

        game.getWalls().add(new Wall(new Vector2(2000,1400), new Vector2(2200,1400)));
        game.getWalls().add(new Wall(new Vector2(2200,1400), new Vector2(2200,1200)));
        game.getWalls().add(new Wall(new Vector2(2000,1400), new Vector2(2200,1400)));

        game.getWalls().add(new Wall(new Vector2(1700,700), new Vector2(1700,600)));
        game.getWalls().add(new Wall(new Vector2(1700,600), new Vector2(1800,600)));
        game.getWalls().add(new Wall(new Vector2(1900,600), new Vector2(2000,600)));
        game.getWalls().add(new Wall(new Vector2(2000,600), new Vector2(2000,700)));
        game.getWalls().add(new Wall(new Vector2(2000,800), new Vector2(2000,900)));
        game.getWalls().add(new Wall(new Vector2(2000,900), new Vector2(1900,900)));
        game.getWalls().add(new Wall(new Vector2(1700,800), new Vector2(1700,900)));
        game.getWalls().add(new Wall(new Vector2(1700,900), new Vector2(1800,900)));


        
        System.out.println(new Gson().toJson(game));
        
        //localIp 127.0.0.1

        clientManager = new ClientManager(42069,"127.0.0.1",game);

        gamePanel = new GamePanel(game, this);
        this.add(gamePanel);

        gameManager = new GameManager(game, gamePanel);
        new Thread(gameManager).start();   
    }


}
