package ru.zayceva.spring.ProjectRest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.zayceva.spring.ProjectRest.models.Sensor;
import ru.zayceva.spring.ProjectRest.repositories.SensorsRepository;
import ru.zayceva.spring.ProjectRest.services.SensorsService;

import java.util.Optional;

@Component
public class SensorValidator implements Validator {

    private final SensorsService sensorsService;

    @Autowired
    public SensorValidator(SensorsService sensorsService) {
        this.sensorsService = sensorsService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sensor.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sensor sensor = (Sensor) target;

        if (sensorsService.findByName(sensor.getName()).isPresent()){
            errors.rejectValue("name", "", "This sensor name is already exists");
        }
    }
}
