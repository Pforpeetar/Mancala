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
 * Delegated pit Component. This creates a pit with the preferred style, stones per pit.
 * This is considered the View portion. 
 * @author Samson Lee, Peter Pham, Benjamin Liu
 */
public class PitComponent implements Icon, ChangeListener {
	/**
	 * width of pit
	 */
	private int width;
	
	/**
	 * height of pit
	 */
	private int height;
	
	/**
	 * style of pit
	 */
	private StyleManager style;
	
	/**
	 * number of stones in pit
	 */
	private int stones;
	
	/**
	 * model data
	 */
	private MancalaModel model;
	
	/**
	 * index of this pit
	 */
	private int index;
	
	/**
	 * Constructor for pit component
	 * @param width width of this
	 * @param height height of this
	 * @param style style of this
	 * @param stones stones in this
	 * @param model model info 
	 * @param index index of this
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
	 * get height of this component
	 */
	public int getIconHeight() {
		return height;
	}

	/**
	 * get width of this component
	 */
	public int getIconWidth() {
		return width;
	}

	/**
	 * color choices of the stones in this component
	 */
	Color[] arrayOfColors = { Color.PINK, Color.BLACK, Color.YELLOW,
			Color.CYAN, Color.WHITE, Color.ORANGE, Color.GREEN, Color.MAGENTA };

	/**
	 * method of Icon interface. paints the pit.
	 */
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

	/**
	 * if there is a state change, reset necessary elements according to model data.
	 */
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		stones = model.getStones(index);
	}

}