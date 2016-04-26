package AI.Logic;

import java.util.concurrent.ThreadLocalRandom;

import AI.GameRules;
import lenz.htw.aipne.Move;

public class AlphaBetaGameTreeAI implements AILogic {
	private int[] board;
	private int playerNum;
	
	private int MAXMOVELENGTH = 8*2*4;
	private int DEPTH = 3;
    private double EPSILON = 0.0;
    
    public AlphaBetaGameTreeAI(int depth, double epislon) {
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
	
	private void scoreAll(int[] board, int[] score) {
		score[0] = GameRules.score(board, 0) - Math.max(GameRules.score(board, 1), GameRules.score(board, 2)) +
					GameRules.pieces(board, 0) - Math.max(GameRules.pieces(board, 1), GameRules.pieces(board, 2));
		score[1] = GameRules.score(board, 1) - Math.max(GameRules.score(board, 0), GameRules.score(board, 2)) +
				GameRules.pieces(board, 1) - Math.max(GameRules.pieces(board, 0), GameRules.pieces(board, 2));
		score[2] = GameRules.score(board, 2) - Math.max(GameRules.score(board, 1), GameRules.score(board, 0)) +
				GameRules.pieces(board, 2) - Math.max(GameRules.pieces(board, 1), GameRules.pieces(board, 0));
	}

	public Move getMove() {
		int[] maxScore = {Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
		int[] moves = new int[MAXMOVELENGTH];
		int[] bestMove = {0, 0, 0, 0};
		
		GameRules.getAllLegalMovesPlayer(moves, this.board, this.playerNum);
		
		int i = 0;
		for (; i < MAXMOVELENGTH; i+=4) {
			// Uninitialized move, reached end of possible moves 
			if (moves[i+0] == 0 && moves[i+2] == 0) {
				break;
			}
			
			int[] nextBoard = GameRules.applyMove(this.board, moves[i+0], moves[i+1], moves[i+2], moves[i+3]);
			
			int[] score = miniMax(nextBoard, this.DEPTH, (this.playerNum + 1) % 2);
			if (score[this.playerNum] >= maxScore[this.playerNum]) {
				System.arraycopy(score, 0, maxScore, 0, 3);
				System.arraycopy(moves, i, bestMove, 0, 4);
			}
		}
		
		// Get Random move with epsilon probability
		if (ThreadLocalRandom.current().nextDouble(0,1) < this.EPSILON) {
			int r = ThreadLocalRandom.current().nextInt(0, i);
			r = (int)(r/4) * 4;
			System.out.println("Random Player taking " + r/4 + "th move");
			System.arraycopy(moves, r, bestMove, 0, 4);
		}
		
		return new Move(bestMove[0], bestMove[1], bestMove[2], bestMove[3]);
	}
	
	private int[] miniMax(int[] board, int depth, int player)
	{		
		int[] maxScore = {Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE};
		if (depth <= 0) {
			scoreAll(board, maxScore);
			return maxScore;
		} 
		
		int[] moves = new int[MAXMOVELENGTH];
		GameRules.getAllLegalMovesPlayer(moves, board, player);
		
		for (int i = 0; i < MAXMOVELENGTH; i+=4) {
			int[] nextBoard = GameRules.applyMove(board, moves[i+0], moves[i+1], moves[i+2], moves[i+3]);
			int[] score = miniMax(nextBoard, depth -1, (playerNum + 1) % 2);
			if (score[player] > maxScore[player]) {
				System.arraycopy(score, 0, maxScore, 0, 3);
			}
			if (moves[i+0] == 0 && moves[i+2] == 0) {
				break;
			}
		}
		
		return maxScore;
	}
}
