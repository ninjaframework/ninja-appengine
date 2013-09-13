package models;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
public class User {
    
    @Id
    public Long id;
    public String username;
    public String password;
    public String fullname;
    public boolean isAdmin;
    
    public User() {}
    
    public User(String username, String password, String fullname) {
        this.username = username;
        this.password = password;
        this.fullname = fullname;
    }
 
}
