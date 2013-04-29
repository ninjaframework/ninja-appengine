package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import models.User;

import org.junit.Test;

import com.googlecode.objectify.Objectify;

import conf.OfyService;

public class UserTest extends NinjaAppengineBackendTest {

    @Test
    public void createAndRetrieveUser() {
        
        Objectify ofy = OfyService.ofy(); 
        
        
        // Create a new user and save it
        User user = new User("bob@gmail.com", "secret", "Bob");
        ofy.save().entity(user).now();
        

        // Retrieve the user with e-mail address bob@gmail.com
        User bob = ofy.load().type(User.class).filter("username", "bob@gmail.com").first().get();

        // Test
        assertNotNull(bob);
        assertEquals("Bob", bob.fullname);
    }

}
