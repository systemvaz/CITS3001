public class sort 
{

	public static int[] isort(int[] our_array)
	{
		int size = our_array.length;	
		for(int i = 1; i < size; i++)
		{
			int j = i - 1;
			int window = our_array[i];
			while(our_array[j] >= 0 && our_array[j] > window)
			{
				our_array[i] = our_array[j];
			}
			our_array[j] = window;
		}
		return our_array;
	}
	
}
