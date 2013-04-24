package ninja.appengine;

import ninja.utils.NinjaProperties;

import com.google.apphosting.api.ApiProxy;
import com.google.inject.Inject;
import com.google.inject.Injector;
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
    
    private NinjaAppengineEnvironment ninjaAppengineEnvironment;
    
    

    @Inject
    public NinjaAppengineEnvironmentProvider(Injector injector, NinjaProperties ninjaProperties) {
        this.ninjaProperties = ninjaProperties;
                
        // make sure we return null when we are in production
        // or the environment is already registered (will be the case in appengine:devserver mode
        if (ninjaProperties.isProd() || ApiProxy.getCurrentEnvironment() != null) {
            
            ninjaAppengineEnvironment = injector.getInstance(NinjaAppengineEnvironmentNull.class);
            
        } else {
            ninjaAppengineEnvironment = injector.getInstance(NinjaAppengineEnvironmentImpl.class); 
        }
        
    }

    @Override
    public NinjaAppengineEnvironment get() {        
        
        return ninjaAppengineEnvironment;


    }

}
