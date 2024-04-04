package ru.zayceva.spring.ProjectRest.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
import ru.zayceva.spring.ProjectRest.models.Sensor;

public class MeasurementDTO {
    @NotNull
    @Min(-100)
    @Max((100))
    private Double value;
    @NotNull
    private boolean raining;

    @NotNull
    private SensorDTO sensor;


    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
