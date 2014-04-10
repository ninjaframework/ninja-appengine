package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import models.Article;
import models.User;

import org.junit.Test;

import com.googlecode.objectify.Objectify;
import conf.ObjectifyProvider;

public class PostTest extends NinjaAppengineBackendTest {


    
    @Test
    public void testCreatePost() {
        
        ObjectifyProvider objectifyProvider = new ObjectifyProvider();
        Objectify ofy = objectifyProvider.get();
        
        // Create a new user and save it
        User anotherBob = new User("another_bob@gmail.com", "secret", "Bob");
        ofy.save().entity(anotherBob).now();
        
        // Create a new post
        Article post = new Article(anotherBob, "My first post", "Hello world");
        ofy.save().entity(post).now();
             
        // Test that the post has been created
        assertNotNull(ofy.load().type(Article.class).first().now());
        
        // Retrieve all posts created by Bob
        List<Article> bobPosts = ofy.load().type(Article.class)
                .filter("authorIds",  anotherBob.id).list();

        
        // Tests
        assertEquals(1, bobPosts.size());
        Article firstPost = bobPosts.get(0);
        assertNotNull(firstPost);
        assertEquals(anotherBob.id, firstPost.authorIds.get(0));
        assertEquals("My first post", firstPost.title);
        assertEquals("Hello world", firstPost.content);
        assertNotNull(firstPost.postedAt);
    }
    
}
