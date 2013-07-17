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
import java.util.Map;

import models.Comment;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.cache.Cache;
import ninja.cache.NinjaCache;
import ninja.params.Param;
import ninja.utils.NinjaProperties;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;

import conf.OfyService;

@Singleton
@FilterWith(AppEngineFilter.class)
public class CommentController {

    //private final LocalServiceTestHelper helper;
    
    @Inject
    NinjaProperties ninjaProperties;
    
    @Inject
    NinjaCache cache;


    public Result postComment(Context context,
                              @Param("text") String text,
                              @Param("email") String email) {
        
        // just make sure that the cache works in integration tests.
        cache.set("key", "arbitraryValue", "10s");

        Objectify ofy = OfyService.ofy();

        Comment comment = new Comment();

        comment.text = text + "-isdev: "+ ninjaProperties.isDev() + " -- isProd " + ninjaProperties.isProd();
        comment.email = email;
        ofy.save().entity(comment).now();

        return Results.redirect("/comments");

    }

    public Result listComments(Context context) {

        Objectify ofy = OfyService.ofy();

        List<Comment> comments = ofy.load().type(Comment.class).list();

        Map<String, Object> map = Maps.newHashMap();

        map.put("comments", comments);

        return Results.html().render(map);

    }

}
