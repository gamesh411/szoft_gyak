/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend;

import gazd.frontend.layouts.StartScreen;
import javax.swing.JFrame;

/**
 *
 * @author <Andó Sándor Zsolt>
 */
public final class GuiManager {
    
    private static StartScreen screen;
    
    private GuiManager(){
    }

    public static void start() {
        screen = new StartScreen();
        screen.pack();
        screen.setVisible(true);
    }
    
    public static void newGame() {
        System.out.println("javaapplication1.frontend.GuiManager.newGame()");
    }
    
    public static void doRoll() {
        System.out.println("javaapplication1.frontend.GuiManager.doRoll()");
    }
    
}
