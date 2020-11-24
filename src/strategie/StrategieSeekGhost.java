package strategie;

import java.util.ArrayList;
import java.util.Random;

import agent.Agent;
import agent.AgentAction;
import agent.Ghost;
import agent.PanelPacmanGame;
import agent.PositionAgent;

public class StrategieSeekGhost extends Strategie{

	private ArrayList<PositionAgent> ghosts;
	
	public StrategieSeekGhost(PanelPacmanGame panel) {
		super(panel);
		this.ghosts = panel.getGhosts_pos();
	}

	@Override
	public AgentAction coup(Agent agent) {
		PositionAgent PlusProcheGhost = null;
		double meilleurDistance = 0;
		PositionAgent pacman = agent.getPosition();
		for(PositionAgent ghost : ghosts) {
			double terme1 = Math.pow((ghost.getX() - pacman.getX()), 2);
			double terme2 = Math.pow((ghost.getY() - pacman.getY()), 2);
			double distance = Math.sqrt(terme1+terme2);
			if(PlusProcheGhost == null) {
				meilleurDistance = distance;
				PlusProcheGhost = ghost;
			}
			else {
				if(meilleurDistance > distance) {
					meilleurDistance = distance;
					PlusProcheGhost = ghost;
				}
			}
		}
		AgentAction coup = deplacementVersGhost(agent, pacman, PlusProcheGhost);
		return coup;
	}
	
	public AgentAction deplacementVersGhost(Agent agent, PositionAgent pacman, PositionAgent ghost) {
		int px = pacman.getX();
		int py = pacman.getY();
		int gx = ghost.getX();
		int gy = ghost.getY();
		if(px > gx) {
			if(py > gy) {
				if(this.isLegalMove(px-1, py, agent)) {
					return new AgentAction(3);
				}
				else if(this.isLegalMove(px, py-1, agent)) {
					return new AgentAction(0);
				}
			}
			else if(py < gy) {
				if(this.isLegalMove(px-1, py, agent)) {
					return new AgentAction(3);
				}
				else if(this.isLegalMove(px, py+1, agent)) {
					return new AgentAction(1);
				}
			}
			else {
				if(this.isLegalMove(px-1, py, agent)) {
					return new AgentAction(3);
				}
				else {
					if(this.isLegalMove(px, py-1, agent)) {
						return new AgentAction(0);
					}
					if(this.isLegalMove(px, py+1, agent)) {
						return new AgentAction(1);
					}
				}
			}
			return new AgentAction(4);
		}
		else if(px < gx) {
			if(py > gy) {
				if(this.isLegalMove(px+1, py, agent)) {
					return new AgentAction(2);
				}
				else if(this.isLegalMove(px, py-1, agent)) {
					return new AgentAction(0);
				}
				return new AgentAction(4);
			}
			else if(py < gy) {
				if(this.isLegalMove(px+1, py, agent)) {
					return new AgentAction(2);
				}
				else if(this.isLegalMove(px, py+1, agent)) {
					return new AgentAction(1);
				}
				return new AgentAction(4);
			}
			else {
				if(this.isLegalMove(px+1, py, agent)) {
					return new AgentAction(2);
				}
				else {
					if(this.isLegalMove(px, py-1, agent)) {
						return new AgentAction(0);
					}
					if(this.isLegalMove(px, py+1, agent)) {
						return new AgentAction(1);
					}
					return new AgentAction(4);
				}
			}
		}
		else {
			if(py > gy) {
				if(this.isLegalMove(px, py-1, agent)) {
					return new AgentAction(0);
				}
				if(this.isLegalMove(px-1, py, agent) && this.isLegalMove(px+1, py, agent)) {
					Random r = new Random();
					int coup = r.nextInt(2);
					if(coup == 0)
						return new AgentAction(2);
					else
						return new AgentAction(3);
				}
				else {
					if(this.isLegalMove(px-1, py, agent))
						return new AgentAction(3);
					if(this.isLegalMove(px+1, py, agent))
						return new AgentAction(2);
				}
			}
			else if(py < gy) {
				if(this.isLegalMove(px, py+1, agent)) {
					return new AgentAction(1);
				}
				if(this.isLegalMove(px-1, py, agent) && this.isLegalMove(px+1, py, agent)) {
					Random r = new Random();
					int coup = r.nextInt(2);
					if(coup == 0)
						return new AgentAction(2);
					else
						return new AgentAction(3);
				}
				else {
					if(this.isLegalMove(px-1, py, agent))
						return new AgentAction(3);
					if(this.isLegalMove(px+1, py, agent))
						return new AgentAction(2);
				}
			}
			return new AgentAction(4);
		}
	}
}
