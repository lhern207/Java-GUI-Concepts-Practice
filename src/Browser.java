/*
 *
 */

/**
 *
 * @author Lester
 */

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

public class Browser {

    MyJFrame f;
    JTextField enter;
    JEditorPane contents;
    Container contentPane;

    public Browser() {

        f = new MyJFrame(Constants.BROWSERTITLE);
        buildBrowser();
        f.setSize(1600, 900);
        f.setVisible(true);
    }

    void buildBrowser() {
        //Clear content pane 
        contentPane = f.getContentPane();
        contentPane.removeAll();
        contentPane.revalidate();
        contentPane.repaint();
        f.setLayout(new BorderLayout());

        enter = new JTextField("http://");

        enter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getThePage(e.getActionCommand());
            }
        }
        );

        contentPane.add(enter, BorderLayout.NORTH);

        contents = new JEditorPane();

        contents.setEditable(false);

        contents.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (e.getEventType() == (HyperlinkEvent.EventType.ACTIVATED)) {
                    getThePage(e.getURL().toString());
                }
            }
        }
        );
        
        JScrollPane scrollPane = new JScrollPane(contents);
        contentPane.add(scrollPane, BorderLayout.CENTER);

    }

    void getThePage(String location) {
        try {
            f.getGlassPane().setCursor(new Cursor(Cursor.WAIT_CURSOR));
            f.getGlassPane().setVisible(true);
            contents.setPage(location);
            enter.setText(location);
            
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.out.println("Interrupted exception on cursor loading");
            }
            
            f.getGlassPane().setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            f.getGlassPane().setVisible(false);
        } catch (IOException io) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error cannot access specified URL",
                    "Bad URL", JOptionPane.ERROR_MESSAGE);
        }
    }

}
