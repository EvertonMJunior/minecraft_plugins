package me.everton.hg;

public enum TagType {
	DONO("§4§l"), ADMIN("§c§l"), CODER("§c§l§o"), MODPLUS("§5§l§o"), MOD("§5§l"), BUILDER(
			"§3§l"), TRIAL("§d§l"), YOUTUBERPLUS("§b§l§o"), YOUTUBER("§b§l"), PRO("§6§l"), VIP("§a§l"), NORMAL("§7§l");

	private final String name;

	private TagType(String s) {
		name = s;
	}

	public boolean equalsName(String otherName) {
		return (otherName == null) ? false : name.equals(otherName);
	}

	public String toString() {
		return name;
	}
}
