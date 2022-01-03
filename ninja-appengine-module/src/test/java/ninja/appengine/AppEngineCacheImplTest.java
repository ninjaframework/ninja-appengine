/*
 * Copyright (C) 2012-2021 the original author or authors.
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

import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import ninja.cache.Cache;
import org.junit.*;
import org.junit.runners.MethodSorters;
import org.slf4j.helpers.NOPLogger;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AppEngineCacheImplTest {

    private final LocalServiceTestHelper helper = new LocalServiceTestHelper(new LocalMemcacheServiceTestConfig());

    @Before
    public void setUp() {
        helper.setUp();
    }

    @After
    public void tearDown() {
        helper.tearDown();
    }

    @Test
    public void cacheClear() {
        final Cache cache = new AppEngineCacheImpl(NOPLogger.NOP_LOGGER);

        cache.add("key", Arrays.asList(42, 1337), 1500);
        Object actual = cache.get("key");
        Assert.assertNotNull(actual);

        cache.clear();
        actual = cache.get("key");
        Assert.assertNull(actual);
    }

    @Test
    public void cacheDelete() {
        final Cache cache = new AppEngineCacheImpl(NOPLogger.NOP_LOGGER);

        cache.add("key", Arrays.asList(42, 1337), 1500);
        Object actual = cache.get("key");
        Assert.assertNotNull(actual);

        cache.delete("key");
        actual = cache.get("key");
        Assert.assertNull(actual);
    }

    @Test
    public void cacheDeleteSafe() {
        final Cache cache = new AppEngineCacheImpl(NOPLogger.NOP_LOGGER);

        cache.add("key", Arrays.asList(42, 1337), 1500);
        Object actual = cache.get("key");
        Assert.assertNotNull(actual);

        cache.safeDelete("key");
        actual = cache.get("key");
        Assert.assertNull(actual);
    }

    @Test
    public void cacheListInteger() {
        final Cache cache = new AppEngineCacheImpl(NOPLogger.NOP_LOGGER);

        cache.add("key", Arrays.asList(42, 1337), 1500);
        final Object actual = cache.get("key");

        Assert.assertNotNull(actual);
        Assert.assertTrue(actual instanceof List);
        Assert.assertEquals(Arrays.asList(42, 1337), actual);
    }

    @Test
    public void cacheListPojo() {
        final Cache cache = new AppEngineCacheImpl(NOPLogger.NOP_LOGGER);
        final UserPojo pojo1 = new UserPojo("Amber", 25);
        final UserPojo pojo2 = new UserPojo("Denis", 27);

        cache.add("key", Arrays.asList(pojo1, pojo2), 1500);
        final Object actual = cache.get("key");

        Assert.assertNotNull(actual);
        Assert.assertTrue(actual instanceof List);
        Assert.assertEquals(2, ((List<?>) actual).size());

        UserPojo pojoActual = (UserPojo) ((List<?>) actual).get(0);
        Assert.assertEquals("Amber", pojoActual.firstName);
        Assert.assertEquals(25, pojoActual.age);

        pojoActual = (UserPojo) ((List<?>) actual).get(1);
        Assert.assertEquals("Denis", pojoActual.firstName);
        Assert.assertEquals(27, pojoActual.age);
    }

    @Test
    public void cacheListString() {
        final Cache cache = new AppEngineCacheImpl(NOPLogger.NOP_LOGGER);

        cache.add("key", Arrays.asList("apple", "banana"), 1500);
        final Object actual = cache.get("key");

        Assert.assertNotNull(actual);
        Assert.assertTrue(actual instanceof List);
        Assert.assertEquals(Arrays.asList("apple", "banana"), actual);
    }

    @Test
    public void cacheMultipleInteger() {
        final Cache cache = new AppEngineCacheImpl(NOPLogger.NOP_LOGGER);

        cache.add("number_1", 42, 1500);
        cache.add("number_2", 1337, 1500);
        cache.add("number_3", -42, 1500);
        final Map<String, Object> actual = cache.get(new String[]{"number_1", "number_2", "number_3"});

        Assert.assertNotNull(actual);
        Assert.assertEquals(3, actual.size());

        Assert.assertEquals(42, actual.get("number_1"));
        Assert.assertEquals(1337, actual.get("number_2"));
        Assert.assertEquals(-42, actual.get("number_3"));
    }

    @Test
    public void cacheMultiplePojo() {
        final Cache cache = new AppEngineCacheImpl(NOPLogger.NOP_LOGGER);

        cache.add("pojo_1", new UserPojo("Bob", 23), 1500);
        cache.add("pojo_2", new UserPojo("Clémence", 22), 1500);
        final Map<String, Object> actual = cache.get(new String[]{"pojo_1", "pojo_2"});

        Assert.assertNotNull(actual);
        Assert.assertEquals(2, actual.size());

        UserPojo pojoActual = (UserPojo) actual.get("pojo_1");
        Assert.assertEquals("Bob", pojoActual.firstName);
        Assert.assertEquals(23, pojoActual.age);

        pojoActual = (UserPojo) actual.get("pojo_2");
        Assert.assertEquals("Clémence", pojoActual.firstName);
        Assert.assertEquals(22, pojoActual.age);
    }

    @Test
    public void cacheMultipleString() {
        final Cache cache = new AppEngineCacheImpl(NOPLogger.NOP_LOGGER);

        cache.add("fruit_1", "Apple", 1500);
        cache.add("fruit_2", "Banana", 1500);
        cache.add("fruit_3", "Pear", 1500);
        final Map<String, Object> actual = cache.get(new String[]{"fruit_1", "fruit_2", "fruit_3"});

        Assert.assertNotNull(actual);
        Assert.assertEquals(3, actual.size());

        Assert.assertEquals("Apple", actual.get("fruit_1"));
        Assert.assertEquals("Banana", actual.get("fruit_2"));
        Assert.assertEquals("Pear", actual.get("fruit_3"));
    }

    @Test
    public void cacheSingleInteger() {
        final Cache cache = new AppEngineCacheImpl(NOPLogger.NOP_LOGGER);

        cache.add("key", 42, 1500);
        final Object actual = cache.get("key");

        Assert.assertNotNull(actual);
        Assert.assertTrue(actual instanceof Integer);
        Assert.assertEquals(42, actual);
    }

    @Test
    public void cacheSingleLong() {
        final Cache cache = new AppEngineCacheImpl(NOPLogger.NOP_LOGGER);

        cache.add("key", 1337L, 1500);
        final Object actual = cache.get("key");

        Assert.assertNotNull(actual);
        Assert.assertTrue(actual instanceof Long);
        Assert.assertEquals(1337L, actual);
    }

    @Test
    public void cacheSinglePojo() {
        final Cache cache = new AppEngineCacheImpl(NOPLogger.NOP_LOGGER);
        final UserPojo pojo = new UserPojo("Amber", 25);

        cache.add("key", pojo, 1500);
        final Object actual = cache.get("key");

        Assert.assertNotNull(actual);
        Assert.assertTrue(actual instanceof UserPojo);
        Assert.assertEquals("Amber", ((UserPojo) actual).firstName);
        Assert.assertEquals(25, ((UserPojo) actual).age);
    }

    @Test
    public void cacheSingleString() {
        final Cache cache = new AppEngineCacheImpl(NOPLogger.NOP_LOGGER);

        cache.add("key", "Hello World!", 1500);
        final Object actual = cache.get("key");

        Assert.assertNotNull(actual);
        Assert.assertTrue(actual instanceof String);
        Assert.assertEquals("Hello World!", actual);
    }

    /**
     * Simple POJO.
     */
    public static class UserPojo implements Serializable {

        final String firstName;
        final int age;

        /**
         * Build a new instance.
         *
         * @param firstName First name
         * @param age       Age
         */
        public UserPojo(final String firstName, final int age) {
            this.firstName = firstName;
            this.age = age;
        }
    }
}
