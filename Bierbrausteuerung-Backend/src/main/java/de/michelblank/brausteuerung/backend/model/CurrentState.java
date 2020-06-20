package de.michelblank.brausteuerung.backend.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recording")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CurrentState {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;
    @Column(name = "temperature")
    private float temperature;
    @Column(name = "target_temperature")
    private Float targetTemperature;
    @Column(name = "heating")
    private boolean heating;
    @Column(name = "date")
    private LocalDateTime date;
}
