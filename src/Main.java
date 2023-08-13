//import jaco.mp3.player.MP3Player;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Main {

    final static String SONG = "------Song's path------";
    //static MP3Player mp3player = new MP3Player(new File(SONG));

    public static void main(String[] args) {
        boolean gameContinues = true;
        Object musicChoice[] = {"Yes", "No"};

        Player player = new Player();
        Dealer dealer = new Dealer();
        Card card = new Card();

        String resultText = "";
        Object gamePanel[] = new Object[5];

        int musicChoiceCheck = JOptionPane.showOptionDialog(null, "<html><h1>Do you want to play music in game?<h1><html>", "BlackJack Game",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                new ImageIcon(Main.class.getResource("resimler/mIcon.png")),
                musicChoice, musicChoice[0]);

        if (musicChoiceCheck == 0) {
            //mp3player.play();
        }

        do {
            Object playerChoice[] = {"Hit", "Stay", "Double Down"};
            System.out.println("\n " +
                    "*************  NEW GAME  *************");
            player.playerAceCard = false;
            dealer.dealerAceCard = false;
            boolean betError = false;

            do {
                try {
                    Object betAmount = JOptionPane.showInputDialog(null,
                            "<html><h1>BlackJack</h1></html>\n"
                                    + "Who gets the value 21, gets the BlackJack\n"
                                    + "The side whose cards total closer to 21 wins.\n"
                                    + "The total of the dealer's cards must be at least 17.\n"
                                    + "<html><h2>You have $" + player.playerMoney + ".</h2></html>"
                            ,
                            "Bet Amount",
                            JOptionPane.PLAIN_MESSAGE,
                            new ImageIcon(Main.class.getResource("resimler/yeniresim.jpeg")),
                            null, "");
                    player.bet = Integer.parseInt(betAmount.toString());
                    if (player.bet > player.playerMoney || player.bet < 5) {
                        JOptionPane.showMessageDialog(null,
                                "<html><h2>You cannot place bets bigger than your money or less than $5.</h2></html>",
                                "INCORRECT BET ENTRY",
                                JOptionPane.ERROR_MESSAGE);
                        betError = true;
                    } else {
                        betError = false;
                    }
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null,
                            "<html><h2>You must enter a numeric value!!!</h2></html>",
                            "INCORRECT BET ENTRY",
                            JOptionPane.ERROR_MESSAGE,
                            null);
                    betError = true;
                } catch (NullPointerException e) {
                    JOptionPane.showMessageDialog(null,
                            "<html><h1>You have exited the game.</h1></html>", "EXIT",
                            JOptionPane.WARNING_MESSAGE,
                            null);
                    System.exit(0);
                }
            } while (betError);

            System.out.println("Bet = " + player.bet);
            resultText = "Bet = " + player.bet + "\nTotal amount of money = $" + player.playerMoney;
            dealer.dealersPoint = 0;
            dealer.dealerContinues = true;

            //Computer pulls its 1st card
            card.cardNumber = User.hitACard();
            card.cardValue = dealer.dealerCalculateValue(card.cardNumber);
            System.out.print("cardValue  = " + card.cardValue + "\t");
            dealer.dealersPoint += card.cardValue;
            dealer.dealerPanel.add(new JLabel(new ImageIcon(Main.class.getResource("resimler/k" + card.cardNumber + ".png"))));
            System.out.println("TOTAL POINT  = " + dealer.dealersPoint);

            //The computer shows the 2nd paper closed.
            JLabel closedCard = new JLabel(new ImageIcon(Main.class.getResource("resimler/k0.png")));
            dealer.dealerPanel.add(closedCard);
            dealer.dealerText = "Dealer = " + dealer.dealersPoint + " Point";

            player.playerPoint = 0;
            player.playerContinues = true;

            //Player's 1st card
            card.cardNumber = User.hitACard();
            card.cardValue = player.playerCalculateValue(card.cardNumber);
            System.out.print("cardValue = " + card.cardValue + "\t");
            player.playerPoint += card.cardValue;
            player.playerPanel.add(new JLabel(new ImageIcon(Main.class.getResource("resimler/k" + card.cardNumber + ".png"))));
            System.out.println("TOTAL POINT  = " + player.playerPoint);

            //Player's 2nd card
            card.cardNumber = User.hitACard();

            card.cardValue = player.playerCalculateValue(card.cardNumber);
            System.out.print("cardValue = " + card.cardValue + "\t");
            player.playerPoint += card.cardValue;
            player.playerPanel.add(new JLabel(new ImageIcon(Main.class.getResource("resimler/k" + card.cardNumber + ".png"))));
            System.out.println("TOTAL POINT  = " + player.playerPoint);
            player.playerText = "Player = " + player.playerPoint + " Point";

            if (player.playerPoint == 21) { //Player won no need to draw cards
                player.playerContinues = false;
                dealer.dealerContinues = true;
            }

            while (player.playerContinues) {
                gamePanel[0] = dealer.dealerText;
                gamePanel[1] = dealer.dealerPanel;
                gamePanel[2] = resultText;
                gamePanel[3] = player.playerPanel;
                gamePanel[4] = player.playerText;

                int cardChoice = JOptionPane.showOptionDialog(null, gamePanel, "BlackJack Game",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        new ImageIcon(Main.class.getResource("resimler/yeniresim5.jpeg")),
                        playerChoice, playerChoice[0]);


                if (cardChoice == 1) { //Player chose to stay
                    player.playerContinues = false;
                }
                else if (cardChoice == 2) { // Player chose to double down
                    if (player.bet * 2 > player.playerMoney) {
                        JOptionPane.showMessageDialog(null,
                                "<html><h2>You cannot choose to double down.</h2></html>",
                                "NOT ENOUGH MONEY",
                                JOptionPane.ERROR_MESSAGE);
                        continue;
                    }
                    player.bet *= 2;
                    card.cardNumber = User.hitACard();
                    card.cardValue = player.playerCalculateValue(card.cardNumber);
                    System.out.print("Player\tcardNumber = " + card.cardNumber + "\tcardValue = " + card.cardValue + "\t");
                    player.playerPoint += card.cardValue;
                    player.playerPanel.add(new JLabel(new ImageIcon(Main.class.getResource("resimler/k" + card.cardNumber + ".png"))));

                    if (player.playerAceCard && player.playerPoint > 21) { // Player has ace, but the score is greater than 21 so count ace as 1
                        player.playerPoint -= 10;
                        player.playerAceCard = false;
                    }

                    System.out.println("Total points  = " + player.playerPoint);
                    player.playerText = "Player = " + player.playerPoint + " Point";
                    player.playerContinues = false;
                } else if (cardChoice == 0) {
                    Object temp[] = {"Hit", "Stay"};
                    playerChoice = temp;
                    // Player's new card
                    card.cardNumber = User.hitACard();
                    card.cardValue = player.playerCalculateValue(card.cardNumber);
                    System.out.print("Player\tcardNumber = " + card.cardNumber + "\tcardValue = " + card.cardValue + "\t");
                    player.playerPoint += card.cardValue;
                    player.playerPanel.add(new JLabel(new ImageIcon(Main.class.getResource("resimler/k" + card.cardNumber + ".png"))));
                    if (player.playerAceCard && player.playerPoint > 21) { // Player has ace, but the score is greater than 21 so count ace as 1
                        player.playerPoint -= 10;
                        player.playerAceCard = false;
                    }
                    System.out.println("Total point  = " + player.playerPoint);
                    player.playerText = "Player = " + player.playerPoint + " Point";
                } else {
                    JOptionPane.showMessageDialog(null,
                            "<html><h1>You have exited the game.</h1></html>", "EXIT",
                            JOptionPane.WARNING_MESSAGE,
                            null);
                    System.exit(0);

                } //Player's ace control is ended
                if (player.playerPoint >= 21) {
                    player.playerContinues = false;
                    dealer.dealerContinues = true;
                }
            } //Player's turn is ended

            //Computer started playing - Unloading closed paper
            dealer.dealerPanel.remove(closedCard);
            while (dealer.dealerContinues) {
                gamePanel[0] = dealer.dealerText;
                gamePanel[1] = dealer.dealerPanel;
                gamePanel[2] = resultText;
                gamePanel[3] = player.playerPanel;
                gamePanel[4] = player.playerText;

                if (dealer.dealersPoint < 17) {
                    //Computer's new card
                    card.cardNumber = User.hitACard();
                    card.cardValue = dealer.dealerCalculateValue(card.cardNumber);

                    System.out.print("Computer\tcardNumber = " + card.cardNumber + "\tcardValue = " + card.cardValue + "\t");
                    dealer.dealersPoint += card.cardValue;
                    dealer.dealerPanel.add(new JLabel(new ImageIcon(Main.class.getResource("resimler/k" + card.cardNumber + ".png"))));
                    if (dealer.dealerAceCard && dealer.dealersPoint > 21) { //Computer has ace, but the score is greater than 21, so count ace as 1
                        dealer.dealersPoint -= 10;
                        dealer.dealerAceCard = false;
                    }
                    System.out.println("Total point = " + dealer.dealersPoint);
                    dealer.dealerText = "Dealer = " + dealer.dealersPoint + " Point";
                } else {
                    dealer.dealerContinues = false;
                }
            }
            //Computer's turn is ended

            //Checking the conditions
            String durum;
            if (player.playerPanel.getComponentCount() == 2 && player.playerPoint == 21) {
                player.playerMoney += player.bet;
                durum = "YOU WON";
            } else if (player.playerPoint > 21 || dealer.dealersPoint == 21) {
                player.playerMoney -= player.bet;
                durum = "YOU LOST";
            } else if (player.playerPoint == 21 || dealer.dealersPoint > 21) {
                player.playerMoney += player.bet;
                durum = "YOU WON";
            } else if (player.playerPoint == dealer.dealersPoint) {
                durum = "DRAW";
            } else {
                if (player.playerPoint < dealer.dealersPoint) {
                    player.playerMoney -= player.bet;
                    durum = "YOU LOST";
                } else {
                    player.playerMoney += player.bet;
                    durum = "YOU WON";
                }
            }

            /*if (musicChoiceCheck == 0) {
                mp3player.stop();
            }*/

            resultText = "Bet = " + player.bet + "\n<html><h1>!!! " + durum + " !!!</h1></html>\nYour Money = $" + player.playerMoney;
            gamePanel[0] = dealer.dealerText;
            gamePanel[1] = dealer.dealerPanel;
            gamePanel[2] = resultText;
            gamePanel[3] = player.playerPanel;
            gamePanel[4] = player.playerText;
            JOptionPane.showMessageDialog(null, gamePanel, "SCORE", JOptionPane.ERROR_MESSAGE,
                    new ImageIcon(Main.class.getResource("resimler/yeniresim5.jpeg")));
            dealer.dealerPanel.removeAll();
            player.playerPanel.removeAll();
            if (player.playerMoney < 5) { //If you have less money that the minimum bet amount
                resultText = "<html><h2>You don't have enough money to place a bet!!!</h2></html>"
                        + "\n<html><h1>Your money = $" + player.playerMoney + ".</h1></html>";
                gameContinues = false;
            }
        } while (gameContinues);

        JOptionPane.showMessageDialog(null,
                resultText, "Game Over",
                JOptionPane.ERROR_MESSAGE,
                new ImageIcon(Main.class.getResource("resimler/bitisLogo.png")));

    }
}
