/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend.helper;

import java.awt.Point;

/**
 *
 * @author MetaPC
 */
public class DrawHelper {

    private static final int RIGHT_BOTTOOM = 0;
    private static final int LEFT_BOTTOOM = 13;
    private static final int LEFT_TOP = 21;
    private static final int RIGHT_TOP = 34;

    public static Point getPoint(int width, int height, int position, int plus) {
        int diffToOtherPieces = plus * 10;
        if (position == RIGHT_BOTTOOM) {
            return new Point(12 * width / 13, 8 * height / 9 + diffToOtherPieces);
        }
        if (position == LEFT_BOTTOOM) {
            return new Point(width / 17, 8 * height / 9 + diffToOtherPieces);
        }
        if (position == LEFT_TOP) {
            return new Point(width / 17, height / 11 + diffToOtherPieces);
        }
        if (position == RIGHT_TOP) {
            return new Point(12 * width / 13, height / 11 + diffToOtherPieces);
        }
        if (position < LEFT_BOTTOOM) {
            int twidth = width / 4;
            twidth = width - twidth / 2 - position * width / 17;
            return new Point(twidth, 7 * height / 8 + diffToOtherPieces);
        } else if (position < LEFT_TOP) {
            int tpos = position - 14;
            int theight = (int) (height / 2);
            theight = height - theight / 2 - tpos * height / 11;
            return new Point(width / 15 + diffToOtherPieces, theight);
        } else if (position < RIGHT_TOP) {
            int tpos = 13 - (position - 21);
            int twidth = width / 4;
            twidth = width - twidth / 2 - tpos * width / 17;
            return new Point(twidth, height / 9 + diffToOtherPieces);
        } else {
            int tpos = 6 - (position - 35);
            int theight = (int) (height / 2);
            theight = height - theight / 2 - tpos * height / 11;
            return new Point(14 * width / 15 + diffToOtherPieces, theight);
        }
    }

    private DrawHelper() {

    }
}
