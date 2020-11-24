package game;

import java.util.ArrayList;

import view.Observable;
import view.Observer;

public abstract class Game implements Runnable, Observable{
	private int turn;
	private int maxturn;
	private boolean isRunning;
	private long time;
	private ArrayList<Observer> observer;
	
	public Game (int maxTurn, long time) {
		maxturn = maxTurn;
		this.time = time;
		this.observer = new ArrayList<Observer>();
	}
	
	public void init() {
		turn = 0;
		notifyObserver();
		isRunning = true;
		initializeGame();
	}
	
	public void launch() {
		isRunning = true;
		Thread t1 = new Thread(this);
		t1.start();
	}
	
	public abstract void initializeGame();
	
	public void step() {
		++turn;
		System.out.println("Début du tour " + this.turn);
		if(turn < maxturn) takeTurn();
		else {
			isRunning=false;
			gameOver();
		}
	}
	
	public void run() {
		System.out.println("Lancement de la partie");
		while(isRunning) {
			step();
			try {
				Thread.sleep(time);
			}catch(Exception e) {
				
			}
		}
	}
	
	public void pause() {
		isRunning=false;
	}
	
	public void notifyObserver() {
		System.out.println(observer.size());
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
	
	public abstract boolean gameContinue();
	
	public abstract void takeTurn();
	public abstract void gameOver();

	public int getTurn() {
		return turn;
	}

	public void setTurn(int turn) {
		this.turn = turn;
	}

	public int getMaxturn() {
		return maxturn;
	}

	public void setMaxturn(int maxturn) {
		this.maxturn = maxturn;
	}

	public boolean isIsRunning() {
		return isRunning;
	}

	public void setIsRunning(boolean isrunning) {
		this.isRunning = isrunning;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
	
}
