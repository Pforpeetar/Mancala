import java.util.ArrayList;
import java.util.Scanner;

/**
 * Back-end portion of mancala program. 
 * @author Samson Lee, Peter Pham, Benjamin Liu
 */
public class MancalaGame {

	/**
	 * Number of pits not including players mancala
	 */
	final int NUMBER_OF_PITS = 12;
	
	/**
	 * Index of player A's mancala
	 */
	final int PLAYER_A_PIT = 6;
	
	/**
	 * Index of player B's mancala
	 */
	final int PLAYER_B_PIT = 13;
	
	/**
	 * String representation of the board index layout. Counter-clockwise layout.
	 */
	final static String PIT_NUMBERS[] = { "A1", "A2", "A3", "A4", "A5", "A6",
			"playerA", "B1", "B2", "B3", "B4", "B5", "B6", "playerB" };

	/**
	 *  Board information. Elements represent stones in particular index
	 */
	private ArrayList<Integer> board;
	
	/**
	 * Previous board information
	 */
	private ArrayList<Integer> previous;

	/**
	 * default stones per pit
	 */
	private int stonesPerPit;
	
	/**
	 * stones left in pits
	 */
	private int stonesLeft;

	/**
	 * false is A, true is B
	 */
	private boolean playerBTurn;
	
	/**
	 * true if extra turn is granted, false if not
	 */
	private boolean extraTurn = false;

	/**
	 * Constructor for MancalaGame. 
	 * @param stonesPerPit stones per pit. Can be 3 or 4.
	 */
	public MancalaGame(int stonesPerPit) {
		this.board = new ArrayList<Integer>();
		this.previous = new ArrayList<Integer>();
		this.stonesPerPit = stonesPerPit;
		playGUIVersion();
	}

	/**
	 * method to set player turn
	 * @param turn true - set to B turn, false - set to A turn
	 */
	public void setPlayerTurn(boolean turn){
		playerBTurn = turn;
	}
	
	/**
	 * Accessor for board information
	 * @return board arrayList containing board information
	 */
	public ArrayList<Integer> getBoard(){
		return board;
	}
	
	/**
	 * Accessor for previous board information
	 * @return previous arrayList containing previous information
	 */
	public ArrayList<Integer> getPrevious(){
		return previous;
	}
	
	/**
	 * method to get rid of previous board info and replace with current board info.
	 */
	public void erasePrevious(){
		previous = board;
	}
	
	/**
	 * return number of stones left in pits
	 * @return stonesLeft number of stones left in pits
	 */
	public int getStonesLeft(){
		return stonesLeft;
	}
	
	/**
	 * Get stones in specific index
	 * @param index index of pit to be taken
	 * @return number of stones in selected pit
	 */
	public int getStonesInPit(int index){
		return board.get(index);
	}
	
	/**
	 * Drop stone(s) into specific pit
	 * @param index index of pit 
	 * @param amount number of stones to be dropped into pit index
	 */
	private void dropStone(int index, int amount){	
		board.set(index, getStonesInPit(index) + amount);	
	}
	
	/**
	 * Clear pit at certain index
	 * @param index index to be cleared
	 */
	private void clearPit(int index){
		board.set(index, 0);
	}
	
	/**
	 * Change player turn. 
	 */
	public void changeTurn(){
		playerBTurn = !playerBTurn;
	}
	
	/**
	 * Check if player had an extra turn (last stone dropped into own mancala)
	 * @return true if had extraTurn, false if not
	 */
	public boolean checkExtraTurn(){
		return extraTurn;
	}	
	
