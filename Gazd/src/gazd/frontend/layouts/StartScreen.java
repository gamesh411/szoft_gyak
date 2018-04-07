/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.layouts;

import java.awt.FlowLayout;
import gazd.frontend.menu.Menu;
import javax.swing.JFrame;

/**
 *
 * @author Dell
 */
public class StartScreen extends JFrame {

    public StartScreen() {
        initStartScreen();          
    }

    private void initStartScreen() {
        this.setLayout(new FlowLayout());
        add(new MainPanel());
        setJMenuBar(new Menu());
        setTitle("Gazdálkodj okosan");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
