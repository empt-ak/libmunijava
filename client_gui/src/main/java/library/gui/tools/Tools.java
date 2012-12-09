/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.gui.tools;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import library.webservice.BookDTO;
import library.webservice.UserDTO;

/**
 *
 * @author Emptak
 */
public class Tools 
{
    private Tools(){}
    
    
    /**
     * Creates error dialog from given message
     * @param errorMessage 
     */
    public static void createErrorDialog(String errorMessage)
    {
       javax.swing.JOptionPane.showMessageDialog(null, errorMessage);
    }
    
    /**
     * Is current operation system windows ?
     * @return true if win, false otherwise
     */
    public static boolean isWin()
    {
        return System.getProperty("os.name").toLowerCase().indexOf("win") >= 0;
    }
    
    /**
     * Custom entity values printer to simulate toString method, since generated classes do not have toString method.
     * Only {@link library.webservice.BookDTO} and {@link  library.webservice.UserDTO} are supported, otherwise IAE
     * is thrown
     * @param o to be printed
     * @throws IllegalArgumentException  if object is not BookDTO or UserDTO
     */
    public static void printEntity(Object o) throws IllegalArgumentException
    {
        if(o != null)
        {
            if(o instanceof BookDTO)
            {//BookDTO{bookID=1, title=DATABASE, author=novy author, department=ADULT, bookStatus=AVAILABLE}
                BookDTO b = (BookDTO) o;
                StringBuilder sb = new StringBuilder("BookDTO{bookdID=");
                sb.append(b.getBookID()).append(", title=");
                sb.append(b.getTitle()).append(", author=");
                sb.append(b.getAuthor()).append(", department=");
                sb.append(b.getDepartment()).append(",bookStatus=");
                sb.append(b.getBookStatus()).append("}");
                
                System.out.println(sb.toString());
            }
            else if(o instanceof UserDTO)
            {// "User{" + "userID=" ", username=" ", password=" ", realName=" ", systemRole=" '}';
                UserDTO u = (UserDTO) o;
                StringBuilder sb = new StringBuilder("UserDTO{usrID=");
                sb.append(u.getUserID()).append(", username=");
                sb.append(u.getUsername()).append(", password=");
                sb.append(u.getPassword()).append(", realName=");
                sb.append(u.getSystemRole()).append("}");
                
                System.out.println(sb.toString());                
            }
            else
            {
                throw new IllegalArgumentException("ERROR: Given class '"+o.getClass()+"' is not supported");
            }
        }
    }
    
    /**
     * taken from
     * http://www.anyexample.com/programming/java/java_simple_class_to_compute_sha_1_hash.xml
     * method computes sha1
     *
     * @param text to be hashed
     * @return hashed value
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md;
        md = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash = new byte[40];
        md.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9)) {
                    buf.append((char) ('0' + halfbyte));
                } else {
                    buf.append((char) ('a' + (halfbyte - 10)));
                }
                halfbyte = data[i] & 0x0F;
            } while (two_halfs++ < 1);
        }

        return buf.toString();
    }
    
}
