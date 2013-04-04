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

import java.util.Properties;

import javax.mail.Session;

import ninja.postoffice.Mail;
import ninja.postoffice.Postoffice;
import ninja.postoffice.commonsmail.CommonsmailHelper;

import org.apache.commons.mail.MultiPartEmail;

import com.google.inject.Inject;

/**
 * A Emailer implementation for the GAE and Ninja.
 * 
 * Very similar to the default {@link PostofficeImpl}.
 * 
 * The major difference is that it does not use a built-in smtp server,
 * but GAE's mailer session/transport facilities.
 * 
 * @author ra
 */
public class AppEnginePostofficeImpl implements Postoffice {

    private CommonsmailHelper commonsmailHelper;
    
    private Session session;

    @Inject
    public AppEnginePostofficeImpl(CommonsmailHelper commonsmailHelper) {
        this.commonsmailHelper = commonsmailHelper;
        
        createOrRecreateNewSessionWhenNeeded(session);
        
    }

    @Override
    public void send(Mail mail) throws Exception {
        
        // If the session became unusable we recreate it:
        createOrRecreateNewSessionWhenNeeded(session);

        // create a correct multipart email based on html / txt content:
        MultiPartEmail multiPartEmail = commonsmailHelper.createMultiPartEmailWithContent(mail);
        
        // fill the from, to, bcc, css and all other fields:
        commonsmailHelper.doPopulateMultipartMailWithContent(multiPartEmail, mail);

        // And send it:
        multiPartEmail.setMailSession(session);
        multiPartEmail.send();
    }
    
    
    /**
     * Simply checks if session is usable and recreates it.
     * Works on the reference "session".
     * @param session The session that should be checked or recreated.
     */ 
    private void createOrRecreateNewSessionWhenNeeded(Session session) {
        
        if (session == null) {
            Properties props = new Properties();
            session = Session.getDefaultInstance(props);
        } 
        
    }

}
