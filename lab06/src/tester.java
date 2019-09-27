
import java.util.*;

public class tester 
{
	public static void main(String[] args)
	{
		MancalaImp mancala = new MancalaImp();
		RandomAgent random = new RandomAgent();
		Mancala.play(mancala, random);
		
//		int[] startGame = {3,3,3,3,3,3,0,3,3,3,3,3,3,0};
//		mancala.move(startGame);
	}
}
