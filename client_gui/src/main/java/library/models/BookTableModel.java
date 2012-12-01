/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.models;


import java.util.ArrayList;
import java.util.List;
import library.entity.dto.BookDTO;

/**
 *
 * @author Emptak
 */
public class BookTableModel extends javax.swing.table.AbstractTableModel
{
    private List<BookDTO> books = new ArrayList<>();
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Messages");
    
    public void addBook(BookDTO b)
    {
        this.books.add(b);
        refresh();
    }
    
    public void addBooks(List<BookDTO> bookz)
    {
        this.books.clear();
        this.books.addAll(bookz);
        refresh();
    }
    
    public BookDTO getBookAt(int index)
    {
        return books.get(index);
    }
    
    
    public void refresh()
    {
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
         return books.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BookDTO b = books.get(rowIndex);
        
        switch(columnIndex)
        {
            case 0: return b.getBookID();
            case 1: return b.getTitle();
            case 2: return b.getAuthor();
            case 3: return b.getDepartment();
            case 4: return b.getBookStatus();
            default: throw new IllegalArgumentException("meh");
        }         
    }

    @Override
    public String getColumnName(int column) {
        switch(column)
        {
            case 0: return bundle.getString("gui.book.bookid");
            case 1: return bundle.getString("gui.book.title");
            case 2: return bundle.getString("gui.book.author");
            case 3: return bundle.getString("gui.book.department");
            case 4: return bundle.getString("gui.book.availability");
            default : throw new IllegalArgumentException("peh");            
        }
    }
    
    
    
    
}
