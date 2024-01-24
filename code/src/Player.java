import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

class Player extends Thread {
    private String name;
    private CountDownLatch latch;
    private static volatile boolean isGameOver = false;
    private static final ArrayList<String> sortingWinner=new ArrayList<>();
    private ArrayList<Card> hand;
    private Player nextPlayer;
    private static volatile int discardedCardCount = 0; // New variable to track discarded card count

    private boolean isMyTurn=false;
    private static final Object lock = new Object();
    public Player(String name,CountDownLatch latch) {
        this.name = name;
        this.latch = latch;
        this.hand = new ArrayList<>();
    }

    public void setNextPlayer(Player nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public void receiveCard(Card card) throws InterruptedException {
        synchronized (lock) {
            hand.add(card);
            System.out.println(name +"will take card "+card.toString());

            lock.notifyAll();
        }
    }


    public String getPlayerName() {
        return name;
    }

    public void run() {

            synchronized (lock)
            {

                while (!isGameOver) {
                    try {
                    while (!isMyTurn) {
                        lock.wait();
                    }
                   // Thread.sleep(500);
                        for (int i = 0; i < hand.size(); i++) {
                        Card currentCard = hand.get(i);
                        for (int j = i + 1; j < hand.size(); j++) {
                            Card nextCard = hand.get(j);
                            if (currentCard.isMatchingPair(nextCard)) {
                                System.out.println(name + " discarded " + currentCard + " and " + nextCard);
                                hand.remove(j);
                                hand.remove(i);
                                discardedCardCount += 2;
                                break;
                            }
                        }
                    }
                    isMyTurn = false;
                    nextPlayer.isMyTurn = true; // Move the turn to the next player
                        if (hand.size()==1 && hasJoker()  && Player.discardedCardCount==52) {
                            System.out.println("-----------------------------------------------------");
                            System.out.println("the total card discarded is: "+Player.getDiscardedCardCount());
                            System.out.println(name+" still has the Joker");
                            isGameOver = true;
                            break;
                        }

                    Card cardToReceive = giveRandomCard();
                    if (cardToReceive != null) {
                        System.out.println(name+" will give card : "+cardToReceive);
                        nextPlayer.receiveCard(cardToReceive);


                    }else{
                        lock.notifyAll();
                    }


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        sortingWinner.add(name);
        latch.countDown();


    }
    public static int getDiscardedCardCount() {
        return discardedCardCount;
    }
    private Card giveRandomCard() {
        synchronized (lock) {
            if (!hand.isEmpty()) {
                int randomIndex = (int) (Math.random() * hand.size());
                return hand.remove(randomIndex);
            }
            return null;
        }
    }

    public void isMyTurn(boolean b) {
        this.isMyTurn = b;
    }

    // Add a method to check if the player has the Joker card
    public boolean hasJoker() {
        for (Card card : hand) {
            if (card.getSuit().equals("Joker") && card.getValue().equals("Joker")) {
                return true;
            }
        }
        return false;
    }
    public static void getTheResult() {
        for (int i=1;i<sortingWinner.size();i++){
            System.out.println("- Winner "+i + " is : "+sortingWinner.get(i));
        }
            System.out.println("* the loser is :"+sortingWinner.get(0));

    }

}


//-------------------------------------------------------------------------------------------------