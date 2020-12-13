package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import agent.Maze;
import model.PacmanGame;
import strategie.*;
import view.ViewCommand;
import view.ViewPacmanGame;

public class ControleurPacmanGame implements InterfaceControleur{
	private ViewPacmanGame pacman;
	private ViewCommand command;
	private String layout;
	private String strategie;
	private PacmanGame game;
	private boolean firstClick = true;
	
	public ControleurPacmanGame(PacmanGame game) {
		this.game = game;
		this.command = new ViewCommand(game);
		command.getJFileChooser().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				layout = "src/layout/" + command.getJFileChooser().getSelectedFile().getName();
				try {
					Maze maze = new Maze(layout);
					setUpdate(maze);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				command.getChooseButton().setEnabled(true);
			}
			
		});
		command.getChooseButton().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				strategie = command.getListe().getSelectedValue().toString();
				setStrategie(strategie);
				if(strategie.equals("Interactive")) {
					command.getRunButton().setEnabled(false);
					command.getStepButton().setEnabled(false);
					command.getPauseButton().setEnabled(true);
				}
				else {
					command.getRunButton().setEnabled(true);
					command.getStepButton().setEnabled(true);
				}
			}
			
		});
		command.getRunButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				if(firstClick) {
					start();
					run();
					firstClick = false;
				}
				else {
					run();
				}
				command.getRunButton().setEnabled(false);
				command.getStepButton().setEnabled(false);
				command.getRestartButton().setEnabled(true);
				command.getPauseButton().setEnabled(true);
			}
		});
		command.getPauseButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				pause();
				command.getStepButton().setEnabled(true);
				command.getPauseButton().setEnabled(false);
			}
		});
		command.getStepButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				step();
			}
		});
		command.getRestartButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				restart();
				command.getRunButton().setEnabled(true);
				command.getStepButton().setEnabled(true);
				command.getRestartButton().setEnabled(false);
				command.getPauseButton().setEnabled(false);
			}
		});
	}
	
	public void setStrategie(String strategie) {
		if(strategie.equals("Interactive")) {
			StrategieInteractive strat = new StrategieInteractive(game.getPanel(),game);
			game.initialisePacman(strat);
			game.initializeGame();
			game.launch();
		}
		else {
			if(strategie.equals("Random")) {
				game.initialisePacman(new StrategieRandom(game.getPanel()));
			}
			else if(strategie.equals("Food")){
				game.initialisePacman(new StrategieFood(game.getPanel()));
			}
			else {
				game.initialisePacman(new StrategiePerceptron(game.getPanel()));
			}
		}
	}
	
	public void start() {
		game.init();
	}
	
	public void restart() {
		game.restartGame();		
	}
	
	public void step() {
		game.step();
		if(game.getTurn()==0) {
			command.getRestartButton().setEnabled(true);
			command.getPauseButton().setEnabled(true);
		}
	}
	
	public void run() {
		game.launch();
		command.getRestartButton().setEnabled(true);
		command.getPauseButton().setEnabled(true);
		command.getRunButton().setEnabled(false);
		command.getStepButton().setEnabled(false);
	}
	
	public void pause() {
		game.pause();
		command.getStepButton().setEnabled(true);
		command.getRunButton().setEnabled(true);
	}
	
	public void setTime(double time) {
		game.setTime((long)time);
	}
	
	public void setUpdate(Maze maze) {
		game.getPanel().setMaze(maze);
		game.getPanel().setGhosts_pos(maze.getGhosts_start());
		game.getPanel().setPacmans_pos(maze.getPacman_start());
		game.setGhostsPos(maze.getGhosts_start());
		game.setPacmansPos(maze.getPacman_start());
		if(pacman == null) {
			pacman = new ViewPacmanGame(this.game);
		}
		else {
			pacman.actualiser(this.game);
		}
	}
}