	/**
	 * Set if player has extra turn. 
	 * Used to correct edge case when player gets extra turn but the player turn is changed to the opponent. 
	 * @param set
	 */
	public void setExtraTurn(boolean set){
		extraTurn= set;
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
	 */
	public void undo() {
		changeTurn();
		board = new ArrayList<Integer>(previous);
	}

	/**
	 * Take stones at pitNumber, and drop a stone one by one into each pit moving counter-clockwise. 
	 * 		checks if player gets extra turn, or steals opponents' stones. 
	 * @param pitNumber
	 */
	public void step(int pitNumber) {

		int index;
		int steps;

		//check to see if a steal occurred 
		boolean stealStones = false;
		
		// Make a copy of the board
		
		previous = new ArrayList<Integer>(board);

		index = pitNumber;

		// Amount of stones in the pit selected
		steps = getStonesInPit(index);
		
		//if selected pit is empty, make the player choose a pit with stones
		if(steps==0){
			changeTurn();
		}

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
			//it's 12 - index, not index - 6 and index + 6
			if (steps == 1 && (getStonesInPit(index) == 0) && index != PLAYER_A_PIT
					&& index != PLAYER_B_PIT) {
				if (index < PLAYER_A_PIT && !playerBTurn&& getStonesInPit(12-index)>0) {
					dropStone(PLAYER_A_PIT, getStonesInPit(12 - index));
					clearPit(12 - index);
					dropStone(PLAYER_A_PIT, 1);
					stealStones =true;
				} else if (index > 6 && playerBTurn && getStonesInPit(12-index)>0) {
					dropStone(PLAYER_B_PIT, getStonesInPit(12 - index));
					clearPit(12 - index);
					dropStone(PLAYER_B_PIT, 1);
					stealStones =true;
				}
			}

			// Drop stone into pit
			if(stealStones == false)
				dropStone(index, 1);
			else 
				stealStones = false; 
			
			index++;
			steps--;
			printBoard();
		}

		// Extra turn if last stone lands on own mancala
		if (PLAYER_A_PIT == index - 1) {
			changeTurn();
			extraTurn = true;
		} else if (PLAYER_B_PIT == index - 1) {
			changeTurn();
			extraTurn = true;
		}

		// Change the turn
		changeTurn();
	}
	
	/**
	 * Counts up the number of stones Player A and B has. Winner is the one with the most stones. 
	 * */
	public String determineWinner() {
		String result;
		int ATotal = 0;
		int BTotal = 0;
		int j = board.size() / 2;
		
		
		for (int i = 0; i < board.size() / 2 ; i++) {
			ATotal += getStonesInPit(i);
			BTotal += getStonesInPit(j);
			j++;
		}

		for(int i =0 ; i<board.size();i++){
			clearPit(i);
		}
		dropStone(PLAYER_A_PIT, ATotal);
		dropStone(PLAYER_B_PIT, BTotal);
		

		if (ATotal > BTotal) {
			result = "Player A wins with: " + ATotal;
		} else if (BTotal > ATotal) {
			result = "Player B wins with: " + BTotal;
		} else {
			result = "Tie game!";
		}
		
		printBoard();
		return result;

	}

	/**
	 * If player's row is empty, then game is over. Time to call determineWinner method. 
	 */
	public boolean gameOverCheck() {

		int countA = 0;
		int countB = 0;

		for (int i = 0; i < PLAYER_A_PIT; i++) {
			if (getStonesInPit(i) == 0) {
				countA++;
			}
		}

		for (int i = PLAYER_A_PIT; i < PLAYER_B_PIT; i++) {
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
	 * Convert String of pit index to int
	 * @param pitNumber The name of the pit
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
	
	public boolean getTurn() {
		return playerBTurn;
	}

	
	/**
	 * Console version of the game.
	 */
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

	/**
	 * Sets default state of Mancala game.
	 */
	private void playGUIVersion() {
		stonesLeft = stonesPerPit * NUMBER_OF_PITS;
		// Player A goes first
		playerBTurn = false;
		this.resetBoard();
	}
	
	/**
	 * Prints the mancala board onto the console.
	 */
	private void printBoard() {

		if (playerBTurn) {
			System.out.println("PlayerB Turn.");
		} else {
			System.out.println("PlayerA Turn.");
		}
		
		System.out.print("     ");
		
		for (int i = PIT_NUMBERS.length - 2; i >= PIT_NUMBERS.length/2; i--) {
			System.out.print(PIT_NUMBERS[i] + " ");
		}
		
		//
		System.out.println();
		
		System.out.print(" " + getStonesInPit(board.size()-1) + " ");
		System.out.print("  ");
		for (int i = board.size() - 2; i >= board.size()/2; i--) {
			System.out.print(" " + getStonesInPit(i) + " ");
		}
		//
		System.out.println();
		System.out.print("     ");
		for (int i = 0; i < board.size() / 2; i++) {
			System.out.print(" " + getStonesInPit(i) + " ");
		}

		System.out.println();
		System.out.print("     ");
		for (int i = 0; i < PIT_NUMBERS.length / 2 - 1; i++) {
			System.out.print(PIT_NUMBERS[i] + " ");
		}

		System.out.println();

	}

}