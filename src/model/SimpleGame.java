package model;

import java.util.ArrayList;

import view.Observer;

public class SimpleGame extends Game{
	private ArrayList<Observer> observer;

	public SimpleGame(int maxTurn, long time) {
		super(maxTurn,time);
		this.observer = new ArrayList<Observer>();
	}
	
	public void takeTurn() {
		System.out.println("Tour " + this.getTurn() + " du jeu en cours");
	}
	
	public void initializeGame() {
		System.out.println("Début de la partie");
	}
	
	public boolean gameContinue() {
		return true;
	}
	
	public void gameOver() {
		
	}
	
	public void notifyObserver() {
		for(Observer o : observer) {
			o.actualiser(this);
		}
	}
	
	public void addObserver(Observer o) {
		this.observer.add(o);
	}
	
	public void removeObserver(Observer o) {
		this.observer.remove(o);
	}
}
