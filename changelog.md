1.9.23
======

 * 2015-07-02 Bump to Objectify 5.1.5 (ra)
 * 2015-07-01 Bump to Ninja 4.1.4 / GAE 1.9.23 (ra)

1.9.18
======

 * 2015-03-27 Bump to Ninja 5.1.0 / GAE 1.9.18 (ra)

1.9.6
=====

 * 2014-06-28 Bump to Ninja 3.2.0. (ra)
 * 2014-06-28 Bump to GAE 1.9.6 (ra)

1.9.4
=====

 * 2014-05-15 Bump to Ninja 3.1.5. (ra) 
 * 2014-05-15 Bump to GAE 1.9.4. (ra) 
 * 2014-04-14 Bump to objectify 4.1.3. (ra)
 * 2014-04-10 Added support for @AppEngineEnvironment annotation that allows to
              use the AppEngineEnvironment in non-controller classes. Needed
              ins some cases - for instance for modules like ninja-casino (ra).
 * 2014-04-08 Added support for better logging via jcl. (Nomi + ra)
 * 2014-04-08 Bump to objectify 4.1.2. (ra)

1.9.2
=====

 * 2014-04-08 Bump to Ninja 3.1.4 and GAE 1.9.2 (ra)
 * 2014-03-23 Enabled testing of archetype (ra)
 * 2014-03-23 Bump to Ninja 3.1.3 (ra)

1.9.1
=====

 * 2014-03-21 Bump to GAE 1.9.1 (ra)
 * 2014-03-21 Bump to Ninja 3.1.2 (ra)
 * 2014-03-21 Fix of controller that wanted to send html instead of json/xml. (ra)
 * 2014-02-21 Tinx fix in documentation (indendation wrong) (ra)

1.9.0
=====

 * 2014-02-27 Compatible with Ninja 3.0.3. (ra)
 * 2014-02-27 Support for GAE 1.9.0. (ra)


1.8.9.1
=======

 * 2014-01-29 Added fool proof release dependency management to archetype. (ra)
 * 2014-01-29 Compatible with ninja-3.0.2. (ra)
 * 2014-01-29 Changed profile names to match appengine goals => deployment is now "update"
              and appengine-dev is not "devserver". (ra)

1.8.9
=====

  * 2013-11-20 Bump to GAE 1.8.9 (ra)
  * 2013-11-20 Bump to Ninja 2.5.1 (also in archetype) (ra)

1.8.8
=====

  * 2013-11-20 Bump to GAE 1.8.8 (ra)
  * 2013-11-20 Bump to Ninja 2.2.0 in archetype (ra)

1.8.7
=====

  * 2013-10-19 Bump to GAE 1.8.7 (ra)
  
1.8.6
=====

  * 2013-10-19 Bump to GAE 1.8.6 (ra)
  
1.8.5
=====

  * 2013-08-26 Bump to GAE 1.8.5 / Added getModuleId to Environment implementation for compatability (ra)

1.8.4
=====

  * 2013-08-26 Tiny fix to exclude java files from archetype deployment (ra) 
  * 2013-08-26 enableJarClasses enabled by default at archetype (ra) 
  * 2013-08-29 Fixed wrong flash_* ("_" was replaced with ".") in archetype and blog demo (ra) 
  * 2013-09-01 Bugfix - static assets should not handle any flash or session scopes 
               https://github.com/ninjaframework/ninja/issues/109 (Sojin, ra).
  * 2013-09-14 New integration test and archetype. 
  * 2013-09-24 Bump to Ninja 2.0.0
  
1.8.3
=====

 * 2013-08-07 Bump to Appengine SDK 1.8.3 (ra)
 * 2013-08-07 Bump to Ninja 1.6.0 (ra)
 * 2013-08-07 Added enforcer plugin to ninja-appengine-blog to make
              sure we do not include commons-logging on the classpath (ra)
 
1.8.2
=====

 * 2013-07-01 Added native GAE caching support support via (cache.implementation=ninja.appengine.AppEngineCacheImpl) (ra)
 * 2013-07-17 Bump to Appengine SDK 1.8.2 (ra)
 * 2013-07-17 Bump to Ninja 1.5.1 (ra)
 * 2013-07-17 Bump to Objectify 4.0rc1 (ra)


1.8.1
=====

 * Nothing new. We now just copy the GAE sdk versioning scheme for clarity.

1.6.2
=====

 * Bump to ninja-1.4.4
 * Bump to GAE 1.8.1
 * Bump to objectify 4.0rc1
 
 
 1.6.1
=====

 * Bump to ninja-1.4.2
 
1.6
=====

 * Bump to Appengine plugin and version 1.8.0
 * Class files no longer included as source
 * Switch to java7 as default
 

1.5.2
=====

 * Bump to Ninja 1.4
 
 
1.5.1
=====

 * Added ObjectifyFilter to ServletModule in demo app 
 * Added appengine-blog demo project
 * Added archetype that creates an appengine-blog for quick start of projects
 
1.5
===

 * Bump to Objectify 4.02b
 * Update of demo project to use OfyService
 * Removed a bug where multiple datastores were loaded per running app


1.3
===

- Support for appengine:devserver runmode
- Support for Ninja 1.3


1.2.1
===

- Tiny spelling mistake

1.1
===

- Support for appengine maven module and official maven plugin
- bumpg to 1.7.7 appengine


1.0.1
=====

- bugfix mail against "null" session while mailing.


1.0
===

- initial version
- mailer support
- persistence layer support via objectify
