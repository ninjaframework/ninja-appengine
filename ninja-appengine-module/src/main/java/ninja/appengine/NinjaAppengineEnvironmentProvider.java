package ninja.appengine;

import ninja.utils.NinjaProperties;

import com.google.apphosting.api.ApiProxy;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

/**
 * 
 * Provide the "correct" binding for {@link NinjaAppengineEnvironment}.
 * In production => bind to {@link NinjaAppengineEnvironmentNull} that does nothing.
 * In test and for dev bind to {@link NinjaAppengineEnvironmentImpl}.
 * 
 * @author ra
 *
 */
@Singleton
public class NinjaAppengineEnvironmentProvider implements Provider<NinjaAppengineEnvironment> {
    
    private NinjaProperties ninjaProperties;

    @Inject
    public NinjaAppengineEnvironmentProvider(NinjaProperties ninjaProperties) {
        this.ninjaProperties = ninjaProperties;
        
    }

    @Override
    public NinjaAppengineEnvironment get() {        
        
        // make sure we return null when we are in production
        // or the environment is already registered (will be the case in appengine:devserver mode
        if (ninjaProperties.isProd() || ApiProxy.getCurrentEnvironment() != null) {
            
            return new NinjaAppengineEnvironmentNull();
            
        } else {
            return new NinjaAppengineEnvironmentImpl(ninjaProperties); 
        }

    }

}
