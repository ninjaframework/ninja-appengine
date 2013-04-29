#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package models;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
@Index
public class Comment {

    @Id
    public Long id;
    public String author;
    public Date postedAt;     

    public String content;
   
    public Long postId;
    
    public Comment() {}
    
    public Comment(Article post, String author, String content) {
        this.postId = post.id;
        this.author = author;
        this.content = content;
        this.postedAt = new Date();
    }
 
}