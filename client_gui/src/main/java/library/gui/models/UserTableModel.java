/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.gui.models;

import java.util.ArrayList;
import java.util.List;
import library.webservice.UserDTO;


/**
 *
 * @author Emptak
 */
public class UserTableModel extends javax.swing.table.AbstractTableModel
{
    List<UserDTO> users = new ArrayList<>();
    java.util.ResourceBundle bundle = java.util.ResourceBundle.getBundle("Messages");
    
    public UserTableModel()
    {
        UserDTO u = new UserDTO();
        u.setUsername("DATABASE");
        u.setRealName("ERROR");
        u.setUserID(new Long(-1));
        u.setSystemRole("ERROR");
        
        this.users.add(u);
    }
    
    public void addUser(UserDTO user)
    {
        this.users.add(user);
        refresh();
    }
    
    public void addUsers(List<UserDTO> users)
    {
        this.users.clear();
        this.users.addAll(users);
        refresh();
    }
    
    public UserDTO getUserAt(int index)
    {
        return users.get(index);
    }
    
    public void clear()
    {
        this.users.clear();
    }
    
    public void refresh()
    {
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        UserDTO u = users.get(rowIndex);
        
        switch(columnIndex)
        {
            case 0: return u.getUserID();
            case 1: return u.getUsername();
            case 2: return u.getRealName();
            case 3: return u.getSystemRole();
            default: throw new IllegalArgumentException("no such column");
        }
    }

    @Override
    public String getColumnName(int column) 
    {
        switch(column)
        {
            case 0: return bundle.getString("gui.user.userID");
            case 1: return bundle.getString("gui.user.username");
            case 2: return bundle.getString("gui.user.realname");
            case 3: return bundle.getString("gui.user.systemrole");
            default : throw new IllegalArgumentException("no such column");
        }
    }

}
