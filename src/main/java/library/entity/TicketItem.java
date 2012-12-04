/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Gajdos
 */
@Entity
@Table(name="ticketItem")
public class TicketItem implements java.io.Serializable
{
    private static final long serialVersionUID = 4734694315399882778L;
    
    @Id
    @GeneratedValue
    @Column(name="ticketItemID")
    private Long ticketItemID;
    
    @ManyToOne(optional=false)
    private Book book;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private TicketItemStatus ticketItemStatus;

    public Long getTicketItemID() {
        return ticketItemID;
    }

    public void setTicketItemID(Long ticketItemID) {
        this.ticketItemID = ticketItemID;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public TicketItemStatus getTicketItemStatus() {
        return ticketItemStatus;
    }

    public void setTicketItemStatus(TicketItemStatus ticketItemStatus) {
        this.ticketItemStatus = ticketItemStatus;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.ticketItemID);
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
        final TicketItem other = (TicketItem) obj;
        if (!Objects.equals(this.ticketItemID, other.ticketItemID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TicketItem{" + "ticketItemID=" + ticketItemID + ", book=" + book + ", ticketItemStatus=" + ticketItemStatus + '}';
    }
    
    
    
    
}
