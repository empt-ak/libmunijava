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
public class TicketDTO
{
    private Long ticketID;    
    private UserDTO user;    
    private DateTime borrowTime;    
    private DateTime dueTime;    
    private DateTime returnTime;    
    private List<TicketItemDTO> ticketItems;

    public Long getTicketID() {
        return ticketID;
    }

    public void setTicketID(Long ticketID) {
        this.ticketID = ticketID;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
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

    public List<TicketItemDTO> getTicketItems() {
        return ticketItems;
    }

    public void setTicketItems(List<TicketItemDTO> ticketItems) {
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
        final TicketDTO other = (TicketDTO) obj;
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
