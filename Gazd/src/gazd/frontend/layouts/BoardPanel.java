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
import java.awt.Toolkit;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

/**
 *
 * @author <Andó Sándor Zsolt>
 */
public class BoardPanel extends JLayeredPane{
    
    private JLabel board;
    private GuiManager gui;
    private final Dimension SCREEN_SIZE;

    public BoardPanel(GuiManager gui) {
	this.gui = gui;
        SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
        init();
    }
    
    private void init(){
        setPreferredSize(new Dimension(SCREEN_SIZE.width, SCREEN_SIZE.height - 300));
        setLayout(new FlowLayout());
        setBackgroundImage();
        
    }

    private void setBackgroundImage() {
        board = new JLabel(loadBackgroundImage());
        add(board, new Integer(0));

    }

    private ImageIcon loadBackgroundImage() {
        Image img = new ImageIcon("img/board.jpg").getImage();
        Image newimg = img.getScaledInstance(SCREEN_SIZE.width, SCREEN_SIZE.height - 300, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }
    

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs); //To change body of generated methods, choose Tools | Templates.
        gui.getPlayers().stream().forEach(p -> drawPlayer(grphcs, p));
    }
    
    private void drawPlayer(Graphics g, Player player){
        Point p = getPoint(SCREEN_SIZE.width, SCREEN_SIZE.height - 300, player.getPosition(), player.getPiece().ordinal());
        Image img = new ImageIcon("img/" +player.getPiece().name()+".jpg").getImage();
        g.drawImage(img, (int)p.getX(), (int)p.getY(),30,30, this);
    }

    private Point getPoint(int width, int height, int position, int plus){
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
