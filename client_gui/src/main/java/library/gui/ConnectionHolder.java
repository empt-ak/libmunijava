/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.gui;

import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import library.webservice.BookWebService;
import library.webservice.UserWebService;
import library.webservice.impl.BookWebServiceImplService;
import library.webservice.impl.UserWebServiceImplService;


/**
 *
 * @author Emptak
 */
public class ConnectionHolder 
{
    private BookWebService bws = null;
    private UserWebService uws = null;
    private static final String ERROR = java.util.ResourceBundle.getBundle("Messages").getString("gui.connection.error");
    
    
    public ConnectionHolder() throws ConnectException
    {
        if(check())
        {
            bws = new BookWebServiceImplService().getBookWebServiceImplPort();
            uws = new UserWebServiceImplService().getUserWebServiceImplPort();            
        }
        else
        {
            error();
            throw new ConnectException(ERROR);
        }         
    }

    public BookWebService getBws() throws ConnectException
    {
        if(!check())
        {
            error();
            throw new ConnectException(ERROR);
        }
        else
        {
            if(bws == null)
            {
                bws = new BookWebServiceImplService().getBookWebServiceImplPort();
            }
            return bws;
        }        
    }

    public UserWebService getUws() throws ConnectException
    {
        if(!check())
        {
            error();
            throw new ConnectException(ERROR);
        }
        else
        {
            if(uws == null)
            {
                uws = new UserWebServiceImplService().getUserWebServiceImplPort();
            }
            return uws;
        }        
    }
    
    
    private boolean check()
    {
        return checkIfURLExists("http://localhost:8080/pa165/services/wsdl/BookWebService?wsdl");
    }
    
    /**
     * Method used for checking if web service on specified port and adress is running.
     * taken from http://singztechmusings.wordpress.com/2011/05/26/java-how-to-check-if-a-web-page-exists-and-is-available/
     * 
     * @param targetUrl url to be checked
     * @return true if server is running and given url is one of ours, false otherwise
     */
    private static boolean checkIfURLExists(String targetUrl) 
    {
        HttpURLConnection httpUrlConn = null;
        boolean exists = false;
        try 
        {
            httpUrlConn = (HttpURLConnection) new URL(targetUrl).openConnection();

            httpUrlConn.setRequestMethod("HEAD");

            // Set timeouts in milliseconds
            httpUrlConn.setConnectTimeout(30000);
            httpUrlConn.setReadTimeout(30000);

            // Print HTTP status code/message for your information.
            System.out.println("Response Code: "
                                + httpUrlConn.getResponseCode());
            System.out.println("Response Message: "
                                + httpUrlConn.getResponseMessage());
            System.out.println("WEB Service is running");

            exists =  (httpUrlConn.getResponseCode() == HttpURLConnection.HTTP_OK);
        } 
        catch (Exception e) 
        {
            System.out.println("Error: " + e.getMessage());        
        }
        
        return exists;
    }
    
    private void error()
    {
        Tools.createErrorDialog(ERROR);
    }
    
}
