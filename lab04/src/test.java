import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class test 
{
	public static void main(String[] args) throws FileNotFoundException
	{
		Scanner sc = new Scanner(new File("C:\\Users\\syste\\OneDrive - systemvaz\\Education\\UWA\\2019\\S2\\CITS3001 - AAAI\\CITS3001\\lab04\\src\\corncob_caps.txt"));
		List<String> lines = new ArrayList<String>();
		
		WordChessImp chess = new WordChessImp();
		
		String start = "SICK";
		String end = "WELL";
		
		while (sc.hasNextLine()) 
		{
		  lines.add(sc.nextLine());
		}

		String[] dictionary = lines.toArray(new String[lines.size()]);
		
		String[] result = chess.findPath(dictionary, start, end);
		System.out.println("Finished!");
		for(String word : result)
		{
			System.out.println(word);
		}
		
		sc.close();
		
	}
}
