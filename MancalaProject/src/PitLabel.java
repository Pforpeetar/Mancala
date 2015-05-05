import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class PitLabel extends JPanel implements ChangeListener {
	public int index;
	public int stoneAmount;
	public String playerSide;
	public boolean isPlayer;

	public PitLabel(PitComponent pit, int i, int s, String pSide, boolean ip) {
		// TODO Auto-generated constructor stub
		isPlayer = ip;
		JLabel label = new JLabel(pit);
		stoneAmount = s;
		index = i;
		playerSide = pSide;
		add(label);
		add(new JLabel("" + stoneAmount));
		if (!isPlayer) {
			if (playerSide == "B") {
			add(new JLabel(playerSide + (index+1)));
			} else {
			add(new JLabel(playerSide + (index+-6)));
			}
		}
	}

	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub

	}

}
