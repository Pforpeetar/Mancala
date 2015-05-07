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
	 * @param ChangeListener
	 *            c
	 */
	public void addChangeListener(ChangeListener c) {
		listeners.add(c);
	}

	/**
	 * @param int pit
	 */
	public void updateGameState(int pit) {
		mancalaData.step(pit);
		//check if game is over after each step. if so, determine winner
		if(mancalaData.gameOverCheck()){
			mancalaData.determineWinner();
		}
	}

	/**
	 * @return
	 */
	public ArrayList<Integer> getGameState() {
		return mancalaData.getBoard();
	}
	
	public int getStones(int pit) {
		//System.out.println("Get Stone in Pit " + pit + " is " + mancalaData.getStonesInPit(pit));
		return mancalaData.getStonesInPit(pit);
	}
	
	public boolean getTurn() {
		return mancalaData.getTurn();
	}

	public void update() {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
}