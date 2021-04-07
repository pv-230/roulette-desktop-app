import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Displays an interactable roulette table.
 */
public class RouletteGUI implements ActionListener
{
  Roulette game;

  /**
   * Default constructor.
   */
  public RouletteGUI()
  {
    game = new Roulette();
  }

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
    menuItem = new JMenuItem("Save Profile");
    fileMenu.add(menuItem);
    menuItem = new JMenuItem("Load Profile");
    fileMenu.add(menuItem);

    // Sets up the help menu
    menuItem = new JMenuItem("How to Play");
    helpMenu.add(menuItem);

    return menuBar;
  }

  /**
   * Builds and returns a content pane.
   */
  private JPanel buildContentPane()
  {
    JPanel contentPane;
    JLayeredPane layeredPane;
    ImageIcon imageIcon;
    JLabel bgLabel, gifLabel, balanceLabel;
    CustomPanel customPanel;

    // Sets up the content pane.
    contentPane = new JPanel(new BorderLayout());
    contentPane.setOpaque(true);

    // Sets up the layered pane.
    layeredPane = new JLayeredPane();
    layeredPane.setPreferredSize(new Dimension(1024, 768));

    // Adds the background image of the roulette board.
    imageIcon = new ImageIcon(RouletteGUI.class.getResource("board.png"));
    bgLabel = new JLabel(imageIcon);
    bgLabel.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
    layeredPane.add(bgLabel, Integer.valueOf(0));

    // Adds the spinner gif.
    imageIcon = new ImageIcon(RouletteGUI.class.getResource("spinner.gif"));
    gifLabel = new JLabel(imageIcon);
    gifLabel.setBounds(1024 / 2 - imageIcon.getIconWidth() / 2, 
                       768 / 2  - imageIcon.getIconHeight() / 2,
                       imageIcon.getIconWidth(),
                       imageIcon.getIconHeight());
    gifLabel.setVisible(false);
    layeredPane.add(gifLabel, Integer.valueOf(2));

    // Adds the player balance label
    balanceLabel = new JLabel("Balance: " + game.getBalance());
    balanceLabel.setPreferredSize(new Dimension(250, 50));
    balanceLabel.setBounds(1024 / 2 - balanceLabel.getPreferredSize().width / 2,
                           625 - balanceLabel.getPreferredSize().height / 2,
                           balanceLabel.getPreferredSize().width,
                           balanceLabel.getPreferredSize().height);
    // balanceLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
    balanceLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
    balanceLabel.setForeground(Color.BLACK);
    layeredPane.add(balanceLabel, Integer.valueOf(1));

    // Adds the custom JPanel
    customPanel = new CustomPanel();
    customPanel.setBounds(0, 0,
                          customPanel.getPreferredSize().width,
                          customPanel.getPreferredSize().height);
    layeredPane.add(customPanel, Integer.valueOf(3));


    JButton[] buttons = new JButton[49];  // Array to store all the buttons
    setButtons(buttons);  // Calls the set button function
    
    for(int i = 0; i < 49; i++) // Makes the buttons transparent and adds them to the layered pane
    {
      buttons[i].setOpaque(false);
      buttons[i].setContentAreaFilled(false);
      buttons[i].setBorderPainted(false);
      layeredPane.add(buttons[i], Integer.valueOf(4));
    }

    // Adds the layered pane to the content pane.
    contentPane.add(layeredPane);

    return contentPane;
  }

  public void setButtons(JButton []buttons) // Creates all the board buttons
  {
    for(int i = 0; i < 49; i++) // Creates all the buttons
    {
      buttons[i] = new JButton("");
    }

    buttons[0].setBounds(200,177,40,205);  // Creates the 0 Button
    int xpos = 245;
    int buttonNum = 1;
    for(int i = 0; i < 13; i++) // Creates the 1 - 36, 1st, 2nd, and 3rd buttons
    {
      buttons[buttonNum].setBounds(xpos, 317, 40, 65);
      buttonNum++;
      buttons[buttonNum].setBounds(xpos,247,40,65);
      buttonNum++;
      buttons[buttonNum].setBounds(xpos,177,40,65);
      buttonNum++;
      xpos += 45;
    }

    xpos = 245; // Creates the dozen buttons
    for(int i = 40; i < 43; i++)
    {
      buttons[i].setBounds(xpos,387,175,65);
      xpos += 180;
    }

    xpos = 245; // Creates the bottom buttons
    for(int i = 43; i < 49; i++)
    {
      buttons[i].setBounds(xpos,457,85,65);
      xpos += 90;
    }
  }

  /**
   * Allows for interacting with other parts of the application.
   */
  public void actionPerformed(ActionEvent e)
  {

  }

  /**
   * Custom JPanel to be used for testing stuff.
   */
  private class CustomPanel extends JPanel
  {
    public CustomPanel()
    {
      super();
      setPreferredSize(new Dimension(1024, 768));
      setOpaque(false);
    }

    public void paintComponent(Graphics g)
    {
      super.paintComponent(g);
      g.setColor(Color.RED);
      g.drawLine(1024 / 2, 0, 1024 / 2, 768);
      g.drawLine(0, 768 / 2, 1024, 768 / 2);
    }
  }

  public static void main(String args[])
  {
    JFrame rouletteFrame;
    RouletteGUI gui;

    // Builds a new frame for the GUI.
    rouletteFrame = new JFrame("Roulette");
    rouletteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    rouletteFrame.setResizable(false);
    rouletteFrame.setSize(1024, 768);

    // Sets the frame's menu bar and content pane.
    gui = new RouletteGUI();
    rouletteFrame.setJMenuBar(gui.buildMenuBar());
    rouletteFrame.setContentPane(gui.buildContentPane());
    rouletteFrame.setVisible(true);
  }
}
