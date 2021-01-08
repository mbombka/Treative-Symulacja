package treative.symulacja.repository;


import org.springframework.data.repository.CrudRepository;
import treative.symulacja.model.Simulation;

import java.util.List;

public interface SimulationRepository extends CrudRepository<Simulation, Long> {
  List<Simulation> findByParametersId(long parametersId);
  Simulation deleteByParametersId(long parametersId);

}
