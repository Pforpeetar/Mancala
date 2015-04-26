
import java.awt.Point;
import java.util.*;

import javax.swing.event.ChangeEvent;

/**
 * 
 */
public class MancalaFrame{

	
	
    /**
     * 
     */
    public MancalaFrame(int defaultStones, String style) {
    	model = new MancalaModel(defaultStones);
    	if(style.equals("red"))
    		changeStyle(new RedStyle());
    	else if(style.equals("blue"))
    		changeStyle(new BlueStyle());	
    	
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
     * @param StyleManager style
     */
    public void changeStyle(StyleManager style) {
        this.style = style;
    }

}