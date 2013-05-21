/**
 * Copyright (C) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import java.util.List;

import models.Article;
import models.ArticleDto;
import models.User;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.PathParam;

import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;

import conf.OfyService;

@Singleton
@FilterWith(AppEngineFilter.class)
public class ApiController {
    
    public Result getArticles(@PathParam("username") String username) {
        
 
        
        Objectify ofy = OfyService.ofy();
        
        List<Article> articles = ofy.load().type(Article.class).list();
        
        return Results.json().render(articles);
        
    }
    
    public Result postArticle(@PathParam("username") String username,
                              ArticleDto articleDto) {
        
        
        Objectify ofy = OfyService.ofy();
        User user = ofy.load().type(models.User.class).filter("username", username).first().get();
        
        if (user == null) {
            return Results.notFound();
        } else {
            
            Article article = new Article(user, articleDto.title, articleDto.content);
            ofy.save().entity(article).now();
            return Results.ok();
            
        }
        
    }
    
    

}
