
import java.awt.Color;
import java.util.*;

/**
 * Strategy interface. 
 * @author Samson Lee, Peter Pham, Benjamin Liu
 */
public interface StyleManager {

    /**
     * set style according to child class
     * @return color that child prefers 
     */
    public Color setStyle();

}