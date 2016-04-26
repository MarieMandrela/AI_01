package main;

import java.io.IOException;

import AI.AIClient;
import AI.Logic.AILogic;
import AI.Logic.AlphaBetaGameTreeAI;
import AI.Logic.GameTreeAI;
import AI.Logic.RandomAI;

public class Application {

	public static void main(String[] args) throws InterruptedException, IOException
	{		
		ClientRunner.runClients();
	}
	
	public static class ClientRunner {
		public static void runClients () {
			String hostName = "127.0.0.1";
			
			AIClient clientA = new AIClient(hostName, "Tree 5", "/home/fuchs/Documents/HTW/AI/sebastian.png");
			AILogic aiA = new GameTreeAI(5, 0.0);
			clientA.setAI(aiA);
			Thread a = new Thread(clientA);
			
			AIClient clientB = new AIClient(hostName, "Tree 5",  "/home/fuchs/Documents/HTW/AI/michael.png");
			AILogic aiB = new GameTreeAI(5, 0.0);
			clientB.setAI(aiB);
			Thread b = new Thread(clientB);
			
			AIClient clientC = new AIClient(hostName, "ABTree 5",  "/home/fuchs/Documents/HTW/AI/gabriel.png");
			AILogic aiC = new AlphaBetaGameTreeAI(5, 0.0);
			clientC.setAI(aiC);
			Thread c = new Thread(clientC);	
			
			a.start();
			b.start();
			c.start();
		}
	}
}
