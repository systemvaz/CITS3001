package agents;
import java.util.ArrayList;
import java.util.Random;
import java.util.Arrays;
import java.util.List;
import loveletter.*;

public class MCTSAgent implements Agent
{
	private Node rootNode;
	private State currState;
	private int myIndex;
	
	@Override
	public void newRound(State start) 
	{
		currState = start;
		myIndex = currState.getPlayerIndex();
	}

	@Override
	public void see(Action act, State results) 
	{
		currState = results;
	}
	
	public String toString() {return "systemvaz";}

	@Override
	public Action playCard(Card c) 
	{
		rootNode = new Node(null, null, currState.get(), -1);
		
		//Get system time and set runtime to 5 seconds
		long time = System.currentTimeMillis();
		long end = time + 5000;
		Card cardSelection = null;
		Action returnAction = null;
		
		//Add our two playable cards as children of the root node.
		Card cardOne = c;
		Card cardTwo = currState.getCard(myIndex);
		addChild(rootNode, cardOne, currState.get(), 0);
		addChild(rootNode, cardTwo, currState.get(), 0);
		
		//Performs MCTS within our designated run time of 5 seconds
		while(System.currentTimeMillis() < end)
		{
			//Select the next node based on max UCB1
			System.out.println("Performing Selection....");
			Node selection = mctsSelection(rootNode);
			
			//If selected node has not been simulated, perform now.
			boolean win = false;
			if(selection.numSims == 0) 
			{
				//Simulate and record if we win.
				System.out.println("Performing Simulation....");
				win = mctsSimulation(selection);
				//After simulation, perform back propagation of results.
				System.out.println("Performing Backprop....");
				mctsUpdate(selection, win);
			}
			
			//If selected node already simulated, expand the node with possible moves.
			else 
			{
				System.out.println("Performing Expansion....");
				mctsExpansion(selection);
			}	
		}
		
		//Calculate which rootNode child card to play based on max average simulation/wins.
		System.out.println("Performing Card Selection....");
		cardSelection = selectCard();
		System.out.println(cardSelection.name());
		
		//Determine play based on card
		System.out.println("Performing Action Determination....");
		returnAction = selectAction(cardSelection);
		
		System.out.println("RETURNING OUR ACTION !!!");
		return returnAction;
	}
	
	
	public Node mctsSelection(Node rootNode)
	{
		Node currNode, nextNode;
		Double maxUCB, nodeUCB; 	

		maxUCB = 0.0;
		nextNode = null;
		currNode = rootNode;
		
		//While the current node is not a leaf node
		while(currNode.getChildren().size() != 0)
		{
			//For each node calculate the UCB1. Record node with max UCB1.
			for(Node child : currNode.getChildren())
			{
				//Only calculate the UCB if values required aren't 0
				if(child.numSims != 0) {nodeUCB = calcUCB(child);}
				else {nodeUCB = 99999.0;}
				//Did we find node with bigger UCB1?
				if(nodeUCB > maxUCB) 
				{
					maxUCB = nodeUCB;
					nextNode = child;
				}
			}	
			//Current node becomes node with max UCB. If none, select first child.
			if(nextNode != null) {currNode = nextNode;}
			else {currNode = currNode.getChildren().get(0);}
		}	
		
		//Leaf node with highest UCB1 reached. Return this node.
		return currNode;
	}
	
	public double calcUCB(Node node)
	{
		//Calculate UCB1 for this node.
		System.out.println("Calculating UCB1");
		int totalSims = rootNode.numSims;
		int nodeSims = node.numSims;
		double avg = node.numWins/node.numSims;
		double ucb = avg + (Math.sqrt((2 * Math.log(totalSims)) / nodeSims));
		
		return ucb;
	}
	
	public void mctsExpansion(Node node)
	{
		//Keep record of card types added as nodes already.
		List<Card> visited = new ArrayList<>();
		int player = 0;
		
		//Set the current player for this node.
		if(node.player < 3) {player = node.player + 1;}
		else {player = 0;}
		
		//For all unique card types available in state, create a new node
		for(Card card : node.state)
		{
			if(card != null && visited.indexOf(card) == -1)
			{
				System.out.println("Creating new state in expansion");
				//Create a new state with this card removed
				visited.add(card);
				Card[] newState = node.state.clone();
				int remove = Arrays.asList(newState).indexOf(card);
				newState[remove] = null;
				//Expand a new child node with this card and the new state
				addChild(node, card, newState, player);
			}
		}
	}
	
