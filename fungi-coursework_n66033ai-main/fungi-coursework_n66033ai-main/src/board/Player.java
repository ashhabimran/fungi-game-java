package board;

import java.util.ArrayList;
import cards.*;

public class Player{

	private Hand h;
	private Display d;
	private int score, handlimit, sticks;

	public Player() {
		h = new Hand();
		d = new Display();
		addCardtoDisplay(new Pan());
		score = 0;
		handlimit = 8;
		sticks = 0;
	}

	public int getScore() {
		return score;
	}

	public int getHandLimit() {
		return handlimit;
	}

	public int getStickNumber() {
		return sticks;
	}

	public void addSticks(int extra_sticks) {
		sticks = getStickNumber() + extra_sticks;
		for (int i = 0; i < extra_sticks; i++) {
			addCardtoDisplay(new Stick());
		}
	}

	public void removeSticks(int excess_sticks) {
		if (getStickNumber() >= excess_sticks) {
			sticks = getStickNumber() - excess_sticks;
			for (int i = 0; i < d.size(); i++) {
				if ((d.getElementAt(i).getName() == "stick") && (excess_sticks > 0)) {
					d.removeElement(i);
					excess_sticks = excess_sticks - 1;
				}
			}
		}
	}

	public Hand getHand() {
		return h;
	}

	public Display getDisplay() {
		return d;
	}

	public void addCardtoHand(Card card) {
		if (card.getName() == "basket") {
			d.add(card);
			handlimit = handlimit + 2;
		} else {
			h.add(card);
		}
	}

	public void addCardtoDisplay(Card card) {
		d.add(card);
	}

	public boolean takeCardFromTheForest(int c) {
		if ((c > Board.getForest().size()) || (c <= 0)) {
			return false;
		} else {
			Card card = Board.getForest().removeCardAt(c);
			if (c > 2 && (sticks < (c - 2))) {
				return false;
			} else {
				if (c > 2) {
					removeSticks(c - 2);
				}
				if (card.getName() == "basket") {
					addCardtoDisplay(card);
					handlimit = handlimit + 2;
					return true;
				} else {
					if (h.size() < getHandLimit()) {
						addCardtoHand(card);
						return true;
					} else {
						return false;
					}
				}
			}
		}
	}

	public boolean takeFromDecay() {
		int noBasketCards = Board.getDecayPile().size();
		for (int i = 0; i < Board.getDecayPile().size(); i++) {
			if (Board.getDecayPile().get(i).getName() == "basket") {
				addCardtoDisplay(Board.getDecayPile().get(i));
				handlimit = handlimit + 2;
				noBasketCards = noBasketCards - 1;
			} else {
				if ((noBasketCards + h.size() <= getHandLimit())) {
					addCardtoHand(Board.getDecayPile().get(i));
				} else {
					return false;
				}
				Board.getDecayPile().clear();
			}
		}
		return true;
	}

	public boolean cookMushrooms(ArrayList<Card> mushrooms) {
		boolean panInDisplay = false;
		boolean panInMushrooms = false;
		for (int i = 0; i < d.size(); i++) {
			if (d.getElementAt(i).getName() == "pan") {
				panInDisplay = true;
				break;
			}
		}
		for (int i = 0; i < mushrooms.size(); i++) {
			if (mushrooms.get(i).getName() == "pan") {
				panInMushrooms = true;
				break;
			}
		}
		if (!(panInMushrooms) && !(panInDisplay)) {
			return false;
		} else {
			int mushroomCount = 0;
			int butterCount = 0;
			int ciderCount = 0;
			ArrayList<Card> mushroomTracker = new ArrayList<Card>();
			for (int i = 0; i < mushrooms.size(); i++) {
				if (mushrooms.get(i).getType() == CardType.DAYMUSHROOM) {
					mushroomCount = mushroomCount + 1;
					mushroomTracker.add(mushrooms.get(i));
				} else if (mushrooms.get(i).getType() == CardType.NIGHTMUSHROOM) {
					mushroomCount = mushroomCount + 2;
					mushroomTracker.add(mushrooms.get(i));
				} else if (mushrooms.get(i).getName() == "butter") {
					butterCount = butterCount + 1;
				} else if (mushrooms.get(i).getName() == "cider") {
					ciderCount = ciderCount + 1;
				} else if ((mushrooms.get(i).getName() == "basket") || ((mushrooms.get(i).getName() == "stick"))) {
					return false;
				}
			}
			if (mushroomCount < 3) {
				return false;
			} else {
				for (int i = 0; i < mushroomTracker.size(); i++) {
					if (mushroomTracker.get(i).getName() != mushroomTracker.get(0).getName()) {
						return false;
					}
				}
				if (((butterCount*4) + (ciderCount*5) > mushroomCount) || (mushroomCount <= 0)) {
					return false;
				} else {
					ArrayList<Mushroom> mushroomList = new ArrayList<Mushroom>();
					for (int i = 0; i < mushroomTracker.size(); i++) {
						Mushroom m = (Mushroom) mushroomTracker.get(i);
						mushroomList.add(m);
					}
					Butter butterClass = new Butter();
					Cider ciderClass = new Cider();
					score = score + (mushroomCount * mushroomList.get(0).getFlavourPoints());
					score = score + (butterCount * butterClass.getFlavourPoints());
					score = score + (ciderCount * ciderClass.getFlavourPoints());
					boolean panInHand = false;
					for (int i = 0; i < getHand().size(); i++) {
						if (getHand().getElementAt(i).getName() == "pan") {
							panInHand = true;
						}
					}
					if (panInDisplay) {
						for (int i = 0; i < getDisplay().size(); i++) {
							if (getDisplay().getElementAt(i).getName() == "pan") {
								getDisplay().removeElement(i);
								break;
							}
						}
					} else if (panInHand) {
						for (int i = 0; i < getHand().size(); i++) {
							if (getHand().getElementAt(i).getName() == "pan") {
								getHand().removeElement(i);
								break;
							}
						}
					}
					for (int i = 0; i < getHand().size(); i++) {
						if (getHand().getElementAt(i).getName() == mushroomTracker.get(0).getName()) {
							getHand().removeElement(i);
						} else {
							if (getHand().getElementAt(i).getName() == "butter" && butterCount > 0) {
								getHand().removeElement(i);
								butterCount = butterCount - 1;
							} else if (h.getElementAt(i).getName() == "cider" && ciderCount > 0) {
								getHand().removeElement(i);
								ciderCount = ciderCount - 1;
							}
						}
					}
					return true;
				}
			}
			
		}
	}

