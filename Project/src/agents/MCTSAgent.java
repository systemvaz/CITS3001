package agents;
import java.util.ArrayList;
import java.util.List;
import loveletter.*;

public class MCTSAgent implements Agent
{
	private Node rootNode;
	private State currState;
	private int ourIndex;
	
	@Override
	public void newRound(State start) 
	{
		currState = start;
		ourIndex = currState.getPlayerIndex();
		rootNode = new Node(null, currState, null);
	}

	@Override
	public void see(Action act, State results) 
	{
		currState = results;
	}

	@Override
	public Action playCard(Card c) 
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public void mctsSelection()
	{
		
	}
	
	public void mctsExpansion()
	{
		
	}
	
	public void mctsSimulation()
	{
		
	}
	
	public void mctsUpdate()
	{
		
	}
	
	public Node addChild(Node parent, State state, Action action)
	{
		Node node = new Node(parent, state, action);
		parent.getChildren().add(node);
		return node;
	}
	
	public class Node
	{
		private int numWins;
		private int numSims;
		private final Node parent;
		private final State state;
		private final Action action;
		private final List<Node> children = new ArrayList<>();
		
		public Node(Node parent, State nodeState, Action action)
		{
			this.numWins = 0;
			this.numSims = 0;
			this.parent = parent;
			this.action = action;
			this.state = nodeState;
		}
		
		public void setWin() {this.numWins = this.numWins + 1;}
		
		public void setSim() {this.numSims = this.numSims + 1;}
		
		public State getState() {return state;}
		
		public Action getAction() {return action;}
		
		public Node getParent()	{return parent;}
		
		public List<Node> getChildren()	{return children;}
	}

}
