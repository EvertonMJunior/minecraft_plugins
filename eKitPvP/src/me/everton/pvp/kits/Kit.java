package me.everton.pvp.kits;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;

public class Kit {
	public static ArrayList<Kit> kits = new ArrayList<>();
	
	public static Kit getKit(String k) {
		for(Kit kit : kits) {
			if(kit.nome.equalsIgnoreCase(k)) {
				return kit;
			}
		}
		return null;
	}
	
	public String nome;
	public 	String[] descricao;
	public ItemStack itemSeletor;
	public ItemStack itemParaPlayer;
	public Color corArmadura;
	
	public Kit(String nome, String[] descricao, ItemStack itemSeletor, ItemStack itemParaPlayer, Color corArmadura) {
		this.nome = nome;
		this.descricao = descricao;
		this.itemSeletor = itemSeletor;
		this.itemParaPlayer = itemParaPlayer;
		this.corArmadura = corArmadura;
		kits.add(this);
	}
}
