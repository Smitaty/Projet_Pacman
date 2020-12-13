package strategie;

import java.util.ArrayList;
import java.util.Random;

import agent.Agent;
import agent.AgentAction;
import agent.PanelPacmanGame;
import agent.PositionAgent;
import perceptron.Perceptron;
import perceptron.SparseVector;

public class StrategiePerceptron extends Strategie{

	private PanelPacmanGame panel;
	private int nbActions = 0;
	private Perceptron perceptron;
	
	public StrategiePerceptron(PanelPacmanGame panel) {
		super(panel);
		this.panel = panel;
	}

	@Override
	public AgentAction coup(Agent agent) {
		ArrayList<AgentAction> coups = this.coupsPossible(agent);
		nbActions = coups.size();
		AgentAction meilleureAction = new AgentAction(4);
		double meilleurScore = 0;
		for(int i = 0; i < nbActions; ++i) {
			SparseVector s = encodageEtatAction(encodeEtat(agent.getPosition()), coups.get(i));
			perceptron = new Perceptron(0.1,10,s,nbActions);
			double score = perceptron.getScore(s);
			if(score > meilleurScore) {
				meilleureAction = coups.get(i);
				meilleurScore = score;
			}
			else if(score == meilleurScore) {
				Random r = new Random();
				int res = r.nextInt(2);
				if(res == 0) {
					meilleureAction = coups.get(i);
					meilleurScore = score;
				}
			}
		}
		return meilleureAction;
	}

	
	public SparseVector encodeEtat(PositionAgent pos) {
		int x = pos.getX();
		int y = pos.getY();
		SparseVector s = new SparseVector(75);
		int cpt = 0;
		for(int i = -2; i <= 2; ++i) {
			for(int j = -2; j <= 2; ++j) {
				if(x+i >= 0 && x+i < panel.getMaze().getSizeX() && y+j >= 0 && y+j < panel.getMaze().getSizeY()) {
					if(this.thereIsSomeone(x+i, y+j)) {
						s.setValue(cpt, 1);
					}
					else if(panel.getMaze().isFood(x+i, y+j)) {
						s.setValue(25+cpt, 1);
					}
					else if(panel.getMaze().isCapsule(x+i, y+j)) {
						s.setValue(50+cpt, 1);
					}
					else {
						s.setValue(cpt, 0);
						s.setValue(25+cpt, 0);
						s.setValue(50+cpt, 0);
					}
				}
				++cpt;
			}
		}
		return s;
	}
	
	public SparseVector encodageEtatAction(SparseVector etat, AgentAction action) {
		SparseVector s = new SparseVector(nbActions * 75 + 1);
		s.setValue(0, 1);
		int depart = 1;
		switch(action.get_direction()) {
			case 0:
				for(int i = depart; i < depart+75; ++i) {
					int cpt = 0;
					s.setValue(i, etat.getValue(cpt));
					++cpt;
				}
				depart += 75;
				break;
			case 1:
				for(int i = depart ; i < depart+75; ++i) {
					int cpt = 0;
					s.setValue(i, etat.getValue(cpt));
					++cpt;
				}
				depart += 75;
				break;
			case 2:
				for(int i = depart; i < depart+75; ++i) {
					int cpt = 0;
					s.setValue(i, etat.getValue(cpt));
					++cpt;
				}
				depart += 75;
				break;
			case 3:
				for(int i = depart; i < depart+75; ++i) {
					int cpt = 0;
					s.setValue(i, etat.getValue(cpt));
					++cpt;
				}
				depart += 75;
				break;
			case 4:
				for(int i = depart; i < depart+75; ++i) {
					int cpt = 0;
					s.setValue(i, etat.getValue(cpt));
					++cpt;
				}
				depart += 75;
				break;
		}
		return s;
	}
	
	public boolean thereIsSomeone(int x, int y) {
		ArrayList<PositionAgent> positions = panel.getGhosts_pos();
		for(PositionAgent pos : positions) {
			if(pos.getX()==x && pos.getY()==y)
				return true;
		}
		return false;
	}
	
	public Perceptron getPerceptron() {
		return perceptron;
	}
}
