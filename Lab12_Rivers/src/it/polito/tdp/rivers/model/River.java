package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class River implements Comparable<River>{
	
	private int id;
	private String name;
	private List<Flow> misurazioni;
	private double mediaFlusso;
	
	public River(int id, String name) {
		this.id = id;
		this.name = name;
		this.mediaFlusso = 0.0;
		this.misurazioni = new ArrayList<Flow>();
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Flow> getMisurazioni() {
		return misurazioni;
	}
	
	public void addMisurazione(Flow misurazione) {
		this.misurazioni.add(misurazione);
		this.mediaFlusso = this.calcolaMedia();
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(River o) {
		return this.name.compareTo(o.name);
	}
	
	private double calcolaMedia() {
		double somma = 0.0;
		for(Flow f: misurazioni) {
			somma += f.getFlow();
		}
		return somma/misurazioni.size();
	}
	
	/*
	 * PER POPOLARE L'INTERFACCIA DOPO LA SELEZIONE DEL FIUME
	 */
	public Flow getUltimaMisurazione() {
		Collections.sort(misurazioni);
		return misurazioni.get(misurazioni.size() -1);
	}
	
	public Flow getPrimaMisurazione() {
		Collections.sort(misurazioni);
		return misurazioni.get(0);
	}
	
	public double getMediaFlusso() {
		return this.mediaFlusso;
	}
	
	public int numeroTotMisurazioni() {
		return misurazioni.size();
	}
}
