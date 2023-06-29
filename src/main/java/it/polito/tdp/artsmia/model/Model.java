package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	
	private List<String> allRoles;
	private List<Integer> allArtists;
	private Graph<Integer, DefaultWeightedEdge> graph;
	private List<Arco> edges;
	private ArtsmiaDAO dao;
	
	//per ricorsione
	private List<Integer> best;
	
	
	public Model() {
		this.dao = new ArtsmiaDAO();
		allRoles = dao.getRoles();
		allArtists = new ArrayList<>();
		edges = new ArrayList<>();
	}

	public void creagrafo(String role) {
		graph = new SimpleWeightedGraph<Integer, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		allArtists.clear();
		edges.clear();
		
		allArtists = dao.getArtistsFilteredByRole(role);
		Graphs.addAllVertices(this.graph, allArtists);
		
		edges= dao.getEdges(role);
		
		for(Arco a: edges) {
			Graphs.addEdgeWithVertices(this.graph, a.getA1(), a.getA2(), a.getPeso());
		}
		
	}
	
	public List<Arco> getArtistiConnessi(){
		
		List<Arco> connessi = this.edges;
		Collections.sort(connessi);
		return connessi;
	}
	public List<String> getAllRoles(){
		return allRoles;
	}
	
	public List<Integer> getAllVertex(){
		return allArtists;
	}
	
	public boolean isInGraph(int idArtista) {
		if(!this.graph.containsVertex(idArtista)) {
			return false;
		}
		return true;
	}
	public List<Integer> calcolaPercorso(int idArtista) {
		
		this.best= new ArrayList<>();
		List<Integer> parziale = new ArrayList<>();
		parziale.add(idArtista);
		
		ricorsione(parziale, -1);
		
		return best;
	}

	private void ricorsione(List<Integer> parziale, int peso) {
		Integer ultimo = parziale.get(parziale.size()-1);
		
		// vicini
		List<Integer> vicini = Graphs.neighborListOf(this.graph, ultimo);
		for(Integer v: vicini) {
			//primo livello della ricorsion
			if(!parziale.contains(v) && peso==-1) {
				parziale.add(v);
				ricorsione(parziale, (int)this.graph.getEdgeWeight(this.graph.getEdge(ultimo, v)));
				parziale.remove(v);
			}else {
				if(!parziale.contains(v) && peso==(int)this.graph.getEdgeWeight(this.graph.getEdge(ultimo, v))) {
					parziale.add(v);
					ricorsione(parziale, peso);
					parziale.remove(v);
				}
			}
		}
		if(parziale.size()>best.size()) {
			best = new ArrayList<>(parziale);
		}
		
	}
	
}
