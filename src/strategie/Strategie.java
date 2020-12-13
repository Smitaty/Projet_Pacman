package strategie;

import java.util.ArrayList;
import agent.*;

public abstract class Strategie {

	private PanelPacmanGame panel;
	
	public Strategie(PanelPacmanGame panel) {
		this.panel=panel;
	}
	
	public ArrayList<AgentAction> coupsPossible(Agent agent){
		PositionAgent pos = agent.getPosition();
		ArrayList<AgentAction> coups = new ArrayList<AgentAction>();
		if(isLegalMove(pos.getX()+1,pos.getY(),agent)) coups.add(new AgentAction(2));
		if(isLegalMove(pos.getX()-1,pos.getY(),agent)) coups.add(new AgentAction(3));
		if(isLegalMove(pos.getX(),pos.getY()-1,agent)) coups.add(new AgentAction(0));
		if(isLegalMove(pos.getX(),pos.getY()+1,agent)) coups.add(new AgentAction(1));
		if(coups.size()==0)
			coups.add(new AgentAction(4));
		return coups;
	}
	
	public abstract AgentAction coup(Agent agent);
	
	public boolean isLegalMove(int x, int y, Agent agent) {
		if(panel.getMaze().isWall(x,y))
			return false;
		else if(isSomeone(x,y,agent))
			return false;
		else if(isEating(x,y,agent))
			return true;
		else
			return true;
	}
	
	public boolean isSomeone(int x, int y, Agent agent) {
		if(agent instanceof Ghost) {
			ArrayList<PositionAgent> positions = panel.getGhosts_pos();
			for(PositionAgent pos : positions) {
				if(pos.getX()==x && pos.getY()==y)
					return true;
			}
		}
		else {
			ArrayList<PositionAgent> positions = panel.getPacmans_pos();
			for(PositionAgent pos : positions) {
				if(pos.getX()==x && pos.getY()==y)
					return true;
			}
		}
		return false;
	}
	
	public boolean isEating(int x, int y, Agent agent) {
		if(agent instanceof Ghost) {
			ArrayList<PositionAgent> positions = panel.getPacmans_pos();
			for(PositionAgent pos : positions) {
				if(pos.getX()==x && pos.getY()==y && !panel.getGhostsScarred())
					return true;
			}
		}
		else {
			ArrayList<PositionAgent> positions = panel.getGhosts_pos();
			for(PositionAgent pos : positions) {
				if(pos.getX()==x && pos.getY()==y && panel.getGhostsScarred())
					return true;
			}
		}
		return false;
	}
	
	public void jouer(AgentAction action, Agent agent) {
		PositionAgent pos = agent.getPosition();
		switch(action.get_direction()) {
			case AgentAction.NORTH:
				if(agent instanceof Pacman) {
					panel.get(agent.getPosition(), true).setX(pos.getX());
					panel.get(agent.getPosition(), true).setY(pos.getY()-1);
					panel.get(agent.getPosition(), true).setDir(0);
				}
				else {
					panel.get(agent.getPosition(), false).setX(pos.getX());
					panel.get(agent.getPosition(), false).setY(pos.getY()-1);
					panel.get(agent.getPosition(), false).setDir(0);
				}
				break;
			case AgentAction.SOUTH:
				if(agent instanceof Pacman) {
					panel.get(agent.getPosition(), true).setX(pos.getX());
					panel.get(agent.getPosition(), true).setY(pos.getY()+1);
					panel.get(agent.getPosition(), true).setDir(1);
				}
				else {
					panel.get(agent.getPosition(), false).setX(pos.getX());
					panel.get(agent.getPosition(), false).setY(pos.getY()+1);
					panel.get(agent.getPosition(), false).setDir(1);
				}
				break;
			case AgentAction.EAST:
				if(agent instanceof Pacman) {
					panel.get(agent.getPosition(), true).setX(pos.getX()+1);
					panel.get(agent.getPosition(), true).setY(pos.getY());
					panel.get(agent.getPosition(), true).setDir(2);
				}
				else {
					panel.get(agent.getPosition(), false).setX(pos.getX()+1);
					panel.get(agent.getPosition(), false).setY(pos.getY());
					panel.get(agent.getPosition(), false).setDir(2);
				}
				break;
			case AgentAction.WEST:
				if(agent instanceof Pacman) {
					panel.get(agent.getPosition(), true).setX(pos.getX()-1);
					panel.get(agent.getPosition(), true).setY(pos.getY());
					panel.get(agent.getPosition(), true).setDir(3);
				}
				else {
					panel.get(agent.getPosition(), false).setX(pos.getX()-1);
					panel.get(agent.getPosition(), false).setY(pos.getY());
					panel.get(agent.getPosition(), false).setDir(3);
				}
				break;
			case AgentAction.STOP:
				break;
		}
	}
	
	public void setPanel(PanelPacmanGame panel) {
		this.panel = panel;
	}
}
