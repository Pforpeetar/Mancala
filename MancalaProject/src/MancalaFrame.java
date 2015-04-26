
import java.awt.Point;
import java.util.*;

import javax.swing.event.ChangeEvent;

/**
 * 
 */
public class MancalaFrame{

	StyleManager style;
    /**
     * 
     */
    public MancalaFrame() {
    	model = new MancalaModel(3);
    }

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
        // TODO implement here
    }

}