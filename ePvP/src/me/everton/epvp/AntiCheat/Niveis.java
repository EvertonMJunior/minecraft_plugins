package me.everton.epvp.AntiCheat;

public enum Niveis {
	TALVEZ("Talvez"), PROVAVELMENTE("Provavelmente"), DEFINITIVAMENTE("Definitivamente");
	
	private final String nome;
	 
	private Niveis(final String n){
		this.nome = n;
	}
	 
	public String toString(){
		return nome;
	}
}
