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
your Controllers with

    @FilterWith(AppEngineFilter.class
    public class MyController {
        ...
    }
    
This is needed to setup the dev environment. If you forget this you'll get a lot
of strange error messages.
    
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

    mvn clean install gae:deploy -Pdeployment


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
        <version>1.0.1</version>
    </dependency>


2) Add profiles for production / development to your pom:

These profiles will allow you to correctly set the ninja mode for test and 
development:

    <profiles>
        <profile>
            <id>default</id>
            <properties>
                <ninja.mode>dev</ninja.mode>
            </properties>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
        </profile>
        <profile>
            <id>deployment</id>
            <properties>
                <ninja.mode>prod</ninja.mode>
            </properties>
        </profile>
    </profiles>

3) Filter the resources to finally set the mode dev / production:

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



    
    

