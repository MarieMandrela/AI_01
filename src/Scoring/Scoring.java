package Scoring;

public interface Scoring {

	abstract int score(int[] board, int playerNum);
	
	abstract void scoreAll(int[] board, int[] score);
}