package ninja.appengine;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;

import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import com.google.appengine.tools.development.ApiProxyLocal;
import com.google.appengine.tools.development.ApiProxyLocalFactory;
import com.google.appengine.tools.development.LocalServerEnvironment;
import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Environment;

public class NinjaDevEnvironment implements Environment, LocalServerEnvironment {


    public NinjaDevEnvironment() {

        // Create a fake development environment if not run in the Google SDK

        if (ApiProxy.getCurrentEnvironment() == null) {

            ApiProxyLocalFactory factory = new ApiProxyLocalFactory();
            ApiProxyLocal proxy = factory.create(this);
            ApiProxy.setDelegate(proxy);

            proxy.setProperty(LocalDatastoreService.BACKING_STORE_PROPERTY,
                    new File("/tmp/datastore").getAbsolutePath());

            System.out
                    .println("No App Engine environemnt found - starting local dev environment");

            ApiProxy.setEnvironmentForCurrentThread(this);

        }

    }

    @Override
    public String getAppId() {
        return "appId";
    }

    @Override
    public String getVersionId() {
        throw new NotImplementedException();
    }

    @Override
    public String getEmail() {
        throw new NotImplementedException();
    }

    @Override
    public boolean isLoggedIn() {
        throw new NotImplementedException();
    }

    @Override
    public boolean isAdmin() {
        throw new NotImplementedException();
    }

    @Override
    public String getAuthDomain() {
        throw new NotImplementedException();
    }

    @Override
    public String getRequestNamespace() {
        throw new NotImplementedException();
    }

    public String getDefaultNamespace() {
        throw new NotImplementedException();
    }

    public void setDefaultNamespace(String ns) {
    }

    @Override
    public Map<String, Object> getAttributes() {
        return new HashMap<String, Object>();
    }

    @Override
    public void waitForServerToStart() throws InterruptedException {
    }

    @Override
    public int getPort() {
        throw new NotImplementedException();
    }

    @Override
    public File getAppDir() {
        return new File("/tmp/");
    }

    @Override
    public String getAddress() {
        throw new NotImplementedException();
    }

    @Override
    public boolean enforceApiDeadlines() {
        return false;
    }

    @Override
    public boolean simulateProductionLatencies() {
        return false;
    }

    @Override
    public String getHostName() {
        throw new NotImplementedException();
    }

    @Override
    public long getRemainingMillis() {
        throw new NotImplementedException();
    }

}
