package view;
import game.Game;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import game.SimpleGame;

public class ViewCommand implements Observer{
	private int turn;
	private JLabel tour;
	private JButton restartButton;
	private JButton runButton;
	private JButton stepButton;
	private JButton pauseButton;

	public void actualiser(Game game) {
		this.turn = game.getTurn();
		tour.setText("Tour : " + turn);
		if(turn>0) {
			restartButton.setEnabled(true);
			restartButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evenement) {
					game.init();
				}
			});
		}
	}
	
	public ViewCommand(Game game) {
		game.addObserver(this);
		JFrame jframe = new JFrame();
		jframe.setTitle("Game");
		jframe.setSize(new Dimension(700, 700));
		Dimension windowSize = jframe.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x-windowSize.width/ 2-500;
		int dy = centerPoint.y-windowSize.height/ 2-350;
		jframe.setLocation(dx,dy);
		
		JPanel panelHaut = new JPanel();
		GridLayout haut = new GridLayout(2,1);
		panelHaut.setLayout(haut);
		
		JPanel panelIcon = new JPanel();
		GridLayout haut_icon = new GridLayout(1,4);
		panelIcon.setLayout(haut_icon);
		
		Icon restartIcon = new ImageIcon("src/icone/icon_restart.png");
		restartButton = new JButton(restartIcon);
		restartButton.setEnabled(false);
		panelIcon.add(restartButton);
		
		Icon runIcon = new ImageIcon("src/icone/icon_run.png");
		runButton = new JButton(runIcon);
		panelIcon.add(runButton);
		
		Icon stepIcon = new ImageIcon("src/icone/icon_step.png");
		stepButton = new JButton(stepIcon);
		panelIcon.add(stepButton);
		
		Icon pauseIcon = new ImageIcon("src/icone/icon_pause.png");
		pauseButton = new JButton(pauseIcon);
		pauseButton.setEnabled(false);
		panelIcon.add(pauseButton);
		
		panelHaut.add(panelIcon);
		jframe.add(panelHaut);
		
		JPanel panelSlider = new JPanel();
		GridLayout bas_slider = new GridLayout(1,2);
		panelSlider.setLayout(bas_slider);
		
		JSlider slider = new JSlider(1,10,1);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setPaintTrack(true);
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(1);
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent evenement) {
				game.setTime(game.getTime()/slider.getValue());
			}
		});
		JLabel texte = new JLabel("Nombre de tour par seconde");
		panelSlider.add(texte);
		panelSlider.add(slider);
		
		
		tour = new JLabel("Tour : "+turn, SwingConstants.CENTER);
		panelSlider.add(tour);
		
		panelHaut.add(panelSlider);
		
		jframe.add(panelHaut);
		jframe.setVisible(true);
		
	}
	
	public JButton getRestartButton() {
		return this.restartButton;
	}
	
	public JButton getRunButton() {
		return runButton;
	}

	public JButton getStepButton() {
		return stepButton;
	}

	public JButton getPauseButton() {
		return pauseButton;
	}
}
