#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package controllers;

import ninja.NinjaTest;

import org.junit.Before;

public class NinjaAppengineTest extends NinjaTest {

    @Before
    public void setUp() {
        ninjaTestBrowser.makeRequest(getServerAddress() + "setup");
    }


}
