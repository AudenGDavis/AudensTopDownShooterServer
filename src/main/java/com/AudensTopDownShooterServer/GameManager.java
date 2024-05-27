package com.AudensTopDownShooterServer;

import java.awt.event.KeyEvent;
import java.util.ArrayList;


import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Game;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Gun;
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
            while (true) {
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
            
            // ifa
            // this.update();
        
            

        }
        
    }


    synchronized private void update()
    {
        //update player's positions and their respective gun reload times
        for(int p = 0; p < game.getPlayers().size();p++)
        {
            //reloading their gun
            game.getPlayers().get(p).getGun().setReloadTime(
                game.getPlayers().get(p).getGun().getReloadTime() >= game.getPlayers().get(p).getGun().getReloadTimeRequirment()? 
                game.getPlayers().get(p).getGun().getReloadTimeRequirment() : game.getPlayers().get(p).getGun().getReloadTime() + miliSecondRate
            );


            //adjust players position
            game.getPlayers().get(p).setXPosition(game.getPlayers().get(p).getXVelocity()*(((float)miliSecondRate)/1000) + game.getPlayers().get(p).getXPosition());
            game.getPlayers().get(p).setYPosition(game.getPlayers().get(p).getYVelocity()*(((float)miliSecondRate)/1000) + game.getPlayers().get(p).getYPosition());





            //adjust player in they're colliding
            Vector2 collision = ColliderManager.isCollidingAny(game, game.getPlayers().get(p));
            while(collision != null)
            {
                double dy = game.getPlayers().get(p).getYPosition() - collision.getY();
                double dx = game.getPlayers().get(p).getXPosition() - collision.getX();

                double s = Math.sqrt(dy*dy + dx*dx)/game.getPlayers().get(p).getSize();

                dy = dy/s*1.00001;
                dx = dx/s*1.00001;

                

                game.getPlayers().get(p).setXPosition(collision.getX() + dx);
                game.getPlayers().get(p).setYPosition(collision.getY() + dy);
                collision = ColliderManager.isCollidingAny(game, game.getPlayers().get(p));
            }




            
        }   
    
        
        




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
