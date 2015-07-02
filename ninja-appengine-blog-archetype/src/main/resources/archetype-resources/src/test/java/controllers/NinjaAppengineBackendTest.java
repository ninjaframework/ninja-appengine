#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package controllers;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.googlecode.objectify.ObjectifyService;
import conf.ObjectifyProvider;
import java.io.Closeable;

public class NinjaAppengineBackendTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(
            new LocalDatastoreServiceTestConfig());
    
    Closeable session;

    @Before
    public void setUp() {
        helper.setUp();
        session = ObjectifyService.begin();
        ObjectifyProvider.setup();
    }

    @After
    public void tearDown() throws Exception {
        helper.tearDown();
        session.close();
    }

}