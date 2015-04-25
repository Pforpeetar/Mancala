//sorry its a mess and im still not sure if it fully works haha
//ill try to clean it up on sunday
//also i forgot how to use github

import java.util.ArrayList;
import java.util.Scanner;

public class MancalaGame {

	final int NUMBER_OF_PITS = 12;
	final int PLAYER_A_PIT = 6;
	final int PLAYER_B_PIT = 13;

	final String pitNumbers[] = { "A1", "A2", "A3", "A4", "A5", "A6",
			"playerA", "B1", "B2", "B3", "B4", "B5", "B6", "playerB" };

	private ArrayList<Integer> board;
	private ArrayList<Integer> previous;

	private int stonesPerPit;
	private int stonesLeft;

	// FALSE = A, TRUE = B
	private boolean playerTurn;

	/**
	 * Constructor
	 * */
	public MancalaGame(int stonesPerPit) {
		this.board = new ArrayList<Integer>();
		this.previous = new ArrayList<Integer>();
		// this.stonesPerPit = stonesPerPit;
		// stonesLeft = stonesPerPit * NUMBER_OF_PITS;
		// Player A goes first
		// playerTurn = false;
		// this.resetBoard();
	}

	/**
	 * This clears the board and places stones into each pit.
	 * */
	public void resetBoard() {

		board.clear();

		for (int i = 0; i < NUMBER_OF_PITS + 2; i++) {
			if (i == PLAYER_A_PIT) {
				board.add(PLAYER_A_PIT, 0);
			} else if (i == PLAYER_B_PIT) {
				board.add(PLAYER_B_PIT, 0);
			} else {
				board.add(i, stonesPerPit);
			}
		}
		// Makes a copy of the board.
		previous = new ArrayList<Integer>(board);
	}

	/**
	 * Return to a previous state.
	 * */
	public void undo() {
		board = new ArrayList<Integer>(previous);
	}

	/**
	 * 
	 * */
	public void step(String pitNumber) {

		int index;
		boolean test = false;
		int steps;

		// Make a copy of the board
		previous = new ArrayList<Integer>(board);

		index = convertToInt(pitNumber);
		// Amount of stones in the pit selected
		steps = board.get(index);

		// Clear the chosen pit
		board.set(index, 0);
		index++;

		// For the amount of stones in the pit drop 1 stone into the next pits
		while (steps > 0) {

			// If pit does not belong to the player wise skip it.
			if ((index == PLAYER_A_PIT) && playerTurn) {
				index++;
			} else if ((index == PLAYER_B_PIT) && !playerTurn) {
				index = 0;
			}
			// Make sure it does not go out of bounds
			if (index > PLAYER_B_PIT) {
				index = 0;
			}

			// if you place last stone on empty pit that is on your side you get
			// all the stones on the adjacent pit
			if (steps == 1 && (board.get(index) == 0) && index != PLAYER_A_PIT
					&& index != PLAYER_B_PIT) {
				if (index < 7 && !playerTurn) {
					board.set(PLAYER_A_PIT,
							board.get(PLAYER_A_PIT) + board.get(index + 7));
					board.set(index + 7, 0);
				} else if (index > 7 && playerTurn) {
					board.set(PLAYER_B_PIT,
							board.get(PLAYER_B_PIT) + board.get(index - 7));
					board.set(index - 7, 0);
				}
			}

			// Drop stone into pit
			board.set(index, board.get(index) + 1);

			index++;
			steps--;

		}

		// Check if players last stone lands on their mancala.
		if (PLAYER_A_PIT == index - 1) {
			playerTurn = !playerTurn;
		} else if (PLAYER_B_PIT == index - 1) {
			playerTurn = !playerTurn;
		}

		// Change the turn
		playerTurn = !playerTurn;
	}

	/**
	 * 
	 * */

	private void determineWinner() {

		int j = 0;
		int ATotal = 0;
		int BTotal = 0;
		String winner = "";

		for (int i = 0; i < board.size() / 2 - 1; i++) {
			ATotal += board.get(i);

			j = board.size() / 2 + 1;
			BTotal += board.get(j);

		}

		ATotal += board.get(PLAYER_A_PIT);
		BTotal += board.get(PLAYER_B_PIT);

		if (ATotal > BTotal) {
			System.out.println("Player A wins with: " + ATotal);
		} else if (BTotal > ATotal) {
			System.out.println("Player B wins with: " + BTotal);
		} else {
			System.out.println("Tie game!");
		}

	}

	/**
	 * 
	 * */
	public boolean gameOverCheck() {

		int countA = 0;
		int countB = 0;

		for (int i = 0; i < board.size() / 2 - 1; i++) {
			if (board.get(i) == 0) {
				countA++;
			}
		}

		for (int i = board.size() / 2; i < board.size() - 1; i++) {
			if (board.get(i) == 0) {
				countB++;
			}
		}

		if (countA == 6 || countB == 6) {
			return true;
		}

		return false;

	}

	/**
	 * @param pitNumber
	 *            The name of the pit
	 * @return i The index of the pitNumber
	 * */
	public int convertToInt(String pitNumber) {

		for (int i = 0; i < pitNumbers.length; i++) {
			if (pitNumber.equals(pitNumbers[i])) {

				return i;
			}
		}
		return -1;
	}

	/**
	 * Console version of the game.
	 * */
	public void playConsoleVersion() {

		String text;
		Scanner scan = new Scanner(System.in);
		System.out.println("How many stones per pit?[3 or 4]");
		String sP = scan.nextLine();
		this.stonesPerPit = Integer.parseInt(sP);

		if (stonesPerPit < 3 || stonesPerPit > 4) {
			System.out.println("Please pick 3 or 4");
			return;
		}

		stonesLeft = stonesPerPit * NUMBER_OF_PITS;

		// Player A goes first
		playerTurn = false;
		this.resetBoard();
		printBoard();

		while (stonesLeft > 0) {
			System.out
					.println("Select a pit or type 'undo' if the previous player would like to return to a previous state");
			text = scan.nextLine();

			if (text.equals("undo")) {
				playerTurn = !playerTurn;
				undo();
				System.out
						.println("Which pit would you like to select? No more undos");
				printBoard();
				text = scan.nextLine();
			}

			step(text);

			if (gameOverCheck() == true) {
				determineWinner();
				return;
			}

			printBoard();

		}

	}

	/**
	 * Prints the mancala board onto the console.
	 * */

	private void printBoard() {

		if (playerTurn) {
			System.out.println("PlayerB Turn.");
		} else {
			System.out.println("PlayerA Turn.");
		}

		for (int i = 0; i < pitNumbers.length / 2 - 1; i++) {
			System.out.print(pitNumbers[i] + " ");
		}
		//
		System.out.println();

		for (int i = 0; i < board.size() / 2; i++) {
			System.out.print(" " + board.get(i) + " ");
		}
		System.out.print(":A");

		System.out.println();

		for (int i = board.size() / 2; i < board.size(); i++) {
			System.out.print(" " + board.get(i) + " ");
		}
		System.out.print(":B");
		//
		System.out.println();

		for (int i = pitNumbers.length / 2; i < pitNumbers.length - 1; i++) {
			System.out.print(pitNumbers[i] + " ");
		}

		System.out.println();

	}

}
