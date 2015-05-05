import java.util.ArrayList;
import java.util.Scanner;

public class MancalaGame {

	final int NUMBER_OF_PITS = 12;
	final int PLAYER_A_PIT = 6;
	final int PLAYER_B_PIT = 13;
	final static String PIT_NUMBERS[] = { "A1", "A2", "A3", "A4", "A5", "A6",
			"playerA", "B1", "B2", "B3", "B4", "B5", "B6", "playerB" };

	private ArrayList<Integer> board;
	private ArrayList<Integer> previous;

	private int stonesPerPit;
	//I just realized that we don't need stonesLeft, should i delete it?
	private int stonesLeft;

	// FALSE = A, TRUE = B
	private boolean playerBTurn;

	/**
	 * Constructor
	 * */
	public MancalaGame(int stonesPerPit) {
		this.board = new ArrayList<Integer>();
		this.previous = new ArrayList<Integer>();
		this.stonesPerPit = stonesPerPit;
		playGUIVersion();
		// stonesLeft = stonesPerPit * NUMBER_OF_PITS;
		// Player A goes first
		// playerBTurn = false;
		// this.resetBoard();
	}

	/**
	 * 
	 * @return board
	 */
	public ArrayList<Integer> getBoard(){
		return board;
	}
	
	/**
	 * 
	 */
	public ArrayList<Integer> getPrevious(){
		return previous;
	}
	
	/**
	 * @return stonesLeft
	 */
	public int getStonesLeft(){
		return stonesLeft;
	}
	/**
	 * 
	 * */
	public int getStonesInPit(int index){
		//System.out.println(index);
		//System.out.println(board.get(index));
		return board.get(index);
	}
	
	/**
	 * 
	 * */
	private void dropStone(int index, int amount){
		
		board.set(index, getStonesInPit(index) + amount);
		
	}
	
	/**
	 * 
	 * */
	private void clearPit(int index){
		board.set(index, 0);
	}
	
	/**
	 * 
	 * */
	private void changeTurn(){
		playerBTurn = !playerBTurn;
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
		changeTurn();
		board = new ArrayList<Integer>(previous);
	}

	/**
	 * 
	 * */
	public void step(int pitNumber) {

		int index;
		int steps;

		// Make a copy of the board
		previous = new ArrayList<Integer>(board);

		index = pitNumber;

		// Amount of stones in the pit selected
		steps = getStonesInPit(index);

		// Clear the chosen pit
		clearPit(index);
		index++;

		// For the amount of stones in the pit drop 1 stone into the next pits
		while (steps > 0) {

			// If pit does not belong to the player skip it.
			if ((index == PLAYER_A_PIT) && playerBTurn) {
				index++;
			} else if ((index == PLAYER_B_PIT) && !playerBTurn) {
				index = 0;
			}
			// Move index back to A1 if index goes past playerB's pit
			if (index > PLAYER_B_PIT) {
				index = 0;
			}

			// if you place last stone on empty pit that is on your side you get
			// all the stones on the adjacent pit
			if (steps == 1 && (getStonesInPit(index) == 0) && index != PLAYER_A_PIT
					&& index != PLAYER_B_PIT) {
				if (index < 7 && !playerBTurn) {
					dropStone(PLAYER_A_PIT, getStonesInPit(index + 7));
					clearPit(index + 7);
				} else if (index > 7 && playerBTurn) {
					dropStone(PLAYER_B_PIT, getStonesInPit(index - 7));
					clearPit(index - 7);
				}
			}

			// Drop stone into pit
			dropStone(index, 1);

			index++;
			steps--;
			//printBoard();
		}

		// Extra turn if last stone lands on own mancala
		if (PLAYER_A_PIT == index - 1) {
			changeTurn();
		} else if (PLAYER_B_PIT == index - 1) {
			changeTurn();
		}

		// Change the turn
		changeTurn();
	}
	
	/**
	 * 
	 * */
	private void determineWinner() {

		int ATotal = 0;
		int BTotal = 0;
		int j = board.size() / 2;

		for (int i = 0; i < board.size() / 2 ; i++) {
			ATotal += getStonesInPit(i);
			BTotal += getStonesInPit(j);
			j++;
		}

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
			if (getStonesInPit(i) == 0) {
				countA++;
			}
		}

		for (int i = board.size() / 2; i < board.size() - 1; i++) {
			if (getStonesInPit(i) == 0) {
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

		for (int i = 0; i < PIT_NUMBERS.length; i++) {
			if (pitNumber.equals(PIT_NUMBERS[i])) {

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
		playerBTurn = false;
		this.resetBoard();
		printBoard();

		while (!gameOverCheck()) {
			System.out
					.println("Select a pit or type 'undo' if the previous player would like to return to a previous state");
			text = scan.nextLine();

			if (text.equals("undo")) {
				undo();
				System.out
						.println("Which pit would you like to select? No more undos");
				printBoard();
				text = scan.nextLine();
			}

			step(this.convertToInt(text));
			printBoard();

		}
		
		determineWinner();

	}

	private void playGUIVersion() {
		stonesLeft = stonesPerPit * NUMBER_OF_PITS;
		// Player A goes first
		playerBTurn = false;
		this.resetBoard();
		//determineWinner();

	}
	
	/**
	 * Prints the mancala board onto the console.
	 * */

	private void printBoard() {

		if (playerBTurn) {
			System.out.println("PlayerB Turn.");
		} else {
			System.out.println("PlayerA Turn.");
		}

		for (int i = 0; i < PIT_NUMBERS.length / 2 - 1; i++) {
			System.out.print(PIT_NUMBERS[i] + " ");
		}
		//
		System.out.println();

		for (int i = 0; i < board.size() / 2; i++) {
			System.out.print(" " + getStonesInPit(i) + " ");
		}
		System.out.print(":A");

		System.out.println();

		for (int i = board.size() / 2; i < board.size(); i++) {
			System.out.print(" " + getStonesInPit(i) + " ");
		}
		System.out.print(":B");
		//
		System.out.println();

		for (int i = PIT_NUMBERS.length / 2; i < PIT_NUMBERS.length - 1; i++) {
			System.out.print(PIT_NUMBERS[i] + " ");
		}

		System.out.println();

	}

}