	public boolean mctsSimulation(Node node)
	{
		ArrayList<Card> currCards = new ArrayList<Card>();
		ArrayList<int[]> playerCards = new ArrayList<int[]>();
		Random randomGen = new Random();
		int currPlayer = 0;
		
		int[] cardsHeld = {0,0};
		for(int i = 0; i < 4; i++) {playerCards.add(cardsHeld);}
		
		//Select our next player
		if(node.player < 3) {currPlayer = node.player + 1;}
		else {currPlayer = 0;}
		
		//Get all playable cards
		for(Card card : node.getState())
		{
			if(card != null) {currCards.add(card);}
		}
		
		//While there is more than one card remaining, simulate play for each player.
		while(currCards.size() > 0)
		{
			//If player has no cards, assign one first
			int[] hand = playerCards.get(currPlayer);
			if(hand[0] == 0)
			{
				int pickFirstCard = randomGen.nextInt(currCards.size());
				Card cardOne = currCards.remove(pickFirstCard);
				hand[0] = cardOne.value();
			}
			
			if(currCards.size() == 0) {break;}
			
			//Assign player the second card
			int pickSecondCard = randomGen.nextInt(currCards.size());
			Card cardTwo = currCards.remove(pickSecondCard);
			hand[1] = cardTwo.value();
			
			//Play the card with the smallest value. Keep biggest.
			hand[0] = Math.max(hand[0],  hand[1]);
			hand[1] = 0;
			playerCards.set(currPlayer,  hand);
			
			//Iterate to next player
			if(currPlayer < 3) {currPlayer = currPlayer + 1;}
			else {currPlayer = 0;}			
		}
		
		//Game finished. Find out if we won the round
		boolean weWin = true;
		int[] ourScore = playerCards.get(0);
		for(int i = 1; i < 4; i++)
		{
			int[] checkPlayer = playerCards.get(i);
			if(checkPlayer[0] > ourScore[0]) {weWin = false;}
		}
		
		return weWin;
	}
	
	public void mctsUpdate(Node node, boolean win)
	{
		//Set number of wins and simulations on bottom node.
		node.numSims = node.numSims + 1;
		if(win == true) {node.numWins = node.numWins + 1;}
		
		//Back propagate, setting wins and simulations up to the root note
		while(node.getParent() != null)
		{
			node = node.getParent();
			node.numSims = node.numSims + 1;
			if(win == true) {node.numWins = node.numWins + 1;}
		}
	}
	
	
	public Card selectCard()
	{
		double maxAverage = 0;
		Card returnCard = null;
		
		//Return the card with the highest simulation/win average
		//We will never return the Princess however.
		for(Node n : rootNode.getChildren())
		{
			double avg = n.numWins / n.numSims;
			if(avg >= maxAverage && !n.playingCard.name().equals("PRINCESS")) 
			{
				maxAverage = avg;
				returnCard = n.playingCard;
			}
		}
		
		return returnCard;
	}
	
	public Action selectAction(Card cardSelection)
	{
		Action returnAction = null;
		Random randomGen = new Random();
		
		while(!currState.legalAction(returnAction, cardSelection))
		{
			int target = randomGen.nextInt(currState.numPlayers());
			//Pick action to perform based on card.
		      try{
		          switch(cardSelection)
		          {
		            case GUARD:
		            	returnAction = Action.playGuard(myIndex, target, Card.values()[randomGen.nextInt(7)+1]);
		            	break;
		            case PRIEST:
		            	returnAction = Action.playPriest(myIndex, target);
		            	break;
		            case BARON:
		            	returnAction = Action.playBaron(myIndex, target);
		            	break;
		            case HANDMAID:
		            	returnAction = Action.playHandmaid(myIndex);
		            	break;
		            case PRINCE:
		            	returnAction = Action.playPrince(myIndex, target);
		            	break;
		            case KING:
		            	returnAction = Action.playKing(myIndex, target);
		            	break;
		            case COUNTESS:
		            	returnAction = Action.playCountess(myIndex);
		            	break;
		            default:
		            	returnAction = null;//never play princess
		          }
		        }
		      catch(IllegalActionException e) {/*do nothing, just try again*/}  
		}
				
		return returnAction;
	}
	
	public Node addChild(Node parent, Card playingCard, Card[] state, int player)
	{
		Node node = new Node(parent, playingCard, state, player);
		parent.getChildren().add(node);
		return node;
	}
	
	public class Node
	{
		private int player;
		private int numWins;
		private int numSims;
		private final Node parent;
		private final Card[] state;
		private final Card playingCard;
		private final List<Node> children = new ArrayList<>();
		
		public Node(Node parent, Card playingCard, Card[] state, int player)
		{
			this.numWins = 0;
			this.numSims = 0;
			this.state = state;
			this.parent = parent;
			this.player = player;
			this.playingCard = playingCard;
		}
		
		public void setWin() {this.numWins = this.numWins + 1;}
		
		public void setSim() {this.numSims = this.numSims + 1;}
		
		public Node getParent()	{return parent;}
		
		public Card[] getState() {return state;}
		
		public Card getCard() {return playingCard;}
		
		public List<Node> getChildren()	{return children;}
	}

}
