package de.michelblank.brausteuerung.backend.calculator;

import de.michelblank.brausteuerung.backend.model.TargetStateConfiguration;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Component
public class TargetStateConfigurationManager {
    private Queue<TargetStateConfiguration> configurationQueue;

    public TargetStateConfigurationManager() {
        this.configurationQueue = new ConcurrentLinkedQueue<>();
    }

    @Nullable
    public TargetStateConfiguration getCurrentConfiguration() {
        return this.configurationQueue.peek();
    }

    public boolean hasNextConfiguration() {
        return this.configurationQueue.size() > 1;
    }

    @Nullable
    public TargetStateConfiguration getNextConfiguration() {
        if (hasNextConfiguration()) {
            this.configurationQueue.remove();
            return getCurrentConfiguration();
        } else {
            return null;
        }
    }

    public void clearConfigurations() {
        this.configurationQueue.clear();
    }

    public void addConfiguration(@NonNull TargetStateConfiguration tsc) {
        this.configurationQueue.add(tsc);
    }
}
