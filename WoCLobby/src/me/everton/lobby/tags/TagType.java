package me.everton.lobby.tags;

public enum TagType {
	DONO("DONO", "§4§l"),
	ADMIN("ADMIN", "§c§l"),
	CODER("CODER", "§c§l§o"),
	MODPLUS("MOD+", "§5§l§o"),
	MOD("MOD", "§5§l"),
	BUILDER("BUILDER", "§3§l"),
	TRIAL("TRIAL", "§d§l"),
	YOUTUBERPLUS("YT+", "§b§l§o"),
	YOUTUBER("YT", "§b§l"),
	PRO("PRO", "§6§l"),
	MVP("MVP", "§9§l"),
	VIP("VIP", "§a§l"),
	NORMAL("NORMAL", "§7");
	
	String tag;
	String cor;
	
	private TagType(String t, String color) {
		tag = t;
		cor = color;
	}
	
	public String getTag() {
		return tag;
	}
	
	public String getColor() {
		return cor;
	}
	
	public String getPrefix() {
		return cor + tag + " " + cor.replace("§l", "");
	}
}
