/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.entity.dto;

import java.util.Objects;
import library.entity.enums.BookStatus;
import library.entity.enums.Department;

/**
 *
 * @author Nemko
 */
public class BookDTO
{
    private Long bookID;    
    private String title;    
    private String author;    
    private Department department;   
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
        final BookDTO other = (BookDTO) obj;
        if (!Objects.equals(this.bookID, other.bookID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BookDTO{" + "bookID=" + bookID + ", title=" + title + ", author=" + author + ", department=" + department + ", bookStatus=" + bookStatus + '}';
    }
}
