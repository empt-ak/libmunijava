/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.models;

import library.webservice.Department;

/**
 *
 * @author Emptak
 */
public class DepartmentBoxModel 
    extends javax.swing.AbstractListModel<Department> 
    implements javax.swing.ComboBoxModel<Department>
{
    private java.util.List<library.webservice.Department> list = java.util.Arrays.asList(Department.values());
    private int selectedItem = 0;
    

    @Override
    public int getSize() {
        return list.size();
    }

    @Override
    public Department getElementAt(int index) {
        return list.get(index);
    }

    @Override
    public void setSelectedItem(Object anItem) {
        Department d = (Department) anItem;
        selectedItem = list.indexOf(d);
    }

    @Override
    public Object getSelectedItem() {
        return list.get(selectedItem);
    }
    
}
