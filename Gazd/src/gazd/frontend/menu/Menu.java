/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.menu;

import java.awt.event.ActionEvent;
import gazd.frontend.GuiManager;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Dell
 */
public class Menu extends JMenuBar{
    
    private final JMenu menu = new JMenu();
    private final JMenuItem start = new JMenuItem();

    public Menu() {
        initMenu();
    }


    private void initMenu() {
        menu.setText("Játék");
        add(menu);
        
        start.setText("Játék indítása");
        start.addActionListener(this::start);
        menu.add(start);
    }
    
    private void start(ActionEvent e){
        GuiManager.newGame();
    }
    
}
