package model;

import java.util.ArrayList;

import agent.Maze;
import agent.PanelPacmanGame;
import controller.ControleurPacmanGame;
import perceptron.Quadruplet;
import strategie.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Maze labyrinthe = new Maze("src/layout/mediumClassic.lay");
			PanelPacmanGame panel = new PanelPacmanGame(labyrinthe);
			vizualise(300,1000,panel); // Pour lancer le pacman avec l'interface graphique
			//getAverageReward(10,200,"src/layout/mediumClassic.lay"); // Pour lancer le pacman en mode batch
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void getAverageReward(int nbSimulation, int maxTurn, String layout) {
		ArrayList<PacmanGame> list = new ArrayList<PacmanGame>();
		//ArrayList<Thread> threads = new ArrayList<Thread>();
		for(int i=0; i<nbSimulation; ++i) {
			try {
				Maze maze = new Maze(layout);
				PanelPacmanGame panel = new PanelPacmanGame(maze);
				System.out.println(panel.getMaze().getPacman_start().toString());
				PacmanGame game = new PacmanGame(maxTurn,0,panel);
				StrategiePerceptron stratPacman = new StrategiePerceptron(panel);
				game.initialisePacman(stratPacman);
				game.init();
				list.add(game);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		for (int i=0; i<nbSimulation; ++i) {
			list.get(i).launch();
		}
		
		for (int i=0; i<nbSimulation; ++i) {
			try{
				list.get(i).getThread().join();
				int cpt = i+1;
				System.out.println("Partie " + cpt + " score : " + list.get(i).getScorePerceptron());
			}catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public ArrayList<PacmanGame> getAverageReward2(int maxTurn, int nbSimulation, int nbMeilleurs, String layout) {
		ArrayList<PacmanGame> list = new ArrayList<PacmanGame>();
		ArrayList<PacmanGame> meilleurGame = new ArrayList<PacmanGame>();
		for(int i=0; i<nbSimulation; ++i) {
			try {
				Maze maze = new Maze(layout);
				PanelPacmanGame panel = new PanelPacmanGame(maze);
				PacmanGame game = new PacmanGame(maxTurn,0,panel);
				StrategiePerceptron stratPacman = new StrategiePerceptron(panel);
				game.initialisePacman(stratPacman);
				game.init();
				list.add(game);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		for (int i=0; i<nbSimulation; ++i) {
			list.get(i).launch();
		}
		
		for (int i=0; i<nbSimulation; ++i) {
			try{
				list.get(i).getThread().join();
				if(meilleurGame.size() <= nbMeilleurs) {
					meilleurGame.add(list.get(i));
				}
				else {
					int plusPetitScore = -1;
					int mauvaiseGame = 0;
					for(int j = 0; j < meilleurGame.size(); ++j) {
						if(plusPetitScore == -1) {
							plusPetitScore = meilleurGame.get(j).getScorePerceptron();
							mauvaiseGame = j;
						}
						else {
							if(plusPetitScore > meilleurGame.get(j).getScorePerceptron()) {
								plusPetitScore = meilleurGame.get(j).getScorePerceptron();
								mauvaiseGame = j;
							}
						}
					}
					if(plusPetitScore < meilleurGame.get(i).getScorePerceptron()) {
						meilleurGame.remove(mauvaiseGame);
						meilleurGame.add(list.get(i));
					}
				}
			}catch(InterruptedException e) {
				e.printStackTrace();
			}		
		}
		return meilleurGame;		
	}
	
	public ArrayList<PacmanGame> rechercheAleatoire(int nbPerceptron, int maxTurn, int nbMeilleursGame, String layout){
		ArrayList<PacmanGame> meilleursGame = new ArrayList<PacmanGame>();
		while(meilleursGame.size() < nbMeilleursGame) {
			int nbSimulation = nbPerceptron - meilleursGame.size(); // N-M
			ArrayList<PacmanGame> newMeilleursGame = this.getAverageReward2(maxTurn, nbSimulation, nbMeilleursGame, layout);
			for(int i = 0; i < nbMeilleursGame; ++i) {
				if(meilleursGame.size() < nbMeilleursGame)
					meilleursGame.add(newMeilleursGame.get(i));
			}
			PacmanGame meilleurGame = getMeilleurGame(newMeilleursGame);
			StrategiePerceptron strat = (StrategiePerceptron) meilleurGame.getPacmans().get(0).getStrategie();
			strat.getPerceptron().bruiter(0.1);
		}
		return meilleursGame;
	}
	
	public ArrayList<Quadruplet> getLearningSet(int maxTurn, int nbSimulation, String layout){
		ArrayList<Quadruplet> quadruplets = new ArrayList<Quadruplet>();
		ArrayList<PacmanGame> games = getAverageReward2(nbSimulation, maxTurn, nbSimulation, layout);
		for(PacmanGame game : games) {
			quadruplets.addAll(game.getQuadruplets());
		}
		return quadruplets;
	}
	
	public PacmanGame getMeilleurGame(ArrayList<PacmanGame> games) {
		PacmanGame meilleurGame = null;
		int meilleurScore = 0;
		for(PacmanGame game : games) {
			if(meilleurGame == null) {
				meilleurGame = game;
				meilleurScore = game.getScorePerceptron();
			}
			else {
				if(meilleurScore < game.getScorePerceptron()) {
					meilleurGame = game;
					meilleurScore = game.getScorePerceptron();
				}
			}
		}
		return meilleurGame;
	}
		
	public static void vizualise(int maxTurn, int time, PanelPacmanGame panel) {
		PacmanGame game = new PacmanGame(maxTurn,time,panel);
		ControleurPacmanGame pacman = new ControleurPacmanGame(game);
		System.out.println(game.getScorePerceptron());
	}
}
