     _______  .___ _______        ____.  _____   
     \      \ |   |\      \      |    | /  _  \  
     /   |   \|   |/   |   \     |    |/  /_\  \ 
    /    |    \   /    |    \/\__|    /    |    \
    \____|__  /___\____|__  /\________\____|__  /
         web\/framework   \/                  \/ 
        


Google App Engine support for Ninja
===================================

- Continuous integration: [![Build Status](https://api.travis-ci.org/ninjaframework/ninja-appengine.svg)](https://travis-ci.org/ninjaframework/ninja-appengine)
- Mailing list and forum:  https://groups.google.com/forum/#!forum/ninja-framework
- More about Ninja: http://www.ninjaframework.org


This module allows to use Ninja on the GAE easily.

In particular it provides:
- Objectify to store data.
- The default Mailer.
- Everything else is absolutely standard Ninja.

Usage
=====

ninja-appengine is released along with the GAE SDK. Therefore we use the same version.
For instance ninja-appengine 1.9.9 uses SDK 1.9.9.

Usage is straight forward. The most important thing you have to keep in mind is to annotate 
your controller classes with @FilterWith(AppEngineFilter.class)

    @FilterWith(AppEngineFilter.class)
    public class MyController {
        ...
    }

If you cannot use @FilterWith because you are developing a library you can also
use annotation @AppEngineEnvironment on classes or methods. But you should prefer
using @FilterWith.
    
This is needed to setup the dev environment. If you forget this you'll get a lot
of strange error messages especially in tests.
    
When using persistence you have to register your objectify models.
Please refer to https://github.com/objectify/objectify/wiki/BestPractices Of Service
for some best practises.

In Ninja's context it is best to create a Guice Provider. That we you are able
to inject Objectify into your classes. We usually put the class under conf.ObjectifyProvider:


    public class ObjectifyProvider implements Provider<Objectify> {
    
        static {
            factory().register(Model1.class);
            factory().register(Model2.class);
            ...etc
        }

        @Override
        public Objectify get() {
            return ObjectifyService.ofy();
        }

    }

You then have to bind your Provider in your conf.Module class:

    public class Module extends AbstractModule {


        protected void configure() {

            // bind your Objectify.class to your provider like so:
            bind(Objectify.class).toProvider(ObjectifyProvider.class);

            install(new AppEngineModule());        

        }

    }
        
More about Objectify: https://github.com/objectify/objectify


Quick start
===========

Generate a Ninja Appengine archetype by calling the following command:

    mvn archetype:generate -DarchetypeGroupId=org.ninjaframework -DarchetypeArtifactId=ninja-appengine-blog-archetype

This will generate a full archetype showing a simple blog complete with Objectify as 
persistence layer. A nice archetype to start your own projects...


Deployment
==========

Is as easy as:

    mvn clean appengine:update -Pupdate
    
    
Starting
========

Use the devserver of GAE:

    mvn appengine:devserver -Pdevserver


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
        <version>1.9.9</version>
    </dependency>


2) Add profiles for production / development to your pom:

The easiest way is to go to

    https://github.com/reyez/ninja-appengine/blob/master/ninja-appengine-blog-integration-test/pom.xml
    
and copy the relevant parts to your project. In short you have to:

- Have profiles for "update", "devserver" and "jetty-dev".
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
with "prod" in case of the update profile and "dev" else...

    <appengine-web-app xmlns="http://appengine.google.com/ns/1.0">

        <application>myappid</application>
        <version>myappversion</version>

        <static-files>
            <include path="/static/**" />
        </static-files>
    
        <threadsafe>true</threadsafe>
    
        <!-- will be set to "prod" when running mvn with profile update -->
        <!-- eg: mvn install appengine:update -Pupdate -->
        <system-properties>
            <property name="ninja.mode" value="${ninja.mode}" />
        </system-properties>
    
    </appengine-web-app>


Special configuration variables in application.conf
---------------------------------------------------

(If you are using the default Ninja AppEngine archetype there is nothing you have to set up)

 * <code>cache.implementation=ninja.appengine.AppEngineCacheImpl</code> Tells Ninja to use the GAE built in cache.
 * <code>postoffice.implementation=ninja.appengine.AppEnginePostofficeImpl</code> Tells Ninja to use GAE to send mails.




    
    

