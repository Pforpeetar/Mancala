import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PitLabel extends JPanel implements ChangeListener {
	private int pitIndex;
	private int stoneAmount;
	private MancalaModel model;
	private JLabel stoneLabel;
	private JLabel countLabel;
	private JLabel indexLabel;
	private PitComponent pit;

	
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

	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		stoneAmount = model.getStones(pitIndex);
		countLabel.setText("" + stoneAmount);
		countLabel.repaint();
		repaint();
	}
	
	public int getPitIndex() {
		return pitIndex;
	}
	
	public int getStone() {
		return stoneAmount;
	}

}
