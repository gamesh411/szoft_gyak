/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.frontend.windows;

import hu.elte.gazdapp.frontend.GuiManager;
import hu.elte.gazdapp.frontend.layouts.MainPanel;
import java.awt.FlowLayout;
import hu.elte.gazdapp.frontend.menu.Menu;
import javax.swing.JFrame;

/**
 *
 * @author Dell
 */
public class GameWindow extends JFrame {

    MainPanel main;
    Menu menu;

    GuiManager gui;

    public GameWindow(GuiManager gui) {
        this.gui = gui;
        initStartScreen();
    }

    private void initStartScreen() {
        setLayout(new FlowLayout());
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        main = new MainPanel(gui);
        add(main);
        menu = new Menu(gui);
        setJMenuBar(menu);
        setTitle("Gazdálkodj okosan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void update() {
        repaint();
        main.update();
    }

    public void setInGameMessage(String message) {
        main.setInGameMessage(message);
    }

}
