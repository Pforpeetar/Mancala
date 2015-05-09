import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Label for specific pit. Is used in conjunction with pit component. Displays all information of a pit. 
 * @author Samson Lee, Peter Pham, Benjamin Liu
 *
 */
public class PitLabel extends JPanel implements ChangeListener {
	/**
	 * index of pit
	 */
	private int pitIndex;
	
	/**
	 * number of stones in specific pit
	 */
	private int stoneAmount;
	
	/**
	 * instance of model that was passed in
	 */
	private MancalaModel model;
	
	/**
	 * label of stone that contains pitComponent pit. 
	 */
	private JLabel stoneLabel;
	
	/**
	 * label of count. shows the number of stones in certain pit
	 */
	private JLabel countLabel;
	
	/**
	 * label of index as required in project rules.
	 */
	private JLabel indexLabel;
	
	/**
	 * pitComponent that paints pit and its stone(s)
	 */
	private PitComponent pit;

	/**
	 * Constructor for pit label
	 * @param pit pitComponent that paints pit and stone(s)
	 * @param i index of pitLabel in the game board
	 * @param model model for game information
	 */
	public PitLabel(PitComponent pit, int i, MancalaModel model) {
		// TODO Auto-generated constructor stub
		this.model = model;
		this.pit = pit;
		this.stoneLabel = new JLabel(pit);
		stoneAmount = model.getStones(pitIndex);
		//stoneAmount = 4;
		pitIndex = i;
		countLabel = new JLabel("" + stoneAmount);

		if (pitIndex != 6 && pitIndex != 13) {
			if (pitIndex > 6) {
			indexLabel = new JLabel("B" + (pitIndex-6) + ":");
			add(indexLabel);
			} else {
			indexLabel = new JLabel("A" + (pitIndex+1) + ":");
			add(indexLabel);
			}
		}
		if (pitIndex == 6) {
			indexLabel = new JLabel("A");
			add(indexLabel);
		}
		if (pitIndex == 13) {
			indexLabel = new JLabel("B");
			add(indexLabel);
		}
		add(countLabel);
		add(stoneLabel);
	}

	/**
	 * repaint pitLabel when there is a change in state. Set necessary changes, and repaint.
	 */
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		stoneAmount = model.getStones(pitIndex);
		countLabel.setText("" + stoneAmount);
		countLabel.repaint();
		repaint();
	}
	
	/**
	 * get index of pit this is assigned to
	 * @return index of this pit
	 */
	public int getPitIndex() {
		return pitIndex;
	}
	
	/**
	 * get number of stones in this pit
	 * @return number of stones in this pit
	 */
	public int getStone() {
		return stoneAmount;
	}

}
