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
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ninja.utils.NinjaProperties;

import org.apache.commons.lang.NotImplementedException;

import com.google.appengine.api.datastore.dev.LocalDatastoreService;
import com.google.appengine.repackaged.com.google.common.io.Files;
import com.google.appengine.tools.development.ApiProxyLocal;
import com.google.appengine.tools.development.ApiProxyLocalFactory;
import com.google.appengine.tools.development.LocalServerEnvironment;
import com.google.apphosting.api.ApiProxy;
import com.google.apphosting.api.ApiProxy.Environment;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A simple Environment for developing GAE applications within Ninja.
 * 
 * Supports:
 * - in tests: uses a in memory db to run tests in parallel
 * - using jetty:run attaches a AppengineEnironment to executing thread
 * - using appengine:devserer does nothgin as environment is provided by dev server.
 * 
 * @author ra
 */
@Singleton
public class NinjaAppengineEnvironmentImpl implements
        NinjaAppengineEnvironment, Environment, LocalServerEnvironment {

    private NinjaProperties ninjaProperties;

    @Inject
    public NinjaAppengineEnvironmentImpl(NinjaProperties ninjaProperties) {
        this.ninjaProperties = ninjaProperties;

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

    @Override
    public void initOrSkip() {
        // Create a fake development environment if not run in the Google SDK

        if (ApiProxy.getCurrentEnvironment() == null) {

            System.out
                    .println("No production App Engine environment found - starting local development environment");

            ApiProxyLocalFactory factory = new ApiProxyLocalFactory();
            ApiProxyLocal proxy = factory.create(this);
            ApiProxy.setDelegate(proxy);

            // If we are in test mode we do not persist data to disk
            if (ninjaProperties.isTest()) {

                System.out
                        .println("In test mode - not saving Appengine data to disk");

                proxy.setProperty(LocalDatastoreService.NO_STORAGE_PROPERTY,
                        Boolean.toString(true));

            } else {
                // write to disk:

                /**
                 * Set the property in your profile. It should match the
                 * appengine's output dir. You can use both jetty:run and
                 * appengine:deverserver - and both use the same db.
                 * 
                 */
                String appengineGeneratedDir = System
                        .getProperty("appengine.generated.dir");
                // in tests we output stuff to target:
                if (appengineGeneratedDir == null) {
                    appengineGeneratedDir = "target";
                }

                try {
                    Files.createParentDirs(new File(appengineGeneratedDir));
                } catch (IOException e) {
                    // something strange happened. Can not create parent dirs...
                    e.printStackTrace();
                }

                System.out.println("Local datastore at: "
                        + new File(appengineGeneratedDir + File.separator
                                + "local_db.bin").getAbsolutePath());

                proxy.setProperty(LocalDatastoreService.BACKING_STORE_PROPERTY,
                        new File(appengineGeneratedDir + File.separator
                                + "local_db.bin").getAbsolutePath());

            }

            ApiProxy.setEnvironmentForCurrentThread(this);
        }

    }

}
