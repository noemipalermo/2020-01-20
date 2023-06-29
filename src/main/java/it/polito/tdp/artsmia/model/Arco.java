package it.polito.tdp.artsmia.model;

public class Arco implements Comparable<Arco> {

	private int a1;
	private int a2;
	private int peso;
	
	public Arco(int i, int j, int peso) {
		super();
		this.a1 = i;
		this.a2 = j;
		this.peso = peso;
	}

	public int getA1() {
		return a1;
	}

	public int getA2() {
		return a2;
	}

	public int getPeso() {
		return peso;
	}

	@Override
	public int compareTo(Arco o) {
		
		return o.getPeso()-this.peso;
	}
	
	
}
