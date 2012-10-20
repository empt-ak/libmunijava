/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.entity;

import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

/**
 *
 * @author Szalai
 */
@Entity
@Table(name="ticket")
public class Ticket implements java.io.Serializable
{
    private static final long serialVersionUID = 1202246631796977382L;
    
    @Id
    @GeneratedValue
    @Column(name="ticketID")
    private Long ticketID;
    
    @ManyToOne
    @JoinColumn(name="userID")
    private User user;
    
    // maps Joda-Time DateTime for persisting to a database using Hibernate
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name="borrowTime",nullable=false)
    private DateTime borrowTime;
    
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name="dueTime")
    private DateTime dueTime;
    
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @Column(name="returnTime")
    private DateTime returnTime;
    
    
    @OneToMany(fetch = FetchType.EAGER)
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
        return "Ticket{" + "ticketID=" + ticketID + ", user=" + user + ", borrowTime=" + borrowTime + ", dueTime=" + dueTime + ", returnTime=" + returnTime + ", ticketItems=" + ticketItems + '}';
    }
    
}
