import java.util.*;


public class MancalaImp3 implements MancalaAgent
{

	Node root;
	int depth;
	
	@Override
	public int move(int[] board) {
		// TODO Auto-generated method stub
		depth = 1;
		root = new Node(null);
		
		root.setBoard(board);
		buildTree(root, depth, true, 1);
		
		return 0;
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
			if(i < 7)
			{
				if(board[i] == 0)
				{
					checkmax++;
				}
			}
			if(i > 7)
			{
				if(board[i] == 0)
				{
					checkmin++;
				}
			}
		}
		
		if(height != 0 && checkmax != 6 && checkmin != 6)
		{			
			if(isMax)
			{
				for(int i = 0; i < 6; i++)
				{
					int count = i;
					int seeds = board[i];
					if(seeds != 0 )
					{
						board[i] = 0;
						while(seeds != 0)
						{
							count++;
							board[count] = board[count] + 1;
							seeds--;
						}
						System.out.println("Adding MAX child at Depth " + height);
						System.out.println(Arrays.toString(board));
						Node child = addChild(n, board);
						isMax = false;
//						buildTree(child, height-1, isMax, branch);	
					}
				}		
			}
			else
			{
				for(int i = 7; i < 13; i++)
				{
					int count = i;
					int seeds = board[i];
					if(seeds != 0)
					{
						board[i] = 0;
						while(seeds != 0)
						{
							count++;
							count = count % 14;
							board[count] = board[count] + 1;
							seeds--;
						}
						System.out.println("Adding Min child at Depth" + height);
						System.out.println(Arrays.toString(board));
						Node child = addChild(n, board);
						isMax = true;
						buildTree(child, height-1, isMax, branch);
					}
				}
			}
		}
		else if(height == 0)
		{
			if(branch < 7)
			{
				buildTree(root, depth, true, branch+1);	
			}
		}
	}
	
	public Node addChild(Node parent, int[] board)
	{
		Node node = new Node(parent);
		node.setBoard(board);
		parent.getChildren().add(node);
		return node;
	}
	
	 public void printTree(Node node, String appender) 
	 {
		System.out.println(appender + Arrays.toString(node.getBoard()));
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
		
		public Node(Node parent)
		{
			this.parent = parent;
		}
		
		public int[] getBoard()
		{
			return board;
		}
		
		public void setBoard(int[] board)
		{
			this.board = board;
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
