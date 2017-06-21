package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.DB.RiversDAO;

public class Model {
	
	private RiversDAO riversDao;
	private Map<Integer, River> rivers;
	private final double PROBABILITA = 0.05;
	

	public Model() {
		this.riversDao = new RiversDAO();
		this.rivers = new HashMap<Integer, River>();
	}
	
	public List<River> getAllRivers() {
		
		// Popolo RIVERS
		this.rivers = this.riversDao.getAllRiver();
		for(River r: rivers.values()) {
			for(Flow f: this.riversDao.getFlows(r)) {
				r.addMisurazione(f);
			}
		}
				
		if(rivers.isEmpty()) {
			System.out.println("Non è stato possibile popolare RIVERS");
			return null;
		}
		
		List<River> riv = new ArrayList<River>();
		for(River r: rivers.values()) {
			riv.add(r);
		}
		return riv;
	}
	
	public SimulationResult simulate(River river, double k) {
		
		
		if(k < 0 || k > 1) {
			System.out.println("Valore di k sbagliato");
			return null;
		}
		
		List<Double> capacita = new ArrayList<Double>();
	
		double Q = this.convertM2SecToM2Day(k * 30 * river.getMediaFlusso()); // Capienza totale
		double C = Q / 2 ; // Occupazione iniziale
		double f_out_min = this.convertM2SecToM2Day(0.8 * river.getMediaFlusso());
		int numGiorniInsoddisf = 0;
		
		for(Flow f: river.getMisurazioni()) {
			
			System.out.println("Date: " + f.getDay());
			
			double fOut = f_out_min;
			
			// Esiste il 5% di probabilità giornaliero che il flusso in uscita sia 10 volte maggiore
			if(Math.random() > 1-PROBABILITA) {
				fOut = 10 * f_out_min;
				System.out.println("fOut = 10 * f_out_min: ");
			}
			
			System.out.println("Out: " + fOut);
			System.out.println("In: " + f.getFlow());
			
			C += this.convertM2SecToM2Day(f.getFlow());
			
			if(C - fOut > Q) {
				// trancimazione --> riportata a livello massimo
				C = Q;
			} else if(C < fOut) {
				// la diga si svuota
				numGiorniInsoddisf ++;
				C = 0;
			} else {
				C -= fOut;
			}
			
			capacita.add(C);
		}
		
		// calcolo la media delle capacità
		double cMed = 0;
		for(Double d: capacita) {
			cMed += d;
		}
		cMed = cMed / capacita.size();
		
		System.out.println("Number of days in which it cannot guarantee the minimum capacity: " + numGiorniInsoddisf);
		System.out.println("Average Capacity: " + cMed);
		
		return new SimulationResult(cMed, numGiorniInsoddisf);
	}
	
	
	/*
	 * Utility
	 */
	
	public LocalDate getStartDate(River river) {
		return river.getPrimaMisurazione().getDay();
	}
	
	public LocalDate getEndDate(River river) {
		return river.getUltimaMisurazione().getDay();
	}
	
	public double convertM2SecToM2Day(double m2) {
		return m2 * 60 * 60 * 24;
	}
	
	public double convertM2DayToM2Sec(double m2) {
		return m2 / 60 / 60 / 24;
	}

}
