#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/**
 * Copyright (C) 2013 the original author or authors.
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

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.List;

import models.ArticleDto;
import ninja.NinjaTest;

import org.junit.Test;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

public class ApiControllerTest extends NinjaTest {

    @Test
    public void testGetAndPostArticle() throws Exception {

        // /////////////////////////////////////////////////////////////////////
        // Test initial data:
        // /////////////////////////////////////////////////////////////////////
        String response = ninjaTestBrowser.makeJsonRequest(getServerAddress()
                + "api/bob@gmail.com/articles");

        Type listOfArticles = new TypeToken<List<ArticleDto>>() {}.getType();
        List<ArticleDto> result = new Gson().fromJson(response, listOfArticles);

        assertEquals(3, result.size());
        
        // /////////////////////////////////////////////////////////////////////
        // Post new article:
        // /////////////////////////////////////////////////////////////////////
        ArticleDto articleDto = new ArticleDto();
        articleDto.content = "contentcontent";
        articleDto.title = "new title new title";

        ninjaTestBrowser.postJson(getServerAddress()
                + "api/bob@gmail.com/article", articleDto);
        
        // /////////////////////////////////////////////////////////////////////
        // Fetch articles again => assert we got a new one ...
        // /////////////////////////////////////////////////////////////////////
        response = ninjaTestBrowser.makeJsonRequest(getServerAddress()
                + "api/bob@gmail.com/articles");

        result = new Gson().fromJson(response, listOfArticles);
        // one new result:
        assertEquals(4, result.size());
        

    }

}
