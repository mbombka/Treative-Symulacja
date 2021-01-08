package treative.symulacja.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import treative.symulacja.model.Parameters;
import treative.symulacja.model.Simulation;
import treative.symulacja.repository.ParametersRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class ParametersController {

	@Autowired
	ParametersRepository parametersRepository;

	@Autowired
	SimulationController simulationController;

	@GetMapping("/parameters")
	public ResponseEntity<List<Parameters>> getAllParameters() {
		try {
			List<Parameters> parameters = new ArrayList<Parameters>();

			parametersRepository.findAll().forEach(parameters::add);

			if (parameters.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(parameters, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/parameters/{id}")
	public ResponseEntity<Parameters> getParametersById(@PathVariable("id") long id) {
		Optional<Parameters> parameterData = parametersRepository.findById(id);

		if (parameterData.isPresent()) {
			return new ResponseEntity<>(parameterData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/parameters")
	public ResponseEntity<Parameters> createParameters(@RequestBody Parameters parameter) {
		try {
			Parameters _parameter = parametersRepository
					.save(new Parameters(parameter.getName(), parameter.getpopulation(), parameter.getInfected(), parameter.getNumR(), parameter.getNumM(), parameter.getNumTi(), parameter.getNumTm(), parameter.getNumTs()  ));

			System.out.println(_parameter.toString());
			createSimmulation(_parameter);

			return new ResponseEntity<>(_parameter, HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/parameters/{id}")
	public ResponseEntity<Parameters> updateParameters(@PathVariable("id") long id, @RequestBody Parameters parameter) {
		Optional<Parameters> parameterData = parametersRepository.findById(id);

		if (parameterData.isPresent()) {
			Parameters _parameter = parameterData.get();
			_parameter.setName(parameter.getName());
			_parameter.setpopulation(parameter.getpopulation());
			_parameter.setInfected(parameter.getInfected());
			_parameter.setNumR(parameter.getNumR());
			_parameter.setNumM(parameter.getNumM());
			_parameter.setNumTi(parameter.getNumTi());
			_parameter.setNumTm(parameter.getNumTm());
			_parameter.setNumTs(parameter.getNumTs());
			//delete and re create simulations
			simulationController.deleteSimulationbyParId(_parameter.getId());
			createSimmulation(_parameter);

			return new ResponseEntity<>(parametersRepository.save(_parameter), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/parameters/{id}")
	public ResponseEntity<HttpStatus> deleteParameters(@PathVariable("id") long id) {
		try {
			parametersRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/parameters")
	public ResponseEntity<HttpStatus> deleteAllParameterss() {
		try {
			parametersRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}


	private void createSimmulation(Parameters parameter){

		//create simulation array
		Simulation[] simulation = new Simulation[parameter.getNumTs()];
		//fill simulation
		simulation = FillSimulation.createSimmulation(parameter);
		//save simulation to database
		for (Simulation day: simulation) {
			simulationController.createSimulation(day);
			System.out.println(day.toString());

		}
	}

}
