package board;

import java.util.ArrayList;
import cards.Card;

public class CardList{
	private ArrayList<Card> cList;

	public CardList() {
		cList = new ArrayList<Card>();
	}

	public void add(Card card) {
		cList.add(null);
		for (int i = cList.size() - 1; i > 0; i--) {
			cList.set(i, cList.get(i - 1));
		}
		cList.set(0, card);
	}

	public int size() {
		return cList.size();
	}

	public Card getElementAt(int i) {
		return cList.get(i);
	}

	public Card removeCardAt(int i) {
		int index = cList.size() - i;
		return cList.remove(index);
	}
}