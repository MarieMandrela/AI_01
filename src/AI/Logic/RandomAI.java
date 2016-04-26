package AI.Logic;

import java.util.concurrent.ThreadLocalRandom;

import AI.GameRules;
import lenz.htw.aipne.Move;

public class RandomAI implements AILogic {
	private int[] board;
	private int playerNum;
	private int maxMoveLength = 8*2*4;
    
	public void setPlayerNum(int playerNum) {
		this.playerNum = playerNum;
	}

	public void setGameBoard(int[] board) {
		this.board = board;
	}

	public void observeMove(Move move) {
		this.board = GameRules.applyMove(this.board, move.fromX, move.fromY, move.toX, move.toY);
	}

	public Move getMove() {
		int[] moves = new int[maxMoveLength];
		int[] bestMove = new int[4];
		
		GameRules.getAllLegalMovesPlayer(moves, this.board, this.playerNum);
		
		int i = 0;
		for (; i < maxMoveLength; i+=4) {
			// Uninitialized move, reached end of possible moves 
			if (moves[i+0] == 0 && moves[i+2] == 0) {
				System.out.println("Random Player found " + i/4 + " moves");
				break;
			}
		}
		
		int r = ThreadLocalRandom.current().nextInt(0, i);
		r = (int)(r/4) * 4;
		System.out.println("Random Player taking " + r/4 + "th move");
		bestMove[0] = moves[r+0];
		bestMove[1] = moves[r+1];
		bestMove[2] = moves[r+2];
		bestMove[3] = moves[r+3];
		
		return new Move(bestMove[0], bestMove[1], bestMove[2], bestMove[3]);
	}
}
