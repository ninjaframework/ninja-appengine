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

import ninja.utils.NinjaProperties;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

/**
 * use it in your applications module like:
 * 
 * <code>
 *  install(new AppEngineModule())
 * </code>
 *
 */
public class AppEngineModule extends AbstractModule {

    protected void configure() {
        //bind(NinjaAppengineEnvironmentImpl.class).in(Singleton.class);
    }
    
//    @Provides
//    @Singleton
//    NinjaAppengineEnvironment getNinjaAppengineEnvironment(NinjaProperties ninjaProperties) {
//        
//        if (ninjaProperties.isProd()) {
//            
//            return new NinjaAppengineEnvironmentNull();
//            
//        } else {
//            return new NinjaAppengineEnvironmentImpl(ninjaProperties); 
//        }
//        
//    }
    
    
    
    

}
