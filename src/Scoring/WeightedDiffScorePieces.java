package Scoring;

import AI.GameRules;

public class WeightedDiffScorePieces implements Scoring {
	
	public void scoreAll(int[] board, int[] score) {
		score[0] = 2*GameRules.score(board, 0) - GameRules.score(board, 1) - GameRules.score(board, 2) +
					9*GameRules.pieces(board, 0) - 3*GameRules.pieces(board, 1) - 3*GameRules.pieces(board, 2);
		score[1] = 2*GameRules.score(board, 1) - GameRules.score(board, 0) - GameRules.score(board, 2) +
				9*GameRules.pieces(board, 1) - 3*GameRules.pieces(board, 0) - 3*GameRules.pieces(board, 2);
		score[2] = 2*GameRules.score(board, 2) - GameRules.score(board, 1) - GameRules.score(board, 0) +
				9*GameRules.pieces(board, 2) - 3*GameRules.pieces(board, 1) - 3*GameRules.pieces(board, 0);
		
	}
	
	public int score(int[] board, int playerNum) {
		return 2*GameRules.score(board, playerNum) - 
				GameRules.score(board, (playerNum + 1) % 2) - GameRules.score(board, (playerNum + 2) % 2) +
				9*GameRules.pieces(board, playerNum) - 
				3*GameRules.pieces(board, (playerNum + 1) % 2) - 3*GameRules.pieces(board, (playerNum + 2) % 2);
	}
}
