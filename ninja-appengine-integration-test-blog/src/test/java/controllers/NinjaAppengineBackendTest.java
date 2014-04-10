package controllers;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyFilter;
import conf.ObjectifyProvider;

public class NinjaAppengineBackendTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
        ObjectifyProvider.setup();
    }

    @After
    public void tearDown() {
        
        helper.tearDown();
        ObjectifyFilter.complete();
    }

}
