package filters;

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;
import ninja.appengine.NinjaDevEnvironment;

import com.google.apphosting.api.ApiProxy;
import com.google.inject.Inject;

/**
 * Just a simple demo filter that changes exemplifies two things 1. Change the
 * output of the response 2. Change the status code. 3. Stops execution of all
 * other filters and the route method itself.
 * 
 * We are simply using 418 I'm a teapot (RFC 2324) .
 * 
 * @author ra
 * 
 */
public class SetGaeEnvironment implements Filter {

    private NinjaDevEnvironment ninjaDevEnvironment;

    @Inject
    public SetGaeEnvironment(NinjaDevEnvironment ninjaDevEnvironment) {

        this.ninjaDevEnvironment = ninjaDevEnvironment;

    }

    @Override
    public Result filter(FilterChain chain, Context context) {

        // not running on GAE:
        if (ApiProxy.getCurrentEnvironment() == null) {
            ApiProxy.setEnvironmentForCurrentThread(ninjaDevEnvironment);
        }

        return chain.next(context);

    }
}
