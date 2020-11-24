package game;

import agent.Maze;
import agent.PanelPacmanGame;
import strategie.StrategieFood;
import strategie.StrategieRandom;
import view.ControleurPacmanGame;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Maze labyrinthe = new Maze("src/layout/test.lay");
			PanelPacmanGame panel = new PanelPacmanGame(labyrinthe);
			PacmanGame game = new PacmanGame(100,1000,panel);
			ControleurPacmanGame pacman = new ControleurPacmanGame(game);
			game.initializeGame();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
