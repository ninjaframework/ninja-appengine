package conf;

import javax.inject.Singleton;

import ninja.lifecycle.Start;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import java.io.Closeable;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
            try (Closeable closeable = ObjectifyService.begin()) {
                ObjectifyProvider.setup();
            } catch (IOException ex) {
                Logger.getLogger(StartupActions.class.getName()).log(Level.SEVERE, null, ex);
            } 
        }
    }

}
