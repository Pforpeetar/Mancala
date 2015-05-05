import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 */
public class MancalaFrame extends JPanel implements ChangeListener {

	/**
     * 
     */
	public MancalaFrame(int defaultStones, StyleManager style, final MancalaModel dataModel) {
		// setSize(new Dimension(100, 100));
		this.model = dataModel;
		this.style = style;

		JPanel pits = new JPanel();
		JPanel poop = new JPanel();
		poop.setLayout(new FlowLayout());

		pits.setLayout(new GridLayout(2, defaultStones));

		//create A pits
		for (int i = 0; i < 6; i++) {
			final PitComponent pit = new PitComponent(100, 100, this.style,
					defaultStones, model, i);
			final PitLabel pitLabel = new PitLabel(pit, i, model);
			pitLabel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent event) {
					//System.out.println("Pit Label: " + pitLabel.getPitIndex() + " Stones: " + pitLabel.getStone());
					//System.out.println("Model Pit: " + model.getStones(pitLabel.getPitIndex()) + " Stones: " + model.getStones(pitLabel.getPitIndex()));					
					if (model.getTurn()) {
						model.updateGameState(pitLabel.getPitIndex());
						model.update();			
					}
				}
			});
			pits.add(pitLabel);
			model.addChangeListener(pitLabel);
			model.addChangeListener(pit);
		}
		
		for (int i = 7; i < 13; i++) {
			final PitComponent pit = new PitComponent(100, 100, this.style,
					defaultStones, model, i);
			final PitLabel pitLabel = new PitLabel(pit, i, model);
			pitLabel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent event) {
					//System.out.println("Pit Label: " + pitLabel.getPitIndex() + " Stones: " + pitLabel.getStone());
					//System.out.println("Model Pit: " + model.getStones(pitLabel.getPitIndex()) + " Stones: " + model.getStones(pitLabel.getPitIndex()));					
					if (!model.getTurn()) {
						model.updateGameState(pitLabel.getPitIndex());
						model.update();			
					}
				}
			});
			pits.add(pitLabel);
			model.addChangeListener(pitLabel);
			model.addChangeListener(pit);
		}
		
		final PitComponent playerA = new PitComponent(100, 200, this.style,
				0, model, 6);
		final PitLabel playerALabel = new PitLabel(playerA, 6, model);
		final PitComponent playerB = new PitComponent(100, 200, this.style,
				0, model, 13);
		final PitLabel playerBLabel = new PitLabel(playerB, 13, model);
		model.addChangeListener(playerA);
		model.addChangeListener(playerALabel);
		model.addChangeListener(playerB);
		model.addChangeListener(playerBLabel);
		poop.add(playerALabel);
		poop.add(pits);
		poop.add(playerBLabel);
		add(poop);
		if (model.getTurn()) {
			playerTurn = new JLabel("Player B Turn");
		} else {
			playerTurn = new JLabel("Player A Turn");
		}
		add(playerTurn);
	}

	/**
     * 
     */
	private StyleManager style;

	/**
     * 
     */
	private MancalaModel model;
	JLabel playerTurn;
	public void stateChanged(ChangeEvent arg0) {
		// TODO Auto-generated method stub
		if (model.getTurn()) {
			playerTurn.setText("Player B Turn");
		} else {
			playerTurn.setText("Player A Turn");
		}
		playerTurn.repaint();
		repaint();
	}

}