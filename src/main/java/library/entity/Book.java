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

/**
 *
 * @author Gaspar
 */
@Entity
public class Book implements java.io.Serializable
{
    private static final long serialVersionUID = 7515477206657718701L;
    
    @Id
    @GeneratedValue
    @Column(name="bookID",length=10)
    private Long bookID;
    
    @Column(name="title",length=100,nullable=false)
    private String title;
    
    @Column(name="author",length=100,nullable=false)
    private String author;
    
    @Column(name="department",nullable=false)
    @Enumerated(EnumType.STRING)
    private Department department;
    
    @Column(name="bookStatus",nullable=false)
    @Enumerated(EnumType.STRING)
    private BookStatus bookStatus;

    public Long getBookID() {
        return bookID;
    }

    public void setBookID(Long bookID) {
        this.bookID = bookID;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.bookID);
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
        final Book other = (Book) obj;
        if (!Objects.equals(this.bookID, other.bookID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Book{" + "bookID=" + bookID + ", title=" + title + ", author=" + author + ", department=" + department + ", bookStatus=" + bookStatus + '}';
    }
}
