/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.gui;

/**
 *
 * @author Emptak
 */
public class Tools 
{
    private Tools(){}
    
    public static void createErrorDialog(String errorMessage)
    {
       javax.swing.JOptionPane.showMessageDialog(null, errorMessage);
    }
    
    
    public static boolean isWin()
    {
        return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
    }
    
}
