import java.util.*;


public class MancalaImp3 implements MancalaAgent
{
	Node root;
	int depth;
	Stack<Node> myStack;
	
	@Override
	public int move(int[] board) 
	{
		int move = 0;
		depth = 2;
		root = new Node(null, 0, false, false, 0);
		myStack = new Stack<Node>();
		
		root.setBoard(board);
		buildTree(root, depth, true, 1);
//		printTree(root, " ");
		
		createStack(root);
//		while(!myStack.isEmpty())
//		{
//			Node n = myStack.pop();
//			System.out.println(Arrays.toString(n.board) + " |Player: " + n.player);
//		}
		move = evaluateMoves(depth);
		System.out.println("Move: " + move);
		
		return move;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}
	
	public void buildTree(Node n, int height, boolean isMax, int branch)
	{
		int[] board = n.getBoard();
		int checkmax = 0;
		int checkmin = 0;
		
		for(int i = 0; i < board.length; i++)
		{
			if(i < 6) {if(board[i] == 0) {checkmax++;}}
			if(i > 6) {if(board[i] == 0) {checkmin++;}}
		}
		
		if(height != 0 && checkmax != 6 && checkmin != 6)
		{			
			if(isMax)
			{
				playMax(n, board, height, isMax, branch);
			}
			else
			{
				playMin(n, board, height, isMax, branch);
			}
		}
	}
	
	public void playMax(Node n, int[] board, int height, boolean isMax, int branch)
	{
		for(int i = 0; i < 6; i++)
		{
			int count = i;
			int seeds = board[i];
			if(seeds != 0 )
			{
				int[] tempboard = board.clone();
				tempboard[i] = 0;
				while(seeds != 0)
				{
					count++;
					tempboard[count] = tempboard[count] + 1;
					seeds--;
				}
				boolean freeMove = false;
				boolean stealMove = false;
				if(tempboard[count] - 1 == 0) {stealMove = true;}
				if(count == 6) {freeMove = true;}
				isMax = false;
				Node child = addChild(n, tempboard, 1, freeMove, stealMove, i);
				buildTree(child, height-1, isMax, branch);	
			}
		}	
	}
	
	public void playMin(Node n, int[] board, int height, boolean isMax, int branch)
	{
		for(int i = 7; i < 13; i++)
		{
			int count = i;
			int seeds = board[i];
			if(seeds != 0)
			{
				int[] tempboard = board.clone();
				tempboard[i] = 0;
				while(seeds != 0)
				{
					count++;
					count = count % 14;
					tempboard[count] = tempboard[count] + 1;
					seeds--;
				}
				boolean freeMove = false;
				boolean stealMove = false;
				if(tempboard[count] - 1 == 0) {stealMove = true;}
				if(count == 13)	{freeMove = true;}
				isMax = true;
				Node child = addChild(n, tempboard, 0, freeMove, stealMove, i);
				buildTree(child, height-1, isMax, branch);
			}
		}
	}
	
	public void createStack(Node nodes)
	{
		myStack.push(nodes);
		for(Node node : nodes.getChildren())
		{
			createStack(node);
		}
	}
	
	public int evaluateMoves(int playDepth)
	{
		int min = 99999;
		int max = -99999;
		int maxmove = 0;
		int maxcount = 0;
		int[] maxvals = {0, 0, 0, 0, 0, 0};
		
		while(!myStack.isEmpty())
		{
			int eval = 0;
			Node currNode = myStack.pop();
			if(currNode.getParent() == null)
			{
				for(int i = 0; i < 6; i++)
				{
					int maxcheck = maxvals[i];
					if(maxcheck > max) {maxmove = i;}
				}
				return maxmove;
			}
			if(currNode.player == 0)
			{
				eval -= (currNode.board[13] * 10);
				if(currNode.freeMove == true)
				{
					eval -= 50; 
				}
				if(currNode.stealMove == true)
				{
					eval -= 100;
				}
			}
			if(eval < min) 
			{
				min = eval;
				System.out.println("Min agent chooses: " + min);
			}
			System.out.println("eval: " + eval + " | min-eval: " + min);

			if(currNode.player == 1)
			{
				if(min > max) 
				{
					max = Math.max(min, max);
					maxmove = currNode.move;
					System.out.println("Depth: " + playDepth);
					playDepth--;
				}	
			}
			
			if(currNode.getParent().parent == null)
			{
				maxvals[maxcount] = max;
				maxcount++;
				max = -99999;
				min = 99999;
				System.out.println("MAX VALS: " + Arrays.toString(maxvals));
			}
		}
		
		System.out.println("Max value: " + max);
		return maxmove;
	}
	
	public Node addChild(Node parent, int[] board, int player, boolean freeMove, boolean stealMove, int move)
	{
		Node node = new Node(parent, player, freeMove, stealMove, move);
		node.setBoard(board);
		parent.getChildren().add(node);
		return node;
	}
	
	 public void printTree(Node node, String appender) 
	 {
		System.out.println(appender + Arrays.toString(node.getBoard()) + " |Player: " + node.player + " |FreeMove: " + node.freeMove);
		for (Node each : node.getChildren()) 
		{
		   printTree(each, appender + appender);
		}
	 }
	
	public class Node
	{
		private int[] board;
		private final List<Node> children = new ArrayList<>();
		private final Node parent;
		private final int player;
		private final int move;
		private final boolean freeMove;
		private final boolean stealMove;
		
		public Node(Node parent, int player, boolean freeMove, boolean stealMove, int move)
		{
			this.parent = parent;
			this.player = player;
			this.move = move;
			this.freeMove = freeMove;
			this.stealMove = stealMove;
		}
		
		public int[] getBoard()
		{
			return board;
		}
		
		public void setBoard(int[] board)
		{
			this.board = board.clone();
		}
		
		public List<Node> getChildren()
		{
			return children;
		}
		
		public Node getParent()
		{
			return parent;
		}
	}

}
