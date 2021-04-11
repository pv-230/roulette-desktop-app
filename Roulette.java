import java.util.Random;

/**
 * Contains the game logic and holds the player's balance
 */
public class Roulette
{
    private int balance;    // Stores the player's balance
    private int num;        // Stores the number that the ball landed on
    private Random random;

    private int[] bets;
    // Array slots for bets array are:
    // 0 - 36 = Numbers 0 - 36
    // 37 = 1st column, 38 = 2nd column, 39 = 3rd column
    // 40 = 1st dozen, 41 = 2nd dozen, 42 = 3rd dozen
    // 43 = 1 - 18, 44 = 19 - 36
    // 45 = Even, 46 = Odd
    // 47 = Reds, 48 = Blacks

    /**
     * Default constructor. Balance is set to 1000 upon starting a new game.
     */
    public Roulette()
    {
        balance = 1000;
        random = new Random();

        // Creates and initializes the bets array.
        bets = new int[49];
        for(int i = 0; i < 49; i++)
        {
            bets[i] = 0;
        }
    }

    /**
     * Adds the player's bet amount to a spot on the roulette table
     */
    public void addBet(int betPos, int betAmount)
    {
        if(balance >= betAmount)
        {
            bets[betPos] += betAmount;
            balance -= betAmount;
        }
    }

    /**
     * Chooses a random number and pays out if the play guessed correctly.
     * Returns the number that the ball landed on.
     */
    public int spin()
    {
        num = random.nextInt(37);
        int winnings = 0;

        for(int i = 0; i < 49; i++)
        {
            // Checks for winning bet on single number
            if(bets[i] != 0 && i == num)
                winnings += (bets[i] * 36);
            // Checks for win on Left 2 to 1
            if(bets[i] != 0 && i == 37 && ((num%3) - 1) == 0)
                winnings += (bets[37] * 3);
            // Checks for win on Middle 2 to 1
            if(bets[i] != 0 && i == 38 &&  ((num%3) - 2) == 0)
                winnings += (bets[38] * 3);
            // Checks for win on Right 2 to 1
            if(bets[i] != 0 && i == 39 && (num%3) == 0)
                winnings += (bets[39] * 3);
            // Checks for win on first 12
            if(bets[i] != 0 && i == 40 && num > 0 && num <= 12)
                winnings += (bets[40] * 3);
            // Checks for win on middle 12
            if(bets[i] != 0 && i == 41 && num > 12 && num <= 24)
                winnings += (bets[41] * 3);
            // Checks for win on last 12
            if(bets[i] != 0 && i == 42 && num > 24 && num <= 36)
                winnings += (bets[42] * 3);
            // Checks for win on first 18
            if(bets[i] != 0 && i == 43 && num > 0 && num <= 18) 
                winnings += (bets[43] * 2);
            // Checks for win on last 18
            if(bets[i] != 0 && i == 44 && num > 18 && num <= 36)
                winnings += (bets[44] * 2);
            // Checks for win on Evens
            if(bets[i] != 0 && i == 45 && (num % 2) == 0)
                winnings += (bets[45] * 2);
            // Checks for win on Odds
            if(bets[i] != 0 && i == 46 && ((num % 2) - 1) == 0)
                winnings += (bets[46] * 2);
            // Checks for win on Reds
            if(bets[i] != 0 && i == 47 && red(num))
                winnings += (bets[47] * 2);
            // Checks for win on Blacks
            if(bets[i] != 0 && i == 48 && black(num))
                winnings += (bets[48] * 2);
        }

        balance += winnings;  // Adds their winnings to their balance
        clearChips();         // Clears the chips off the board
        return num;
    }

    /**
     * Checks if the number is red
     */
    private boolean red(int num)
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

    /**
     * Checks if the number is black
     */
    private boolean black(int num)
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

    /**
     * Clears the chips off the boards
     */
    private void clearChips()
    {
        for(int i = 0; i < 49; i++)
        {
            bets[i] = 0;
        }
    }

    /**
     * Clears their bets (Gives back the chips that they had already bet)
     */
    public void clearBets()
    {
        for(int i = 0; i < 49; i++)
        {
            if(bets[i] != 0)
            {
                balance += bets[i];
            }
            bets[i] = 0;
        }
    }

    /**
     * Returns the player's balance
     */
    public int getBalance()
    {
        return balance;
    }
}
