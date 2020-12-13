package model;

import java.util.ArrayList;

import agent.*;
import perceptron.Quadruplet;
import perceptron.SparseVector;
import strategie.*;
import view.Observer;

public class PacmanGame extends Game{
	
	private PanelPacmanGame panel;
	private ArrayList<PositionAgent> ghostsPos;
	private ArrayList<Ghost> ghosts, ghostsToRemove;
	private ArrayList<Pacman> pacmans, pacmansToRemove;
	private ArrayList<PositionAgent> pacmansPos;
	private ArrayList<Observer> observer;
	private int cptScarred = 0;
	private int scorePerceptron = 0;
	private AgentAction actionInteractive = new AgentAction(4);
	private boolean isScared = false;
	private ArrayList<Quadruplet> quadruplets;
	
	public PacmanGame(int maxTurn, long time, PanelPacmanGame panel) {
		super(maxTurn,time);
		this.panel = panel;
		this.ghostsPos = panel.getGhosts_pos();
		this.pacmansPos = panel.getPacmans_pos();
		this.observer = new ArrayList<Observer>();
		this.ghosts = new ArrayList<Ghost>();
		this.pacmans = new ArrayList<Pacman>();
		this.pacmansToRemove = new ArrayList<Pacman>();
		this.ghostsToRemove = new ArrayList<Ghost>();
	}
	
