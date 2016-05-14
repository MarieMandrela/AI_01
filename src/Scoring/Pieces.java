package Scoring; 

import AI.GameRules;

public class Pieces implements Scoring {
	
	public void scoreAll(int[] board, int[] score) {
		score[0] = GameRules.pieces(board, 0);
		score[1] = GameRules.pieces(board, 1);
		score[2] = GameRules.pieces(board, 2);
	}
	
	public int score(int[] board, int playerNum) {
		return GameRules.pieces(board, playerNum);
	}
}
