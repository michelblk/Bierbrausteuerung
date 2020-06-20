package de.michelblank.brausteuerung.backend.model;

public interface ConfigurationChangedListener {
    void onConfigurationChanged(TargetStateConfiguration tsc);
}
