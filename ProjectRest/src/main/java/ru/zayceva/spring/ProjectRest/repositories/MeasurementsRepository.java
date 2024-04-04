package ru.zayceva.spring.ProjectRest.repositories;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zayceva.spring.ProjectRest.models.Measurement;

import java.util.List;

@Repository
public interface MeasurementsRepository extends JpaRepository<Measurement, Integer> {

    List<Measurement> findByRainingTrue();
}
