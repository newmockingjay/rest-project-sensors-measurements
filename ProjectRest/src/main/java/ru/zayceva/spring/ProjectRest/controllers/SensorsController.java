package ru.zayceva.spring.ProjectRest.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.zayceva.spring.ProjectRest.dto.SensorDTO;
import ru.zayceva.spring.ProjectRest.models.Sensor;
import ru.zayceva.spring.ProjectRest.services.SensorsService;
import ru.zayceva.spring.ProjectRest.util.SensorErrorResponse;
import ru.zayceva.spring.ProjectRest.util.SensorNotCreatedException;
import ru.zayceva.spring.ProjectRest.util.SensorValidator;

import java.util.List;

@RestController
@RequestMapping("/sensors")
public class SensorsController {
    private final SensorsService sensorsService;
    private final ModelMapper modelMapper;
    private final SensorValidator sensorValidator;

    @Autowired
    public SensorsController(SensorsService sensorsService, ModelMapper modelMapper, SensorValidator sensorValidator) {
        this.sensorsService = sensorsService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid SensorDTO sensorDTO,
                                             BindingResult bindingResult){
        Sensor sensor = converToSensor(sensorDTO);

        sensorValidator.validate(sensor, bindingResult);

        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new SensorNotCreatedException(errorMsg.toString());
            //throw new RuntimeException();
        }

        sensorsService.save(sensor);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler()
    public ResponseEntity<SensorErrorResponse> handleException(SensorNotCreatedException e){
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    private SensorDTO converToSensorDTO(Sensor sensor){
        return modelMapper.map(sensor, SensorDTO.class);
    }

    private Sensor converToSensor(SensorDTO sensorDTO){
        return modelMapper.map(sensorDTO, Sensor.class);
    }
}
