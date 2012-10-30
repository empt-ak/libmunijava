/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.entity.dto;


import java.util.List;
import java.util.Objects;
import org.joda.time.DateTime;

/**
 *
 * @author Szalai
 */
public class Ticket
{
    private Long ticketID;    
    private User user;    
    private DateTime borrowTime;    
    private DateTime dueTime;    
    private DateTime returnTime;    
    private List<TicketItem> ticketItems;

    public Long getTicketID() {
        return ticketID;
    }

    public void setTicketID(Long ticketID) {
        this.ticketID = ticketID;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public DateTime getBorrowTime() {
        return borrowTime;
    }

    public void setBorrowTime(DateTime borrowTime) {
        this.borrowTime = borrowTime;
    }

    public DateTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(DateTime dueTime) {
        this.dueTime = dueTime;
    }

    public DateTime getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(DateTime returnTime) {
        this.returnTime = returnTime;
    }

    public List<TicketItem> getTicketItems() {
        return ticketItems;
    }

    public void setTicketItems(List<TicketItem> ticketItems) {
        this.ticketItems = ticketItems;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.ticketID);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ticket other = (Ticket) obj;
        if (!Objects.equals(this.ticketID, other.ticketID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TicketDTO{" + "ticketID=" + ticketID + ", user=" + user + ", borrowTime=" + borrowTime + ", dueTime=" + dueTime + ", returnTime=" + returnTime + ", ticketItems=" + ticketItems + '}';
    }
    
}
