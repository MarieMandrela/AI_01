package Scoring;

import AI.GameRules;

public class DiffMaxScorePieces implements Scoring {

	public void scoreAll(int[] board, int[] score) {
		score[0] = GameRules.score(board, 0) - Math.max(GameRules.score(board, 1), GameRules.score(board, 2)) +
					GameRules.pieces(board, 0) - Math.max(GameRules.pieces(board, 1), GameRules.pieces(board, 2));
		score[1] = GameRules.score(board, 1) - Math.max(GameRules.score(board, 0), GameRules.score(board, 2)) +
				GameRules.pieces(board, 1) - Math.max(GameRules.pieces(board, 0), GameRules.pieces(board, 2));
		score[2] = GameRules.score(board, 2) - Math.max(GameRules.score(board, 1), GameRules.score(board, 0)) +
				GameRules.pieces(board, 2) - Math.max(GameRules.pieces(board, 1), GameRules.pieces(board, 0));
	}
	
	public int score(int[] board, int playerNum) {
		return GameRules.score(board, playerNum) - 
				Math.max(GameRules.score(board, (playerNum + 1) % 2), GameRules.score(board, (playerNum + 2) % 2)) +
				GameRules.pieces(board, playerNum) - 
				Math.max(GameRules.pieces(board, (playerNum + 1) % 2), GameRules.pieces(board, (playerNum + 2) % 2));
	}

}
