package de.michelblank.brausteuerung.backend.py4j;

import de.michelblank.brausteuerung.backend.calculator.TargetStateCalculator;
import de.michelblank.brausteuerung.backend.model.CurrentState;
import de.michelblank.brausteuerung.backend.model.TargetState;
import de.michelblank.brausteuerung.backend.repository.CurrentStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import py4j.GatewayServer;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class Py4JProcessor implements PythonInterface {
    private static final int SAVE_INTERVAL_IN_SECONDS = 30;
    private static final Logger LOGGER = LoggerFactory.getLogger(Py4JProcessor.class);

    private TargetStateCalculator calculator;
    private CurrentStateRepository currentStateRepository;

    private GatewayServer gatewayServer;
    private LocalDateTime lastSave = null;

    @Autowired
    private Py4JProcessor(TargetStateCalculator calculator, CurrentStateRepository currentStateRepository) {
        this.calculator = calculator;
        this.currentStateRepository = currentStateRepository;
        this.gatewayServer = new GatewayServer(this);
    }

    @PostConstruct
    private void init() {
        LOGGER.info("Starting Py4J Gateway ...");
        this.gatewayServer.start();
        LOGGER.info("... Gateway started");
    }

    @Override
    public TargetState getState(float temperature) {
        LOGGER.debug("TargetState requested for temperature {}", temperature);
        TargetState targetState = calculator.getState(temperature);
        saveStateToDatabase(targetState, temperature);
        return targetState;
    }

    private void saveStateToDatabase(@NonNull TargetState targetState, float currentTemperature) {
        if (lastSave == null
                || LocalDateTime.now().isAfter(lastSave.plus(SAVE_INTERVAL_IN_SECONDS, ChronoUnit.SECONDS))) {
            CurrentState currentState = new CurrentState(
                    null,
                    currentTemperature,
                    targetState.getTargetTemperature(),
                    targetState.isHeating(),
                    LocalDateTime.now());
            lastSave = LocalDateTime.now();
            this.currentStateRepository.save(currentState);
        } else {
            // dismiss
        }
    }
}
