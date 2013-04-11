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

import java.util.Map;

import models.Comment;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.appengine.NinjaAppengineEnvironmentImpl;
import ninja.params.Param;
import ninja.utils.NinjaProperties;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

@Singleton
@FilterWith(AppEngineFilter.class)
public class CommentController {

    //private final LocalServiceTestHelper helper;
    
    @Inject
    NinjaProperties ninjaProperties;


    public Result postComment(Context context,
                              @Param("text") String text,
                              @Param("email") String email) {
        
        
        System.out.println("!: " + ninjaProperties.isDev());
        System.out.println("isprod: " + ninjaProperties.isProd());

        Objectify ofy = ObjectifyService.begin();

        Comment comment = new Comment();

        comment.text = text + "-isdev: "+ ninjaProperties.isDev() + " -- isProd " + ninjaProperties.isProd();
        comment.email = email;
        ofy.put(comment);

        return Results.redirect("/comments");

    }

    public Result listComments(Context context) {

        Objectify ofy = ObjectifyService.begin();

        Query<Comment> q = ofy.query(Comment.class);

        Map<String, Object> map = Maps.newHashMap();

        java.util.List<Comment> comments = Lists.newArrayList();
        for (Comment com : q) {

            comments.add(com);

        }
        map.put("comments", comments);

        return Results.html().render(map);

    }

}
