
import java.util.*;

public class MancalaImp4 implements MancalaAgent 
{
	public Node root;
	int depth;
	int branch;

	@Override
	public int move(int[] board) 
	{
		// TODO Auto-generated method stub
		depth = 2;
		branch = (board.length-2) / 2;
		root = new Node(null);
		
		root.setBoard(board);
		root = buildTree(root, depth, branch, true);	
		printTree(root, " ");
		
		return 0;
	}

	@Override
	public String name() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void reset() 
	{
		// TODO Auto-generated method stub
		
	}
	
	public Node buildTree(Node n, int height, int branch, boolean isMax)
	{
		Node child = null;
		int[] board = n.board;
		
		if(height != 0)
		{
			for(int i = 0; i < branch; i++)
			{
//				int[] board = {i, height};				
				int count = i;
				int seeds = board[i];

				if(seeds != 0)
				{
					board[i] = 0;
					while(seeds != 0)
					{
						count++;
						board[count] += 1;
						seeds--;
					}
					child = addChild(n, board);
					System.out.println(Arrays.toString(child.board));
					buildTree(child, height-1, branch, true);
				}
			}
			buildTree(root, height-1, branch, true);
		}

		return root;
	}
	
	
	//Helpers methods and class below.....................
	 public void printTree(Node node, String appender) 
	 {
		System.out.println(appender + Arrays.toString(node.getBoard()));
		for (Node each : node.getChildren()) 
		{
		   printTree(each, appender + appender);
		}
	 }
	
	public Node addChild(Node parent, int[] board)
	{
		Node node = new Node(parent);
		node.setBoard(board);
		parent.getChildren().add(node);
		return node;
	}
	
	//Inner class for parent child node structure.....
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
