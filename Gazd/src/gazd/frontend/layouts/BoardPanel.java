/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.layouts;

import gazd.frontend.util.DrawHelper;
import gazd.backend.domain.Player;
import gazd.frontend.GuiManager;
import gazd.frontend.util.ScreenConstants;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 *
 * @author <Andó Sándor Zsolt>
 */
public class BoardPanel extends JLayeredPane {

    private JLabel board;
    private GuiManager gui;
    private final int PANEL_WIDTH = ScreenConstants.SCREEN_WIDTH.getValue();
    private final int PANEL_HEIGHT = ScreenConstants.BOARD_PANEL_HEIGHT.getValue();

    public BoardPanel(GuiManager gui) {
        this.gui = gui;
        init();
    }

    private void init() {
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        setLayout(new FlowLayout());
        setBackgroundImage();

    }

    private void setBackgroundImage() {
        board = new JLabel(loadBackgroundImage());
        add(board, new Integer(0));

    }

    private ImageIcon loadBackgroundImage() {
        Image img = new ImageIcon("img/board.jpg").getImage();
        Image newimg = img.getScaledInstance(PANEL_WIDTH, PANEL_HEIGHT, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        gui.getPlayers().forEach(p -> drawPlayer(grphcs, p));
    }

    private void drawPlayer(Graphics g, Player player) {
        Point p = DrawHelper.getPoint(PANEL_WIDTH, PANEL_HEIGHT, player.getPosition(), player.getPiece().ordinal());
        Image img = new ImageIcon("img/" + player.getPiece().name() + ".jpg").getImage();
        g.drawImage(img, (int) p.getX(), (int) p.getY(), PANEL_WIDTH / 40, PANEL_WIDTH / 40, this);
    }
}
