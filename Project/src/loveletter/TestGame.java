package loveletter;
import agents.*;

public class TestGame 
{
	public void main(String args[])
	{
		RandomAgent r1 = new RandomAgent();
		RandomAgent r2 = new RandomAgent();
		RandomAgent r3 = new RandomAgent();
		RandomAgent r4 = new RandomAgent();
		Agent[] agents = {r1, r2, r3, r4};
		
		LoveLetter ourGame = new LoveLetter();
		
		ourGame.playGame(agents);
	}
}
