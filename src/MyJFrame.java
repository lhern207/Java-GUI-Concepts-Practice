/*
 * 
 */

/**
 *
 * @author Lester
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import static java.awt.GridBagConstraints.HORIZONTAL;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultEditorKit;

public class MyJFrame extends JFrame {

    private JMenuBar menubar;
    private JMenu f;
    private JMenuItem mi;
    private JMenu cascade;
    MenuChoiceListener mcl = new MenuChoiceListener();

    public MyJFrame(String title) {
        super(title);

        //Not wanted
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menubar = new JMenuBar();
        setJMenuBar(menubar);
        buildMenu();
        addButtons();
    }

    void buildMenu() {
        for (int i = 0; i < Constants.MENU.length; i++) {
            f = new JMenu(Constants.MENU[i]);
            switch (i) {
                case 0:
                    for (int j = 0; j < Constants.FILEITEMS.length; j++) {
                        if (Constants.FILEITEMS[j].equals("-")) {
                            f.addSeparator();
                        } else {
                            f.add(mi = new JMenuItem(Constants.FILEITEMS[j]));

                            mi.addActionListener(mcl);
                        }
                    }
                    break;
                case 1:
                    // Same pattern
                    for (int j = 0; j < Constants.TOOLITEMS.length; j++) {
                        if (Constants.TOOLITEMS[j].equals("*")) {
                            //Create cascading menu where "*" comes up
                            j++;
                            cascade = new JMenu(Constants.TOOLITEMS[j]);
                            for (int k = 0; k < Constants.EDITCASCADE.length; k++) {

                                if (Constants.EDITCASCADE[k].equalsIgnoreCase("Copy")) {
                                    mi = new JMenuItem(new DefaultEditorKit.CopyAction());
                                    mi.setText(Constants.EDITCASCADE[k]);
                                    cascade.add(mi);

                                } else {
                                    mi = new JMenuItem(new DefaultEditorKit.PasteAction());
                                    mi.setText(Constants.EDITCASCADE[k]);
                                    cascade.add(mi);
                                }
                                mi.addActionListener(mcl);
                            }
                            f.add(cascade);

                        } else {
                            f.add(mi = new JMenuItem(Constants.TOOLITEMS[j]));
                            mi.addActionListener(mcl);
                        }

                    }
                    break;

            }
            menubar.add(f);
        }

    }

    private class MenuChoiceListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {

            if (e.getActionCommand().equalsIgnoreCase("New")) {
                MyJFrame f = new MyJFrame(Constants.NEWFILE);
                f.getContentPane().removeAll();
                f.setSize(740, 400);
                f.setVisible(true);

            } else if (e.getActionCommand().equalsIgnoreCase("List Files")) {
                BasicFile choose = new BasicFile();
                try {
                    displayFileContents(choose.getContents(), choose.getName());
                } catch (FileNotFoundException ex) {
                    System.out.println("File not found");
                }

            } else if (e.getActionCommand().equalsIgnoreCase("Save As")) {
                //No implementation required
            } else if (e.getActionCommand().equalsIgnoreCase("Close")) {
                //No implementation required
            } else if (e.getActionCommand().equalsIgnoreCase("Sort")) {
                //No implementation required
            } else if (e.getActionCommand().equalsIgnoreCase("Search")) {
                //No implementation required
            } else if (e.getActionCommand().equalsIgnoreCase("Help")) {
                //No implementation required
            }
        }
    }

    //List files helper
    static void displayFileContents(String text, String title) throws FileNotFoundException {

        JTextArea textArea = new JTextArea();
        textArea.setText(text);
        JScrollPane scrollPane = new JScrollPane(textArea);
        MyJFrame f = new MyJFrame(title);
        f.getContentPane().removeAll();
        f.getContentPane().revalidate();
        f.getContentPane().repaint();
        f.setLayout(new BorderLayout());

        f.getContentPane().add(scrollPane, BorderLayout.CENTER);

        //Not desired
        //f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(740, 400);
        f.setLocationRelativeTo(null);
        f.setVisible(true);

    }

    public void addButtons() {
        JButton b;

        Container c = this.getContentPane();

        // Places components in a grid of rows & columns
        GridBagLayout gbag = new GridBagLayout();
        c.setLayout(gbag); // Layout each component

        // Specify the constraints for each component
        GridBagConstraints constraints = new GridBagConstraints();

        for (int i = 0; i < Constants.BUTTONS.length; i++) {
            b = new JButton(Constants.BUTTONS[i]);
            switch (i) {
                case 0:
                    // Specify the (x,y) coordinate for this component
                    constraints.gridx = 0;
                    constraints.gridy = 0; // (x,y) = (0,0)
                    constraints.insets = new Insets(0, 40, 0, 175);

                    b.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JFrame window = new JFrame();
                            window.setSize(800, 800);
                            
                            JLabel Instruction = new JLabel("You can draw the back of the house"
                                    + " and the tree by clicking and dragging the mouse. "
                                    + "A poorly drawn example can be found on the right");
                            
                            window.add(Instruction, BorderLayout.NORTH);
                            
                            //Create Polygons
                            window.getContentPane().add(new MyGraphics());
                            
                            //Implement whiteboard
                            Whiteboard drawing = new Whiteboard(window);
                            
                            //Show Example
                            JFrame example =  new JFrame(Constants.EXAMPLETITLE);
                            JLabel exampleDrawing = new JLabel(new ImageIcon(Constants.EXAMPLEIMAGE));
                            example.add(exampleDrawing);
                            example.setBounds(810, 0, 780, 800);
                                    
                            //Set both frames visible
                            window.setVisible(true);
                            example.setVisible(true);
                        }
                    });

                    break;

                case 1:
                    constraints.gridx = 2;
                    constraints.gridy = 0;
                    constraints.insets = new Insets(0, 40, 0, 275);

                    //Inner Class listener for close button
                    b.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            JOptionPane.showMessageDialog(null, "The window is closing",
                                    "COP3337", JOptionPane.INFORMATION_MESSAGE);
                            System.exit(0);
                        }
                    });
                    break;
                case 2:
                    b = new JButton(new ImageIcon(Constants.BUTTONIMAGE));
                    constraints.gridx = 0;
                    constraints.gridy = 1;
                    constraints.insets = new Insets(50, 470, 50, 100);
                    break;
                case 3:
                    constraints.gridx = 0;
                    constraints.gridy = 2;
                    constraints.insets = new Insets(0, 275, 0, 275);
                    constraints.gridwidth = 3;
                    constraints.fill = HORIZONTAL;

                    b.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {
                            Browser myBrowser = new Browser();
                        }
                    });

                    break;

                default:
                    break;
            }
            gbag.setConstraints(b, constraints);
            c.add(b);
        }
    }

}
