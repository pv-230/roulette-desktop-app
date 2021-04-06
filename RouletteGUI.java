import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Displays an interactable roulette table.
 */
public class RouletteGUI extends MouseAdapter implements ActionListener
{
  /**
   * Builds and returns a menu bar.
   */
  private JMenuBar buildMenuBar()
  {
    JMenuBar menuBar;
    JMenu fileMenu, helpMenu;
    JMenuItem menuItem;

    // Sets up the menu bar
    menuBar = new JMenuBar();
    fileMenu = new JMenu("File");
    helpMenu = new JMenu("Help");
    menuBar.add(fileMenu);
    menuBar.add(helpMenu);

    // Sets up the file menu
    menuItem = new JMenuItem("New Game");
    fileMenu.add(menuItem);
    menuItem = new JMenuItem("Save Game");
    fileMenu.add(menuItem);
    menuItem = new JMenuItem("Load Game");
    fileMenu.add(menuItem);

    // Sets up the help menu
    menuItem = new JMenuItem("How to Play");
    helpMenu.add(menuItem);

    return menuBar;
  }

  /**
   * Builds and returns a layered pane that holds the background image of a
   * roulette table,
   */
  private JLayeredPane buildLayeredPane()
  {
    ImageIcon img;
    JLabel label;
    JLayeredPane pane;

    // Sets up the layered pane.
    pane = new JLayeredPane();
    pane.setLayout(null);  // For absolute layout positioning

    // Adds the background image of the roulette board.
    img = new ImageIcon(RouletteGUI.class.getResource("board.png"));
    label = new JLabel(img);
    label.setBounds(0, 0, img.getIconWidth(), img.getIconHeight());
    pane.add(label, Integer.valueOf(0));

    // Adds the spinner.
    img = new ImageIcon(RouletteGUI.class.getResource("spinner.gif"));
    label = new JLabel(img);
    label.setBounds(1024 / 2 - img.getIconWidth() / 2, 
                    768 / 2  - img.getIconHeight() / 2,
                    img.getIconWidth(),
                    img.getIconHeight());
    pane.add(label, Integer.valueOf(2));

    return pane;
  }

  /**
   * Allows for the player to interact with the roulette board.
   */
  public void mouseClicked(MouseEvent e)
  {

  }

  /**
   * Allows for interacting with other parts of the application.
   */
  public void actionPerformed(ActionEvent e)
  {

  }

  public static void main(String args[])
  {
    JFrame rouletteFrame;
    RouletteGUI gui;

    // Builds a new frame for the application.
    rouletteFrame = new JFrame("Roulette");
    rouletteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    rouletteFrame.setResizable(false);

    // Sets up the frame with a menu bar and a layered pane.
    gui = new RouletteGUI();
    rouletteFrame.setJMenuBar(gui.buildMenuBar());
    rouletteFrame.add(gui.buildLayeredPane());

    // Displays the frame.
    rouletteFrame.setSize(1024, 768);
    rouletteFrame.setVisible(true);
  }
}
