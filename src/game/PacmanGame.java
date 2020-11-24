package game;

import java.util.ArrayList;

import agent.*;
import strategie.*;
import view.Observer;

public class PacmanGame extends Game{
	
	private PanelPacmanGame panel;
	private ArrayList<PositionAgent> ghostsPos;
	private ArrayList<Ghost> ghosts, ghostsToRemove;
	private ArrayList<Pacman> pacmans, pacmansToRemove;
	private ArrayList<PositionAgent> pacmansPos;
	private ArrayList<Observer> observer;
	private int cptScarred=0;
	private MyKeyAdapter key = new MyKeyAdapter();
	
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
		if(panel.getGhostsScarred()) {
			affectationGhost(new StrategieEscape(panel));
			affectationPacman(new StrategieSeekGhost(panel));
			if(cptScarred==10) {
				affectationGhost(new StrategieRandom(panel));
				affectationPacman(new StrategieFood(panel));
				panel.setGhostsScarred(false);
				cptScarred=0;
			}
			else
				++cptScarred;
		}
		for(Pacman pacman : pacmans) {
			AgentAction coup;
			if(pacman.getStrategie() instanceof StrategieInteractive) {
				StrategieInteractive strategie = (StrategieInteractive) pacman.getStrategie();
				strategie.setKey(key);
				coup = strategie.coup(pacman);
				while(coup.get_direction() == 4) {
					coup = strategie.coup(pacman);
				}
			}
			else {
				coup = pacman.getStrategie().coup(pacman);
			}
			pacman.getStrategie().jouer(coup, pacman);
			PositionAgent pos = pacman.getPosition();
			if(panel.getMaze().isFood(pos.getX(), pos.getY())) {
				panel.getMaze().setFood(pos.getX(), pos.getY(), false);
				panel.setCptFood(panel.getCptFood()-1);
			}
			if(panel.getMaze().isCapsule(pos.getX(), pos.getY())) {
				panel.getMaze().setCapsule(pos.getX(), pos.getY(), false);
				if(!panel.getGhostsScarred())
					panel.setGhostsScarred(true);
				else
					cptScarred -= 10;
			}
			isEating();
		}
		for(Ghost ghost : ghosts) {
			AgentAction coup = ghost.getStrategie().coup(ghost);
			ghost.getStrategie().jouer(coup, ghost);
			isEating();
		}
		pacmans.removeAll(pacmansToRemove);
		ghosts.removeAll(ghostsToRemove);
		notifyObserver();
		try {
			Thread.sleep(1000);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void initializeGame() {
		System.out.println("Initialisation de la partie");
		for(PositionAgent pos : panel.getGhosts_pos()) {
			ghosts.add(new Ghost(new StrategieRandom(panel),pos));
			System.out.println(pos.getX()+" "+pos.getY());
		}
		for(PositionAgent pos : panel.getPacmans_pos()) {
			System.out.println(pos.getX()+" "+pos.getY());
			pacmans.add(new Pacman(new StrategieInteractive(panel),pos));
		}
		while(pacmans.size()>0 && ghosts.size()>0 && this.getTurn()<this.getMaxturn() && panel.getCptFood()!=0) {
			this.step();
		}
		if(panel.getCptFood()==0)
			System.out.println("Victoire Pacman");
		else if(pacmans.size()==0 || ghosts.size()==0) {
			if(pacmans.size()==0)
				System.out.println("Victoire fantôme");
			else
				System.out.println("Victoire Pacman");
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
		System.out.println("Tour maximale atteint : égalité");
	}
	
	public PanelPacmanGame getPanel() {return panel;}
	
	public MyKeyAdapter getKey() {
		return key;
	}

	public void setKey(MyKeyAdapter key) {
		this.key = key;
	}
}
