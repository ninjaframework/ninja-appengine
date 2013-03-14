package controllers;

import models.License;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;

import com.google.appengine.repackaged.com.google.common.collect.Lists;
import com.google.inject.Singleton;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.Query;

import filters.SetGaeEnvironment;

@Singleton
@FilterWith(SetGaeEnvironment.class)
public class LicenseController {

    public Result listLicenses() {
        
        ObjectifyService.register(License.class);

        Objectify ofy = ObjectifyService.begin();

        Query<License> q = ofy.query(License.class);

        java.util.List<License> licenses = Lists.newArrayList();
        for (License com : q) {

            licenses.add(com);

        }

        return Results.json().render(licenses);

    }

}
