package ru.zayceva.spring.ProjectRest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zayceva.spring.ProjectRest.models.Measurement;
import ru.zayceva.spring.ProjectRest.repositories.MeasurementsRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MeasurementsService {
    private final MeasurementsRepository measurementsRepository;
    private final SensorsService sensorsService;

    @Autowired
    public MeasurementsService(MeasurementsRepository measurementsRepository, SensorsService sensorsService) {
        this.measurementsRepository = measurementsRepository;
        this.sensorsService = sensorsService;
    }

    public List<Measurement> findAll(){
        return measurementsRepository.findAll();
    }

    public Long findRainyDays(){
        return (long) measurementsRepository.findByRainingTrue().size();
    }

    @Transactional
    public void save(Measurement measurement){
        addAllFields(measurement);
        measurementsRepository.save(measurement);
    }

    public void addAllFields(Measurement measurement){
        measurement.setTime(LocalDateTime.now());
        measurement.setSensor(sensorsService.findByName(measurement.getSensor().getName()).get());
    }
}
