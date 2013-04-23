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

import ninja.utils.NinjaProperties;

import com.google.appengine.repackaged.com.google.common.io.Files;
import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.apphosting.api.ApiProxy;
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
public class NinjaAppengineEnvironmentImpl implements NinjaAppengineEnvironment{

    private NinjaProperties ninjaProperties;

    @Inject
    public NinjaAppengineEnvironmentImpl(NinjaProperties ninjaProperties) {
        this.ninjaProperties = ninjaProperties;

    }

    @Override
    public void initOrSkip() {
        // Create a fake development environment if not run in the Google SDK

        if (ApiProxy.getCurrentEnvironment() == null) {

            System.out
                    .println("No production App Engine environment found - starting local development environment");

            // If we are in test mode we do not persist data to disk
            if (ninjaProperties.isTest()) {

                System.out
                        .println("In test mode - not saving Appengine data to disk");
    
                LocalServiceTestHelper helper = new LocalServiceTestHelper(
                        new LocalDatastoreServiceTestConfig().setNoStorage(true));
        
        
                helper.setUp();
        

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
                
                // add all relative path information to the dir
                String fullAppengineGeneratedDirWithLocalDbFile = new File(appengineGeneratedDir + File.separator
                        + "local_db.bin").getAbsolutePath();

                try {
                    Files.createParentDirs(new File(fullAppengineGeneratedDirWithLocalDbFile));
                } catch (IOException e) {
                    // something strange happened. Can not create parent dirs...
                    e.printStackTrace();
                }

                System.out.println("Local datastore at: " + fullAppengineGeneratedDirWithLocalDbFile);
                
                LocalServiceTestHelper helper = new LocalServiceTestHelper(
                        new LocalDatastoreServiceTestConfig().setBackingStoreLocation(
                                fullAppengineGeneratedDirWithLocalDbFile).setNoStorage(false));
        
                helper.setUp();

            }

            
            
        }

    }

}
