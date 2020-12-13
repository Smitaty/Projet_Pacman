package view;

import javax.swing.*;

import model.Game;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ViewCommand implements Observer{
	private int turn;
	private JLabel tour;
	private JButton restartButton;
	private JButton runButton;
	private JButton stepButton;
	private JButton pauseButton;
	private JButton chooseButton;
	private JFileChooser jFile;
	private JList liste;
	private JFrame jframe;
	private JSlider slider;
	private int sliderValue;

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
		if(sliderValue < slider.getValue())
			game.setTime(game.getTime() - (100*(slider.getValue()-sliderValue)));
		else
			game.setTime(game.getTime() + (100*(sliderValue-slider.getValue())));
		sliderValue = slider.getValue();
	}
	
	public ViewCommand(Game game) {
		game.addObserver(this);
		jframe = new JFrame();
		jframe.setTitle("Pacman");
		jframe.setSize(new Dimension(800, 1000));
		Dimension windowSize = jframe.getSize();
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Point centerPoint = ge.getCenterPoint();
		int dx = centerPoint.x-windowSize.width/ 2-500;
		int dy = centerPoint.y-windowSize.height/ 2-350;
		jframe.setLocation(dx,dy);
		
		JPanel panel = new JPanel(new GridLayout(3,1));
		
		JPanel panelIcon = new JPanel(new GridLayout(1,2));
		
		Icon restartIcon = new ImageIcon("src/icone/icon_restart.png");
		restartButton = new JButton(restartIcon);
		restartButton.setEnabled(false);
		panelIcon.add(restartButton);
		
		Icon runIcon = new ImageIcon("src/icone/icon_run.png");
		runButton = new JButton(runIcon);
		runButton.setEnabled(false);
		panelIcon.add(runButton);
		
		Icon stepIcon = new ImageIcon("src/icone/icon_step.png");
		stepButton = new JButton(stepIcon);
		stepButton.setEnabled(false);
		panelIcon.add(stepButton);
		
		Icon pauseIcon = new ImageIcon("src/icone/icon_pause.png");
		pauseButton = new JButton(pauseIcon);
		pauseButton.setEnabled(false);
		panelIcon.add(pauseButton);
		
		panel.add(panelIcon);
		jframe.add(panel);
		
		JPanel panelSlider = new JPanel(new GridLayout(1,3));
		
		slider = new JSlider(1,10,1);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setPaintTrack(true);
		slider.setMinorTickSpacing(1);
		slider.setMajorTickSpacing(1);
		this.sliderValue = slider.getValue();
		JLabel texte = new JLabel(" Nombre de tour par seconde");
		panelSlider.add(texte);
		panelSlider.add(slider);
		
		
		tour = new JLabel("Tour : "+turn, SwingConstants.CENTER);
		panelSlider.add(tour);
		
		panel.add(panelSlider);
		
		JPanel panelChooser = new JPanel(new GridLayout(1,3));
		jFile = new JFileChooser(new File("/home/travail/eclipse-workspace/Projet/src/layout/"));
		panelChooser.add(jFile);
		
		DefaultListModel strategies = new DefaultListModel();
		strategies.addElement("Random");
		strategies.addElement("Food");
		strategies.addElement("Interactive");
		strategies.addElement("Perceptron");
		
		liste = new JList(strategies);
		liste.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		liste.setSelectedIndex(0);
		liste.setVisibleRowCount(3);
		panelChooser.add(liste);
		
		chooseButton = new JButton("Choisir stratégie");
		chooseButton.setEnabled(false);
		panelChooser.add(chooseButton);
		
		panel.add(panelChooser);
		
		jframe.setVisible(true);
		
	}
	
	public JFrame getJFrame() {
		return this.jframe;
	}
	
	public JButton getChooseButton() {
		return this.chooseButton;
	}
	
	public JList getListe() {
		return liste;
	}
	
	public JFileChooser getJFileChooser() {
		return jFile;
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
