package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import game.Game;

public class ControleurSimpleGame implements InterfaceControleur{
	private Game game;
	private ViewCommand command;
	private ViewSimpleGame view;
	private boolean firstClick = true;
	
	public ControleurSimpleGame(Game game) {
		this.game = game;
		ViewSimpleGame view = new ViewSimpleGame(game);
		this.view = view;
		ViewCommand command = new ViewCommand(game);
		this.command = command;
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
			}
		});
		command.getPauseButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				pause();
			}
		});
		command.getStepButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				step();
			}
		});
		command.getRestartButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evenement) {
				start();
				command.getRunButton().setEnabled(true);
			}
		});
	}
	
	public void start() {
		game.init();
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
}
