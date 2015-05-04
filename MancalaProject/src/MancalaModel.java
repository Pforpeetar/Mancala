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
	}

	/**
	 * @return
	 */
	public ArrayList<Integer> getGameState() {
		return mancalaData.getBoard();
	}

	public void update() {
		for (ChangeListener l : listeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
}