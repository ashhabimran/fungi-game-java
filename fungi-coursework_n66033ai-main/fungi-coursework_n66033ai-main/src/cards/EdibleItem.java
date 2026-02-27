package cards;

public abstract class EdibleItem extends Card {

	protected int flavourPoints;

	public void EdibleItem(CardType type, String name) {
		super.Card(type, name);
	}

	public int getFlavourPoints() {
		return flavourPoints;
	}
}