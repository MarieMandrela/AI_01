package AI;

public class GameRules {
	static int width = 14;
	static int height = 7;
	static int maxPos = height*height + width;
	static int arraySize = maxPos + 1 + 3 + 3;
	
	public static void initAllPlayers(int[] board)
	{
		initPlayerZero(board);
		initPlayerOne(board);
		initPlayerTwo(board);
	}
	
	public static int score(int[] board, int playerNum) {
		return board[maxPos + 1 + playerNum];
	}
	
	public static int pieces(int[] board, int playerNum) {
		return board[maxPos + 3 + 1 + playerNum];
	}
	
	public static void initBoard(int[] board) {
		for (int i = 0; i <= maxPos; i++) {
			board[i] = -1;
		}
		board[maxPos + 1] = 0;
		board[maxPos + 2] = 0;
		board[maxPos + 3] = 0;
		board[maxPos + 4] = 8;
		board[maxPos + 5] = 8;
		board[maxPos + 6] = 8;
	}
	
	private static void initPlayerZero(int[] board)	{
		board[7*7 + 4] = 0;
		board[7*7 + 6] = 0;
		board[7*7 + 8] = 0;
		board[7*7 + 10] = 0;
		board[7*7 + 5] = 0;
		board[7*7 + 7] = 0;
		board[7*7 + 9] = 0;
		board[6*6 + 6] = 0;
	}
	
	private static void initPlayerOne(int[] board)	{
		board[2*2 + 0] = 1;
		board[3*3 + 0] = 1;
		board[4*4 + 0] = 1;
		board[5*5 + 0] = 1;
		board[3*3 + 1] = 1;
		board[4*4 + 1] = 1;
		board[5*5 + 1] = 1;
		board[4*4 + 2] = 1;
	}
	
	private static void initPlayerTwo(int[] board) {
		board[2*2 + 4] = 2;
		board[3*3 + 6] = 2;
		board[4*4 + 8] = 2;
		board[5*5 + 10] = 2;
		board[3*3 + 5] = 2;
		board[4*4 + 7] = 2;
		board[5*5 + 9] = 2;
		board[4*4 + 6] = 2;
	}
	
	/**
	 * 
	 * @param move
	 */
	public static int[] applyMove(int[] board, int fromX, int fromY, int toX, int toY) {
		int[] applied = new int[arraySize];
		System.arraycopy(board, 0, applied, 0, arraySize);
		
		int activePlayer = board[fromY*fromY + fromX];
		
		scoreMove(board, fromX, fromY, toX, toY, activePlayer);
		
		applied[fromY*fromY + fromX] = -1;
		applied[toY*toY + toX] = activePlayer;
		
		return applied;
	}
	
	private static void scoreMove(int[] board, int fromX, int fromY, int toX, int toY, int activePlayer) {
		
		if (isInOppositeCorner(toX, toY, activePlayer)) {
			board[maxPos + 1 + activePlayer] += 11;
		}
		else if (fromX % 2 != 0) {
			board[maxPos + 1 + activePlayer] += 1;
		}
		else if (board[toY*toY + toX] != -1) {
			board[maxPos + 1 + activePlayer] += 1;
			board[maxPos + 3 + 1 + board[toY*toY + toX]] -= 1;
		}
	}
	
	private static boolean isInOppositeCorner(int toX, int toY, int playerNum) {
		switch (playerNum)
		{
			case 0:
				return toX == 0 && toY == 0;
			case 1:
				return toX == width && toY == height;
			case 2:
				return toX == 0 && toY == height;
			default:
				return false;
		}
	}
	
	public static boolean isOnBoard(int x, int y) {
		return x >= 0 &&
			   y >= 0 &&
			   x <= 2*y &&
			   y <= height;
	}
	
	public static boolean hasSamePlayer(int[] board, int x, int y, int playerNum) {
		return board[y*y + x] == playerNum;
	}
	
	public static boolean hasEnemyPlayer(int[] board, int x, int y, int playerNum) {
		return board[y*y + x] != -1 && board[y*y + x] != playerNum;
	}
	
	public static boolean isEmpty(int[] board, int x, int y) {
		return board[y*y + x] == -1;
	}
	
