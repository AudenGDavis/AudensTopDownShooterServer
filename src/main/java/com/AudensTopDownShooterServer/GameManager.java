package com.AudensTopDownShooterServer;

import java.util.ArrayList;
import java.util.Map;

import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Game;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Player;
import com.AudensTopDownShooterServer.SupportClasses.PhysicsClasses.LineCollider;
import com.AudensTopDownShooterServer.SupportClasses.PhysicsClasses.Vector2;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Bullet;

public class GameManager implements Runnable {

    private Game game;
    private GamePanel gamePanel;
    private int miliSecondRate = 1;//milliseconds per update
    public boolean isFiring = false;
    private Thread renderingThread;

    public GameManager(Game Game, GamePanel GamePanel){
        game = Game;
        gamePanel = GamePanel;
    }
    
    public void run() 
    {
        renderingThread = new Thread(() -> {
            while (true) 
            {
                // Render game graphics
                gamePanel.repaint();

                // Sleep for a short interval
                try {
                    Thread.sleep(10); // Adjust as needed
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        new Thread(renderingThread).start();
        while(true){
        
            try {
                Thread.sleep(miliSecondRate);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // this.update();
        
            

        }
        
    }


    synchronized private void update()
    {
        System.out.println(game);
        //update player's positions and their respective gun reload times
        // for (Map.Entry<Integer, Player> playerEntry : game.getPlayers().entrySet()) 
        // {
        //     //reloading their gun
        //     game.getPlayers().get(playerEntry.getKey()).getGun().setReloadTime(
        //         game.getPlayers().get(playerEntry.getKey()).getGun().getReloadTime() >= game.getPlayers().get(playerEntry.getKey()).getGun().getReloadTimeRequirment()? 
        //         game.getPlayers().get(playerEntry.getKey()).getGun().getReloadTimeRequirment() : game.getPlayers().get(playerEntry.getKey()).getGun().getReloadTime() + miliSecondRate
        //     );


        //     //adjust players position
        //     game.getPlayers().get(playerEntry.getKey()).setXPosition(game.getPlayers().get(playerEntry.getKey()).getXVelocity()*(((float)miliSecondRate)/1000) + game.getPlayers().get(playerEntry.getKey()).getXPosition());
        //     game.getPlayers().get(playerEntry.getKey()).setYPosition(game.getPlayers().get(playerEntry.getKey()).getYVelocity()*(((float)miliSecondRate)/1000) + game.getPlayers().get(playerEntry.getKey()).getYPosition());





        //     //adjust player in they're colliding
        //     Vector2 collision = ColliderManager.isCollidingAny(game, game.getPlayers().get(playerEntry.getKey()));
        //     while(collision != null)
        //     {
        //         double dy = game.getPlayers().get(playerEntry.getKey()).getYPosition() - collision.getY();
        //         double dx = game.getPlayers().get(playerEntry.getKey()).getXPosition() - collision.getX();

        //         double s = Math.sqrt(dy*dy + dx*dx)/game.getPlayers().get(playerEntry.getKey()).getSize();

        //         dy = dy/s*1.00001;
        //         dx = dx/s*1.00001;

                

        //         game.getPlayers().get(playerEntry.getKey()).setXPosition(collision.getX() + dx);
        //         game.getPlayers().get(playerEntry.getKey()).setYPosition(collision.getY() + dy);
               
                
        //     } 
        // } 
    
        
        




        //bullet update
        for(int b = game.getBullets().size()-1; b >= 0;b--)
        {
            Bullet bullet = game.getBullets().get(b);
            // ArrayList<Vector2> wallCollisions = ColliderManager.isCollidingAnyWalls(game, new LineCollider(bullet.getXPosition(), bullet.getYPosition(), bullet.getXPosition()+bullet.getXVelocity()*miliSecondRate/1000, bullet.getYPosition()+bullet.getYVelocity()*miliSecondRate/1000));

            // LineCollider lineCollider = new LineCollider(bullet.getXPosition(), bullet.getYPosition(), bullet.getXPosition() + bullet.getXVelocity()/100, bullet.getYPosition() + bullet.getYVelocity()/100);

            boolean realCollision = false;
            ArrayList<Player> playerCollisons = ColliderManager.isCollidingAnyPlayers(game,new LineCollider(bullet.getXPosition(),bullet.getYPosition(), bullet.getXPosition()+bullet.getXVelocity()*miliSecondRate/1000*1.5, bullet.getYPosition()+bullet.getYVelocity()*miliSecondRate/1000*1.5));
            if(playerCollisons.size() != 0){
                for(int i = 0; i < playerCollisons.size(); i++)
                {   
                    if(playerCollisons.get(i).getHealth() >= 0){
                        playerCollisons.get(i).setHealth(playerCollisons.get(i).getHealth() - (bullet.getDamage()));
                        realCollision = true;
                    }
                }
                
            }

            if(ColliderManager.isCollidingAnyWalls(game, new LineCollider(bullet.getXPosition() - bullet.getXVelocity()*miliSecondRate/1000*0.5,bullet.getYPosition() - bullet.getYVelocity()*miliSecondRate/1000*0.5, bullet.getXPosition()+bullet.getXVelocity()*miliSecondRate/1000*1.6, bullet.getYPosition()+bullet.getYVelocity()*miliSecondRate/1000*1.6)).size() == 0)
            {
                bullet.setXPosition(bullet.getXPosition()+bullet.getXVelocity()*miliSecondRate/1000);
                bullet.setYPosition(bullet.getYPosition()+bullet.getYVelocity()*miliSecondRate/1000);
            }
            else{
                realCollision = true;
            }
            if(realCollision){
                game.getBullets().remove(b);
            }
        }
    }   
}
