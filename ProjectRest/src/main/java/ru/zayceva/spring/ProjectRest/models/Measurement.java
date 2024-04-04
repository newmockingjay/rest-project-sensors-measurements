package ru.zayceva.spring.ProjectRest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "Measurement")
public class Measurement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    //@Size(min = -100, max = 100, message = "Value should between -100 and 100")
    @NotNull
    @Min(-100)
    @Max((100))
    @Column(name = "value")
    private Double value;
    @NotNull
    @Column(name = "raining")
    private boolean raining;
    @NotNull
    @Column(name = "time")
    private LocalDateTime time;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "sensor_id", referencedColumnName = "id")
    private Sensor sensor;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
