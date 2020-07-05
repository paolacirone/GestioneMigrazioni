package it.polito.tdp.borders.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import it.polito.tdp.borders.db.BordersDAO;

public class Model {
	
    private Graph<Country,DefaultEdge> grafo;
    private Map<Integer,Country> idMap;
    private BordersDAO dao;
    
	public Model() {
		
		dao = new BordersDAO();
		

	}
	
	public void creaGrafo(int anno) {
		
		grafo = new SimpleGraph<Country, DefaultEdge>(DefaultEdge.class);
        
		idMap = new HashMap<>();
		
		List<Country> countries = dao.loadAllCountries(idMap);
		
		for(Adiacenza a: dao.getAdiacenze(idMap, anno)) {
			if(!this.grafo.containsVertex(a.getC1())) {
				this.grafo.addVertex(a.getC1());
			}
			if(!this.grafo.containsVertex(a.getC2())) {
				this.grafo.addVertex(a.getC2());
			}
			Graphs.addEdgeWithVertices(this.grafo, a.getC1(), a.getC2());
		}
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	

}
