import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.*;

import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * 
 */
public class PitComponent implements Icon, ChangeListener {
	private int width;
	private int height;
	private StyleManager style;
	private int stones;
	private MancalaModel model;
	private int index;
	/**
     * 
     */
	public PitComponent(int width, int height, StyleManager style, int stones,
			MancalaModel model, int index) {
		this.width = width;
		this.height = height;
		this.style = style;
		this.stones = stones;
		this.model = model;
		this.index = index;
	}

	/**
	 * @param Graphics
	 *            g
	 */
	public void paintComponent(Graphics g) {
		// TODO implement here
	}

	public int getIconHeight() {
		// TODO Auto-generated method stub
		return height;
	}

	public int getIconWidth() {
		// TODO Auto-generated method stub
		return width;
	}

	Color[] arrayOfColors = { Color.PINK, Color.BLACK, Color.YELLOW,
			Color.CYAN, Color.WHITE, Color.ORANGE, Color.GREEN, Color.MAGENTA };

	public void paintIcon(Component c, Graphics g, int x, int y) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) g;
		Rectangle2D.Double pit = new Rectangle2D.Double(x, y, width, height);
		g2.setColor(style.setStyle());
		g2.fill(pit);
		int stoneSize = 20;
		for (int i = 0; i < stones; i++) {
			// TO DO SAMSON: Make a sin curve.
			Ellipse2D.Double stone = new Ellipse2D.Double();
			stone = new Ellipse2D.Double(x + Math.random()
					* (width - stoneSize), y + Math.random()
					* (height - stoneSize), stoneSize, stoneSize);
			g2.setColor(arrayOfColors[(int) (Math.random() * arrayOfColors.length)]);
			g2.fill(stone);
		}
		// Ellipse2D.Double wheel2 = new Ellipse2D.Double(x + width*.70, y +
		// height/4, height, height);

	}

	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		stones = model.getStones(index);
	}

}