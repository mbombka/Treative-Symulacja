package treative.symulacja.controller;

import treative.symulacja.model.Parameters;
import treative.symulacja.model.Simulation;


public class FillSimulation {

    public static Simulation[]  createSimmulation(Parameters parameter){
        //create array of simulation with size of days
        Simulation[] simulation = new Simulation[parameter.getNumTs() + 1];
        //set values to temporary variable for clear algorithm view
        int population = parameter.getpopulation();
        int initInfected = Math.min(population, parameter.getInfected());
        double numR = Math.max(0, parameter.getNumR());
        double numM = Math.max(0, parameter.getNumM());
        int numTi = Math.max(0, parameter.getNumTi());
        int numTm = Math.max(0, parameter.getNumTm());
        int numTs = Math.max(0, parameter.getNumTs());

        Simulation testDay = new Simulation();
        simulation[0] = testDay;

        //Set values for first day
        simulation[0].setParametersId(parameter.getId());
        simulation[0].setDay(0);
        simulation[0].setNumPi(initInfected);
        simulation[0].setNumPv(population - initInfected);
        simulation[0].setNumPm(0);
        simulation[0].setNumPr(0);

        //iterate trough simulation array and set values for next numTs days
        for(int i = 1; i <= numTs; i++) {

            Simulation currentDay = new Simulation();
            Simulation previousDays = simulation[i-1];
            int _numPassed = 0;
            int _numPr = 0;
            int _numInfected = 0;

            //set number of death Pm
            if(i >= numTm) {
                _numPassed = (int) Math.min(Math.min(numM, simulation[i - numTm].getNumPi()), previousDays.getNumPi());
                _numPassed = Math.max(0, _numPassed);
                _numPassed += previousDays.getNumPm();
                currentDay.setNumPm(_numPassed);
            } else {
                currentDay.setNumPm(0);
            }
            //set number of recovered Pr
            if(i >= numTi) {
                _numPr = (int) Math.min((simulation[i - numTi].getNumPi() - numM), previousDays.getNumPi());
                _numPr = Math.max(0, _numPr);
                _numPr += previousDays.getNumPr();
                currentDay.setNumPr(_numPr);
            } else {
                currentDay.setNumPr(0);
            }
            //set number of infected persons
            if(numR > 0) {
                _numInfected = (int) Math.ceil(Math.min(previousDays.getNumPv() + previousDays.getNumPi() - _numPassed, previousDays.getNumPi() * numR));
            } else {
                _numInfected = previousDays.getNumPi();
            }
            _numInfected = _numInfected  - _numPr - _numPassed;
            _numInfected = Math.max(0, _numInfected);
            currentDay.setNumPi(_numInfected);

            //set number of healthy person Pv
            int _numHealthy = population -  currentDay.getNumPm() - currentDay.getNumPi() - currentDay.getNumPr();
            currentDay.setNumPv(_numHealthy);

            //set parameterID
            currentDay.setParametersId(parameter.getId());
            //set day
            currentDay.setDay(i);
            //move current day to simulation array
            simulation[i] = currentDay;

        }
      return simulation;
    }

}
