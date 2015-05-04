import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PitLabel extends JPanel implements ChangeListener {
	public int index;
	public int stoneAmount;

	public PitLabel(PitComponent pit, int i, int s) {
		// TODO Auto-generated constructor stub

		JLabel label = new JLabel(pit);
		stoneAmount = s;
		index = i;
		add(label);
		add(new JLabel("" + stoneAmount));
	}

	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub

	}

}
