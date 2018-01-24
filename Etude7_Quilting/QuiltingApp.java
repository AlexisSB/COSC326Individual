package quilting;

import javax.swing.*;

/**
 * Quilting application class.
 * Draws a series of squares as per specification.
 * COSC326 Etude 7 SS 2018
 * @author Alexis Barltrop
 */
public class QuiltingApp{

    public static void main(String[] args){
        JFrame frame = new JFrame("Quilting");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        QuiltingPanel quilting = new QuiltingPanel();
        frame.getContentPane().add (quilting);
        frame.pack();
        frame.setVisible(true);
    
    }
}
