package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import Scoring.DiffMaxScorePieces;
import Scoring.DiffMaxScorePiecesLegalMovesLeft;
import Scoring.Pieces;
import Scoring.MaxScorePieces;
import Scoring.Score;
import Scoring.WeightedDiffMaxScorePiecesLegalMovesLeft;
import Scoring.WeightedDiffPieces;
import Scoring.WeightedDiffScorePieces;

import AI.AIClient;
import AI.Logic.AILogic;
import AI.Logic.GreedyEnemyGameTreeAI;
import AI.Logic.GameTreeAI;
import AI.Logic.RandomAI;

public class Application {

	public static void main(String[] args) throws InterruptedException, IOException
	{		
		ClientRunner.runClients();
	}
	
	public static class ClientRunner {
		public static void runClients () throws InterruptedException {
			String hostName = "127.0.0.1";
			
			AIClient clientA = new AIClient(hostName, "Tree5 (ml)", "/home/fuchs/HTW/AI/sebastian.png");
			AILogic aiA = new GameTreeAI(5, 0.00, new WeightedDiffMaxScorePiecesLegalMovesLeft());
			clientA.setAI(aiA);
			Thread a = new Thread(clientA);
			
			AIClient clientB = new AIClient(hostName, "Random",  "/home/fuchs/HTW/AI/michael.png");
			AILogic aiB = new RandomAI();
			clientB.setAI(aiB);
			Thread b = new Thread(clientB);
			
			AIClient clientC = new AIClient(hostName, "Tree7 (ml)",  "/home/fuchs/HTW/AI/gabriel.png");
			AILogic aiC = new GameTreeAI(7, 0.00, new WeightedDiffMaxScorePiecesLegalMovesLeft());
			clientC.setAI(aiC);
			Thread c = new Thread(clientC);	
			
			List<Integer> clients = new ArrayList<Integer>();
			clients.add(1);
			clients.add(3);
			clients.add(2);
			
			do {
				int r = ThreadLocalRandom.current().nextInt(0, clients.size());
				switch (clients.get(r)) {
					case 1:
						a.start();
						Thread.sleep(333);
						break;
					case 2:
						b.start();
						Thread.sleep(333);
						break;
					case 3:
						c.start();
						Thread.sleep(333);
						break;
				}
				clients.remove(clients.get(r));
			} while (clients.size() > 0);

		}
	}
}
