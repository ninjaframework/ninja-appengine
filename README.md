     _______  .___ _______        ____.  _____   
     \      \ |   |\      \      |    | /  _  \  
     /   |   \|   |/   |   \     |    |/  /_\  \ 
    /    |    \   /    |    \/\__|    /    |    \
    \____|__  /___\____|__  /\________\____|__  /
         web\/framework   \/                  \/ 
        


Google App Engine support for Ninja
===================================

- CI: https://buildhive.cloudbees.com/job/ninjaframework/job/ninja-appengine
- Mailing list and forum:  https://groups.google.com/forum/#!forum/ninja-framework
- More about Ninja: http://www.ninjaframework.org


This module allows to use Ninja on the GAE easily.

In particular it provides:
- Objectify to store data.
- The default Mailer.
- Everything else is absolutely standard Ninja.


IMPORTANT NOTES
===============

- Please upgrade to at least appengine-ninja 1.8.3 asap as previous versions contain a security issue. Please find more
  at the release notes of Ninja 1.6.0.
- From 1.8.3 onwards you need Maven 3.1 to build your GAE projects.


Usage
=====

ninja-appengine is released along with the GAE SDK. Therefore we use the same version.
For instance ninja-appengine 1.8.4 uses SDK 1.8.4.

Usage is straight forward. The most important thing you have to keep in mind is to annotate 
your controller classes with

    @FilterWith(AppEngineFilter.class)
    public class MyController {
        ...
    }
    
This is needed to setup the dev environment. If you forget this you'll get a lot
of strange error messages especially in tests.
    
When using persistence you have to register your objectify models via an OfyService.
Please refer to https://code.google.com/p/objectify-appengine/wiki/BestPractices OfyService.
The OfyService will look roughly like the following class.
We usually put the class under conf.OfyService.


    public class OfyService {
        static {
            factory().register(Thing.class);
            factory().register(OtherThing.class);
            ...etc
        }

        public static Objectify ofy() {
            return ObjectifyService.ofy();
        }

        public static ObjectifyFactory factory() {
            return ObjectifyService.factory();
        }
    }
        
More about Objectify: https://code.google.com/p/objectify-appengine/


Quick start
===========

Generate a Ninja Appengine archetype by calling the following command:

    mvn archetype:generate -DarchetypeGroupId=org.ninjaframework -DarchetypeArtifactId=ninja-appengine-blog-archetype -DarchetypeVersion=2.0.0

This will generate a full archetype showing a simple blog complete with Objectify as 
persistence layer. A nice archetype to start your own projects...


Deployment
==========

Is as easy as:

    mvn clean appengine:update -Pdeployment
    
    
Starting
========

Use the devserver of GAE:

   mvn appengine:devserver -Pappengine-dev 
   (built-in admin tools, but setting up productive reloading requires one more step)


Testing
=======

When your tests extend NinjaTest you can just start right away. Initialization of
the test env is done via the filter you used for your controllers. The tests use
an in-memory implementation of the datastore.


Basic Setup
===========

First of all have a look at the demo application at:
https://github.com/reyez/ninja-appengine/tree/master/ninja-appengine-blog-integration-test

The demo application show best how to setup

- pom.xml
- application.conf
- appengine-web.xml


pom.xml
-------

1) Add the dependency to your pom:

    <dependency>
        <groupId>org.ninjaframework</groupId>
        <artifactId>ninja-appengine-module</artifactId>
        <version>1.8.4</version>
    </dependency>


2) Add profiles for production / development to your pom:

The easiest way is to go to

    https://github.com/reyez/ninja-appengine/blob/master/ninja-appengine-blog-integration-test/pom.xml
    
and copy the relevant parts to your project. In short you have to:

- Have profiles for deployment, appengine-dev and jetty-dev.
- Configure the appengine libraries with the correct scopes
- Set the build output dir consistently

It is also important to filter the resources to finally set the mode dev / production:

    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <configuration>
            <webResources>
                <webResource>
                    <directory>${basedir}/src/main/webapp/WEB-INF</directory>
                    <includes>
                        <include>**/*</include>
                    </includes>
                    <targetPath>WEB-INF</targetPath>
                    <filtering>true</filtering>
                </webResource>
            </webResources>
        </configuration>
    </plugin>



appengine-web.xml
-----------------

The Appengine xml is pretty default. The only difference is that we are setting
the Ninja mode via a system property. Now the whole process inside the pom.xml
makes more sense I guess :) ${ninja.mode} is simply the variable getting exchanged
with "prod" in case of the deployment profile and "dev" else...

    <appengine-web-app xmlns="http://appengine.google.com/ns/1.0">

        <application>myappid</application>
        <version>myappversion</version>

        <static-files>
            <include path="/static/**" />
        </static-files>
    
        <threadsafe>true</threadsafe>
    
        <!-- will be set to "prod" when runnin mvn with profile deployment -->
        <!-- eg: mvn install gae:deploy -Pdeployment -->
        <system-properties>
            <property name="ninja.mode" value="${ninja.mode}" />
        </system-properties>
    
    </appengine-web-app>


Special configuration variables in application.conf
---------------------------------------------------

(If you are using the default Ninja AppEngine archetype there is nothing you have to set up)

 * <code>cache.implementation=ninja.appengine.AppEngineCacheImpl</code> Tells Ninja to use the GAE built in cache.
 * <code>postoffice.implementation=ninja.appengine.AppEnginePostofficeImpl</code> Tells Ninja to use GAE to send mails.




    
    

