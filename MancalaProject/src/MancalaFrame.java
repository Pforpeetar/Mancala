import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;

/**
 * 
 */
public class MancalaFrame extends JPanel {

	/**
     * 
     */
	public MancalaFrame(int defaultStones, String style) {
		// setSize(new Dimension(100, 100));
		model = new MancalaModel(defaultStones);
		if (style.equals("red"))
			changeStyle(new RedStyle());
		else if (style.equals("blue"))
			changeStyle(new BlueStyle());

		JPanel pits = new JPanel();
		JPanel poop = new JPanel();
		poop.setLayout(new FlowLayout());

		pits.setLayout(new GridLayout(2, defaultStones));

		//create A pits
		for (int i = 0; i < 6; i++) {
			final PitComponent pit = new PitComponent(100, 100, this.style,
					defaultStones, model);
			final PitLabel pitLabel = new PitLabel(pit, i, defaultStones, "B", false);
			pitLabel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent event) {
					// model.updateGameState(pitLabel.index);
					System.out.println(pitLabel.index);
				}
			});
			pits.add(pitLabel);
		}
		
		for (int i = 7; i < 13; i++) {
			final PitComponent pit = new PitComponent(100, 100, this.style,
					defaultStones, model);
			final PitLabel pitLabel = new PitLabel(pit, i, defaultStones, "A", false);
			pitLabel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent event) {
					// model.updateGameState(pitLabel.index);
					System.out.println(pitLabel.index);
				}
			});
			pits.add(pitLabel);
		}
		
		final PitComponent playerA = new PitComponent(100, 200, this.style,
				0, model);
		final PitLabel playerALabel = new PitLabel(playerA, 6, 0, "A", true);
		final PitComponent playerB = new PitComponent(100, 200, this.style,
				0, model);
		final PitLabel playerBLabel = new PitLabel(playerB, 13, 0, "B", true);
		
		poop.add(playerALabel);
		poop.add(pits);
		poop.add(playerBLabel);
		add(poop);
	}

	/**
     * 
     */
	public StyleManager style;

	/**
     * 
     */
	public Point mousePoint;

	/**
     * 
     */
	public MancalaModel model;

	/**
	 * @param StyleManager
	 *            style
	 */
	public void changeStyle(StyleManager style) {
		this.style = style;
	}

}