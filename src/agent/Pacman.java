package agent;

import strategie.Strategie;

public class Pacman extends Agent{

	private PositionAgent position;
	private Strategie strategie;
	private AgentAction action;
	
	public Pacman(Strategie strategie, PositionAgent coor) {
		super(strategie,coor);
		this.position = coor;
		this.strategie = strategie;
		this.action = new AgentAction(4);
	}

	public PositionAgent getPosition() {
		return position;
	}

	public void setPosition(PositionAgent position) {
		this.position = position;
	}

	public Strategie getStrategie() {
		return strategie;
	}

	public void setStrategie(Strategie strategie) {
		this.strategie = strategie;
	}	
	
	public AgentAction getAction() {
		return action;
	}

	public void setAction(AgentAction action) {
		this.action = action;
	}
	
}
