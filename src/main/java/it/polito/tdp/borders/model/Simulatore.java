package it.polito.tdp.borders.model;

import java.util.*;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;

public class Simulatore {
	
	
	//modello(stato del sistema di cui tenere traccia ad ogni passo)
	private Graph<Country, DefaultEdge> grafo;
	
	
	//tipi di evento
	private PriorityQueue<Evento> queue;
	
	
	
	//parametri
	
	private int N_MIGRANTI = 1000; 
	private Country partenza; 
	
	
	
	//output
	
	private int T =-1; //n di passi
	private Map<Country, Integer> stanziali; 
	
	//salviamo i parametri che ci passa il model
	public void init(Country partenza, Graph<Country, DefaultEdge> grafo) {
		this.partenza = partenza; 
		this.grafo=grafo;
		
		//inizializzo lo stato iniziale
		this.T= 1; 
		stanziali = new HashMap<>();
		
		for(Country c: this.grafo.vertexSet()) {
			stanziali.put(c, 0);
		}
		
		this.queue = new PriorityQueue<Evento>();
		//inserisco il primo evento
		this.queue.add(new Evento(T,partenza,N_MIGRANTI));
		
		
		
	}
	
	public void run() {
		//finchè la coda non si svuota estraggo un evento per volta e lo eseguo
		Evento e; 
		while((e= this.queue.poll())!=null) {
			this.T=e.getTempo();
			//eseguo l'evento e
			int nPersone = e.getN(); 
			Country c = e.getStato(); 
			//cerco i vicini di stato 
			List<Country> vicini = Graphs.neighborListOf(grafo, c);
			
			int migranti = (nPersone/2)/vicini.size(); 
			
			if( migranti>0 ) {
				//le persone si possono muovere 
				for(Country confinante: vicini) {
					queue.add(new Evento( e.getTempo()+1, confinante, migranti));
					
				}
			}
			
			int stanziali = nPersone- migranti*vicini.size(); 
			
			this.stanziali.put(c, this.stanziali.get(c)+stanziali);
			
		}
		
	}
	
	public Map<Country,Integer> getStanziali(){
		return this.stanziali;
	}
	//il numero di passi simulati sarà l'ultimo t della coda
	
	public Integer getT() {
		return this.T; //ritornerà l'ultimo t sovrascritto quindi l'ultimo passo
	}
	
	

}