	public static int[] getAllLegalMovesPlayer(int[] moves, int[] board, int playerNum) {
		int pos = 0;
		
		for (int y = 0; y <= height; y++) {
			for (int x = 0; x <= 2*y; x++) {
				if (board[y*y + x] == playerNum) {
					pos = getLegalMoves(moves, pos, board, x, y, playerNum);
				}
			}
		}

		return moves;
	}
	
	// TODO: move switch further up in call stack
	public static int getLegalMoves(int[] moves, int pos, int[] board, int x, int y, int playerNum) {
		int nextPos = pos;
		
		switch (playerNum)
		{
			case 0:
				nextPos = getLegalMovesPlayerZero(moves, pos, board, x, y);
				break;
			case 1:
				nextPos = getLegalMovesPlayerOne(moves, pos, board, x, y);
				break;
			case 2:
				nextPos = getLegalMovesPlayerTwo(moves, pos, board, x, y);
				break;
		}
		
		return nextPos;
	}
	
	// TODO more specific onboard checks
	private static int getLegalMovesPlayerZero(int[] moves, int pos, int[] board, int x, int y)
	{
		int nextPos = pos;
		
		if(x % 2 == 0) {
			int leftX = x + 1;
			int leftY = y;
			int rightX = x - 1;
			int rightY = y;
			if (isOnBoard(leftX, leftY) && board[leftY*leftY + leftX] != 0) {
				moves[nextPos++] = x;
				moves[nextPos++] = y;
				moves[nextPos++] = leftX;
				moves[nextPos++] = leftY;
			}
			if (isOnBoard(rightX, rightY) && board[rightY*rightY + rightX] != 0) {
				moves[nextPos++] = x;
				moves[nextPos++] = y;
				moves[nextPos++] = rightX;
				moves[nextPos++] = rightY;
			}
		} else {
			int forwardX = x - 1;
			int forwardY = y - 1;
			if (board[forwardY*forwardY + forwardX] == -1) {
				moves[nextPos++] = x;
				moves[nextPos++] = y;
				moves[nextPos++] = forwardX;
				moves[nextPos++] = forwardY;
			}
		}
		
		return nextPos;
	}
	
	private static int getLegalMovesPlayerOne(int[] moves, int pos, int[] board, int x, int y)
	{
		int nextPos = pos;
		
		if(x % 2 == 0) {
			int leftX = x + 1;
			int leftY = y + 1;
			int rightX = x + 1;
			int rightY = y;
			if (isOnBoard(leftX, leftY) && board[leftY*leftY + leftX] != 1) {
				moves[nextPos++] = x;
				moves[nextPos++] = y;
				moves[nextPos++] = leftX;
				moves[nextPos++] = leftY;
			}
			if (isOnBoard(rightX, rightY) && board[rightY*rightY + rightX] != 1) {
				moves[nextPos++] = x;
				moves[nextPos++] = y;
				moves[nextPos++] = rightX;
				moves[nextPos++] = rightY;
			}
		} else {
			int forwardX = x + 1;
			int forwardY = y; 
			if (board[forwardY*forwardY + forwardX] == -1) {
				moves[nextPos++] = x;
				moves[nextPos++] = y;
				moves[nextPos++] = forwardX;
				moves[nextPos++] = forwardY;
			}
		}
		
		return nextPos;
	}
	
	private static int getLegalMovesPlayerTwo(int[] moves, int pos, int[] board, int x, int y) {
		int nextPos = pos;
		
		if(x % 2 == 0) {
			int leftX = x - 1;
			int leftY = y ;
			int rightX = x + 1;
			int rightY = y + 1;
			if (isOnBoard(leftX, leftY) && board[leftY*leftY + leftX] != 2) {
				moves[nextPos++] = x;
				moves[nextPos++] = y;
				moves[nextPos++] = leftX;
				moves[nextPos++] = leftY;
			}
			if (isOnBoard(rightX, rightY) && board[rightY*rightY + rightX] != 2) {
				moves[nextPos++] = x;
				moves[nextPos++] = y;
				moves[nextPos++] = rightX;
				moves[nextPos++] = rightY;
			}
		} else {
			int forwardX = x - 1;
			int forwardY = y; 
			if (board[forwardY*forwardY + forwardX] == -1) {
				moves[nextPos++] = x;
				moves[nextPos++] = y;
				moves[nextPos++] = forwardX;
				moves[nextPos++] = forwardY;
			}
		}
		
		return nextPos;
	}
}
