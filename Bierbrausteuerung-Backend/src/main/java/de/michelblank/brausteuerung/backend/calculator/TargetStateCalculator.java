package de.michelblank.brausteuerung.backend.calculator;

import de.michelblank.brausteuerung.backend.model.TargetState;
import de.michelblank.brausteuerung.backend.model.TargetStateConfiguration;
import de.michelblank.brausteuerung.backend.repository.TargetStateConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TargetStateCalculator {
    private TargetState currentState;
    private TargetStateConfigurationManager configurationManager;
    private TargetStateConfigurationRepository targetStateConfigurationRepository;

    @Autowired
    public TargetStateCalculator(TargetStateConfigurationManager configurationManager,
                                 TargetStateConfigurationRepository targetStateConfigurationRepository) {
        this.configurationManager = configurationManager;
        this.targetStateConfigurationRepository = targetStateConfigurationRepository;
    }

    @NonNull
    public TargetState getState(float currentTemperature) {
        TargetStateConfiguration configuration = getConfiguration();

        currentState = calculateState(currentTemperature, configuration);
        updateConfigurationTimer(currentTemperature, configuration);

        return currentState;
    }

    @Nullable
    private TargetStateConfiguration getConfiguration() {
        TargetStateConfiguration currentConfig = this.configurationManager.getCurrentConfiguration();
        if (currentConfig == null || currentConfig.isFinished()) {
            currentConfig = null;
            if (this.configurationManager.hasNextConfiguration()) {
                currentState = null;
                currentConfig = this.configurationManager.getNextConfiguration();
            }
        }
        return currentConfig;
    }

    @NonNull
    private TargetState calculateState(float currentTemperature, @Nullable TargetStateConfiguration configuration) {
        if (configuration != null) {
            return new TargetState(
                    configuration.getTargetTemperature(),
                    calculateIsHeating(currentTemperature, configuration));
        } else {
            return new TargetState(
                    null, false
            );
        }
    }

    private boolean calculateIsHeating(float currentTemperature, @NonNull TargetStateConfiguration configuration) {
        boolean isHeating = currentState != null && currentState.isHeating();
        float targetTemperature = configuration.getTargetTemperature();
        float buffer = configuration.getBuffer();
        return ((currentTemperature < targetTemperature - buffer && !isHeating) // is not heating and buffer is reached
                || (currentTemperature < targetTemperature && isHeating)); // is already heating and has to heat until target is reached
    }

    private void updateConfigurationTimer(float currentTemperature, @Nullable TargetStateConfiguration configuration) {
        if (configuration != null
                && configuration.getStartedAt() == null // only set if not set already
                && currentTemperature >= configuration.getTargetTemperature() // temperature has to at least be at target // TODO evalute if !#isHeating() would be better
        ) {
            // when target temperature of configuration is reached, start timer
            configuration.setStartedAt(LocalDateTime.now());
            this.targetStateConfigurationRepository.save(configuration); // TODO find better solution
        }
    }
}
