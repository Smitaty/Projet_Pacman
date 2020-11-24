package strategie;

import java.util.ArrayList;
import java.util.Random;

import agent.Agent;
import agent.AgentAction;
import agent.PanelPacmanGame;
import agent.PositionAgent;

public class StrategieFood extends Strategie{

	private int nbFood;
	private PanelPacmanGame panel;
	private ArrayList<PositionAgent> ghostsPos;
	
	public StrategieFood(PanelPacmanGame panel) {
		super(panel);
		this.panel = panel;
		nbFood = panel.getCptFood();
		ghostsPos = panel.getGhosts_pos();
	}

	@Override
	public AgentAction coup(Agent agent) {
		ArrayList<AgentAction> coups = this.coupsPossible(agent);
		ArrayList<AgentAction> coupForFood = new ArrayList<AgentAction>();
		int cptFood = 0;
		PositionAgent pos = agent.getPosition();
		for(AgentAction coup : coups) {
			switch(coup.get_direction()) {
			case AgentAction.NORTH:
				if(panel.getMaze().isFood(pos.getX(), pos.getY()-1)) {
					coupForFood.add(coup);
					++cptFood;
				}
				break;
			case AgentAction.SOUTH:
				if(panel.getMaze().isFood(pos.getX(), pos.getY()+1)) {
					coupForFood.add(coup);
					++cptFood;
				}
				break;
			case AgentAction.EAST:
				if(panel.getMaze().isFood(pos.getX()+1, pos.getY())) {
					coupForFood.add(coup);
					++cptFood;
				}
				break;
			case AgentAction.WEST:
				if(panel.getMaze().isFood(pos.getX()-1, pos.getY())) {
					coupForFood.add(coup);
					++cptFood;
				}
				break;
			case AgentAction.STOP:
				break;
			}
		}
		if(cptFood==0) {
			int nbcoups = coups.size();
			Random r = new Random();
			int coup = r.nextInt(nbcoups);
			return coups.get(coup);
		}
		else {
			int nbcoups = coupForFood.size();
			Random r = new Random();
			int coup = r.nextInt(nbcoups);
			return coupForFood.get(coup);
		}
	}
}
