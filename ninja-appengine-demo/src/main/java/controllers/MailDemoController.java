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

package controllers;

import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.appengine.AppEngineFilter;
import ninja.postoffice.Mail;
import ninja.postoffice.Postoffice;

import com.google.inject.Inject;
import com.google.inject.Provider;

@FilterWith(AppEngineFilter.class)
public class MailDemoController {
    
    @Inject 
    Provider<Mail> mailProvider;
    
    @Inject 
    Postoffice postoffice;

    public Result mail() throws Exception {
        Mail mail = mailProvider.get();
        
        mail.setFrom("raphael@test.com");
        mail.setBodyText("textText");
        mail.addTo("to@example.com");

        postoffice.send(mail);
        
        
        return Results.html();
        
    }

    
}
