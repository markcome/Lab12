package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Flow implements Comparable<Flow>{
	
	private int id;
	private LocalDate day;
	private float flow;
	private River river;

	public Flow(int id, LocalDate day, float flow, River river) {
		super();
		this.id = id;
		this.day = day;
		this.flow = flow;
		this.river = river;
	}

	public int getId() {
		return id;
	}

	public LocalDate getDay() {
		return day;
	}

	public float getFlow() {
		return flow;
	}

	public River getRiver() {
		return river;
	}

	@Override // Ordinamento per data
	public int compareTo(Flow o) {
	
		if(this.day.isAfter(o.getDay()))
			return 1;
		else if (this.day.isBefore(o.getDay()))
			return -1;
		else 
			return 0;
	}
	
	
	
}
