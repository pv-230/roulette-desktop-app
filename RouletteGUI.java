/******************************************************************************
Names: Zach Porcoro and Peter Vasiljev
Course: COP 3252
Assignment Number: X

This is the GUI class file for the roulette game. The playing area is locked in
a 1024x768 window. The GUI frame contains a menu bar and a content pane that
displays the roulette table.
******************************************************************************/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Displays an interactable roulette table
 */
public class RouletteGUI implements ActionListener
{
  private Roulette game;    // An instance of a roulette game
  protected int betAmount;  // The current bet amount by the player

  // Buttons used to play the game
  private JButton[] buttons;
  private JButton chip1, chip10, chip100, chip500, spin, clear;

  // Various components used to render the game
  private JPanel contentPane;
  private JLayeredPane layeredPane;
  private ImageIcon imageIcon;
  private JLabel bgLabel, balanceLabel;

  /**
   * Default constructor
   */
  public RouletteGUI()
  {
    game = new Roulette();
    buttons = new JButton[49];
    betAmount = 1;
  }

  /**
   * Builds and returns a menu bar
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
   * Builds and returns a content pane
   */
  private JPanel buildContentPane()
  {
    // Sets up the content pane
    contentPane = new JPanel(new BorderLayout());
    contentPane.setOpaque(true);
    contentPane.setPreferredSize(new Dimension(1024, 768));

    // Sets up the layered pane
    layeredPane = new JLayeredPane();

    // Adds the background image of the roulette table
    imageIcon = new ImageIcon(getClass().getResource("images/table.png"));
    bgLabel = new JLabel(imageIcon);
    bgLabel.setBounds(0, 0, imageIcon.getIconWidth(), imageIcon.getIconHeight());
    layeredPane.add(bgLabel, Integer.valueOf(0));

    // Adds the player balance label
    balanceLabel = new JLabel("Balance: " + game.getBalance());
    balanceLabel.setPreferredSize(new Dimension(250, 50));
    balanceLabel.setBounds(1024 / 2 - balanceLabel.getPreferredSize().width / 2,
                           650 - balanceLabel.getPreferredSize().height / 2,
                           balanceLabel.getPreferredSize().width,
                           balanceLabel.getPreferredSize().height);
    balanceLabel.setHorizontalAlignment(SwingConstants.CENTER);
    balanceLabel.setFont(new Font("Sans Serif", Font.BOLD, 24));
    balanceLabel.setForeground(Color.WHITE);
    layeredPane.add(balanceLabel, Integer.valueOf(1));

    // Adds all buttons needed to play the game
    addTableButtons();
    addChipButtons();
    addSpinClearButtons();

    // Adds the layered pane to the content pane
    contentPane.add(layeredPane);

    return contentPane;
  }

  /**
   * Creates all the table buttons
   */
  public void addTableButtons()
  {
    int xpos;          // An x-coordinate position
    int buttonNum = 1; // Used to track which button is being created

    // Populates the button array, sets button properties, and adds action
    // listeners
    for(int i = 0; i < 49; i++)
    {
      buttons[i] = new JButton("");
      buttons[i].setContentAreaFilled(false);
      buttons[i].setBorderPainted(false);
      buttons[i].setFocusPainted(false);
      buttons[i].addActionListener(this);
    }

    // Positions the 0 Button
    buttons[0].setBounds(200,177,40,205);

    // Positions the 1 - 36 buttons and the 1st, 2nd, and 3rd column buttons
    xpos = 245;
    buttonNum = 1;
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

    // Positions the dozen buttons
    xpos = 245;
    for(int i = 40; i < 43; i++)
    {
      buttons[i].setBounds(xpos,387,175,65);
      xpos += 180;
    }

    // Positions the bottom buttons
    xpos = 245; 
    for(int i = 43; i < 49; i++)
    {
      buttons[i].setBounds(xpos,457,85,65);
      xpos += 90;
    }

    // Adds all the table buttons
    for(int i = 0; i < 49; i++)
    {
      layeredPane.add(buttons[i], Integer.valueOf(1));
    }
  }

  /**
   * Creates the chip selection buttons
   */
  public void addChipButtons()
  {
    // Declares the buttons.
    chip1 = new JButton(""); 
    chip10 = new JButton("");
    chip100 = new JButton("");
    chip500 = new JButton("");

    // Sets their positions.
    chip1.setBounds(0,606,100,100);
    chip10.setBounds(100,606,100,100);
    chip100.setBounds(200,606,100,100);
    chip500.setBounds(300,606,100,100);

    // Sets the button properties.
    chip1.setIcon(new ImageIcon(getClass().getResource("images/placeholder.png")));
    chip1.setContentAreaFilled(false);
    chip1.setBorderPainted(false);
    chip10.setIcon(new ImageIcon(getClass().getResource("images/10Big.png")));
    chip10.setContentAreaFilled(false);
    chip10.setBorderPainted(false);
    chip100.setIcon(new ImageIcon(getClass().getResource("images/100Big.png")));
    chip100.setContentAreaFilled(false);
    chip100.setBorderPainted(false);
    chip500.setIcon(new ImageIcon(getClass().getResource("images/500Big.png")));
    chip500.setContentAreaFilled(false);
    chip500.setBorderPainted(false);

    // Adds the chip buttons.
    layeredPane.add(chip1, Integer.valueOf(1));
    layeredPane.add(chip10, Integer.valueOf(1));
    layeredPane.add(chip100, Integer.valueOf(1));
    layeredPane.add(chip500, Integer.valueOf(1));

    // Adds the action listeners.
    chip1.addActionListener(this);
    chip10.addActionListener(this);
    chip100.addActionListener(this);
    chip500.addActionListener(this);
  }

