package de.michelblank.brausteuerung.backend.model;

import com.google.gson.annotations.SerializedName;
import lombok.*;
import org.springframework.lang.Nullable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class TargetState {
    @Nullable
    @SerializedName("targetTemperature")
    private Float targetTemperature;
    @SerializedName("isHeating")
    private boolean isHeating;
}
