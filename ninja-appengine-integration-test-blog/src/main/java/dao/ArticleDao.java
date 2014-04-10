package dao;

import java.util.List;
import java.util.Map;

import models.Article;
import models.ArticleDto;
import models.ArticlesDto;
import models.User;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.googlecode.objectify.Objectify;

public class ArticleDao {
    
    @Inject
    Provider<Objectify> objectify;
    
    public ArticlesDto getAllArticles() {
        
        ArticlesDto articlesDto = new ArticlesDto();
        articlesDto.articles = objectify.get().load().type(Article.class).list();
        
        return articlesDto;
        
    }
    
    
    public Article getFirstArticleForFrontPage() {
        
        Article frontPost = objectify.get().load().type(Article.class).order("-postedAt").first()
                .now();
        return frontPost;
        
        
    }
    
    public List<Article> getOlderArticlesForFrontPage() {

        List<Article> olderPosts = objectify.get().load().type(Article.class).order("-postedAt")
                .offset(1).limit(10).list();
        
        return olderPosts;
        
    }
    
    
    public Article getArticle(Long id) {
        
        Article article = objectify.get().load().type(Article.class).filter("id", id).first()
                .now();
        
        return article;
        
    }
    
    /**
     * Returns false if user cannot be found in database.
     */
    public boolean postArticle(String username, ArticleDto articleDto) {
        
        User user = objectify.get().load().type(User.class).filter("username", username).first().now();
        
        if (user == null) {
            return false;
        }
        
        Article article = new Article(user, articleDto.title, articleDto.content);
        objectify.get().save().entity(article);
        
        return true;
        
    }

}
