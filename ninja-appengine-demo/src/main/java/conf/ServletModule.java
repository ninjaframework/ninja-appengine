package conf;

import ninja.servlet.NinjaServletDispatcher;

public class ServletModule extends com.google.inject.servlet.ServletModule {

    @Override
    protected void configureServlets() {

        bind(NinjaServletDispatcher.class).asEagerSingleton();

        // do not server
        serveRegex("/[\\w]+").with(NinjaServletDispatcher.class);
    }

}