import java.util.Random;

public class Roulette
{
    private int balance;    // Stores the player's balance
    private Random random = new Random();
    private int[] bets  = new int[49];
    // 0 - 36 = Numbers 0 - 36
    // 37 = Left 2 to 1, 38 = Middle 2 to 1, 39 = Right 2 to 1
    // 40 = 1st 12, 41 = 2nd 12, 42 = 3rd 12
    // 43 = 1 - 18, 44 = 19 - 36
    // 45 = Even, 46 = Odd
    // 47 = Reds, 48 = Blacks

    public Roulette()
    {
        balance = 1000; // Sets the player's balance
        for(int i = 0; i < 49; i++) // Sets all the bet spots to 0
        {
            bets[i] = 0;
        }
    }

    public void addBet(int betPos)  // Bets 5 on the spot
    {
        bets[betPos] += 5;
        balance -= 5;
    }

    public int spin()   // Chooses a random number and calculates of the player won
    {
        int num = random.nextInt(37);
        System.out.print("Earnings: " + calcEarnings(num));
        System.out.println();
        return num;
    }

    private int calcEarnings(int num)   // Calculates if the player won and changes their balance
    {
        int winnings = 0;
        for(int i = 0; i < 49; i++)
        {
            if(bets[i] != 0 && i == num)    // Checks for winning bet on single number
                winnings += (bets[i] * 36);
            if(bets[i] != 0 && i == 37 && ((num%3) - 1) == 0) // Checks for win on Left 2 to 1
                winnings += (bets[37] * 3);
            if(bets[i] != 0 && i == 38 &&  ((num%3) - 2) == 0) // Checks for win on Middle 2 to 1
                winnings += (bets[38] * 3);
            if(bets[i] != 0 && i == 39 && (num%3) == 0)   // Checks for win on Right 2 to 1
                winnings += (bets[39] * 3);
            if(bets[i] != 0 && i == 40 && num > 0 && num <= 12) // Checks for win on first 12
                winnings += (bets[40] * 3);
            if(bets[i] != 0 && i == 41 && num > 12 && num <= 24)    // Checks for win on middle 12
                winnings += (bets[41] * 3);
            if(bets[i] != 0 && i == 42 && num > 24 && num <= 36)    // Checks for win on last 12
                winnings += (bets[42] * 3);
            if(bets[i] != 0 && i == 43 && num > 0 && num <= 18) // Checks for win on first 18
                winnings += (bets[43] * 2);
            if(bets[i] != 0 && i == 44 && num > 18 && num <= 36)    // Checks for win on last 18
                winnings += (bets[44] * 2);
            if(bets[i] != 0 && i == 45 && (num % 2) == 0)   // Checks for win on Evens
                winnings += (bets[45] * 2);
            if(bets[i] != 0 && i == 46 && ((num % 2) - 1) == 0) // Checks for win on Odds
                winnings += (bets[46] * 2);
            if(bets[i] != 0 && i == 47 && red(num)) // Checks for win on Reds
                winnings += (bets[47] * 2);
            if(bets[i] != 0 && i == 48 && black(num))   // Checks for win on Blacks
                winnings += (bets[48] * 2);
        }

        balance += winnings;    // Adds their winnings to their balance
        clearChips();   // Clears the chips off the board
        return winnings;    // Returns their winnings
    }

    private boolean red(int num)    // Checks if the number is red
    {
        int[] reds = {1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36};
        for(int i = 0; i < 18; i++)
        {
            if(num == reds[i])
                return true;
        }
        return false;
    }

    private boolean black(int num)  // Checks if the number is black
    {
        int[] blacks = {2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35};
        for(int i = 0; i < 18; i++)
        {
            if(num == blacks[i])
                return true;
        }
        return false;
    }

    private void clearChips()   // Clears the chips off the boards
    {
        for(int i = 0; i < 49; i++)
        {
            bets[i] = 0;
        }
    }

    public void clearBets() // Clears their bets (Gives back the chips that they had already bet)
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

    // Returns the player's balance.
    public int getBalance()
    {
        return balance;
    }

    /*
    public static void main(String[] args)
    {
        Roulette test = new Roulette(); // Just a little test to make sure everything is working
        test.addBet(48);
        for(int i = 0; i < 49; i++)
        {
            System.out.print(test.bets[i] + " ");
        }
        System.out.println();
        int spinNum = test.spin();
        System.out.print("Number was: " + spinNum);
        System.out.println();
        System.out.print("Balance is: " + test.balance);
        System.out.println();
    }
    */
}