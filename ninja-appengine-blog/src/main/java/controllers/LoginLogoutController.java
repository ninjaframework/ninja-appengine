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

import models.User;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.params.Param;

import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;

import conf.OfyService;

@Singleton
@FilterWith(AppEngineFilter.class)
public class LoginLogoutController {
    
    
    ///////////////////////////////////////////////////////////////////////////
    // Login
    ///////////////////////////////////////////////////////////////////////////
    public Result login(Context context) {

        return Results.html();

    }

    public Result loginPost(@Param("username") String username,
                            @Param("password") String password,
                            Context context) {

        if (username != null && password != null) {

            Objectify ofy = OfyService.ofy();
            User user = ofy.load().type(User.class)
                    .filter("username", username).first().get();

            if (user.password != null
                    && user.password.equals(password)) {
                // some basic authentication...
                context.getSessionCookie().put("username", username);
                context.getFlashCookie().success("login successful");

                return Results.redirect("/");
            }

        }

        // something is wrong with the input or password not found.
        context.getFlashCookie().put("username", username);
        context.getFlashCookie().error("Error username / password not valid.");

        return Results.redirect("/login");

    }

    ///////////////////////////////////////////////////////////////////////////
    // Logout
    ///////////////////////////////////////////////////////////////////////////
    public Result logout(Context context) {

        // remove any user dependent information
        context.getSessionCookie().clear();
        context.getFlashCookie().success("Logout successful.");

        return Results.redirect("/");

    }

}
