import java.util.*;

public class WordChessImp implements WordChess
{	
	@Override
	public String[] findPath(String[] dictionary, String startWord, String endWord) 
	{
		String[] none = {};
		String currentWord = "";
		Queue<String> ourQueue = new LinkedList<>();
		LinkedList<String> adjCheck = new LinkedList<String>();
		LinkedList<String> returnStrings = new LinkedList<String>();
		LinkedList<String> ourDict = new LinkedList<String>();
		
		ourQueue.add(startWord);
		returnStrings.add(startWord);
		
		//Cull dictionary to only words of length startWord
		//Return if start and end word are not same length
		if(startWord.length() == endWord.length()) 
		{
			for(String word : dictionary)
			{
				if(word.length() == startWord.length())
				{
					ourDict.add(word);
				}
			}
		}
		else return none;
		
		while(!ourQueue.isEmpty())
		{
			//Pop next word from our queue
			currentWord = ourQueue.poll();
			
			for(String dictWord : ourDict)
			{
				//If we haven't visited this dictionary word already
				if(!returnStrings.contains(dictWord)) 
				{			
					//Check if dictionary word is adjacent to queue word
					int adj = 0;
					for(int i = 0; i < currentWord.length(); i++)
					{
						if(currentWord.charAt(i) != dictWord.charAt(i))
						{
							adj++;
						}
					}
					
					if(adj == 1)
					{
						adjCheck.add(dictWord);
					}		
				}
			}
			
			//Calculate cost. Add word with lowest cost to queue.
			//Return if end word is in this check
			int bestLoss = Integer.MAX_VALUE;
			String bestWord = "";
			for (String adjWord : adjCheck)
			{	
				int startDistance = 0;
				int endDistance = 0;
				
				//If the current dictionary word equals our end word we have finished
				if(adjWord.equals(endWord))
				{
					returnStrings.add(adjWord);
					return returnStrings.toArray(new String[returnStrings.size()]);
				}
				
				for(int i = 0; i < adjWord.length(); i++)
				{
					//Calculate difference between adjacent word and current word
					int difference = adjWord.charAt(i) - currentWord.charAt(i);
					if(difference < 0)
					{
						difference += 26;
					}
					startDistance += difference;
					//Calculate difference adjacent word and end word
					difference = adjWord.charAt(i) - endWord.charAt(i);
					if(difference < 0)
					{
						difference += 26;
					}
					endDistance += difference;
				}
				
				//Final cost calculation
				int cost = endDistance - startDistance;
				if(cost < bestLoss)
				{
					bestLoss = cost;
					bestWord = adjWord;
				}
			}
			
			//Add best word to queue
			ourQueue.add(bestWord);
			returnStrings.add(bestWord);			
		}	
		
		//We didn't find a path, return empty array
		return none;
	}

}
