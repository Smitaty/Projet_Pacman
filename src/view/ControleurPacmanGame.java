package view;

import game.PacmanGame;

public class ControleurPacmanGame {
	private ViewPacmanGame pacman;
	
	public ControleurPacmanGame(PacmanGame game) {
		ViewPacmanGame pacman = new ViewPacmanGame(game);
		this.pacman = pacman;		
	}
}
