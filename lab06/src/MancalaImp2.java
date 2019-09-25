import java.util.Arrays;

import MancalaImp.gameNode;

public class MancalaImp2 implements MancalaAgent
{
	public Node root;
	
	
	@Override
	public int move(int[] board) 
	{		
		root = new Node(board, 6);
		buildTree(root, 2);
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
	
	
	public static void buildTree(Node n, int height)
	{	
		if(height != 0)
		{		
			int[] board = n.board.clone();
			for(int i = 0; i < 6; i++)
			{
				int count = i;
				int seeds = board[i];
				board[i] = 0;
				while(seeds != 0)
				{
					count++;
					board[count] = board[count] + 1;
					seeds--;
				}
				Node child = new Node(board, 6);
				n.setChild(i, child);
				buildTree(n.getChild(i), height-1);
			}
		}
		else
		{
			return;
		}

	}
	
	public void printTree(Node node, String appender) 
	{
		System.out.println(appender + Arrays.toString(node.board));
		for(int i = 0; i < 6; i++)
		{
			while(node.getChild(i) != null)
			{
				printTree(node.getChild(i), appender + appender);
			}
		}
	}
	
	
	//Inner class defining our Node structure to build tree with
	public static class Node
	{
		private int[] board;
		private Node[] children;
		
		public Node(int[] gameBoard, int numChildren)
		{
			this.board = gameBoard.clone();
			this.children = new Node[numChildren];
		}
		
		public Node getChild(int i)
		{
			return children[i];
		}
		
		public void setChild(int i, Node n)
		{
			this.children[i] = n;
		}
		
		public int numChildren()
		{
			return children.length;
		}
		
		public int[] getBoard()
		{
			return this.board;
		}
	}

}
