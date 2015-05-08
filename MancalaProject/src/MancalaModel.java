import java.util.*;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 */
public class MancalaModel {

	MancalaGame mancalaData;
	ArrayList<ChangeListener> listeners;

	MancalaModel(int defaultPitStones) {
		mancalaData = new MancalaGame(defaultPitStones);
		listeners = new ArrayList<ChangeListener>();
	}

	/**
	 * 
	 * @param c ChangeListener to be added to model. (attach method)
	 */
	public void addChangeListener(ChangeListener c) {
		listeners.add(c);
	}

	/**
	 * method to erase previous board. called if third undo is used. 
	 */
	public void resetPrev(){
		mancalaData.erasePrevious();
	}
	
	/**
	 * 
	 * @param playerTurn current player before undo.
	 */
	public void mancalaUndo(boolean playerTurn){
		mancalaData.setPlayerTurn(playerTurn);
		mancalaData.undo();
	}
	
	/**
	 * @param int pit
	 */
	public void mancalaStep(int pit) {
		mancalaData.step(pit);
		//if game is over after each step move. if so, determine winner
		if(mancalaData.gameOverCheck())
			mancalaData.determineWinner();
	}

	/**
	 * @return
	 */
	public ArrayList<Integer> getGameState() {
		return mancalaData.getBoard();
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean checkExtraTurn(){
		return mancalaData.checkExtraTurn();
	}
	
	/**
	 * method to give correct player control, but player had extra turn and pressed undo. Called if the player was granted an extra turn, but wants to undo. 
	 * if A gets extra turn, but undos. just undo method gives player B control. This method fixes that. 
	 */
	public void setExtraTurntoFalse(){
		//sets extraTurn back to false; 
		mancalaData.setExtraTurn(false);
		//Gives player who pressed undo after getting an extra turn his/her turn back. 
		mancalaData.setPlayerTurn(!mancalaData.getTurn());
	}
	
	/**
	 * @param pit
	 * @return
	 */
	public int getStones(int pit) {
		//System.out.println("Get Stone in Pit " + pit + " is " + mancalaData.getStonesInPit(pit));
		return mancalaData.getStonesInPit(pit);
	}
	
	/**
	 * 
	 * @return B if true, A if false
	 */
	public boolean getTurn() {
		return mancalaData.getTurn();
	}

	public void update() {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
}