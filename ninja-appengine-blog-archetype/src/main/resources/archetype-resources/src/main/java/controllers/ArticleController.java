#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package controllers;

import java.util.Map;

import models.Article;
import models.ArticleDto;
import models.User;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.SecureFilter;
import ninja.appengine.AppEngineFilter;
import ninja.params.PathParam;
import ninja.validation.JSR303Validation;
import ninja.validation.Validation;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;

import conf.OfyService;

@Singleton
@FilterWith(AppEngineFilter.class)
public class ArticleController {

    ///////////////////////////////////////////////////////////////////////////
    // Show article
    ///////////////////////////////////////////////////////////////////////////
    public Result articleShow(@PathParam("id") Long id) {

        Article article = null;

        if (id != null) {

            Objectify ofy = OfyService.ofy();
            article = ofy.load().type(Article.class).filter("id", id).first()
                    .get();

        }

        Map<String, Object> map = Maps.newHashMap();
        map.put("article", article);

        return Results.html().render(map);

    }
    
    ///////////////////////////////////////////////////////////////////////////
    // Create new article
    ///////////////////////////////////////////////////////////////////////////
    @FilterWith(SecureFilter.class)
    public Result articleNew() {

        return Results.html();

    }

    @FilterWith(SecureFilter.class)
    public Result articleNewPost(Context context,
                                 @JSR303Validation ArticleDto articleForm,
                                 Validation validation) {

        if (validation.hasViolations()) {

            context.getFlashCookie().error("Please correct field.");
            context.getFlashCookie().put("title", articleForm.title);
            context.getFlashCookie().put("content", articleForm.content);

            return Results.redirect("/article/new");

        } else {

            String username = context.getSessionCookie().get("username");
            
            Objectify ofy = OfyService.ofy();
            User user = ofy.load().type(User.class).filter("username", username).first().get();
            
            Article article = new Article(user, articleForm.title, articleForm.content);
            ofy.save().entity(article);
            
            context.getFlashCookie().success("New article created.");
            
            return Results.redirect("/");

        }

    }

}
