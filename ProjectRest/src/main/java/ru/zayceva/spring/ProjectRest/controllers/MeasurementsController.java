package ru.zayceva.spring.ProjectRest.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.zayceva.spring.ProjectRest.dto.MeasurementDTO;
import ru.zayceva.spring.ProjectRest.dto.MeasurementsResponse;
import ru.zayceva.spring.ProjectRest.models.Measurement;
import ru.zayceva.spring.ProjectRest.services.MeasurementsService;
import ru.zayceva.spring.ProjectRest.util.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/measurements")
public class MeasurementsController {
    private final MeasurementsService measurementsService;
    private final ModelMapper modelMapper;
    private final MeasurementValidator measurementValidator;

    @Autowired
    public MeasurementsController(MeasurementsService measurementsService, ModelMapper modelMapper, MeasurementValidator measurementValidator){
        this.measurementsService = measurementsService;
        this.modelMapper = modelMapper;
        this.measurementValidator = measurementValidator;
    }

    @GetMapping()
    public ResponseEntity<MeasurementsResponse> getAll(){
        MeasurementsResponse measurementsResponse =  new MeasurementsResponse(
                measurementsService.findAll()
                        .stream()
                        .map(this::convertToMeasurementDTO)
                        .collect(Collectors.toList())
        );
        return new ResponseEntity<>(measurementsResponse, HttpStatus.OK);
    }

    @GetMapping("/rainyDaysCount")
    public ResponseEntity<Long> getRainyDays(){
        Long counter = measurementsService.findAll()
                .stream()
                .filter(Measurement::isRaining)
                .count();
        return new ResponseEntity<>(counter, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> addMeasurement(@RequestBody @Valid MeasurementDTO measurementDTO,
                                                     BindingResult bindingResult){

        measurementValidator.validate(convertToMeasurement(measurementDTO), bindingResult);

        if (bindingResult.hasErrors()){
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors){
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }

            throw new MeasurementException(errorMsg.toString());
        }

        measurementsService.save(convertToMeasurement(measurementDTO));

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<SensorErrorResponse> handleException(MeasurementException e){
        SensorErrorResponse response = new SensorErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

    }

    private Measurement convertToMeasurement(MeasurementDTO measurementDTO){
        return  modelMapper.map(measurementDTO, Measurement.class);
    }

    private MeasurementDTO convertToMeasurementDTO(Measurement measurement){
        return modelMapper.map(measurement, MeasurementDTO.class);
    }
}
