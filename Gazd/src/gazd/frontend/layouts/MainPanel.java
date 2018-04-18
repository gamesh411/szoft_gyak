/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.layouts;

import gazd.frontend.GuiManager;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

/**
 *
 * @author Dell
 */
public class MainPanel extends JPanel {

    ControlPanel controlPanel;
    BoardPanel boardPanel;

    GuiManager gui;

    public MainPanel(GuiManager gui){
	this.gui = gui;
        initPanel();
    }

    private void initPanel() {
        setBackground(Color.red);
        setPreferredSize(new Dimension(1100, 800));
        
        controlPanel = new ControlPanel(gui);       
        boardPanel = new BoardPanel(gui);
        
        add(controlPanel);
        add(boardPanel);

    }

    public ControlPanel getControlPanel() {
	return controlPanel;
    }

    public BoardPanel getBoardPanel() {
	return boardPanel;
    }

    void update() {
        controlPanel.update();
    }
}
