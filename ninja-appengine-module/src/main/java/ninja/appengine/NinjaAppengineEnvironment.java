package ninja.appengine;

import com.google.inject.ProvidedBy;


@ProvidedBy(NinjaAppengineEnvironmentProvider.class)
public interface NinjaAppengineEnvironment {
    
    /**
     * Inits the dev environment for this thread or skips the init.
     * 
     * This is needed for testcases and jetty:run run mode. The appengine devserver
     * does not need a dev environment.
     * 
     * There is some runtime overhead involved here. But if you are on production
     * the overhead is really small - have a look at the {@link NinjaAppengineEnvironmentProvider}
     */
    public void initOrSkip();

}
