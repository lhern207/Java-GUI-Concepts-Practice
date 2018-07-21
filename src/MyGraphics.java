/*
 * 
 */

/**
 *
 * @author Lester
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import javax.swing.JComponent;

class MyGraphics extends JComponent {

    @Override
    public void paint(Graphics g) {
        int[] starX = new int[]{250, 300, 280, 330, 380, 360, 410, 345, 330, 315};
        int[] starY = new int[]{300, 270, 225, 250, 225, 270, 300, 300, 350, 300};

        int[] houseFrontX = new int[]{380, 300, 300, 460, 460};
        int[] houseFrontY = new int[]{400, 430, 500, 500, 430};

        int[] doorX = new int[]{360, 400, 400, 360};
        int[] doorY = new int[]{500, 500, 460, 460};

        g.setColor(Color.ORANGE);
        g.fillPolygon(starX, starY, starX.length);

        g.setColor(Color.BLUE);
        g.fillPolygon(houseFrontX, houseFrontY, houseFrontX.length);

        g.setColor(Color.WHITE);
        g.fillPolygon(doorX, doorY, doorX.length);

        //The house label
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        ge.getAllFonts();
        Font font = new Font("Jokerman", Font.PLAIN, 35);
        g.setColor(Color.BLACK);
        g.setFont(font);
        g.drawChars(Constants.HOUSELABEL, 0, Constants.HOUSELABEL.length, 300, 540);
   
        
        

    }

}
