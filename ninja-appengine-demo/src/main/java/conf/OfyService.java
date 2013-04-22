package conf;

import models.Comment;

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
        ObjectifyService.register(Comment.class);
    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
    
  

}
