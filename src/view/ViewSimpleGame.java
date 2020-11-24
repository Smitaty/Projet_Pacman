package view;

import game.Game;
import javax.swing.*;
import java.awt.*;

public class ViewSimpleGame implements Observer{
	private int turn = 0;
	private JLabel tour;
	
	public ViewSimpleGame(Game game) {
		game.addObserver(this);
		JFrame jframe = new JFrame();
		jframe.setTitle("Game");
		jframe.setSize(new Dimension(700, 700));
		Dimension windowSize = jframe.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		tour = new JLabel("Tour : " + turn, SwingConstants.CENTER);
		jframe.add(tour);
		int dx = centerPoint.x-windowSize.width/ 2+200 ;
		int dy = centerPoint.y-windowSize.height/ 2-350;
		jframe.setLocation(dx,dy);
		jframe.setVisible(true);
	}
	
	public void actualiser(Game game) {
		this.turn = game.getTurn();
		tour.setText("Tour : " + turn);
	}
}
