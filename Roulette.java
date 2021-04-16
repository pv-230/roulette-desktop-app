/******************************************************************************
Names: Zach Porcoro and Peter Vasiljev
Course: COP 3252
Assignment Number: X

This is the game logic file for the Roulette application. All game behavior
such as winnings payout is controlled here. The roulette GUI file will use
various public methods from this class.
******************************************************************************/

import java.util.Random;

/** Contains the game logic and holds the player's balance */
public class Roulette implements java.io.Serializable
{
  private int balance;     // The player's current balance
  private int oldBalance;  // The balance of the player before a round
  private int num;         // The number that the ball landed on
  private int earnings;    // How much a player won/lost after a round
  private Random random;

  // betsBool stores true for the bets that the player won. The bets array
  // stores the bet amounts for each bet position.
  private Boolean[] betsBool;
  private int[] bets;
  // Array slots for bets array are:
  // 0 - 36 = Numbers 0 - 36
  // 37 = 1st column, 38 = 2nd column, 39 = 3rd column
  // 40 = 1st dozen, 41 = 2nd dozen, 42 = 3rd dozen
  // 43 = 1 - 18, 44 = 19 - 36
  // 45 = Even, 46 = Odd
  // 47 = Reds, 48 = Blacks

  /** Default constructor. Balance is set to 1000 upon starting a new game */
  public Roulette()
  {
    balance = oldBalance = 1000;
    earnings = 0;
    random = new Random();

    // Creates and initializes the bets array.
    bets = new int[49];
    for(int i = 0; i < 49; i++)
      bets[i] = 0;

    // Creates and initializes the boolean bets array.
    betsBool = new Boolean[49];
    setBetsFalse();
  }

  /** Adds the player's bet amount to a spot on the roulette table */
  public boolean addBet(int betPos, int betAmount)
  {
    if(balance >= betAmount)
    {
      bets[betPos] += betAmount;
      balance -= betAmount;
      return true;
    }
    else
      return false;
  }

  /**
   * Pays out the bets that the player won based on the number that the ball
   * lands on. Returns the number that the ball landed on.
   */
  public int spin()
  {
    int winnings = 0;          // Resets the player's winnings
    setBetsFalse();            // Sets any bets that were won to false
    num = random.nextInt(37);  // Determines what number the ball falls on
    
    // Calculates the total money the player has before the actual spin
    oldBalance = balance;
    for (int i = 0; i < 49; i++)
    {
      if (bets[i] > 0)
        oldBalance += bets[i];
    }

    for(int i = 0; i < 49; i++)
    {
      // Checks for winning bet on single number
      if(bets[i] != 0 && i == num)
      {
        betsBool[i] = true;
        winnings += (bets[i] * 35);
      }
      // Checks for win on 1st column
      if(bets[i] != 0 && i == 37 && ((num % 3) - 1) == 0)
      {
        betsBool[i] = true;
        winnings += (bets[i] * 2);
      }
      // Checks for win on 2nd column
      if(bets[i] != 0 && i == 38 &&  ((num % 3) - 2) == 0)
      {
        betsBool[i] = true;
        winnings += (bets[i] * 2);
      }
      // Checks for win on 3rd column
      if(bets[i] != 0 && i == 39 && (num % 3) == 0)
      {
        betsBool[i] = true;
        winnings += (bets[i] * 2);
      }
      // Checks for win on first dozen
      if(bets[i] != 0 && i == 40 && num > 0 && num <= 12)
      {
        betsBool[i] = true;
        winnings += (bets[i] * 2);
      }
      // Checks for win on middle dozen
      if(bets[i] != 0 && i == 41 && num > 12 && num <= 24)
      {
        betsBool[i] = true;
        winnings += (bets[i] * 2);
      }
      // Checks for win on last dozen
      if(bets[i] != 0 && i == 42 && num > 24 && num <= 36)
      {
        betsBool[i] = true;
        winnings += (bets[i] * 2);
      }
      // Checks for win on first half
      if(bets[i] != 0 && i == 43 && num > 0 && num <= 18)
      {
        betsBool[i] = true;
        winnings += (bets[i]);
      }
      // Checks for win on second half
      if(bets[i] != 0 && i == 48 && num > 18 && num <= 36)
      {
        betsBool[i] = true;
        winnings += (bets[i]);
      }
      // Checks for win on evens
      if(bets[i] != 0 && i == 44 && (num % 2) == 0 && num != 0)
      {
        betsBool[i] = true;
        winnings += (bets[i]);
      }
      // Checks for win on odds
      if(bets[i] != 0 && i == 47 && ((num % 2) - 1) == 0)
      {
        betsBool[i] = true;
        winnings += (bets[i]);
      }
      // Checks for win on reds
      if(bets[i] != 0 && i == 45 && red(num))
      {
        betsBool[i] = true;
        winnings += (bets[i]);
      }
      // Checks for win on blacks
      if(bets[i] != 0 && i == 46 && black(num))
      {
        betsBool[i] = true;
        winnings += (bets[i]);
      }
    }

    clearChips();         // Clears losing chips
    balance += winnings;  // Adds their winnings to their balance
    calcEarnings();       // Determines the net gain/loss
    return num;
  }

