
import java.util.*;

public class WordChessImp2 implements WordChess
{

	@Override
	public String[] findPath(String[] dictionary, String startWord, String endWord) 
	{
		int dictCount = 0;
		String[] none = {};
		Queue<String> ourQueue = new LinkedList<>();
		LinkedList<String> returnStrings = new LinkedList<String>();
		LinkedList<String> ourDict = new LinkedList<String>();
		
		ourQueue.add(startWord);
		returnStrings.add(startWord);
		
		if(startWord.length() == endWord.length()) 
		{
			for(String word : dictionary)
			{
				if(word.length() == startWord.length())
				{
					ourDict.add(word);
					dictCount++;
				}
			}
		}
		else return none;
		
		int[] distance = new int[dictCount];
		Boolean[] shortestPath = new Boolean[dictCount];
		
		for(int i = 0; i <= dictCount; i++)
		{
			distance[i] = Integer.MAX_VALUE;
			shortestPath[i] = false;
		}
		
		int startLocation = Arrays.asList(ourDict).indexOf(startWord);
		distance[startLocation] = 0;
		
		while(!ourQueue.isEmpty()) 
		{
			String currentWord = ourQueue.poll();
			for(String dictWord : ourDict)
			{
				//Adjacency check
				int adjCheck = 0;
				for (int i = 0; i < currentWord.length(); i ++)
				{
					if(currentWord.charAt(i) != dictWord.charAt(i))
					{
						adjCheck++;
					}
				}
			}
			
			if(adjCheck == 1)
			{
				
			}
			
		}
		
		
		return null;
	}

}
