package me.everton.hg.kits;

import me.everton.hg.API;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum KitType {
	STOMPER("Stomper", API.item(Material.IRON_BOOTS, 1, "§6Stomper"), null, "Caia de lugares altos e cause estragos"),
	WORM("Worm", API.item(Material.DIRT, 1, "§6Worm"), null, "Quebre terras rapidamente"),
	TOWER("Tower", API.item(Material.DIRT, 1, "§6Tower"), null, "Receba as habilidades do kit Stomper e Worm"),
	GRAPPLER("Grappler", API.item(Material.LEASH, 1, "§6Grappler"), API.item(Material.LEASH, 1, "§6Grappler"), "Se locomova rapidamente pelo mapa"),
	KANGAROO("Kangaroo", API.item(Material.FIREWORK, 1, "§6Kangaroo"), API.item(Material.FIREWORK, 1, "§6Kangaroo"), "Dê double-jumps e ande rapidamente"),
	ENDERMAGE("Endermage", API.item(Material.PORTAL, 1, "§6Endermage"), API.item(Material.PORTAL, 1, "§6Endermage"), "Puxe todos com seu portal"),
	C4("C4", API.item(Material.SLIME_BALL, 1, "§6C4"), API.item(Material.SLIME_BALL, 1, "§6C4"), "Exploda tudo com sua bomba"),
	DESHFIRE("Deshfire", API.item(Material.REDSTONE_BLOCK, 1, "§6Deshfire"), API.item(Material.REDSTONE_BLOCK, 1, "§6Deshfire"), "Tenha o poder de dar Boosts e botar fogo em quem passares"),
	QUICKDROPPER("Quickdropper", API.item(Material.BOWL, 1, "§6Quickdropper"), null, "Drope tigelas automaticamente ao tomar as sopas"),
	TITAN("Titan", API.item(Material.BEDROCK, 1, "§6Titan"), API.item(Material.BEDROCK, 1, "§6Titan"), "Fique invencível por 10 segundos"),
	FORCEFIELD("Forcefield", API.item(Material.IRON_FENCE, 1, "§6Forcefield"), API.item(Material.IRON_FENCE, 1, "§6Forcefield"), "Simule estar usando o hack Forcefield por 5 segundos!"),
	NONE("Sem Kit", null, null, null);

	private final String nome;
	private final ItemStack itemSelector;
	private final ItemStack itemKit;
	private final String desc;

	private KitType(String n, ItemStack iS, ItemStack iK, String des) {
		nome = n;
		itemSelector = iS;
		itemKit = iK;
		desc = des;
	}

	public String getName() {
		return nome;
	}
	
	public String getDescription() {
		return desc;
	}
	
	public ItemStack getItemSelector() {
		return itemSelector;
	}
	
	public ItemStack getItemKit() {
		return itemKit;
	}
}
