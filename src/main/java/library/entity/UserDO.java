/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package library.entity;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Nemcek
 */
@Entity
@Table(name="Users")
public class UserDO implements java.io.Serializable
{
    private static final long serialVersionUID = -5804765234884503762L;
    
    @Id
    @GeneratedValue
    @Column(name="userID",length=10)
    private Long userID;
    
    @Column(name="username",unique=true,length=12,nullable=false)
    private String username;
    
    @Column(name="password",length=40,nullable=false)
    private String password;
    
    @Column(name="realName",length=50,nullable=false)
    private String realName;
    
    @Column(name="systemRole",length=13,nullable=false)
    private String systemRole;

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getSystemRole() {
        return systemRole;
    }

    public void setSystemRole(String systemRole) {
        this.systemRole = systemRole;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.userID);
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
        final UserDO other = (UserDO) obj;
        if (!Objects.equals(this.userID, other.userID)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "User{" + "userID=" + userID + ", username=" + username + ", password=" + password + ", realName=" + realName + ", systemRole=" + systemRole + '}';
    }
    
    
    
    
    
}
