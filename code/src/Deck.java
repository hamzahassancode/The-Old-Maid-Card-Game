import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Deck {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        // Add standard 52 cards to the deck
        String[] suits = {"Spades", "Clubs", "Diamonds", "Hearts"};
        String[] values = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
        for (String suit : suits) {
            for (String value : values) {
                cards.add(new Card(suit, value));
            }
        }
        // Add the Joker
        cards.add(new Card("Joker", "Joker"));
        System.out.println(cards.size());

    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public int size() {
        return cards.size();
    }

    public Card dealCard() {
        if (cards.isEmpty()) return null;
        return cards.remove(cards.size() - 1);
    }
}