  /**
   * Creates and adds both the spin and clear buttons
   */
  public void addSpinClearButtons()
  {
    // Creates the buttons.
    spin = new JButton("");
    clear = new JButton("");

    // Sets the button properties.
    spin.setIcon(new ImageIcon(getClass().getResource("images/spin.png")));
    spin.setBounds(824,606,200,100);
    spin.setBorderPainted(false);
    clear.setIcon(new ImageIcon(getClass().getResource("images/clear.png")));
    clear.setBounds(624,606,200,100);
    clear.setBorderPainted(false);

    // Adds both buttons.
    layeredPane.add(spin, Integer.valueOf(1));
    layeredPane.add(clear, Integer.valueOf(1));

    // Adds the action listeners.
    spin.addActionListener(this);
    clear.addActionListener(this);
  }

  /**
   * Highlights the chip selected by the player
   */
  private void setSelectedChip(int newChip)
  {
    if (newChip == 1 && newChip != betAmount)
    {
      betAmount = 1;
      chip1.setIcon(new ImageIcon(getClass().getResource("images/placeholder.png")));
      chip10.setIcon(new ImageIcon(getClass().getResource("images/10Big.png")));
      chip100.setIcon(new ImageIcon(getClass().getResource("images/100Big.png")));
      chip500.setIcon(new ImageIcon(getClass().getResource("images/500Big.png")));
    }
    else if (newChip == 10 && newChip != betAmount)
    {
      betAmount = 10;
      chip1.setIcon(new ImageIcon(getClass().getResource("images/1Big.png")));
      chip10.setIcon(new ImageIcon(getClass().getResource("images/placeholder.png")));
      chip100.setIcon(new ImageIcon(getClass().getResource("images/100Big.png")));
      chip500.setIcon(new ImageIcon(getClass().getResource("images/500Big.png")));
    }
    else if (newChip == 100 && newChip != betAmount)
    {
      betAmount = 100;
      chip1.setIcon(new ImageIcon(getClass().getResource("images/1Big.png")));
      chip10.setIcon(new ImageIcon(getClass().getResource("images/10Big.png")));
      chip100.setIcon(new ImageIcon(getClass().getResource("images/placeholder.png")));
      chip500.setIcon(new ImageIcon(getClass().getResource("images/500Big.png")));
    }
    else if (newChip == 500 && newChip != betAmount)
    {
      betAmount = 500;
      chip1.setIcon(new ImageIcon(getClass().getResource("images/1Big.png")));
      chip10.setIcon(new ImageIcon(getClass().getResource("images/10Big.png")));
      chip100.setIcon(new ImageIcon(getClass().getResource("images/100Big.png")));
      chip500.setIcon(new ImageIcon(getClass().getResource("images/placeholder.png")));
    }
  }

  /**
   * Allows for interacting with all the buttons on the table
   */
  public void actionPerformed(ActionEvent e)
  {
    // Changes the bet amount
    if(e.getSource() == chip1)
      setSelectedChip(1);
    else if(e.getSource() == chip10)
      setSelectedChip(10);
    else if(e.getSource() == chip100)
      setSelectedChip(100);
    else if(e.getSource() == chip500)
      setSelectedChip(500);
    else if(e.getSource() == spin)
      {
      // Spins the wheel and pays out
      game.spin();

      // Removes all the chips from the table
      for(int i = 0; i < 49; i++)
      {
        buttons[i].setIcon(new ImageIcon(getClass().getResource("images/empty.png")));
      }

      // Updates the balance label
      balanceLabel.setText("Balance: " + game.getBalance());
    }
    else if(e.getSource() == clear)
    {
      // Clears the player's bets
      game.clearBets();

      // Removes all the chips from the table
      for(int i = 0; i < 49; i++)
      {
        buttons[i].setIcon(new ImageIcon(getClass().getResource("images/empty.png")));
      }

      // Updates the balance label
      balanceLabel.setText("Balance: " + game.getBalance());
    }
    else
    {
      // Loops through all the buttons
      for(int i = 0; i < 49; i++)
      {
        // Checks the button
        if(e.getSource() == buttons[i])
        {
          // Checks that they have enough money
          if(game.getBalance() >= betAmount)
          {
            // Adds the bet
            game.addBet(i, betAmount);

            // Adds the chip image
            if(betAmount == 1)
              buttons[i].setIcon(new ImageIcon(getClass().getResource("images/1.png")));
            else if(betAmount == 10)
              buttons[i].setIcon(new ImageIcon(getClass().getResource("images/10.png")));
            else if(betAmount == 100)
              buttons[i].setIcon(new ImageIcon(getClass().getResource("images/100.png")));
            else
              buttons[i].setIcon(new ImageIcon(getClass().getResource("images/500.png")));
          }
            
          // Updates the balance label
          balanceLabel.setText("Balance: " + game.getBalance());
          break;
        }
      }
    }
  }

  /**
   * Custom JPanel to be used for testing stuff
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

    // Builds a new frame for the GUI
    rouletteFrame = new JFrame("Roulette");
    rouletteFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    rouletteFrame.setResizable(false);

    // Sets the frame's menu bar and content pane and displays the frame
    gui = new RouletteGUI();
    rouletteFrame.setJMenuBar(gui.buildMenuBar());
    rouletteFrame.setContentPane(gui.buildContentPane());
    rouletteFrame.pack();
    rouletteFrame.setVisible(true);
  }
}
