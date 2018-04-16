/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend;

import gazd.backend.Player;
import gazd.controller.MainController;
import gazd.frontend.layouts.StartScreen;
import java.awt.Point;
import java.util.LinkedList;
import java.util.Random;
import javax.crypto.spec.PSource;
import javax.swing.JFrame;

/**
 *
 * @author <Andó Sándor Zsolt>
 */
public final class GuiManager {
    
    private static StartScreen screen;
    private static MainController control;
    
    private GuiManager(){
    }

    public static void start() {
        screen = new StartScreen();
        screen.pack();
        screen.setVisible(true);
    }
    
    public static void newGame() {
        control = new MainController();
        control.newGame();
        screen.repaint();
    }
    
    public static void doRoll() {
        control.round();
        screen.repaint();
    }
    
    public static LinkedList<Player> getPlayers() {
        return control == null ? new LinkedList<>() : control.getPlayers();
    }
    
    
    public static Point getPoint(int width, int height, int position, int plus){
        Random r = new Random();
        int rand = plus*10;
        if(position==0) return new Point(12*width/13,8*height/9  + rand);
        if(position==13) return new Point(width/17,8*height/9  + rand);
        if(position==21) return new Point(width/17,height/11  + rand);
        if(position==34) return new Point(12*width/13,height/11  + rand);
        if(position<13){
            int twidth = width/4;
            twidth = width-twidth/2 - position*width/17;
            return new Point(twidth,7*height/8  + rand);
        }else if(position<21){
            int tpos = position - 14;
            int theight = (int)(height/2);
            theight = height-theight/2 - tpos*height/11;
            return new Point(width/15  + rand,theight);
        }else if(position<34){
            int tpos = 13-(position - 21);
            int twidth = width/4;
            twidth = width-twidth/2 - tpos*width/17;
            return new Point(twidth,height/9  + rand);
        }else{
            int tpos = 6 - (position - 35);
            int theight = (int)(height/2);
            theight = height-theight/2 - tpos*height/11;
            return new Point(14*width/15  + rand,theight);
        }
    }
    
    
}
