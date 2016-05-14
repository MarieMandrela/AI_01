package Scoring;

import AI.GameRules;

public class Score implements Scoring {

	public void scoreAll(int[] board, int[] score) {
		score[0] = GameRules.score(board, 0);
		score[1] = GameRules.score(board, 1);
		score[2] = GameRules.score(board, 2);
	}
	
	public int score(int[] board, int playerNum) {
		return GameRules.score(board, playerNum);
	}

}
