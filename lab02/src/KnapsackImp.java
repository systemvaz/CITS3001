import java.util.*;

public class KnapsackImp implements Knapsack
{
	public int discreteKnapsack(int[] weights, int[] values, int capacity)
	{
		int numVals = values.length;
		int ourArray[][] = new int[numVals+1][capacity+1];
		
		for(int i = 0; i <= numVals; i++)
		{
			for(int w = 0; w <= capacity; w++)
			{
				if(i == 0 || w == 0)
				{
					ourArray[i][w] = 0;
				}
				else if(weights[i-1] <= w)
				{
					ourArray[i][w] = Math.max(values[i-1] + ourArray[i-1][w-weights[i-1]], ourArray[i-1][w]);
				}
				else
				{
					ourArray[i][w] = ourArray[i-1][w]; 
				}
			}
		}		
		return ourArray[numVals][capacity];
	}

	@Override
	public int fractionalKnapsack(int[] weights, int[] values, int capacity) 
	{
		ourItems theItems[] = new ourItems[values.length];
		//Add everything to our helper object including ratio of each
		for(int i = 0; i < values.length; i++)
		{
			@SuppressWarnings("deprecation")
			Float ratio = ((float)values[i]/(float)weights[i]);
			theItems[i] = new ourItems(weights[i], values[i], ratio);
		}
		
		//Sort object by ratio
		Arrays.sort(theItems, new Comparator<ourItems>()
		{
			public int compare(ourItems first, ourItems second)
			{
				return second.ratio.compareTo(first.ratio);
			}
		});
		
		double result = 0d;
		
		for(ourItems i : theItems)
		{
			if(capacity - i.weight >= 0)
			{
				//Fit whole amount
				capacity -= i.weight;
				result += i.value;
			}
			else
			{
				//Fit a fractional amount
				double fractional = (double)capacity/(double)i.weight;
				result += (i.value*fractional);
				capacity = (int)(capacity - (i.weight*fractional));
				break;
			}
		}
		
		return (int)result;
	}
	
	//Helper class
	public class ourItems
	{
		Float ratio;
		double weight, value;
		public ourItems(int weight, int value, Float ratio)
		{
			this.weight = weight;
			this.value = value;
			this.ratio = ratio;
		}
	}
}