  /** Clears the losing chips off the boards */
  private void clearChips()
  {
    for(int i = 0; i < 49; i++)
    {
      if (!isBetTrue(i))
        bets[i] = 0;
    }
  }

  /** Clears the player's bets. Returns true if any bets were removed. */
  public Boolean clearBets()
  {
    Boolean betsRemoved = false;

    for(int i = 0; i < 49; i++)
    {
      if(bets[i] != 0)
      {
        balance += bets[i];
        bets[i] = 0;
        betsRemoved = true;
      }
    }

    return betsRemoved;
  }

  /** Checks if the number is red */
  public boolean red(int num)
  {
    int[] reds = {1, 3, 5, 7, 9, 12, 14, 16, 18,
                  19, 21, 23, 25, 27, 30, 32, 34, 36};

    for(int i = 0; i < 18; i++)
    {
      if(num == reds[i])
        return true;
    }
    return false;
  }

  /** Checks if the number is black */
  public boolean black(int num)
  {
    int[] blacks = {2, 4, 6, 8, 10, 11, 13, 15, 17,
                    20, 22, 24, 26, 28, 29, 31, 33, 35};

    for(int i = 0; i < 18; i++)
    {
      if(num == blacks[i])
        return true;
    }
    return false;
  }

  /** Returns the player's balance */
  public int getBalance()
  {
    return balance;
  }
  
  /** Calculates the total bets amount on the table */
  public int getTotalBets()
  {
    int totalBets = 0;

    for (int i = 0; i < 49; i++)
      totalBets += bets[i];

    return totalBets;
  }

  /** Returns the net gained/lost after a round */
  public int getEarnings()
  {
    return earnings;
  }

  /** Calculates how much the player earned/lost after a round */
  private void calcEarnings()
  {
    int totalBalance = balance;
    
    // Adds any chips the player still has on the board
    for (int i = 0; i < 49; i++)
    {
      if (bets[i] > 0)
      totalBalance += bets[i];
    }

    // Determines net gain from any winning bets
    earnings = 0;
    if (totalBalance > oldBalance)
      earnings += totalBalance - oldBalance;  // Player gained money
    else if (totalBalance < oldBalance)
      earnings -= oldBalance - totalBalance;  // Player lost money
  }

  /** Sets all boolean bets to false */
  private void setBetsFalse()
  {
    for(int i = 0; i < 49; i++)
      betsBool[i] = false;
  }

  /** Checks if a specific bet was a winning bet */
  public Boolean isBetTrue(int i)
  {
    return betsBool[i];
  }
}
