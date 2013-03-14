package controllers;

import java.util.Map;

import models.Comment;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.NinjaDevEnvironment;
import ninja.params.Param;

import com.google.appengine.repackaged.com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

import filters.SetGaeEnvironment;

@Singleton
@FilterWith(SetGaeEnvironment.class)
public class CommentController {

    //private final LocalServiceTestHelper helper;

    @Inject
    public CommentController(NinjaDevEnvironment ninjaDevEnvironment) {

        ObjectifyService.register(Comment.class);
    }

    public Result postComment(Context context,
                              @Param("text") String text,
                              @Param("email") String email) {

        Objectify ofy = ObjectifyService.begin();

        Comment comment = new Comment();

        comment.text = text;
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
