
import java.util.*;

public class test
{
	
	public static List<Integer> naive(String ourinput, String ourpattern)
	{
		int inputlen = ourinput.length();
		int patternlen = ourpattern.length();
		int searchlen = inputlen - patternlen;
		boolean match;
		
		List<Integer> result = new ArrayList<Integer>();
		
		for (int i = 0; i <= searchlen; i++)
		{
			match = true;
			for (int j = 0; j < patternlen; j++)
			{
				if (ourinput.charAt(i + j) != ourpattern.charAt(j))
				{
					match = false;
				}
			}
			
			if (match == true)
			{
				result.add(i);
			}
		}
		
		return result;
	}
	
	
	public static List<Integer> rabinkarp(String ourinput, String ourpattern)
	{
		int inputlen = ourinput.length();
		int patternlen = ourpattern.length();
		int searchlen = inputlen - patternlen;
		int patternint = 0;
		int shiftint = 0;
		
		List<Integer> result = new ArrayList<Integer>();
		
		for (int j = 0; j <= patternlen-1; j++)
		{
			patternint = patternint * 10 + ourpattern.charAt(j);
			//System.out.println(patternint);
		}
		
		for (int j = 0; j <= patternlen-2; j++)
		{
			shiftint = shiftint * 10 + ourinput.charAt(j);
			//System.out.println(shiftint);
		}
		
		for (int i = 0; i < searchlen; i++)
		{
			shiftint += (int) (shiftint % Math.pow(10, patternlen-1) * 10 + ourinput.charAt(i + patternlen - 1));
			//System.out.println(shiftint);
			
			if (shiftint == patternint)
			{
				result.add(i);
			}
		}
		
		return result;
	}
	
	
	
	public static void main(String[] args)
	{
		String in = "abaaabacccaabbaccaababacaababaac";
		String ptn = "aab";
		
		System.out.println(naive(in, ptn));
		System.out.println(rabinkarp(in, ptn));
	}
}
