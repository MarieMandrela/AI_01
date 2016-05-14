package Scoring;

import AI.GameRules;

public class DiffMaxScorePiecesLegalMovesLeft implements Scoring {
	
	private static int INAGAMESCORE = Integer.MIN_VALUE;

	public void scoreAll(int[] board, int[] score) {
		score[0] = GameRules.score(board, 0) - Math.max(GameRules.score(board, 1), GameRules.score(board, 2)) +
					GameRules.pieces(board, 0) - Math.max(GameRules.pieces(board, 1), GameRules.pieces(board, 2));
		score[1] = GameRules.score(board, 1) - Math.max(GameRules.score(board, 0), GameRules.score(board, 2)) +
				GameRules.pieces(board, 1) - Math.max(GameRules.pieces(board, 0), GameRules.pieces(board, 2));
		score[2] = GameRules.score(board, 2) - Math.max(GameRules.score(board, 1), GameRules.score(board, 0)) +
				GameRules.pieces(board, 2) - Math.max(GameRules.pieces(board, 1), GameRules.pieces(board, 0));
		
		score[0] = GameRules.inGame(board, 0) ? score[0] : INAGAMESCORE;
		score[1] = GameRules.inGame(board, 1) ? score[1] : INAGAMESCORE;
		score[2] = GameRules.inGame(board, 2) ? score[2] : INAGAMESCORE;
	}
	
	public int score(int[] board, int playerNum) {
		if (!GameRules.inGame(board, playerNum)) {
			return INAGAMESCORE;
		}
		
		return GameRules.score(board, playerNum) - 
				Math.max(GameRules.score(board, (playerNum + 1) % 2), GameRules.score(board, (playerNum + 2) % 2)) +
				GameRules.pieces(board, playerNum) - 
				Math.max(GameRules.pieces(board, (playerNum + 1) % 2), GameRules.pieces(board, (playerNum + 2) % 2));
	}
}
