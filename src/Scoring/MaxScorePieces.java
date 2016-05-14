package Scoring;

import AI.GameRules;

public class MaxScorePieces implements Scoring {

	public void scoreAll(int[] board, int[] score) {
		score[0] = GameRules.score(board, 0) + GameRules.pieces(board, 0);
		score[1] = GameRules.score(board, 1) + GameRules.pieces(board, 1);
		score[2] = GameRules.score(board, 2) + GameRules.pieces(board, 2);
	}
	
	public int score(int[] board, int playerNum) {
		return GameRules.score(board, playerNum) + GameRules.pieces(board, playerNum);
	}

}
