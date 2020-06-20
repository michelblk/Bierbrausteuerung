package de.michelblank.brausteuerung.backend.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "configuration")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TargetStateConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "previous_id")
    private Integer previous;
    @Column(name = "target_temperature")
    private float targetTemperature;
    @Column(name = "temperature_buffer")
    private float buffer;
    @Column(name = "duration")
    private float duration;
    @Column(name = "started_at")
    private LocalDateTime startedAt;
    @Column(name = "aborted_at")
    private LocalDateTime abortedAt;

    public boolean isFinished() {
        return startedAt != null
                && startedAt
                .plus((long) duration, ChronoUnit.MINUTES)
                .plus((long) ((duration % 1) * 60), ChronoUnit.SECONDS)
                .isBefore(LocalDateTime.now());
    }

    public boolean isAborted() {
        return abortedAt != null;
    }

    public void abort() {
        this.setAbortedAt(LocalDateTime.now());
    }
}
