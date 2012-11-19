/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.client;

import java.util.Date;
import org.joda.time.DateTime;

/**
 *
 * @author emptak
 */
public class SVNEntry 
{
    private String commiter;
    private String title;
    private DateTime time;

    public String getCommiter() {
        return commiter;
    }

    public void setCommiter(String commiter) {
        this.commiter = commiter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateTime getTime() {
        return time;
    }

    public void setTime(DateTime time) {
        this.time = time;
    }

    public SVNEntry(String commiter, String title, Date time) {
        this.commiter = commiter;
        this.title = title;
        this.time = new DateTime(time); 
    }

    @Override
    public String toString() {
        return "SVNEntry{" + "commiter=" + commiter + ", title=" + title + ", time=" + time + '}';
    }

    
    
    
    
}
