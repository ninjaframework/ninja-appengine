     _______  .___ _______        ____.  _____   
     \      \ |   |\      \      |    | /  _  \  
     /   |   \|   |/   |   \     |    |/  /_\  \ 
    /    |    \   /    |    \/\__|    /    |    \
    \____|__  /___\____|__  /\________\____|__  /
         web\/framework   \/                  \/ 
        


Google App Engine support for Ninja
===================================

- CI: https://buildhive.cloudbees.com/job/reyez/job/ninja-appengine
- Mailing list and forum:  https://groups.google.com/forum/#!forum/ninja-framework
- More about Ninja: http://www.ninjaframework.org


This module allows to use Ninja on the GAE easily.

In particular it uses:
- Objectify to store data (in dev and test mode).
- The default Mailer.
- The rest is absolutely standard Ninja.


Usage
=====

Usage is straight forward. The most important thing you have to keep in mind is to annotate 
your controllers with

    @FilterWith(AppEngineFilter.class
    public class MyController {
        ...
    }
    
This is needed to setup the dev environment. If you forget this you'll get a lot
of strange error messages especially in tests.
    
When using persistence you have to register your objectify models vial:

    @Singleton
    public class Objectify {

    @Start(order=10)
    public void registerModels() {
        
            ObjectifyService.register(MyModel.class);
        
        }

    }   
    
Also don't forget to bind the singleton in your conf.Module:
    bind(Objectify.class);
    
    
More about Objectify: https://code.google.com/p/objectify-appengine/


Deployment
==========

Is as easy as:

    mvn clean appengine:update -Pdeployment
    
    
Starting
========

Starting the dev environment can be done in two ways:
    
- You can use the devserver of GAE (RECOMMENDED)

   mvn appengine:devserver -Pappengine-dev 
   (built-in admin tools, but setting up productive reloading requires one more step)
   
- You can use jetty to run your app:

    mvn jetty:run -Pjetty-dev 
    (automatially picks up changes, but does not provide built in admin tools)

   
Hint: If you are using the devserver it makes a lot of sense to automatically touch appengine-web.xml
so that the dev server restarts and picks up your changes. The idea comes from Miguel Vitorino at
 http://stackoverflow.com/questions/800701/how-do-i-restart-the-google-app-engine-java-server-in-eclipse

Go to you project properties, Builders and add a new build step as a "Program". 
Under "Location" enter the path to your "touch" command 
("D:\bin\UnxUtils\usr\local\wbin\touch.exe" for example - on Posix systems just 
"touch" should be enough since it's already in your PATH) and in "Arguments" put something 
like "${project_loc}/war/WEB-INF/appengine-web.xml". Also go to the "Build Options" 
tab and check "During auto builds".

"touch" will update the timestamp in your appengine-web.xml. 
When the App Engine server detects changes to you appengine-web.xml it will 
reload the app automatically. The load process is very fast so it can be done whenever you 
change any file in your project (which normally triggers the auto-build in Eclipse) - 
you can tweak the builder to only run when you change certain types of files.



Testing
=======

When your tests extend NinjaTest you can just start right away. Initialization of
the test env is done via the filter you used for your controllers. The tests use
an in-memory implementation of the datastore.


Basic Setup
===========

First of all have a look at the demo application at:
https://github.com/reyez/ninja-appengine/tree/master/ninja-appengine-demo

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
        <version>1.1</version>
    </dependency>


2) Add profiles for production / development to your pom:

The easiest way is to go to

    https://github.com/reyez/ninja-appengine/blob/master/ninja-appengine-demo/pom.xml
    
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

The appengine xml is pretty default. The only difference is that we are setting
the ninja mode via a system property. Now the whole process inside the pom.xml
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



    
    

