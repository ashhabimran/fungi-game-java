package board;

import java.util.ArrayList;
import cards.Card;

public class Display implements Displayable {

	private ArrayList<Card> displayList=new ArrayList<Card>();

	public void add(Card card) {
		displayList.add(card);
	}

	public int size() {
		return displayList.size();
	}

	public Card getElementAt(int i) {
		return displayList.get(i);
	}

	public Card removeElement(int i) {
		return displayList.remove(i);
	}
}