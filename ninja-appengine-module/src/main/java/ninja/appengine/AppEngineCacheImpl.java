package ninja.appengine;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import ninja.cache.Cache;

import org.slf4j.Logger;

import com.google.appengine.api.memcache.Expiration;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.inject.Singleton;

@Singleton
public class AppEngineCacheImpl implements Cache {

    private final MemcacheService memcacheService;
    private final Logger logger;

    @Inject
    public AppEngineCacheImpl(Logger logger) {
        this.memcacheService = MemcacheServiceFactory.getMemcacheService();
        
        this.logger = logger;
    }

    private void put(String key,
                     Object value,
                     int expiration,
                     MemcacheService.SetPolicy policy) {

        if (expiration == Integer.MAX_VALUE) {
            memcacheService.put(key, wrap(value), null, policy);
        } else {
            memcacheService.put(key, wrap(value),
                    Expiration.byDeltaSeconds(expiration), policy);
        }
    }

    public void add(String key, Object value, int expiration) {
        put(key, value, expiration,
                MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT);
    }

    public boolean safeAdd(String key, Object value, int expiration) {
        put(key, value, expiration,
                MemcacheService.SetPolicy.ADD_ONLY_IF_NOT_PRESENT);
        return true;
    }

    public void set(String key, Object value, int expiration) {
        put(key, value, expiration, MemcacheService.SetPolicy.SET_ALWAYS);
    }

    public boolean safeSet(String key, Object value, int expiration) {
        put(key, value, expiration, MemcacheService.SetPolicy.SET_ALWAYS);
        return true;
    }

    public void replace(String key, Object value, int expiration) {
        put(key, value, expiration,
                MemcacheService.SetPolicy.REPLACE_ONLY_IF_PRESENT);
    }

    public boolean safeReplace(String key, Object value, int expiration) {
        put(key, value, expiration,
                MemcacheService.SetPolicy.REPLACE_ONLY_IF_PRESENT);
        return true;
    }

    public Object get(String key) {
        return unwrap(memcacheService.get(key));
    }

    public Map<String, Object> get(String[] keys) {
        List<String> list = Arrays.asList(keys);
        Map<String, Object> map = memcacheService.getAll(list);
        Map<String, Object> result = new HashMap<String, Object>();
        for (final Map.Entry<String, Object> entrySet : map.entrySet()) {
            result.put(entrySet.getKey(), unwrap(entrySet.getValue()));
        }
        return result;
    }

    public long incr(String key, int by) {
        return memcacheService.increment(key, by);
    }

    public long decr(String key, int by) {
        return memcacheService.increment(key, -by);
    }

    public void clear() {
        memcacheService.clearAll();
    }

    public void delete(String key) {
        memcacheService.delete(key);
    }

    public boolean safeDelete(String key) {
        memcacheService.delete(key);
        return true;
    }

    public void stop() {
    }

    byte[] wrap(Object o) {
        if (o == null) {
            return null;
        }
        try {
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bytes);
            oos.writeObject(o);
            return bytes.toByteArray();
        } catch (Exception e) {
            String ERROR_MESSAGE = "Cannot wrap a non-serializable value of type ";
            logger.error(ERROR_MESSAGE, e);
            throw new RuntimeException(ERROR_MESSAGE, e);
        }
    }

    Object unwrap(Object bytes) {
        if (bytes == null) {
            return null;
        }
        try {
            return new ObjectInputStream(new ByteArrayInputStream(
                    (byte[]) bytes)) {

                @Override
                protected Class<?> resolveClass(ObjectStreamClass desc)
                        throws IOException, ClassNotFoundException {
                    return Class.forName(desc.getName(), false, Thread
                            .currentThread().getContextClassLoader());
                }
            }.readObject();
        } catch (Exception e) {
            logger.error("Error while deserializing cached value", e);
            return null;
        }
    }
}
