package Scoring;

import AI.GameRules;

public class WeightedDiffPieces implements Scoring {
	
	public void scoreAll(int[] board, int[] score) {
		score[0] =4*GameRules.pieces(board, 0) - 2*GameRules.pieces(board, 1) - 2*GameRules.pieces(board, 2);
		score[1] = 4*GameRules.pieces(board, 1) - 2*GameRules.pieces(board, 0) - 2*GameRules.pieces(board, 2);
		score[2] = 4*GameRules.pieces(board, 2) - 2*GameRules.pieces(board, 1) - 2*GameRules.pieces(board, 0);
		
	}
	
	public int score(int[] board, int playerNum) {
		return 4*GameRules.pieces(board, playerNum) - 2*GameRules.pieces(board, (playerNum + 1) % 2) - 2*GameRules.pieces(board, (playerNum + 2) % 2);
	}
}
