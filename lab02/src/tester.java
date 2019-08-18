public class tester 
{
	static KnapsackImp myknapsack = new KnapsackImp();
	
	public static void main(String[] args)
	{
		int values[] = new int[] {2, 41, 13, 39, 39, 28, 29, 20, 32, 46, 12, 43, 42, 3, 14, 45, 45, 4, 33, 33};
		int weights[] = new int[] {32, 36, 19, 35, 4, 35, 26, 1, 31, 19, 8, 29, 2, 6, 12, 7, 20, 12, 44, 50};
		int capacity = 138;
		
		int result = myknapsack.discreteKnapsack(weights, values, capacity);
		int fract = myknapsack.fractionalKnapsack(weights, values, capacity);
		
		System.out.println(result);
		System.out.println(fract);
	}
}
