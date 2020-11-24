package strategie;

import agent.Agent;
import agent.AgentAction;
import agent.PanelPacmanGame;

public class StrategieInteractive extends Strategie{

	private MyKeyAdapter key;
	
	public StrategieInteractive(PanelPacmanGame panel) {
		super(panel);
		key = new MyKeyAdapter();
	}

	@Override
	public AgentAction coup(Agent agent) {
		int ax = agent.getPosition().getX();
		int ay = agent.getPosition().getY();
		if(key.isTop() && this.isLegalMove(ax, ay-1, agent)) {
			key.setTop(false);
			return new AgentAction(0);
		}
		if(key.isBottom() && this.isLegalMove(ax, ay+1, agent)) {
			key.setBottom(false);
			return new AgentAction(1);
		}
		if(key.isRight() && this.isLegalMove(ax+1, ay, agent)) {
			key.setRight(false);
			return new AgentAction(2);
		}
		if(key.isLeft() && this.isLegalMove(ax-1, ay, agent)) {
			key.setLeft(false);
			return new AgentAction(3);
		}
		return new AgentAction(4);
	}
	
	public MyKeyAdapter getKey() {
		return key;
	}

	public void setKey(MyKeyAdapter key) {
		this.key = key;
	}

}
