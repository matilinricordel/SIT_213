package visualisations;
	
/** 
 * @author B. Prou
 * Updated by E. Cousin - 2021
 *
 */	

import java.util.*;
import javax.swing.*;

/**
 * 
 */
public class Vue extends JFrame{
    private static LinkedList<Vue> lesVues=new LinkedList<Vue>();
    private static final long serialVersionUID = 1917L;
    
    /**
     * 
     */
    protected  static int xPosition = 0;
    /**
     * 
     */
    protected  static int yPosition = 0;
    /**
     * 
     */
    private static int yDecalage = 200;
 
    /**
     * @return .
     */
    public static int getXPosition() {
	xPosition += 0;
	return xPosition - 0;
    }  

    /**
     * @return .
     */
    public static int getYPosition() {
	yPosition += yDecalage;
	return yPosition - yDecalage;
    }  
   	
   
    /**
     * @param nom .
     */
    public  Vue (String nom) {          
	super(nom);
	lesVues.add(this);
    }
    /**
     * 
     */
    public static void resetPosition(){
	yPosition = 0;
    }
    /**
     * @param x .
     */
    public static void setXPosition(int x){
	xPosition = x;
    }
    /**
     * 
     */
    public static void kill(){
	for(Vue v:lesVues)
	    v.dispose();
	lesVues.clear();
	resetPosition();
    }
   
}