	public void takeTurn() {
		if(pacmans.size()>0 && this.getTurn()<this.getMaxturn() && panel.getCptFood()!=0) {
			if(panel.getGhostsScarred()) {
				if(!isScared) {
					affectationGhost(new StrategieEscape(panel));
					affectationPacman(new StrategieSeekGhost(panel));
					isScared = true;
				}
				if(cptScarred == 10) {
					affectationGhost(new StrategieRandom(panel));
					affectationPacman(new StrategieFood(panel));
					panel.setGhostsScarred(false);
					isScared = false;
					cptScarred=0;
				}
				else
					++cptScarred;
			}
			for(Pacman pacman : pacmans) {
				AgentAction coup;
				int ax = pacman.getPosition().getX();
				int ay = pacman.getPosition().getY();
				if(!(pacman.getStrategie() instanceof StrategieInteractive)) {
					coup = pacman.getStrategie().coup(pacman);
					if(pacman.getStrategie() instanceof StrategiePerceptron) {
						StrategiePerceptron strat = (StrategiePerceptron) pacman.getStrategie();
						SparseVector etatAvantAction = strat.encodeEtat(pacman.getPosition());
						pacman.getStrategie().jouer(coup, pacman);
						SparseVector etatApresAction = strat.encodeEtat(pacman.getPosition());
						int score = 0;
						if(panel.getMaze().isFood(ax, ay)) {
							score = 1;
						}
						if(panel.getMaze().isCapsule(ax, ay)) {
							score = 5;
						}
						if(panel.getGhostsScarred()) {
							PositionAgent pacmanPos = pacman.getPosition();
							for(Ghost ghost : ghosts) {
								PositionAgent ghostPos = ghost.getPosition();
								if(pacmanPos.getX()==ghostPos.getX() && pacmanPos.getY()==ghostPos.getY()) {
									score = 10;
								}
							}
						}
						quadruplets.add(new Quadruplet(etatAvantAction,coup,etatApresAction,score));
					}
					else
						pacman.getStrategie().jouer(coup, pacman);
					
				}
				else {
					if(this.actionInteractive.get_direction() == 0 && pacman.getStrategie().isLegalMove(ax, ay-1, pacman)) {
						pacman.getStrategie().jouer(actionInteractive, pacman);
					}
					else if(this.actionInteractive.get_direction() == 1 && pacman.getStrategie().isLegalMove(ax, ay+1, pacman)) {
						pacman.getStrategie().jouer(actionInteractive, pacman);
					}
					else if(this.actionInteractive.get_direction() == 2 && pacman.getStrategie().isLegalMove(ax+1, ay, pacman)) {
						pacman.getStrategie().jouer(actionInteractive, pacman);
					}
					else if(this.actionInteractive.get_direction() == 3 && pacman.getStrategie().isLegalMove(ax-1, ay, pacman)) {
						pacman.getStrategie().jouer(actionInteractive, pacman);
					}
					else {
						this.actionInteractive = new AgentAction(4);
						pacman.getStrategie().jouer(actionInteractive, pacman);
					}
				}
				if(panel.getMaze().isFood(ax, ay)) {
					panel.getMaze().setFood(ax, ay, false);
					panel.setCptFood(panel.getCptFood()-1);
					scorePerceptron += 1;
				}
				if(panel.getMaze().isCapsule(ax, ay)) {
					scorePerceptron += 5;
					panel.getMaze().setCapsule(ax, ay, false);
					if(!panel.getGhostsScarred())
						panel.setGhostsScarred(true);
					else
						cptScarred -= 10;
				}
				isEating();
			}
			ghosts.removeAll(ghostsToRemove);
			for(Ghost ghost : ghosts) {
				AgentAction coup = ghost.getStrategie().coup(ghost);
				ghost.getStrategie().jouer(coup, ghost);
				isEating();
			}
			pacmans.removeAll(pacmansToRemove);
			notifyObserver();
			try {
				Thread.sleep(this.getTime());
			}catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		else {
			this.setIsRunning(false);
			if(panel.getCptFood()==0)
				System.out.println("Victoire Pacman");
			else if(pacmans.size()==0 || ghosts.size()==0) {
				if(pacmans.size()==0)
					System.out.println("Victoire fantôme");
				else
					System.out.println("Victoire Pacman");
			}
		}
	}

	@Override
	public void initializeGame() {
		for(PositionAgent pos : panel.getGhosts_pos()) {
			ghosts.add(new Ghost(new StrategieRandom(panel),pos));
		}
	}
	
	public void restartGame() {
		try {
			Maze newMaze = new Maze(panel.getMaze().getFilename());
			this.panel.setMaze(newMaze);
			this.panel.setPacmans_pos(newMaze.getPacman_start());
			this.panel.setGhosts_pos(newMaze.getGhosts_start());
			this.pacmansPos = panel.getPacmans_pos();
			this.ghostsPos = panel.getGhosts_pos();
			Strategie strategie = pacmans.get(0).getStrategie();
			this.pacmans.clear();
			this.ghosts.clear();
			initializeGame();
			initialisePacman(strategie);
			notifyObserver();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void notifyObserver() {
		for(Observer o : observer) {
			o.actualiser(this);
		}
	}
	
	public void addObserver(Observer o) {
		this.observer.add(o);
	}

	@Override
	public boolean gameContinue() {
		return true;
	}
	
	public void isEating() {
		if(panel.getGhostsScarred()) {
			for(Pacman pacman : pacmans) {
				PositionAgent pacmanPos = pacman.getPosition();
				for(Ghost ghost : ghosts) {
					PositionAgent ghostPos = ghost.getPosition();
					if(pacmanPos.getX()==ghostPos.getX() && pacmanPos.getY()==ghostPos.getY()) {
						scorePerceptron += 10;
						System.out.println("Un fantôme a été mangé");
						ghostsToRemove.add(ghost);
						ghostsPos.remove(ghostPos);
					}
				}
			}
		}
		else {
			for(Pacman pacman : pacmans) {
				PositionAgent pacmanPos = pacman.getPosition();
				for(Ghost ghost : ghosts) {
					PositionAgent ghostPos = ghost.getPosition();
					if(pacmanPos.getX()==ghostPos.getX() && pacmanPos.getY()==ghostPos.getY()) {
						System.out.println("Un pacman a été mangé");
						pacmansToRemove.add(pacman);
						pacmansPos.remove(pacmanPos);
					}
				}
			}
		}
	}
	
	public void affectationGhost(Strategie strat) {
		for(Ghost ghost : ghosts) {
			ghost.setStrategie(strat);
		}
	}
	
	public void affectationPacman(Strategie strat) {
		for(Pacman pacman : pacmans) {
			if(!(pacman.getStrategie() instanceof StrategieInteractive))
				pacman.setStrategie(strat);
		}
	}
	
	public void initialisePacman(Strategie strat) {
		if((strat instanceof StrategieInteractive)) {
			for(int i = 0; i < this.pacmansPos.size(); ++i) {
				if(i == 0) {
					StrategieInteractive strategie = new StrategieInteractive(panel, this);
					pacmans.add(new Pacman(strategie, pacmansPos.get(i)));
				}
				else {
					pacmans.add(new Pacman(new StrategieFood(this.panel),pacmansPos.get(i)));
				}
			}
		}
		else {
			for(int i = 0; i < this.pacmansPos.size(); ++i) {
				pacmans.add(new Pacman(strat,pacmansPos.get(i)));
			}
		}
	}
	
	public Ghost getGhost(PositionAgent pos) {
		for(Ghost ghost : ghosts) {
			if(ghost.getPosition().getX()==pos.getX() && ghost.getPosition().getY()==pos.getY())
				return ghost;
		}
		return null;
	}
	
	public ArrayList<Pacman> getPacmans() {return pacmans;}

	@Override
	public void gameOver() {
		System.out.println("Tour maximale atteint : Victoire fantôme");
	}
	
	public PanelPacmanGame getPanel() {return panel;}
	
	
	public ArrayList<Observer> getObserver() {
		return this.observer;
	}
	
	public void setActionInteractive(AgentAction action) {
		this.actionInteractive = action;
	}
	
	public int getScorePerceptron() {
		return scorePerceptron;
	}

	public ArrayList<PositionAgent> getGhostsPos() {
		return ghostsPos;
	}

	public void setGhostsPos(ArrayList<PositionAgent> ghostsPos) {
		this.ghostsPos = ghostsPos;
	}

	public ArrayList<PositionAgent> getPacmansPos() {
		return pacmansPos;
	}

	public void setPacmansPos(ArrayList<PositionAgent> pacmansPos) {
		this.pacmansPos = pacmansPos;
	}
	
	public ArrayList<Quadruplet> getQuadruplets() {
		return quadruplets;
	}
}
