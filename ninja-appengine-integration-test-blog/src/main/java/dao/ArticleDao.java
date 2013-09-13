package dao;

import java.util.List;
import java.util.Map;

import models.Article;
import models.ArticleDto;
import models.ArticlesDto;
import models.User;

import com.google.common.collect.Maps;
import com.googlecode.objectify.Objectify;

import conf.OfyService;

public class ArticleDao {

    
    public ArticlesDto getAllArticles() {
        
        Objectify ofy = OfyService.ofy();
        
        ArticlesDto articlesDto = new ArticlesDto();
        articlesDto.articles = ofy.load().type(Article.class).list();
        
        return articlesDto;
        
    }
    
    
    public Article getFirstArticleForFrontPage() {
        
        Objectify ofy = OfyService.ofy();
        
        Article frontPost = ofy.load().type(Article.class).order("-postedAt").first()
                .now();
        return frontPost;
        
        
    }
    
    public List<Article> getOlderArticlesForFrontPage() {
        Objectify ofy = OfyService.ofy();
        List<Article> olderPosts = ofy.load().type(Article.class).order("-postedAt")
                .offset(1).limit(10).list();
        
        return olderPosts;
        
    }
    
    
    public Article getArticle(Long id) {
        
        Objectify ofy = OfyService.ofy();
        Article article = ofy.load().type(Article.class).filter("id", id).first()
                .now();
        
        return article;
        
    }
    
    /**
     * Returns false if user cannot be found in database.
     */
    public boolean postArticle(String username, ArticleDto articleDto) {
        
        Objectify ofy = OfyService.ofy();
        User user = ofy.load().type(User.class).filter("username", username).first().now();
        
        if (user == null) {
            return false;
        }
        
        Article article = new Article(user, articleDto.title, articleDto.content);
        ofy.save().entity(article);
        
        return true;
        
    }

}
