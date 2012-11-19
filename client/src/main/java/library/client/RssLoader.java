/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author emptak
 */
public class RssLoader {

    public static List<SVNEntry> getRSSItems(int from, int to) 
    {
        List<SVNEntry> list = new ArrayList<>();

        XmlReader reader = null;
        SyndFeed feed = null;
        List<SyndEntry> listE = new ArrayList<>();
        URL url = null;
        try 
        {
            url = new URL("http://code.google.com/feeds/p/libmunijava/svnchanges/basic?path=/trunk/");
        } 
        catch (MalformedURLException me) 
        {
        }
        try 
        {

            reader = new XmlReader(url);

            feed = new SyndFeedInput().build(reader);
            listE =  feed.getEntries();          
        } 
        catch (IOException | FeedException ex) 
        {            
        }
        finally
        {
            try 
            {
                reader.close();
            } 
            catch (IOException ex) 
            {                
            }
        }
        
        listE = listE.subList(from, to);
        
        for(SyndEntry e : listE)
        {           
            SVNEntry s = new SVNEntry(e.getAuthor(), e.getTitle(), e.getUpdatedDate());
            list.add(s);
        }



        return list;
    }
}
