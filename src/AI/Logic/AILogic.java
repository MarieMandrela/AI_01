package AI.Logic;

import lenz.htw.aipne.Move;

public interface AILogic {
	
	void setPlayerNum(int playerNum);
	
	void setGameBoard(int[] board);
	
	void observeMove(Move move);
	
	Move getMove();
	
}
