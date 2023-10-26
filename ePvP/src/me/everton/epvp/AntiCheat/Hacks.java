package me.everton.epvp.AntiCheat;

public enum Hacks {
	MACRO("Macro"), AUTOSOUP("Auto-Soup"), FF("ForceField"), FLY("Fly"), SPEED("Speed");
	
	private final String nome;
	 
	private Hacks(final String n){
		this.nome = n;
	}
	 
	public String toString(){
		return nome;
	}
}
