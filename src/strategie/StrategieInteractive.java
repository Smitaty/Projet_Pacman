package strategie;

import agent.Agent;
import agent.AgentAction;
import agent.PanelPacmanGame;
import model.PacmanGame;

public class StrategieInteractive extends Strategie{

	private Agent agent;
	private PacmanGame game;
	
	public StrategieInteractive(PanelPacmanGame panel, PacmanGame game) {
		super(panel);
		this.game = game;
	}

	@Override
	public AgentAction coup(Agent agent) {
		return new AgentAction(4);
	}
	
	public void setGame(PacmanGame game) {
		this.game = game;
	}

}
