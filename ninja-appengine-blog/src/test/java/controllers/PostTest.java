package controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import models.Article;
import models.Comment;
import models.User;

import org.junit.Test;

import com.googlecode.objectify.Objectify;

import conf.OfyService;

public class PostTest extends NinjaAppengineBackendTest {


    
    @Test
    public void testCreatePost() {
        
        Objectify ofy = OfyService.ofy(); 
        
        
        // Create a new user and save it
        User anotherBob = new User("another_bob@gmail.com", "secret", "Bob");
        ofy.save().entity(anotherBob).now();
        
        // Create a new post
        Article post = new Article(anotherBob, "My first post", "Hello world");
        ofy.save().entity(post).now();
             
        // Test that the post has been created
        assertNotNull(ofy.load().type(Article.class).first().get());
        
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
    
    
    @Test
    public void testUseTheCommentsRelation() {
        
        

        
        Objectify ofy = OfyService.ofy();  
        
        
        // Create a new user and save it
        User bob = new User("bob@gmail.com", "secret", "Bob");
        ofy.save().entity(bob).now();
     
        // Create a new post
        Article bobPost = new Article(bob, "My first post", "Hello world");
        ofy.save().entity(bobPost).now();
     
        // Post a first comment
        bobPost.addComment("Jeff", "Nice post");
        bobPost.addComment("Tom", "I knew that !");
     
        // Count things
        assertEquals(1, ofy.load().type(User.class).list().size());
        assertEquals(1, ofy.load().type(Article.class).list().size());
        assertEquals(2, ofy.load().type(Comment.class).list().size());
     
        // Retrieve Bob's post
        bobPost = ofy.load().type(Article.class).filter("authorIds", bob.id).first().get();
        assertNotNull(bobPost);
        
        // Delete the post
        List<Comment> commentsToDelete =  ofy.load().type(Comment.class).filter("postId", bobPost.id).list();
        ofy.delete().entities(commentsToDelete); // delete in batch

        
        // Check that all comments have been deleted
        assertEquals(1, ofy.load().type(User.class).list().size());
        assertEquals(1, ofy.load().type(Article.class).list().size());
        assertEquals(0, ofy.load().type(Comment.class).list().size());
    }

}
