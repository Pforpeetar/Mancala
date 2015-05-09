
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * Tester for the gui mancala. Uses MVC and strategy pattern. 
 * @author Samson Lee, Peter Pham, Benjamin Liu
 */
public class MancalaTester {
	/**
	 * initial frame
	 */
	static JFrame startFrame;
	
	/**
	 * constraints for gridbaglayout 
	 */
	static GridBagConstraints c;
	
	/**
	 * model that contains game information
	 */
	static MancalaModel model;
	
	public static void main(String[] arg0){
		
		startFrame = new JFrame();
    	startFrame.setSize(new Dimension(250,250));
    	c = new GridBagConstraints();
    	startFrame.setLayout(new GridBagLayout());

		selectDefaultStones();
	}
	
	/**
	 * Method to prompt user to select default number of stones
	 */
	public static void selectDefaultStones() {
		final JLabel stoneFieldPrompt = new JLabel("Enter Default Stone Amount");
		final JTextField stoneField = new JTextField("3");
		final JButton setStones = new JButton("Set");
		setStones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int stoneAmount = Integer.parseInt(stoneField.getText());
				selectStyle(stoneAmount);
				model = new MancalaModel(stoneAmount);
				startFrame.remove(stoneFieldPrompt);
				startFrame.remove(stoneField);
				startFrame.remove(setStones);
			}
			
		});
		c.gridx = 0;
		c.gridy = 0;
		startFrame.add(stoneFieldPrompt, c);
		c.gridx = 0;
		c.gridy = 1;
		startFrame.add(stoneField, c);
		c.gridy = 2;
		startFrame.add(setStones, c);
		startFrame.pack();
		startFrame.setVisible(true);
	}

	/**
	 * Method to prompt user to select preferred style. Uses strategy pattern. 
	 * 	Adds the main game frame. removes the previous user prompts. 
	 * @param defaultStones default number of stones to be used. Put into view portion. 
	 */
    public static void selectStyle(final int defaultStones) {
        // TODO implement here
    	final JLabel startTitle = new JLabel("Choose a style");
    	final JButton redStyle = new JButton("Red Style");
    	final JButton blueStyle = new JButton("Blue Style");
    	redStyle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MancalaFrame mainFrame = new MancalaFrame(defaultStones, new RedStyle(), model);
				model.addChangeListener(mainFrame);
				//mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				//mainFrame.setVisible(true);		
				c.gridx = 0;
				c.gridy = 5;
				c.gridwidth = 100;
				c.gridheight = 100;
				startFrame.add(mainFrame, c);
				startFrame.remove(startTitle);
				startFrame.remove(redStyle);
				startFrame.remove(blueStyle);
				startFrame.pack();
				startFrame.setVisible(true);
			}
    		
    	});
    	blueStyle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				MancalaFrame mainFrame = new MancalaFrame(defaultStones, new BlueStyle(), model);
				
				model.addChangeListener(mainFrame);
				//mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				//mainFrame.setVisible(true);
				c.gridx = 0;
				c.gridy = 5;
				c.gridwidth = 100;
				c.gridheight = 100;
				mainFrame.setSize(new Dimension(100, 100));
				startFrame.add(mainFrame, c);
				startFrame.remove(startTitle);
				startFrame.remove(redStyle);
				startFrame.remove(blueStyle);
				startFrame.pack();
				startFrame.setVisible(true);
			}
    		
    	});

    	c.gridx = 1;
    	c.gridy = 0;
    	c.gridwidth = 0;
    	startFrame.add(startTitle, c);
    	c.gridwidth = -100;
    	c.gridx = 0;
    	c.gridy = 1;
    	startFrame.add(redStyle, c);
    	c.gridwidth = 100;
    	c.gridx = 3;
    	c.gridy = 1;
    	startFrame.add(blueStyle, c);
    	startFrame.setTitle("MANCALA");
    	startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	startFrame.pack();
		startFrame.setVisible(true);
    }

}