import java.util.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Model class to hold information of mancala game
 * @author Samson Lee, Peter Pham, Benjamin Liu
 */
public class MancalaModel {

	/**
	 * Instance variable of MancalaGame to hold game information.
	 */
	MancalaGame mancalaData;
	
	/**
	 * List of attached change listeners. Notifies each attached listener when there is a change in the data (game state)
	 */
	ArrayList<ChangeListener> listeners;

	/**
	 * Constructor of model
	 * @param defaultPitStones default number of stones per pit
	 */
	MancalaModel(int defaultPitStones) {
		mancalaData = new MancalaGame(defaultPitStones);
		listeners = new ArrayList<ChangeListener>();
	}

	/**
	 * Attach change listeners to model class that care about model updates
	 * @param c ChangeListener to be added to model. (attach method)
	 */
	public void addChangeListener(ChangeListener c) {
		listeners.add(c);
	}

	/**
	 * Method to erase previous board. called if third undo is used. 
	 */
	public void resetPrev(){
		mancalaData.erasePrevious();
	}
	
	/**
	 * Call undo method of mancalaGame.
	 * @param playerTurn current player before undo.
	 */
	public void mancalaUndo(boolean playerTurn){
		mancalaData.setPlayerTurn(playerTurn);
		mancalaData.undo();
	}
	
	/**
	 * Call step method of mancalaGame
	 * @param int pit step at index pit
	 */
	public void mancalaStep(int pit) {
		mancalaData.step(pit);
		//if game is over after each step move. if so, determine winner
		if(mancalaData.gameOverCheck())
			mancalaData.determineWinner();
	}

	/**
	 * Get state of game
	 * @return board information of this game
	 */
	public ArrayList<Integer> getGameState() {
		return mancalaData.getBoard();
	}
	
	/**
	 * Check if current player had extra turn
	 * @return true if had extra turn, false if not
	 */
	public boolean checkExtraTurn(){
		return mancalaData.checkExtraTurn();
	}
	
	/**
	 * Method to give correct player control, but player had extra turn and pressed undo. Called if the player was granted an extra turn, but wants to undo. 
	 *	 if A gets extra turn, but undos. just undo method gives player B control. This method fixes that. 
	 */
	public void setExtraTurntoFalse(){
		//sets extraTurn back to false; 
		mancalaData.setExtraTurn(false);
		//Gives player who pressed undo after getting an extra turn his/her turn back. 
		mancalaData.setPlayerTurn(!mancalaData.getTurn());
	}
	
	/**
	 * Get number of stones in index pit
	 * @param pit index of interest
	 * @return number of stones in selected pit
	 */
	public int getStones(int pit) {
		//System.out.println("Get Stone in Pit " + pit + " is " + mancalaData.getStonesInPit(pit));
		return mancalaData.getStonesInPit(pit);
	}
	
	/**
	 * Get current player information. 
	 * @return B if true, A if false
	 */
	public boolean getTurn() {
		return mancalaData.getTurn();
	}

	/**
	 *||IMPORTANT METHOD||
	 * Update the attached change listeners with the current model information. Controller will call this method, view will be notified and 
	 * 	 	will repaint.
	 */
	public void update() {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
}