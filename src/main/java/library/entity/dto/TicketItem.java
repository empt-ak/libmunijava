/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.entity.dto;

import library.entity.enums.TicketItemStatus;
import java.util.Objects;



/**
 *
 * @author Gajdos
 */
public class TicketItem
{
    private Long ticketItemID;
    private Book book;
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
