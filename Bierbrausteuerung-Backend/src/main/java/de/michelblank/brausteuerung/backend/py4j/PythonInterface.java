package de.michelblank.brausteuerung.backend.py4j;

import de.michelblank.brausteuerung.backend.model.TargetState;

public interface PythonInterface {
    TargetState getState(float temperature);
}
