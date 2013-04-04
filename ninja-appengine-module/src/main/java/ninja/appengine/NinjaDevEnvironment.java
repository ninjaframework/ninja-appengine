/**
 * Copyright (C) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import com.google.inject.Singleton;

/**
 * A simple Environment for developing GAE applications within Ninja.
 * 
 * @author ra
 *
 */
@Singleton
public class NinjaDevEnvironment implements Environment, LocalServerEnvironment {


    public NinjaDevEnvironment() {

        // Create a fake development environment if not run in the Google SDK

        if (ApiProxy.getCurrentEnvironment() == null) {
            

            System.out.println("No production App Engine environment found - starting local development environment");


            ApiProxyLocalFactory factory = new ApiProxyLocalFactory();
            ApiProxyLocal proxy = factory.create(this);
            ApiProxy.setDelegate(proxy);
            
            File fileForLocalDataStoreService = new File("target/ninja-appengine/local_datastore_service");
            System.out.println("Local datastore at: " + fileForLocalDataStoreService.getAbsolutePath());

            proxy.setProperty(LocalDatastoreService.BACKING_STORE_PROPERTY,
                    fileForLocalDataStoreService.getAbsolutePath());

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
