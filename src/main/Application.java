package main;

import java.io.IOException;

import AI.AIClient;
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
			
			AIClient clientA = new AIClient(hostName, "Depth5", "/home/fuchs/Documents/HTW/AI/sebastian.png");
			GameTreeAI aiA = new GameTreeAI(5, 0.2);
			clientA.setAI(aiA);
			Thread a = new Thread(clientA);
			
			AIClient clientB = new AIClient(hostName, "Depth5",  "/home/fuchs/Documents/HTW/AI/michael.png");
			GameTreeAI aiB = new GameTreeAI(5, 0.2);
			clientB.setAI(aiB);
			Thread b = new Thread(clientB);
			
			AIClient clientC = new AIClient(hostName, "Depth5",  "/home/fuchs/Documents/HTW/AI/gabriel.png");
			GameTreeAI aiC = new GameTreeAI(5, 0.2);;
			clientC.setAI(aiC);
			Thread c = new Thread(clientC);	
			
			a.start();
			b.start();
			c.start();
		}
	}
}
