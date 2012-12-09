/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.gui.models;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Emptak
 */
public class UserRoleBoxModel 
    extends javax.swing.AbstractListModel<String>
    implements javax.swing.ComboBoxModel<String>
{
    private List<String> values = Arrays.asList("ADMINISTRATOR","USER");
    private int selectedItem = 0;

    @Override
    public int getSize() {
        return values.size();
    }

    @Override
    public String getElementAt(int index) {
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
