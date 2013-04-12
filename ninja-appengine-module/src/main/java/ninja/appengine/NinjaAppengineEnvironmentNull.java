package ninja.appengine;

import com.google.inject.Singleton;

/**
 * Does nothing intentionally.
 * 
 * When we are on production we do not need the AppEngineEnvironment being attached to
 * the executing thread.
 * 
 * @author ra
 */
@Singleton
public class NinjaAppengineEnvironmentNull implements NinjaAppengineEnvironment {
    
    public NinjaAppengineEnvironmentNull() {
        
        System.out.println("Loading NinjaAppengineEnvironmentNull (for devserver and production).");
        
    }

    @Override
    public void initOrSkip() {
        // doing nothing intentionally...
    }

}
