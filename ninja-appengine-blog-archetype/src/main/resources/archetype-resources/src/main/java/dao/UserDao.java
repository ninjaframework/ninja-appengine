#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package dao;

import models.User;

import com.googlecode.objectify.Objectify;

import conf.OfyService;

public class UserDao {

    
    public boolean isUserAndPasswordValid(String username, String password) {
        
        if (username != null && password != null) {

            Objectify ofy = OfyService.ofy();
            User user = ofy.load().type(User.class)
                    .filter("username", username).first().now();
            
            if (user != null) {
                
                if (user.password.equals(password)) {

                    return true;
                }
                
            }

        }
        
        return false;
 
    }

}
