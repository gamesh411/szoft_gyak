/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.layouts;

import java.awt.Point;

/**
 *
 * @author MetaPC
 */
public class DrawHelper {
    
    public static Point getPoint(int width, int height, int position, int plus){
        int diff = plus*10;
        if(position==0) return new Point(12*width/13,8*height/9  + diff);
        if(position==13) return new Point(width/17,8*height/9  + diff);
        if(position==21) return new Point(width/17,height/11  + diff);
        if(position==34) return new Point(12*width/13,height/11  + diff);
        if(position<13){
            int twidth = width/4;
            twidth = width-twidth/2 - position*width/17;
            return new Point(twidth,7*height/8  + diff);
        }else if(position<21){
            int tpos = position - 14;
            int theight = (int)(height/2);
            theight = height-theight/2 - tpos*height/11;
            return new Point(width/15  + diff,theight);
        }else if(position<34){
            int tpos = 13-(position - 21);
            int twidth = width/4;
            twidth = width-twidth/2 - tpos*width/17;
            return new Point(twidth,height/9  + diff);
        }else{
            int tpos = 6 - (position - 35);
            int theight = (int)(height/2);
            theight = height-theight/2 - tpos*height/11;
            return new Point(14*width/15  + diff,theight);
        }
    }
    
}
