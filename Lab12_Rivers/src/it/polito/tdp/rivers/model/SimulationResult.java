package it.polito.tdp.rivers.model;

public class SimulationResult {

	private double cMed;
	private int giorniInsoddisfatti;
	
	/**
	 * @param cMed Occupazione media del bacino
	 * @param giorniSoddisfatti il numero di giorni in cui non si è potuta garantire l’erogazione minima
	 */
	public SimulationResult(double cMed, int giorniInsoddisfatti) {
		super();
		this.cMed = cMed;
		this.giorniInsoddisfatti = giorniInsoddisfatti;
	}

	public double getcMed() {
		return cMed;
	}

	public int getGiorniInsoddisfatti() {
		return giorniInsoddisfatti;
	}

}
