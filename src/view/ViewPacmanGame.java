package view;

import agent.PanelPacmanGame;
import game.Game;
import game.PacmanGame;
import strategie.MyKeyAdapter;
import strategie.StrategieInteractive;

import javax.swing.*;
import java.awt.*;

public class ViewPacmanGame implements Observer{
	
	private PanelPacmanGame panel;
	private JFrame jframe;
	
	public void actualiser(Game game) {
		PacmanGame pacman = (PacmanGame) game;
		this.panel = pacman.getPanel();
		panel.repaint();
	}
	
	public ViewPacmanGame(PacmanGame game) {
		game.addObserver(this);
		this.panel = game.getPanel();
		try {
			jframe = new JFrame();
			jframe.setTitle("Game");
			jframe.setSize(new Dimension(700, 700));
			Dimension windowSize = jframe.getSize();
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			Point centerPoint = ge.getCenterPoint();
			int dx = centerPoint.x-windowSize.width/ 2 ;
			int dy = centerPoint.y-windowSize.height/ 2-350;
			jframe.setLocation(dx,dy);
			jframe.add(panel);
			jframe.addKeyListener(game.getKey());
			jframe.setVisible(true);
		}catch(Exception e) {
			System.out.println("Le fichier n'existe pas");
		}
	}

}
