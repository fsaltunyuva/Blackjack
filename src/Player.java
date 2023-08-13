import javax.swing.JPanel;

public class Player extends User {

    int playerMoney = 200;
    int playerPoint;
    int bet = 0;
    String playerText;
    JPanel playerPanel = new JPanel();
    boolean playerContinues;
    boolean playerAceCard = false;

    public int playerCalculateValue(int numberInput) {

        int cardNumber = numberInput;

        switch (cardNumber % 13) {
            case 0:
                return 10; //It is K cardValue = 10

            case 1:
                playerAceCard = true;
                return 11;

            case 11:

            case 12:
                return 10; //It is J or Q cardValue = 10

            default:
                return cardNumber % 13;
        }
    }
}
