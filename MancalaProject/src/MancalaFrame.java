import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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

		//create B pits
		for (int i = 12; i >= 7; i--) {
			final PitComponent pit = new PitComponent(100, 100, this.style,
					defaultStones, model, i);
			final PitLabel pitLabel = new PitLabel(pit, i, model);
			pitLabel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent event) {
					//if B's turn, call step method on index of pressed pit
					//update model, to call stateChanged
					if (model.getTurn()) {
						model.mancalaStep(pitLabel.getPitIndex());
						model.update();		
						playerStepped = true;
						if(firstATime == false){
							firstTime = true;
							firstATime = true;
						}
					}
				}
			});
			pits.add(pitLabel);
			model.addChangeListener(pitLabel);
			model.addChangeListener(pit);
		}
		
		//create A pits
		for (int i = 0; i < 6; i++) {
			final PitComponent pit = new PitComponent(100, 100, this.style,
					defaultStones, model, i);
			final PitLabel pitLabel = new PitLabel(pit, i, model);
			pitLabel.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent event) {
					//if A's turn, call step method on index of pressed pit
					//update model, to call stateChanged
					if (!model.getTurn()) {
						model.mancalaStep(pitLabel.getPitIndex());
						model.update();		
						playerStepped = true;
						if(firstBTime==false){
							firstTime = true;
							firstBTime = true;
						}
					}
				}
			});
			pits.add(pitLabel);
			model.addChangeListener(pitLabel);
			model.addChangeListener(pit);
		}
		
		//I swapped the numbers for playerA and playerB
		final PitComponent playerA = new PitComponent(100, 200, this.style,
				0, model, 13);
		final PitLabel playerALabel = new PitLabel(playerA, 13, model);
		final PitComponent playerB = new PitComponent(100, 200, this.style,
				0, model, 6);
		final PitLabel playerBLabel = new PitLabel(playerB, 6, model);
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
		
		JButton undo = new JButton("undo");
		undo.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//make it so that the undo button doesn't work anymore for a player after it is pressed 3 times!
				if(PlayerAUndoCount>2 || PlayerBUndoCount>2){
					firstTime = false;
					if(PlayerAUndoCount>2){ PlayerAUndoCount = 0; firstATime = false;}
					if(PlayerBUndoCount>2){ PlayerBUndoCount = 0; firstBTime = false;}
					model.resetPrev();
				} else if(playerStepped==true&&firstTime==true){
					model.mancalaUndo(model.getTurn());	
					if(model.checkExtraTurn()){
						model.setExtraTurntoFalse();
					}
					model.update();
					playerStepped = false;
					if(playerTurn.getText().equals("Player A Turn")){
						PlayerAUndoCount++;
						System.out.println("# of times Player A hit undo: "+ PlayerAUndoCount);
					}
					if(playerTurn.getText().equals("Player B Turn")){
						PlayerBUndoCount++;
						System.out.println("# of times Player B hit undo: " +PlayerBUndoCount);
					}
					if(PlayerBUndoCount==2||PlayerAUndoCount==2){
						System.out.println("You have one undo left. Use it wisely!");
					}
				}
			}
		});
		JButton quit = new JButton("quit");
		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}		
		});
		add(undo);
		add(quit);
		
	}

	/**
     * 
     */
	private StyleManager style;

	/**
     * 
     */
	private MancalaModel model;
	
	/**
	 * 
	 */
	JLabel playerTurn;
	
	private int PlayerAUndoCount = 0;
	private int PlayerBUndoCount = 0;
	private boolean playerStepped = false;
	private boolean firstTime = true;
	private boolean firstATime = true;
	private boolean firstBTime = true;
	
	/**
	 * 
	 */
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