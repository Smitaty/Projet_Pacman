package strategie;

import java.util.ArrayList;
import java.util.Random;

import agent.*;

public class StrategieRandom extends Strategie{

	public StrategieRandom(PanelPacmanGame panel) {
		super(panel);
	}

	public AgentAction coup(Agent agent) {
		ArrayList<AgentAction> coups = coupsPossible(agent);
		int nbcoups = coups.size();
		Random r = new Random();
		int coup = r.nextInt(nbcoups);
		return coups.get(coup);
	}	
}
