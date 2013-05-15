package conf;

import models.Article;
import models.User;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

/**
 * Best practise for Objectify to register your entities.
 * 
 * @author ra
 *
 */
public class OfyService {
    static {
        ObjectifyService.register(User.class);
        ObjectifyService.register(Article.class);
        
        setup();
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

    
    
    private static void setup() {
        
 
        String lipsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit sed nisl sed lorem commodo elementum in a leo. Aliquam erat volutpat. Nulla libero odio, consectetur eget rutrum ac, varius vitae orci. Suspendisse facilisis tempus elit, facilisis ultricies massa condimentum in. Aenean id felis libero. Quisque nisl eros, accumsan eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula.";
        
        Objectify ofy = ofy();
        User user = ofy.load().type(User.class).first().get();
        
        if (user == null) {

            // Create a new user and save it
            User bob = new User("bob@gmail.com", "secret", "Bob");
            ofy.save().entity(bob);

            // Create a new post
            Article bobPost = new Article(bob, "My first post", lipsum);
            ofy.save().entity(bobPost);
            
            // Create a new post
            Article bobPost2 = new Article(bob, "My second post", lipsum);
            ofy.save().entity(bobPost2);
            
            // Create a new post
            Article bobPost3 = new Article(bob, "My third post", lipsum);
            ofy.save().entity(bobPost3);

        }
        
        
    }
    
    
    
}
