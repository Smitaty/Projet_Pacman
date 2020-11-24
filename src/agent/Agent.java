package agent;

import strategie.Strategie;

public abstract class Agent {

	private Strategie strategie;
	private PositionAgent position;
	private AgentAction action;
	
	public Agent(Strategie strategie, PositionAgent pos) {
		this.strategie = strategie;
		this.position = pos;
		this.action = new AgentAction(4);
	}
	
	public PositionAgent getPosition() {return position;}

	public Strategie getStrategie() {
		return strategie;
	}

	public void setStrategie(Strategie strategie) {
		this.strategie = strategie;
	}

	public void setPosition(PositionAgent position) {
		this.position = position;
	}

	public AgentAction getAction() {
		return action;
	}

	public void setAction(AgentAction action) {
		this.action = action;
	}
	
}
