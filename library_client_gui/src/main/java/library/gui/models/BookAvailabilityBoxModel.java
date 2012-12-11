/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.gui.models;

import library.webservice.BookStatus;

/**
 *
 * @author Emptak
 */
public class BookAvailabilityBoxModel
    extends javax.swing.AbstractListModel<BookStatus>
    implements javax.swing.ComboBoxModel<BookStatus>
{
    
    private java.util.List<BookStatus> values = java.util.Arrays.asList(BookStatus.values());
    private int selectedItem = 0;    

    @Override
    public int getSize() {
        return values.size();
    }

    @Override
    public BookStatus getElementAt(int index) {
        return values.get(index);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        selectedItem = values.indexOf(anItem);
    }

    @Override
    public Object getSelectedItem() {
        return values.get(selectedItem);
    }
}