	public boolean sellMushrooms(String mushroom, int m) {
		int mushroomCount = 0;
		Mushroom mushroomClass = null;
		if (m < 2) {
			return false;
		} else {
			String cleanedMushroom = mushroom.replaceAll("[^a-zA-Z]", "").toLowerCase();
//			System.out.println(mushroom);
//			System.out.println(cleanedMushroom);
			ArrayList<String> mushrooms = new ArrayList<String>();
			mushrooms.add("morel");
			mushrooms.add("lawyerswig");
			mushrooms.add("birchbolete");
			mushrooms.add("chanterelle");
			mushrooms.add("henofwoods");
			mushrooms.add("honeyfungus");
			mushrooms.add("shiitake");
			mushrooms.add("porcini");
			mushrooms.add("treeear");
			if (!(mushrooms.contains(cleanedMushroom))) {
				return false;
			} else {
				ArrayList<Mushroom> mushroomClasses = new ArrayList<Mushroom>();
				mushroomClasses.add(new Morel(CardType.DAYMUSHROOM));
				mushroomClasses.add(new LawyersWig(CardType.DAYMUSHROOM));
				mushroomClasses.add(new BirchBolete(CardType.DAYMUSHROOM));
				mushroomClasses.add(new Chanterelle(CardType.DAYMUSHROOM));
				mushroomClasses.add(new HenOfWoods(CardType.DAYMUSHROOM));
				mushroomClasses.add(new HoneyFungus(CardType.DAYMUSHROOM));
				mushroomClasses.add(new Shiitake(CardType.DAYMUSHROOM));
				mushroomClasses.add(new Porcini(CardType.DAYMUSHROOM));
				mushroomClasses.add(new TreeEar(CardType.DAYMUSHROOM));
				for (int i = 0; i < mushroomClasses.size(); i++) {
					if (mushroomClasses.get(i).getName() == cleanedMushroom) {
						mushroomClass = mushroomClasses.get(i);
						break;
					}
				}
				for (int i = 0; i < h.size(); i++) {
					if ((h.getElementAt(i).getName()) == cleanedMushroom) {
						if (h.getElementAt(i).getType() == CardType.DAYMUSHROOM) {
							mushroomCount = mushroomCount + 1;
						} else if (h.getElementAt(i).getType() == CardType.NIGHTMUSHROOM) {
							mushroomCount = mushroomCount + 2;
						}
					}
				}
				if ((mushroomCount < m) || (mushroomClass == null)) {
					return false;
				} else {
					addSticks(mushroomClass.getSticksPerMushroom() * m);
					for (int i = 0; i < h.size(); i++) {
						if ((h.getElementAt(i).getName() == cleanedMushroom) && m > 0) {
							if ((h.getElementAt(i).getType() == CardType.NIGHTMUSHROOM) && m >= 2) {
								h.removeElement(i);
								m = m - 2;
							} else if (h.getElementAt(i).getType() == CardType.NIGHTMUSHROOM) {
								h.removeElement(i);
								m = m - 1;
							}
						}
					}
					return true;
				}
			}
		}
	}

	public boolean putPanDown() {
		boolean panInHand = false;
		for (int i = 0; i < h.size(); i++) {
			if (h.getElementAt(i).getName() == "pan") {
				panInHand = true;
				break;
			}
		}
		if (!(panInHand)) {
			return false;
		} else {
			for (int i = 0; i < h.size(); i++) {
				if (h.getElementAt(i).getName() == "pan") {
					d.add(new Pan());
					h.removeElement(i);
					break;
				}
			}
			return true;
		}
	}
}