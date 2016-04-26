package AI.Logic;

import AI.GameRules;
import lenz.htw.aipne.Move;

public class GameTreeAI implements AILogic {
	private int[] board;
	//private BoardEvaluation evaluation;
	private int playerNum;
	private int maxMoveLength = 8*2*4;
    private int DEPTH = 3;
    
    /*
    public GameTreeAI(int depth, BoardEvaluation evaluation) {
		this.DEPTH = depth;
		this.evaluation = evaluation;
	}*/
    
    public GameTreeAI(int depth) {
		this.DEPTH = depth;
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
		int[] moves = new int[maxMoveLength];
		int[] bestMove = new int[4];
		
		GameRules.getAllLegalMovesPlayer(moves, this.board, this.playerNum);
		
		for (int i = 0; i < maxMoveLength; i+=4) {
			// Uninitialized move, reached end of possible moves 
			if (moves[i+0] == 0 && moves[i+2] == 0) {
				System.out.println("Player " + this.playerNum + " found moves: " + i/4);
				break;
			}
			
			int[] nextBoard = GameRules.applyMove(this.board, moves[i+0], moves[i+1], moves[i+2], moves[i+3]);
			
			int score = miniMax(nextBoard, this.DEPTH, (this.playerNum + 1) % 2);
			if (score >= bestScore) {
				bestScore = score;
				bestMove[0] = moves[i+0];
				bestMove[1] = moves[i+1];
				bestMove[2] = moves[i+2];
				bestMove[3] = moves[i+3];
			}
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
		
		int[] moves = new int[maxMoveLength];
		GameRules.getAllLegalMovesPlayer(moves, board, player);
		
		// First move is uninitialized, no legal moves left
		if (moves[0] == 0 && moves[2] == 0) {
			return score(board);
		}
		
		int maxScore = 0;
		if (player == this.playerNum) {
			maxScore = Integer.MIN_VALUE;
		} else {
			maxScore = Integer.MAX_VALUE;
		}
		
		for (int i = 0; i < maxMoveLength; i+=4) {
			int[] nextBoard = GameRules.applyMove(board, moves[i+0], moves[i+1], moves[i+2], moves[i+3]);
			int score = miniMax(nextBoard, depth -1, (playerNum + 1) % 2);
			if (player == this.playerNum) {
				maxScore = score > maxScore ? score : maxScore;
			} else {
				maxScore = score < maxScore ? score : maxScore;
			}
		}
		
		return maxScore;
	}
}
