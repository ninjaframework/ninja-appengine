package conf;

import ninja.servlet.NinjaServletDispatcher;

import com.google.appengine.api.utils.SystemProperty;
import com.google.inject.Singleton;
import com.googlecode.objectify.ObjectifyFilter;

public class ServletModule extends com.google.inject.servlet.ServletModule {

    @Override
    protected void configureServlets() {

        bind(NinjaServletDispatcher.class).asEagerSingleton();
        
        // Clean objectify instances with that filter:
        bind(ObjectifyFilter.class).in(Singleton.class);
        filter("/*").through(ObjectifyFilter.class);


        if (SystemProperty.environment.value() ==
                SystemProperty.Environment.Value.Production) {
                
            serve("/*").with(NinjaServletDispatcher.class);
            
        } else {
            // do not serve admin stuff like _ah and so on...
            // allows to call /_ah/admin and so on
            serveRegex("/(?!_ah).*").with(NinjaServletDispatcher.class);
        }
 
        
    }

}