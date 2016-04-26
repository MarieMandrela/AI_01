package AI;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;

import AI.Logic.AILogic;

import lenz.htw.aipne.Move;
import lenz.htw.aipne.net.NetworkClient;

public class AIClient implements Runnable {

    private String hostName;
    private String teamName;
    private BufferedImage logo;
    
    AILogic logic;

    public AIClient(String hostName, String teamName, String logoPath) {
		super();
		this.hostName = hostName;
		this.teamName = teamName;
		
		this.logo = new BufferedImage(256, 256, BufferedImage.TYPE_INT_RGB);
		try {
			File file = new File(logoPath);
			this.logo = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    public void setAI(AILogic ai) {
    	this.logic = ai;
    	int[] board = new int[GameRules.arraySize];
		GameRules.initBoard(board);
		GameRules.initAllPlayers(board);
		this.logic.setGameBoard(board);
    }

	public void run() {
		NetworkClient client = new NetworkClient(this.hostName, this.teamName, this.logo);
		int playerNum = client.getMyPlayerNumber();
		logic.setPlayerNum(playerNum);
		
		boolean playing = true;		
		//int timeLimit = 0;
		Move latestMove = null;
		do {
			//timeLimit = client.getTimeLimitInSeconds();
			try {
				latestMove = client.receiveMove();
			}catch (java.lang.RuntimeException e) {
				return;
			}
			
			if (latestMove != null) {
				logic.observeMove(latestMove);
			} else {
				Move takenMove = logic.getMove();
				client.sendMove(takenMove);
			}
		} while (playing);
    }
}
