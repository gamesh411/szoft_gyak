/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.layouts;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Dell
 */
public class MainPanel extends JPanel {

    public MainPanel(){
        initPanel();
    }

    private void initPanel() {
        setBackground(Color.red);
        setPreferredSize(new Dimension(2200, 1600));
        
        ControlPanel buttonPanel = new ControlPanel();       
        BoardPane layeredPane = new BoardPane();
        
        add(buttonPanel);
        add(layeredPane);

    }

}
