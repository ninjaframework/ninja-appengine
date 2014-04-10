package conf;

import javax.inject.Singleton;

import ninja.lifecycle.Start;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;
import com.googlecode.objectify.Objectify;

@Singleton
public class StartupActions {

    private NinjaProperties ninjaProperties;

    @Inject
    public StartupActions(NinjaProperties ninjaProperties) {
        this.ninjaProperties = ninjaProperties;
    }
    
    @Start(order=100)
    public void generateDummyDataWhenInTest() {
        
        if (ninjaProperties.isDev()) {
            
            ObjectifyProvider.setup();
            
        }
        
    }

}
