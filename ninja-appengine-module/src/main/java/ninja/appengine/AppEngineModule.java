/**
 * Copyright (C) 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package ninja.appengine;

import com.google.inject.AbstractModule;
import static com.google.inject.matcher.Matchers.annotatedWith;
import static com.google.inject.matcher.Matchers.any;

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

        AppEngineEnvironmentInterceptor appEngineEnvironmentInterceptor
                = new AppEngineEnvironmentInterceptor(
                        getProvider(NinjaAppengineEnvironment.class));

        // class-level @AppEngineEnvironment
        bindInterceptor(
                annotatedWith(AppEngineEnvironment.class),
                any(),
                appEngineEnvironmentInterceptor);

        // method-level @AppEngineEnvironment
        bindInterceptor(
                any(),
                annotatedWith(AppEngineEnvironment.class),
                appEngineEnvironmentInterceptor);

    }

}
