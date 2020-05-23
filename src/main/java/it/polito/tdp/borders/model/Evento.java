package it.polito.tdp.borders.model;

public class Evento implements Comparable<Evento>{

	private int tempo; 
	private Country stato;  //stato in cui arrivano i migranti al tempo t
	private int n;  //num migranti che arrivano nello stato al tempo t 
	
	
	public Evento(int tempo, Country stato, int n) {
	    this.tempo = tempo;
		this.stato = stato;
		this.n = n;
	}
	
	public int getTempo() {
		return tempo;
	}
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	public Country getStato() {
		return stato;
	}
	public void setStato(Country stato) {
		this.stato = stato;
	}
	public int getN() {
		return n;
	}
	public void setN(int n) {
		this.n = n;
	}

	@Override
	public int compareTo(Evento o) {
		return this.tempo - o.getTempo();
	}
	
	
	
}
