/*
 * 
 */

/**
 *
 * @author Lester
 */

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;

public class Whiteboard {

    int lastX, lastY;
    JFrame f;

    public Whiteboard(JFrame frame) {
        f = frame;
        f.setTitle(Constants.GRAPHICSTITLE);

        Container c = f.getContentPane();
      

        f.addMouseListener(new PositionRecorder());
        f.addMouseMotionListener(new CircleDrawer());

    }

    void record(int x, int y) {
        lastX = x;
        lastY = y;
    }

    // Record position that mouse entered window or
    // where user pressed mouse button. 
    private class PositionRecorder extends MouseAdapter {

        public void mouseEntered(MouseEvent e) {

            record(e.getX(), e.getY());
        }

        public void mousePressed(MouseEvent e) {
            record(e.getX(), e.getY());
        }

    }

    // As user drags mouse, connect subsequent positions with small circles
    private class CircleDrawer extends MouseMotionAdapter {

        public void mouseDragged(MouseEvent e) {
            Graphics g = f.getGraphics();
            g.setColor(Color.red);
            g.fillOval(lastX, lastY, 10, 10);
            record(e.getX(), e.getY());
           
        }
    }
}
