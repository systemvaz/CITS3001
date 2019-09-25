import java.util.*;

public class MancalaImp implements MancalaAgent
{
	gameNode<int[]> root;
	int maxdepth = 2;
	int depth = 0;

	
	@Override
	public int move(int[] board) 
	{	
		//Create tree with board values
		root = new gameNode<>(board);
		buildTree(root, maxdepth);
		printTree(root, " ");	
		
		return 0;
	}

	@Override
	public String name() 
	{
		return "MyAgent";
	}

	@Override
	public void reset() 
	{
		
	}
	
	public void printTree(gameNode<int[]> node, String appender) 
	{
		System.out.println(appender + Arrays.toString(node.getState()));
		node.getChildren().forEach(each ->  printTree(each, appender + appender));
	}
	
	public void buildTree(gameNode<int[]> node, int maxdepth)
	{		
		if(maxdepth != 0)
		{
			for(int j = 0; j < 6; j++)
			{
				int count = j;
				int[] board = node.getState();
				System.out.println("State: " + Arrays.toString(board));
				int seeds = board[j];
				
				if(board[j] != 0)
				{
					board[j] = 0;
					while(seeds != 0)
					{
						count++;
						board[count] = board[count] + 1;
						seeds--;
					}	
					
					gameNode<int[]> child = node.addChild(new gameNode<int[]>(board));
					buildTree(child, maxdepth-1);
				}	
			}
		}
	}
	
	
	
	//Inner class defining our game tree
	public class gameNode<T>
	{
		private T state = null;
		private gameNode<T> parent = null;
		private List<gameNode<T>> children = new ArrayList<>();
		
		public gameNode(T stateIn)
		{
			this.state = stateIn;
		}
		
		public gameNode<T> addChild(gameNode<T> child)
		{
			child.setParent(this);
			this.children.add(child);
			return child;
		}
		
		public List<gameNode<T>> getChildren()
		{
			return children;
		}
		
		public T getState()
		{
			return state;
		}
		
		public void setState(T newState)
		{
			this.state = newState;
		}
		
		private void setParent(gameNode<T> parent)
		{
			this.parent = parent;
		}
		
		public gameNode<T> getParent()
		{
			return parent;
		}	
	}

}
