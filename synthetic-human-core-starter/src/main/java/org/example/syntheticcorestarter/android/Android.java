package org.example.syntheticcorestarter.android;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.syntheticcorestarter.command.Command;
import org.example.syntheticcorestarter.command.Priority;
import org.example.syntheticcorestarter.log.WatchingLogType;
import org.example.syntheticcorestarter.log.WeylandWatchingYou;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.*;

@Slf4j
@Component
@Scope("prototype")
public class Android {
    @Getter
    private final Long id;

    private final ThreadPoolExecutor executor;

    @Getter
    private final Map<String, Integer> completedTasksByAuthor;

    @Autowired
    private MeterRegistry meterRegistry;

    public Android(Long id, int queueCapacity) {
        this.id = id;
        this.executor = new ThreadPoolExecutor(
                1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(queueCapacity)
        );
        this.completedTasksByAuthor = new ConcurrentHashMap<>();
    }

    @WeylandWatchingYou(WatchingLogType.KAFKA)
    public void addCommand(Command command) {
        if (command.getPriority() == Priority.CRITICAL) {
            log.info("Android {} Started critical command: {}", id, command);
            incrementAuthorMetric(command.getAuthor());
            return;
        }

        executor.execute(() -> {
            try {
                log.info("Android {} Started command: {}", id, command);
                incrementAuthorMetric(command.getAuthor());
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                log.info("Android {} Interrupted.", id);
                Thread.currentThread().interrupt();
            }
        });
    }

    @PostConstruct
    public void initMetrics() {
        meterRegistry.gauge("android.queue.size", Tags.of("androidId", id.toString()),
                executor.getQueue(), BlockingQueue::size);
    }

    private void incrementAuthorMetric(String author) {
        completedTasksByAuthor.merge(author, 1, Integer::sum);

        meterRegistry.gauge(
                "android.tasks.completed",
                Tags.of("androidId", id.toString(), "author", author),
                completedTasksByAuthor,
                map -> map.getOrDefault(author, 0)
        );
    }
}

