package treative.symulacja.model;

import com.sun.istack.NotNull;
import javax.persistence.*;


@Entity
@Table(name = "parameters")
public class Parameters {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotNull
	@Column(name = "name")
	private String name;

	@NotNull
	@Column(name = "population")
	private int population;

	@NotNull
	@Column(name = "infected")
	private int infected;

	@NotNull
	@Column(name = "numR")
	private double numR;

	@NotNull
	@Column(name = "numM")
	private double numM;

	@NotNull
	@Column(name = "numTi")
	private int numTi;

	@NotNull
	@Column(name = "numTm")
	private int numTm;

	@NotNull
	@Column(name = "numTs")
	private int numTs;

	@OneToOne //(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "simulationId", referencedColumnName = "id")
	private  Simulation[] simulation;



	public Parameters() {

	}

	public Parameters(String name, int population, int infected, double numR, double numM, int numTi, int numTm, int numTs) {
		this.name = name;
		this.population = population;
		this.infected = infected;
		this.numR = numR;
		this.numM = numM;
		this.numTi = numTi;
		this.numTm = numTm;
		this.numTs = numTs;

	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getpopulation() {
		return population;
	}
	public void setpopulation(int population) {
		this.population = population;
	}

	public int getInfected() {
		return infected;
	}
	public void setInfected(int infected) {
		this.infected = infected;
	}

	public double getNumR() {
		return numR;
	}
	public void setNumR(double numR) {
		this.numR = numR;
	}

	public double getNumM() {
		return numM;
	}
	public void setNumM(double numM) {
		this.numM = numM;
	}

	public int getNumTi() {
		return numTm;
	}
	public void setNumTi(int numTi) {
		this.numTi = numTi;
	}

	public int getNumTm() {
		return numTm;
	}
	public void setNumTm(int numTm) {
		this.numTm = numTm;
	}

	public int getNumTs() {
		return numTs;
	}
	public void setNumTs(int numTs) {
		this.numTs = numTs;
	}


	@Override
	public String toString() {
		return "Properties [id=" + id
				+ ", name=" + name
				+ ", population=" + population
				+ ", infected=" + infected
				+ ", numR=" + numR
				+ ", numM=" + numM
				+ ", numTi=" + numTi
				+ ", numTm=" + numTm
				+ ", numTs=" + numTs  +"]";
	}




}
