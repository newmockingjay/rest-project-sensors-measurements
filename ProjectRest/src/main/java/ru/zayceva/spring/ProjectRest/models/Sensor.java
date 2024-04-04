package ru.zayceva.spring.ProjectRest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "Sensor")
public class Sensor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name")
    @Size(min = 3, max = 30, message = "Size of name should be between 3 and 30 characters")
    private String name;

//    @OneToMany(mappedBy = "sensor")
//    private List<Measurement> measurements;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Measurement> getMeasurements() {
//        return measurements;
//    }
//
//    public void setMeasurements(List<Measurement> measurements) {
//        this.measurements = measurements;
//    }
}
