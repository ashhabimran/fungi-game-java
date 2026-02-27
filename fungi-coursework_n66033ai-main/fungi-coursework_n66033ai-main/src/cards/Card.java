package cards;

public abstract class Card {

	protected CardType type;
	protected String cardName;

	public void Card(CardType type, String name) {
		this.type = type;
		this.cardName = name;
	}

	public CardType getType() {
		return type;
	}

	public String getName() {
		return cardName;
	}
}