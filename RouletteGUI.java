import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Displays an interactable roulette table.
 */
public class RouletteGUI implements ActionListener
{
  Roulette game;
  private JButton[] buttons = new JButton[49];  // Array to store all the buttons
  private JButton chip1, chip10, chip100, chip500, spin, clear;
  protected int betAmount = 1;

  private JPanel contentPane;
  private JLayeredPane layeredPane;
  private ImageIcon imageIcon;
  private JLabel bgLabel, gifLabel, balanceLabel;
  private CustomPanel customPanel;

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

    // Calls the set button function
    setButtons(buttons);
    
    // Makes the buttons transparent and adds them to the layered pane
    for(int i = 0; i < 49; i++)
    {
      buttons[i].setOpaque(false);
      buttons[i].setContentAreaFilled(false);
      buttons[i].setBorderPainted(false);
      layeredPane.add(buttons[i], Integer.valueOf(4));
    }

    // Adds the action listeners
    for(int i = 0; i < 49; i++)
    {
      buttons[i].addActionListener(this);
    }

    // Creates the chip buttons
    chipButtons();
    // Creates the spin and clear buttons
    spinClearButtons();

    // Adds the layered pane to the content pane.
    contentPane.add(layeredPane);

    return contentPane;
  }

  // Creates the spin and clear buttons
  public void spinClearButtons()
  {
    // Declares the buttons
    spin = new JButton("");
    clear = new JButton("");

    // Sets their sizes
    spin.setBounds(824,606,200,100);
    clear.setBounds(624,606,200,100);

    // Sets their pictures
    spin.setIcon(new ImageIcon(RouletteGUI.class.getResource("spin.png")));
    clear.setIcon(new ImageIcon(RouletteGUI.class.getResource("clear.png")));

    // Adds the buttons to the layered pane
    layeredPane.add(spin, Integer.valueOf(4));
    layeredPane.add(clear, Integer.valueOf(4));

    // Adds the action listener
    spin.addActionListener(this);
    clear.addActionListener(this);
  }

  public void chipButtons()
  {
    // Declares the buttons
    chip1 = new JButton(""); 
    chip10 = new JButton("");
    chip100 = new JButton("");
    chip500 = new JButton("");

    // Sets their positions
    chip1.setBounds(0,606,100,100);
    chip10.setBounds(100,606,100,100);
    chip100.setBounds(200,606,100,100);
    chip500.setBounds(300,606,100,100);

    // Sets their picture and make the button transparent
    chip1.setIcon(new ImageIcon(RouletteGUI.class.getResource("1Big.png")));
    chip1.setOpaque(false);
    chip1.setContentAreaFilled(false);
    //chip1.setBorderPainted(false);
    chip10.setIcon(new ImageIcon(RouletteGUI.class.getResource("10Big.png")));
    chip10.setOpaque(false);
    chip10.setContentAreaFilled(false);
    //chip1.setBorderPainted(false);
    chip100.setIcon(new ImageIcon(RouletteGUI.class.getResource("100Big.png")));
    chip100.setOpaque(false);
    chip100.setContentAreaFilled(false);
    //chip1.setBorderPainted(false);
    chip500.setIcon(new ImageIcon(RouletteGUI.class.getResource("500Big.png")));
    chip500.setOpaque(false);
    chip500.setContentAreaFilled(false);
    //chip1.setBorderPainted(false);

    // Adds the buttons
    layeredPane.add(chip1, Integer.valueOf(4));
    layeredPane.add(chip10, Integer.valueOf(4));
    layeredPane.add(chip100, Integer.valueOf(4));
    layeredPane.add(chip500, Integer.valueOf(4));

    // Adds the action listener
    chip1.addActionListener(this);
    chip10.addActionListener(this);
    chip100.addActionListener(this);
    chip500.addActionListener(this);
  }

  // Creates all the board buttons
  public void setButtons(JButton []buttons)
  {
    // Creates all the buttons
    for(int i = 0; i < 49; i++)
    {
      buttons[i] = new JButton("");
    }

    // Creates the 0 Button
    buttons[0].setBounds(200,177,40,205);
    int xpos = 245;
    int buttonNum = 1;

    // Creates the 1 - 36, 1st, 2nd, and 3rd buttons
    for(int i = 0; i < 13; i++)
    {
      buttons[buttonNum].setBounds(xpos, 317, 40, 65);
      buttonNum++;
      buttons[buttonNum].setBounds(xpos,247,40,65);
      buttonNum++;
      buttons[buttonNum].setBounds(xpos,177,40,65);
      buttonNum++;
      xpos += 45;
    }

    // Creates the dozen buttons
    xpos = 245;
    for(int i = 40; i < 43; i++)
    {
      buttons[i].setBounds(xpos,387,175,65);
      xpos += 180;
    }

    // Creates the bottom buttons
    xpos = 245; 
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
    // Changes the bet amount
    if(e.getSource() == chip1)
      betAmount = 1;
    else if(e.getSource() == chip10)
      betAmount = 10;
    else if(e.getSource() == chip100)
      betAmount = 100;
    else if(e.getSource() == chip500)
      betAmount = 500;
    else if(e.getSource() == spin)
    {
      // Spins the wheel and pays out
      game.spin();

      // Removes all the chips from the board
      for(int i = 0; i < 49; i++)
      {
        buttons[i].setIcon(new ImageIcon(RouletteGUI.class.getResource("empty.png")));
      }

      // Updates the balance label
      balanceLabel.setText("Balance: " + game.getBalance());
    }
    else if(e.getSource() == clear)
    {
      // Clears the bets
      game.clearBets();

      // Removes all the chips from the board
      for(int i = 0; i < 49; i++)
      {
        buttons[i].setIcon(new ImageIcon(RouletteGUI.class.getResource("empty.png")));
      }

      // Updates the balance label
      balanceLabel.setText("Balance: " + game.getBalance());
    }

    // Loops through all the buttons
    for(int i = 0; i < 49; i++)
    {
      // Checks the button
      if(e.getSource() == buttons[i])
      {
        // Adds the bet
        game.addBet(i, betAmount);

        // Adds the chip image
        if(betAmount == 1)
          buttons[i].setIcon(new ImageIcon(RouletteGUI.class.getResource("1.png")));
        else if(betAmount == 10)
          buttons[i].setIcon(new ImageIcon(RouletteGUI.class.getResource("10.png")));
        else if(betAmount == 100)
          buttons[i].setIcon(new ImageIcon(RouletteGUI.class.getResource("100.png")));
        else
          buttons[i].setIcon(new ImageIcon(RouletteGUI.class.getResource("500.png")));
          
        // Updates the balance label
        balanceLabel.setText("Balance: " + game.getBalance());
        return;
      }
    }
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
