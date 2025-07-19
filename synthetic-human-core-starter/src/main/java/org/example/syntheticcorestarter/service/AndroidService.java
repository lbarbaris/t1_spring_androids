package org.example.syntheticcorestarter.service;

import org.example.syntheticcorestarter.android.Android;
import org.example.syntheticcorestarter.android.AndroidFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AndroidService {
    private final AndroidFactory androidFactory;

    public Android createAndroid(Long id, int queueCapacity) {
        return androidFactory.getOrCreate(id, queueCapacity);
    }

    public Optional<Android> getAndroid(Long id) {
        return Optional.ofNullable(androidFactory.getAndroid(id));
    }

    public List<Android> getAllAndroids() {
        return androidFactory.getAllAndroids();
    }

    public void deleteAndroid(Long id) {
        androidFactory.deleteById(id);
    }

    public boolean androidExists(Long id) {
        return androidFactory.existsById(id);
    }
}
