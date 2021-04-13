/******************************************************************************
Names: Zach Porcoro and Peter Vasiljev
Course: COP 3252
Assignment Number: X

This is the game logic file for the Roulette application. All game behavior
such as payout is controlled here.
******************************************************************************/

import java.util.Random;

/** Contains the game logic and holds the player's balance */
public class Roulette implements java.io.Serializable
{
  private int balance;    // The player's balance
  private int num;        // The number that the ball landed on
  private Random random;

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
    balance = 1000;
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
        betsBool[37] = true;
        winnings += (bets[37] * 2);
      }
      // Checks for win on 2nd column
      if(bets[i] != 0 && i == 38 &&  ((num % 3) - 2) == 0)
      {
        betsBool[38] = true;
        winnings += (bets[38] * 2);
      }
      // Checks for win on 3rd column
      if(bets[i] != 0 && i == 39 && (num % 3) == 0)
      {
        betsBool[39] = true;
        winnings += (bets[39] * 2);
      }
      // Checks for win on first dozen
      if(bets[i] != 0 && i == 40 && num > 0 && num <= 12)
      {
        betsBool[40] = true;
        winnings += (bets[40] * 2);
      }
      // Checks for win on middle dozen
      if(bets[i] != 0 && i == 41 && num > 12 && num <= 24)
      {
        betsBool[41] = true;
        winnings += (bets[41] * 2);
      }
      // Checks for win on last dozen
      if(bets[i] != 0 && i == 42 && num > 24 && num <= 36)
      {
        betsBool[42] = true;
        winnings += (bets[42] * 2);
      }
      // Checks for win on first half
      if(bets[i] != 0 && i == 43 && num > 0 && num <= 18)
      {
        betsBool[43] = true;
        winnings += (bets[43]);
      }
      // Checks for win on second half
      if(bets[i] != 0 && i == 48 && num > 18 && num <= 36)
      {
        betsBool[48] = true;
        winnings += (bets[48]);
      }
      // Checks for win on evens
      if(bets[i] != 0 && i == 44 && (num % 2) == 0 && num != 0)
      {
        betsBool[44] = true;
        winnings += (bets[44]);
      }
      // Checks for win on odds
      if(bets[i] != 0 && i == 47 && ((num % 2) - 1) == 0)
      {
        betsBool[47] = true;
        winnings += (bets[47]);
      }
      // Checks for win on reds
      if(bets[i] != 0 && i == 45 && red(num))
      {
        betsBool[45] = true;
        winnings += (bets[45]);
      }
      // Checks for win on blacks
      if(bets[i] != 0 && i == 46 && black(num))
      {
        betsBool[46] = true;
        winnings += (bets[46]);
      }
    }

    balance += winnings;  // Adds their winnings to their balance
    clearChips();         // Clears losing chips
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
  
  /** Calculates the total bets amount on the table */
  public int getTotalBets()
  {
    int totalBets = 0;

    for (int i = 0; i < 49; i++)
      totalBets += bets[i];

    return totalBets;
  }
}
