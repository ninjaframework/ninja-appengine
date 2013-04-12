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

import ninja.Context;
import ninja.Filter;
import ninja.FilterChain;
import ninja.Result;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * 
 * This should be added to all controllers that interact with the GAE.
 * 
 * You can simply add the filter on top of the class
 * 
 * <code>
 * 
 * @FilterWith(AppEngineFilter.class) public class MyController {.... } </code>
 * 
 *                                    This filter ensures that the threadlocal
 *                                    of that controller is populated with the
 *                                    correct ApiProxy bindings for the dev
 *                                    environment.
 * 
 *                                    This also means that in theory this filter
 *                                    is completely useless in the prod
 *                                    environment.
 * 
 * @author ra
 * 
 */
@Singleton
public class AppEngineFilter implements Filter {
    
    NinjaAppengineEnvironment ninjaAppengineEnvironment;
    
    @Inject
    public AppEngineFilter(NinjaAppengineEnvironment ninjaAppengineEnvironment) {

        this.ninjaAppengineEnvironment = ninjaAppengineEnvironment;

    }

    @Override
    public Result filter(FilterChain chain, Context context) {
        
        // Inits the dev environment for this thread or skips the init
        // There is some runtime overhead involved here. But if you are on production
        // the overhead is really small - have a look at the NinjaAppengineEnvironmentProvider
        ninjaAppengineEnvironment.initOrSkip();

        return chain.next(context);

    }
}
