package treative.symulacja.model;

import javax.persistence.*;

@Entity
@Table(name = "simulations")
public class Simulation {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "parametersId")
	private long parametersId;

	@Column(name = "day")
	private int day;

	@Column(name = "numPi")
	private int numPi;

	@Column(name = "numPv")
	private int numPv;

	@Column(name = "numPm")
	private int numPm;

	@Column(name = "numPr")
	private int numPr;

	@OneToOne
	@JoinColumn(name = "parameter")
	private Parameters parameters;

	public Simulation() {

	}

	public Simulation(long parametersId, int day, int numPi, int numPv, int numPm, int numPr) {
		this.parametersId = parametersId;
		this.day = day;
		this.numPi = numPi;
		this.numPv = numPv;
		this.numPm = numPm;
		this.numPr = numPr;
	}

	public long getId() {
		return id;
	}

	public long getParametersId() {
		return parametersId;
	}

	public void setParametersId(long parametersId) {
		this.parametersId = parametersId;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getNumPi() {
		return numPi;
	}

	public void setNumPi(int numPi) {
		this.numPi = numPi;
	}

	public int getNumPv() {
		return numPv;
	}

	public void setNumPv(int numPv) {
		this.numPv = numPv;
	}

	public int getNumPm() {
		return numPm;
	}

	public void setNumPm(int numPm) {
		this.numPm = numPm;
	}

	public int getNumPr() {
		return numPr;
	}

	public void setNumPr(int numPr) {
		this.numPr = numPr;
	}

	@Override
	public String toString() {
		return "Simulation [id=" + id
				+ ", parametersId" + parametersId
				+ ", day=" + day
				+ ", numPi=" + numPi
				+ ", numPv=" + numPv
				+ ", numPm=" + numPm
				+ ", numPr=" + numPr+ "]";
	}

}

