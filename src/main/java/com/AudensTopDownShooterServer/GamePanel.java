package com.AudensTopDownShooterServer;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Game;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Bullet;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Player;
import com.AudensTopDownShooterServer.SupportClasses.GameClasses.Wall;

public class GamePanel extends JPanel
{
    Game game;
    private double xCamera = 0;
    private double yCamera = 0;
    private float zoom = 1f;
    JFrame parentFrame;
    public static final float MIN_ZOOM = 0.05f;
    
    public GamePanel(Game Game, JFrame parentFrame){
        this.game = Game;
        this.parentFrame = parentFrame;
        this.setBackground(Color.GRAY);
    }

    public float getZoom()
    {
        return zoom;
    }

    public void setZoom(float zoom)
    {
        this.zoom = zoom < MIN_ZOOM? MIN_ZOOM : zoom;
    }

    

    public void paint(Graphics G) 
    { 


        this.setBounds(0,0,parentFrame.getWidth(),parentFrame.getHeight());
        // if(game != null && game.getPlayers().size() != 0)
        // {
        //     double xSum = 0;
        //     double ySum = 0;
            
        //     for (Player player : game.getPlayers()) 
        //     {
        //         if(player.getHealth() > 0)
        //         {
        //             System.out.print(player.getHealth() + ", ");
        //             xSum += player.getXPosition();
        //             ySum += player.getYPosition();
        //         }
        //     }
        //     zoom = 0.5f;

        //     xCamera = xSum / game.getPlayers().size();
        //     yCamera = ySum / game.getPlayers().size();
        //     System.out.println(xCamera + " - " + yCamera);
        // }
        //test

        xCamera = 1000;
        yCamera = 700;
        zoom = 0.25f;

        
        Graphics2D g = (Graphics2D) G.create();
        // g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//anti aliasing


        //draw player
        g.setStroke(new BasicStroke(zoom * 5));
        
        for (Map.Entry<Integer, Player> playerEntry : game.getPlayers().entrySet()) 
        {
            Player player = game.getPlayers().get(playerEntry.getKey());
            if(player.getHealth() > 0){
                g.setStroke(new BasicStroke(zoom * 3));
                g.setColor(player.getTeam() == 1? new Color(30, 0, 222) : new Color(235, 16, 0));
                g.drawOval((int) worldToFrameX((player.getXPosition()-player.getSize())),(int) worldToFrameY((player.getYPosition()-player.getSize())), (int)(2*player.getSize()*zoom), (int)(2*player.getSize()*zoom));

                g.setColor(Color.decode(player.getGun().getColor()));
                g.setStroke(new BasicStroke(zoom * 5));
                g.drawLine(
                    (int)worldToFrameX(player.getXPosition()), 
                    (int)worldToFrameY(player.getYPosition()), 
                    (int)worldToFrameX(player.getXPosition() + player.getGun().getBarrelLength()*Math.cos(Math.toRadians(player.getAngle()))), 
                    (int)worldToFrameY(player.getYPosition() + player.getGun().getBarrelLength()*Math.sin(Math.toRadians(player.getAngle()))));
            }
            else
            {
                g.setStroke(new BasicStroke(zoom * 3));
                g.setColor(Color.DARK_GRAY);
                g.drawOval((int) worldToFrameX((player.getXPosition()-player.getSize())),(int) worldToFrameY((player.getYPosition()-player.getSize())), (int)(2*player.getSize()*zoom), (int)(2*player.getSize()*zoom));
            }
        }



        g.setStroke(new BasicStroke(zoom * 5));
        for (int i = 0; i < game.getWalls().size(); i++){
            Wall wall = game.getWalls().get(i);
            g.setColor(Color.BLACK);
            Line2D line = new Line2D.Double(worldToFrameX(wall.getStart().getX()), worldToFrameY(wall.getStart().getY()), worldToFrameX(wall.getEnd().getX()), worldToFrameY(wall.getEnd().getY()));
            
            g.draw(line);


            
        }

        
        

        //draw bullets
        g.setColor(Color.RED);
        g.setStroke(new BasicStroke(zoom * 3));
        for (int b = 0; b < game.getBullets().size(); b++)
        {
            Bullet bullet = game.getBullets().get(b);
            g.setStroke(new BasicStroke(zoom * 5));
            g.drawLine((int)worldToFrameX(bullet.getXPosition()), (int)worldToFrameY(bullet.getYPosition()), (int)worldToFrameX(bullet.getXPosition() + bullet.getXVelocity()/100), (int)worldToFrameY(bullet.getYPosition() + bullet.getYVelocity()/100));
        }

        for (int b = 0; b < game.getLocalBullets().size(); b++)
        {
            Bullet bullet = game.getLocalBullets().get(b);
            g.setStroke(new BasicStroke(zoom * 5));
            g.drawLine((int)worldToFrameX(bullet.getXPosition()), (int)worldToFrameY(bullet.getYPosition()), (int)worldToFrameX(bullet.getXPosition() + bullet.getXVelocity()/100), (int)worldToFrameY(bullet.getYPosition() + bullet.getYVelocity()/100));
        }

        



        
        g.dispose();
    }

    private double worldToFrameX(double X)
    {
        return (X - xCamera)*zoom + this.getWidth()/2;
    }
    private double worldToFrameY(double Y)
    {
        return (Y - yCamera)*zoom + this.getHeight()/2;
    }

    

    


    
    
    
}
