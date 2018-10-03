/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.layouts;

import gazd.frontend.GuiManager;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 *
 * @author Dell
 */
public class MainPanel extends JPanel {

    ControlPanel controlPanel;
    BoardPanel boardPanel;

    GuiManager gui;

    public MainPanel(GuiManager gui) {
        this.gui = gui;
        initPanel();
    }

    private void initPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

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

    public void update() {
        controlPanel.update();
    }
}
