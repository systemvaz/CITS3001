
import java.util.*;

public class tester 
{
	public static void main(String[] args)
	{
		MancalaImp3 mancala = new MancalaImp3();
		int[] startGame = {3,3,3,3,3,3,0,3,3,3,3,3,3,0};
		mancala.move(startGame);
	}
}
