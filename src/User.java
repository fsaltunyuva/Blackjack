import java.util.ArrayList;

public class User {
    static ArrayList<Integer> takenCards = new ArrayList<>();

    public static int hitACard() {

        do {
            int cardNumber = (int) (Math.random() * 52) + 1;
            int count = 0;

            for (int i : takenCards) {

                if (cardNumber == i) {
                    count++;
                }
            }

            if (count < 4) {
                takenCards.add(cardNumber);
                return cardNumber;
            }
        } while (true);
    }

    //To play with one deck of cards

    /*
    public static int hitACard(){
        
        for (int i : takenCards) {
            System.out.println("i : "+  i);
        }

        int cardNumber;

        if (takenCards.isEmpty()) {
            cardNumber = (int) (Math.random() * 52) + 1;
            takenCards.add(cardNumber);
            return cardNumber;
        }

        int count = 0;

        do{
            cardNumber = (int) (Math.random() * 52) + 1;
            for (int i : takenCards) {
                
            if(cardNumber==i){
                count++;
            }
        }
        }
        
        while(takenCards.contains(cardNumber));
        
        takenCards.add(cardNumber);
        return cardNumber;
    }
    */

    //#To play with one deck of cards

}
