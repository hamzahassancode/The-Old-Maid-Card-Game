class Card {
    private String suit;
    private String value;

    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public boolean isMatchingPair(Card other) {
        return this.value.equals(other.value) &&
                ((this.suit.equals("Spades") && other.suit.equals("Clubs")) ||
                        (this.suit.equals("Clubs") && other.suit.equals("Spades")) ||
                        (this.suit.equals("Diamonds") && other.suit.equals("Hearts")) ||
                        (this.suit.equals("Hearts") && other.suit.equals("Diamonds")));
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}


