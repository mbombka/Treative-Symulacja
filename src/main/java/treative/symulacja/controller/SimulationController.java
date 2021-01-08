package treative.symulacja.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import treative.symulacja.model.Simulation;
import treative.symulacja.repository.SimulationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class SimulationController {


	@Autowired
	SimulationRepository simulationRepository;

	@GetMapping("/simulation")
	public ResponseEntity<List<Simulation>> getAllSimulations() {
		try {
			List<Simulation> simulations = new ArrayList<Simulation>();

			simulationRepository.findAll().forEach(simulations::add);

			if (simulations.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(simulations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/simulation/parametersId/{parametersId}")
	public ResponseEntity<List<Simulation>> getSimulationByParametersId(@PathVariable("parametersId") long parametersId) {
		try {
			List<Simulation> simulations = new ArrayList<Simulation>();

			simulationRepository.findByParametersId(parametersId).forEach(simulations::add);

			if (simulations.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(simulations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PostMapping("/simulation")
	public ResponseEntity<Simulation> createSimulation(@RequestBody Simulation simulation) {
		try {
			Simulation _simulation = simulationRepository
					.save(new Simulation(simulation.getParametersId(), simulation.getDay(), simulation.getNumPi(), simulation.getNumPv(), simulation.getNumPm(), simulation.getNumPr()));

			return new ResponseEntity<>(_simulation, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/simulation/{id}")
	public ResponseEntity<Simulation> updateSimulation(@PathVariable("id") long id, @RequestBody Simulation simulation) {
		Optional<Simulation> simulationData = simulationRepository.findById(id);

		if (simulationData.isPresent()) {
			Simulation _simulation = simulationData.get();
			_simulation.setNumPi(simulation.getNumPi());
			_simulation.setNumPv(simulation.getNumPv());
			_simulation.setNumPm(simulation.getNumPm());
			_simulation.setNumPr(simulation.getNumPr());
			return new ResponseEntity<>(simulationRepository.save(_simulation), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/simulation/{id}")
	public ResponseEntity<HttpStatus> deleteSimulation(@PathVariable("id") long id) {
		try {
			simulationRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@Transactional
	@DeleteMapping("/simulation/{parametersId}")
	public ResponseEntity<HttpStatus> deleteSimulationbyParId(@PathVariable("parametersId") long parametersId) {
		try {
			simulationRepository.deleteByParametersId(parametersId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/simulation")
	public ResponseEntity<HttpStatus> deleteAllSimulations() {
		try {
			simulationRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


}
