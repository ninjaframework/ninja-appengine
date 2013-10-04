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
    
    public static String post1Title = "Hello to the blog example!";
    public static String post1Content = 
            "<p>Hi and welcome to the demo of Ninja!</p> "
            + "<p>This example shows how you can use Ninja in the wild. Some things you can learn:</p>"
            + "<ul>"
            + "<li>How to use the templating system (header, footer)</li>"
            + "<li>How to test your application with ease.</li>"
            + "<li>Setting up authentication (login / logout)</li>"
            + "<li>Internationalization (i18n)</li>" 
            + "<li>Static assets / using webjars</li>"
            + "<li>Persisting data</li>"
            + "<li>Beautiful <a href=\"/article/3\">html routes</a> for your application</li>"
            + "<li>How to design your restful Api (<a href=\"/api/bob@gmail.com/articles.json\">Json</a> and <a href=\"/api/bob@gmail.com/articles.xml\">Xml</a>)</li>"
            + "<li>... and much much more.</li>"
            + "</ul>" 
            + "<p>We are always happy to see you on our mailing list! "
            + "Check out <a href=\"http://www.ninjaframework.org\">our website for more</a>.</p>";

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

    public static void setup() {

        String lipsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit sed nisl sed lorem commodo elementum in a leo. Aliquam erat volutpat. Nulla libero odio, consectetur eget rutrum ac, varius vitae orci. Suspendisse facilisis tempus elit, facilisis ultricies massa condimentum in. Aenean id felis libero. Quisque nisl eros, accumsan eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula. eget ornare id, pharetra eget felis. Aenean purus erat, egestas nec scelerisque non, eleifend id ligula.";

        Objectify ofy = ofy();
        User user = ofy.load().type(User.class).first().now();

        if (user == null) {

            // Create a new user and save it
            User bob = new User("bob@gmail.com", "secret", "Bob");
            ofy.save().entity(bob).now();
            
            // Create a new post
            Article bobPost3 = new Article(bob, "My third post", lipsum);
            ofy.save().entity(bobPost3).now();

            // Create a new post
            Article bobPost2 = new Article(bob, "My second post", lipsum);
            ofy.save().entity(bobPost2).now();
            
            // Create a new post
            Article bobPost1 = new Article(bob, post1Title, post1Content);
            ofy.save().entity(bobPost1).now();
        }

    }
}
