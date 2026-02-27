package cards;

public abstract class Mushroom extends EdibleItem {
	
	protected int sticksPerMushroom;

	public void Mushroom(CardType type, String name) {
		super.EdibleItem(type, name);
	}

	public int getSticksPerMushroom() {
		return sticksPerMushroom;
	}

}