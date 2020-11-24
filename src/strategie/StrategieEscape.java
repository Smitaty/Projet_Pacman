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
	
	public StrategieEscape(PanelPacmanGame panel) {
		super(panel);
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
			return new AgentAction(4);
		}
	}

}
