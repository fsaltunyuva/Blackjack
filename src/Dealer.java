import javax.swing.JPanel;

public class Dealer extends User {

    int dealersPoint;
    String dealerText;
    JPanel dealerPanel = new JPanel();
    boolean dealerContinues;
    boolean dealerAceCard = false;

    public int dealerCalculateValue(int numberInput) {

        int cardNumber = numberInput;

        switch (cardNumber % 13) {
            case 0:
                return 10; //It is K cardValue = 10

            case 1:

                dealerAceCard = true;
                return 11;

            case 11:

            case 12:
                return 10; //It is J or Q cardValue = 10

            default:
                return cardNumber % 13;
        }

    }
}