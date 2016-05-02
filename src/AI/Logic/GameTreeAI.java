package AI.Logic;

import java.util.concurrent.ThreadLocalRandom;

import AI.GameRules;
import lenz.htw.aipne.Move;

public class GameTreeAI implements AILogic {
	private int[] board;
	private int playerNum;
	
	private int MAXMOVELENGTH = 8*2*4;
	private int DEPTH = 3;
    private double EPSILON = 0.0;
    
    public GameTreeAI(int depth, double epislon) {
		this.DEPTH = depth;
		this.EPSILON = epislon;
	}
    
    public void setEpsilon(double epsilon) {
    	this.EPSILON = epsilon;
    }

	public void setDepth(int depth) {
    	this.DEPTH = depth;
    }
    
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
		int bestScore = Integer.MIN_VALUE;
		int[] moves = new int[MAXMOVELENGTH];
		int[] bestMove = new int[4];
		
		GameRules.getAllLegalMovesPlayer(moves, this.board, this.playerNum);
		
		int i = 0;
		for (; i < MAXMOVELENGTH; i+=4) {
			// Uninitialized move, reached end of possible moves 
			if (moves[i+0] == 0 && moves[i+2] == 0) {
				break;
			}
			
			int[] nextBoard = GameRules.applyMove(this.board, moves[i+0], moves[i+1], moves[i+2], moves[i+3]);
			
			int score = miniMax(nextBoard, this.DEPTH -1, (this.playerNum + 1) % 2);
			if (score >= bestScore) {
				bestScore = score;
				System.arraycopy(moves, i, bestMove, 0, 4);
			}
		}
		
		// Get Random move with epislon probability
		if (ThreadLocalRandom.current().nextDouble(0,1) < this.EPSILON) {
			int r = ThreadLocalRandom.current().nextInt(0, i);
			r = (int)(r/4) * 4;
			System.out.println("Random Player taking " + r/4 + "th move");
			System.arraycopy(moves, r, bestMove, 0, 4);
		}
		
		return new Move(bestMove[0], bestMove[1], bestMove[2], bestMove[3]);
	}
	
	private int score(int[] board) {
		return GameRules.score(board, this.playerNum) - 
				Math.max(GameRules.score(board, (this.playerNum + 1) % 2), GameRules.score(board, (this.playerNum + 2) % 2));
	}
	
	private int miniMax(int[] board, int depth, int player)
	{		
		if (depth <= 0) {
			return score(board);
		} 
		
		int[] moves = new int[MAXMOVELENGTH];
		GameRules.getAllLegalMovesPlayer(moves, board, player);
		
		int maxScore = 0;
		if (player == this.playerNum) {
			maxScore = Integer.MIN_VALUE;
		} else {
			maxScore = Integer.MAX_VALUE;
		}
		
		for (int i = 0; i < MAXMOVELENGTH; i+=4) {
			int[] nextBoard = GameRules.applyMove(board, moves[i+0], moves[i+1], moves[i+2], moves[i+3]);
			int score = miniMax(nextBoard, depth -1, (playerNum + 1) % 2);
			if (player == this.playerNum) {
				maxScore = score > maxScore ? score : maxScore;
			} else {
				maxScore = score < maxScore ? score : maxScore;
			}
			if (moves[i+0] == 0 && moves[i+2] == 0) {
				break;
			}
		}
		
		return maxScore;
	}
}
