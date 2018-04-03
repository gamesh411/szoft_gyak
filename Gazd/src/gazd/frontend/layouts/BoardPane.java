/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.layouts;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
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
        setPreferredSize(new Dimension(2200, 1400));
        setLayout(new FlowLayout());
        setBackgroundImage();
    }

    private void setBackgroundImage() {
        board = new JLabel(loadBackgroundImage());
        board.setPreferredSize(new Dimension(2200, 1400));
        add(board, new Integer(0));
    }

    private ImageIcon loadBackgroundImage() {
        Image img = new ImageIcon("board.jpg").getImage();
        Image newimg = img.getScaledInstance(2200, 1400, java.awt.Image.SCALE_SMOOTH);
        return new ImageIcon(newimg);
    }
    
    
}
