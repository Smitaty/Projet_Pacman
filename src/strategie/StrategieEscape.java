package strategie;

import java.util.ArrayList;
import java.util.Random;

import agent.Agent;
import agent.AgentAction;
import agent.Ghost;
import agent.Pacman;
import agent.PanelPacmanGame;
import agent.PositionAgent;

public class StrategieEscape extends Strategie{
	
	ArrayList<PositionAgent> pacmans,ghosts;
	PanelPacmanGame panel;
	
	public StrategieEscape(PanelPacmanGame panel) {
		super(panel);
		this.panel = panel;
		pacmans = panel.getPacmans_pos();
		ghosts = panel.getGhosts_pos();
	}

	@Override
	public AgentAction coup(Agent agent) {
		PositionAgent posGhost = agent.getPosition();
		if(pacmans.size()==1) {
			PositionAgent posPacman = pacmans.get(0);
			if(posPacman.getX() > posGhost.getX()) {
				if(this.isLegalMove(posGhost.getX()-1, posGhost.getY(), agent))
					return new AgentAction(3);
				else {
					if(posPacman.getY() > posGhost.getY()) {
						if(this.isLegalMove(posGhost.getX(), posGhost.getY()-1, agent))
							return new AgentAction(0);
						if(this.isLegalMove(posGhost.getX(), posGhost.getY()+1, agent))
							return new AgentAction(1);
					}
					if(posPacman.getY() < posGhost.getY()) {
						if(this.isLegalMove(posGhost.getX(), posGhost.getY()+1, agent))
							return new AgentAction(1);
						if(this.isLegalMove(posGhost.getX(), posGhost.getY()-1, agent))
							return new AgentAction(0);
					}
					if(posPacman.getY() == posGhost.getY()) {
						if(this.isLegalMove(posGhost.getX(), posGhost.getY()-1, agent) && this.isLegalMove(posGhost.getX(), posGhost.getY()+1, agent)) {
							Random r = new Random();
							int coup = r.nextInt(2);
							if(coup == 0)
								return new AgentAction(0);
							else
								return new AgentAction(1);
						}
						if(this.isLegalMove(posGhost.getX(), posGhost.getY()-1, agent))
							return new AgentAction(0);
						if(this.isLegalMove(posGhost.getX(), posGhost.getY()+1, agent))
							return new AgentAction(1);
					}
					return new AgentAction(4);
				}
			}
			else if(posPacman.getX() < posGhost.getX()) {
				if(this.isLegalMove(posGhost.getX()+1, posGhost.getY(), agent)) {
					return new AgentAction(2);
				}
				else {
					if(posPacman.getY() > posGhost.getY()) {
						if(this.isLegalMove(posGhost.getX(), posGhost.getY()-1, agent))
							return new AgentAction(0);
						if(this.isLegalMove(posGhost.getX(), posGhost.getY()+1, agent))
							return new AgentAction(1);
					}
					if(posPacman.getY() < posGhost.getY()) {
						if(this.isLegalMove(posGhost.getX(), posGhost.getY()+1, agent))
							return new AgentAction(1);
						if(this.isLegalMove(posGhost.getX(), posGhost.getY()-1, agent))
							return new AgentAction(0);
					}
					if(posPacman.getY() == posGhost.getY()) {
						if(this.isLegalMove(posGhost.getX(), posGhost.getY()-1, agent) && this.isLegalMove(posGhost.getX(), posGhost.getY()+1, agent)) {
							Random r = new Random();
							int coup = r.nextInt(2);
							if(coup == 0)
								return new AgentAction(0);
							else
								return new AgentAction(1);
						}
						if(this.isLegalMove(posGhost.getX(), posGhost.getY()-1, agent))
							return new AgentAction(0);
						if(this.isLegalMove(posGhost.getX(), posGhost.getY()+1, agent))
							return new AgentAction(1);
					}
					return new AgentAction(4);
				}
			}
			else {
				if(posPacman.getY() > posGhost.getY()) {
					if(this.isLegalMove(posGhost.getX(), posGhost.getY()-1, agent))
						return new AgentAction(0);
					if(this.isLegalMove(posGhost.getX()-1, posGhost.getY(), agent) && this.isLegalMove(posGhost.getX()+1, posGhost.getY(), agent)) {
						Random r = new Random();
						int coup = r.nextInt(2);
						if(coup == 0)
							return new AgentAction(2);
						else
							return new AgentAction(3);
					}
					else if(this.isLegalMove(posGhost.getX()-1, posGhost.getY(), agent)) {
						return new AgentAction(3);
					}
					else if(this.isLegalMove(posGhost.getX()+1, posGhost.getY(), agent)) {
						return new AgentAction(2);
					}
				}
				if(posPacman.getY() < posGhost.getY()) {
					if(this.isLegalMove(posGhost.getX(), posGhost.getY()+1, agent))
						return new AgentAction(1);
					if(this.isLegalMove(posGhost.getX()-1, posGhost.getY(), agent) && this.isLegalMove(posGhost.getX()+1, posGhost.getY(), agent)) {
						Random r = new Random();
						int coup = r.nextInt(2);
						if(coup == 0)
							return new AgentAction(2);
						else
							return new AgentAction(3);
					}
					else if(this.isLegalMove(posGhost.getX()-1, posGhost.getY(), agent)) {
						return new AgentAction(3);
					}
					else if(this.isLegalMove(posGhost.getX()+1, posGhost.getY(), agent)) {
						return new AgentAction(2);
					}
				}
				return new AgentAction(4);
			}
		}
		else {
			PositionAgent pacmanProche = pacmanPlusProche(posGhost);
			int px = pacmanProche.getX();
			int py = pacmanProche.getY();
			int ax = posGhost.getX();
			int ay = posGhost.getY();
			
			if(px == ax) {
				if(py < ay && this.isLegalMove(ax, ay+1, agent)) {
					return new AgentAction(1);
				}
				else {
					if(this.isLegalMove(ax, ay-1, agent))
						return new AgentAction(0);
					else {
						if(this.isLegalMove(ax-1, ay, agent)) {
							return new AgentAction(3);
						}
						else if(this.isLegalMove(ax+1, ay, agent)) {
							return new AgentAction(2);
						}
						else {
							return new AgentAction(4);
						}
					}
				}
			}
			else if(py == ay) {
				if(px < ax && this.isLegalMove(ax+1, ay, agent)) {
					return new AgentAction(2);
				}
				else {
					if(this.isLegalMove(ax-1, ay, agent))
						return new AgentAction(3);
					else {
						if(this.isLegalMove(ax, ay-1, agent)) {
							return new AgentAction(0);
						}
						else if(this.isLegalMove(ax, ay+1, agent)) {
							return new AgentAction(1);
						}
						else {
							return new AgentAction(4);
						}
					}
				}
			}
			else {
				if(px < ax) {
					if(py < ay) { 
						if(this.isLegalMove(ax, ay+1, agent)) 
							return new AgentAction(1);
						else {
							if(this.isLegalMove(ax+1, ay, agent)) {
								return new AgentAction(2);
							}
							else {
								ArrayList<AgentAction> coups = coupsPossible(agent);
								int nbcoups = coups.size();
								Random r = new Random();
								int coup = r.nextInt(nbcoups);
								return coups.get(coup);
							}
						}
					}
					else {
						if(this.isLegalMove(ax, ay+1, agent))
							return new AgentAction(0);
						else {
							if(this.isLegalMove(ax-1, ay, agent)) {
								return new AgentAction(3);
							}
							else {
								ArrayList<AgentAction> coups = coupsPossible(agent);
								int nbcoups = coups.size();
								Random r = new Random();
								int coup = r.nextInt(nbcoups);
								return coups.get(coup);
							}
						}
					}
				}
				else {
					if(py < ay) { 
						if(this.isLegalMove(ax, ay-1, agent)) 
							return new AgentAction(1);
						else {
							if(this.isLegalMove(ax+1, ay, agent)) {
								return new AgentAction(2);
							}
							else {
								ArrayList<AgentAction> coups = coupsPossible(agent);
								int nbcoups = coups.size();
								Random r = new Random();
								int coup = r.nextInt(nbcoups);
								return coups.get(coup);
							}
						}
					}
					else {
						if(this.isLegalMove(ax, ay-1, agent))
							return new AgentAction(0);
						else {
							if(this.isLegalMove(ax-1, ay, agent)) {
								return new AgentAction(3);
							}
							else {
								ArrayList<AgentAction> coups = coupsPossible(agent);
								int nbcoups = coups.size();
								Random r = new Random();
								int coup = r.nextInt(nbcoups);
								return coups.get(coup);
							}
						}
					}
				}
			}
		}
	}
	
	public PositionAgent pacmanPlusProche(PositionAgent posGhost) {
		double plusProche = -1;
		PositionAgent pacmanProche = new PositionAgent(0,0,0);
		for(PositionAgent pos : pacmans) {
			int px = pos.getX();
			int py = pos.getY();
			int ax = posGhost.getX();
			int ay = posGhost.getY();
			double terme1 = Math.pow((px-ax),2);
			double terme2 = Math.pow((py-ay),2);
			double d1 = Math.sqrt(terme1+terme2);
			
			if(plusProche == -1) {
				plusProche = d1;
				pacmanProche = pos;
			}
			else {
				if(d1 < plusProche) {
					plusProche = d1;
					pacmanProche = pos;
				}
			}
		}
		return pacmanProche;
	}

}
