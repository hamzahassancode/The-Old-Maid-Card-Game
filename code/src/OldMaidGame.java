import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class OldMaidGame {
    public static void main(String[] args) throws InterruptedException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("enter the number of players :");
        int numPlayers = scanner.nextInt();
        CountDownLatch latch = new CountDownLatch(numPlayers);

        Deck deck = new Deck();
        deck.shuffle();

        ArrayList<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            players.add(new Player("Player " + i, latch));
        }

        for (int i = 0; i < numPlayers; i++) {
            players.get(i).setNextPlayer(players.get((i + 1) % numPlayers));
        }

        int remainingCards = deck.size();
        while (remainingCards > 0) {
            for (Player player : players) {
                Card card = deck.dealCard();
                if (card != null) {
                    player.receiveCard(card);
                    remainingCards--;
                }
            }
        }

        players.get(0).isMyTurn(true); // Start with the first player

        for (Player player : players) {
            player.start();

            System.out.println(player.getPlayerName() + " is here");
        }
        System.out.println("hello");
        //Thread.sleep(1000);
        try {
            latch.await(); // This will block until the count reaches zero
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("-----------------------------------------------------");

        Player.getTheResult();
        for (Player player : players) {
            try {
                player.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("--- all done ---\n");

        System.out.println("ENTER ANY NUMBER TO EXIT THE GAME:");
        int n = scanner.nextInt();


    }
}


