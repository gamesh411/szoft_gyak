/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.layouts;

import gazd.backend.Player;
import gazd.frontend.GuiManager;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 *
 * @author <Andó Sándor Zsolt>
 */
public class BoardPane extends JLayeredPane{
    
    private JLabel board;

    public BoardPane() {
        init();
    }
    
    private void init(){
        setPreferredSize(new Dimension(1100, 700));
        setLayout(new FlowLayout());
        setBackgroundImage();
        
    }

    private void setBackgroundImage() {
        board = new JLabel(loadBackgroundImage());
        board.setPreferredSize(new Dimension(1100, 700));
        add(board, new Integer(0));

    }

    private ImageIcon loadBackgroundImage() {
        Image img = new ImageIcon("img/board.jpg").getImage();
        Image newimg = img.getScaledInstance(1100, 700, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }
    

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs); //To change body of generated methods, choose Tools | Templates.
        GuiManager.getPlayers().stream().forEach(p -> drawPlayer(grphcs, p));
        
    }
    
    public void drawPlayer(Graphics g, Player player){
        Point p = GuiManager.getPoint(1100, 700, player.getPosition(), player.getPiece().ordinal());
        Image img = new ImageIcon("img/" +player.getPiece().name()+".jpg").getImage();
        g.drawImage(img, (int)p.getX(), (int)p.getY(),30,30, this);
    }
    
 
    
    
}
