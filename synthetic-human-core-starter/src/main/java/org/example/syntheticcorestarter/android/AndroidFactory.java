package org.example.syntheticcorestarter.android;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class AndroidFactory {

    @Autowired
    private ApplicationContext context;

    private final Map<Long, Android> registry;

    public AndroidFactory() {
        registry = new ConcurrentHashMap<>();
    }

    public Android getOrCreate(Long id, int queueCapacity) {
        return registry.computeIfAbsent(id, key ->
                context.getBean(Android.class, id, queueCapacity)
        );
    }

    public Android getAndroid (Long id) {
        return registry.get(id);
    }

    public boolean existsById(Long id) {
        return registry.containsKey(id);
    }

    public List<Android> getAllAndroids() {
        return new ArrayList<>(registry.values());
    }

    public void deleteById(Long id) {
        registry.remove(id);
    }
}



