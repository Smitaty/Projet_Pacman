package view;

import agent.Agent;
import agent.AgentAction;
import agent.PanelPacmanGame;
import model.Game;
import model.PacmanGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class ViewPacmanGame implements Observer, KeyListener{
	
	private PanelPacmanGame panel;
	private PacmanGame game;
	private JFrame jframe;
	
	public void actualiser(Game game) {
		this.game = (PacmanGame) game;
		this.panel = this.game.getPanel();
		this.panel.repaint();
	}
	
	public ViewPacmanGame(PacmanGame game) {
		game.addObserver(this);
		this.panel = game.getPanel();
		try {
			jframe = new JFrame();
			jframe.setTitle("Game");
			jframe.setSize(new Dimension(900, 750));
			Dimension windowSize = jframe.getSize();
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Point centerPoint = ge.getCenterPoint();
			int dx = centerPoint.x-windowSize.width/ 2+350 ;
			int dy = centerPoint.y-windowSize.height/ 2-350;
			jframe.setLocation(dx,dy);
			jframe.add(panel);
			jframe.addKeyListener(this);
			jframe.setVisible(true);
		}catch(Exception e) {
			System.out.println("Le fichier n'existe pas");
		}
	}
	
	public JFrame getJFrame() {
		return jframe;
	}
	
	public PanelPacmanGame getPanel() {
		return panel;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Agent agent = game.getPacmans().get(0);
		int ax = agent.getPosition().getX();
		int ay = agent.getPosition().getY();
		if(e.getKeyChar() == 'z') {
			if(agent.getStrategie().isLegalMove(ax, ay-1, agent)) {
				game.setActionInteractive(new AgentAction(0));
			}
		}
		if(e.getKeyChar() == 's') {
			if(agent.getStrategie().isLegalMove(ax, ay+1, agent)) {
				game.setActionInteractive(new AgentAction(1));
			}
		}
		if(e.getKeyChar() == 'q') {
			if(agent.getStrategie().isLegalMove(ax-1, ay, agent)) {
				game.setActionInteractive(new AgentAction(3));
			}
		}
		if(e.getKeyChar() == 'd') {
			if(agent.getStrategie().isLegalMove(ax+1, ay, agent)) {
				game.setActionInteractive(new AgentAction(2));
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

}
