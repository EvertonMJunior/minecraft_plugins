package me.everton.jpvp.kits;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;

public class Kit{
	public static ArrayList<Kit> kits = new ArrayList<>();
	
	String nome;
	String[] descricao;
	ItemStack itemSeletor;
	ItemStack itemParaPlayer;
	Color corArmadura;
	
	public Kit(String nome, String[] descricao, ItemStack itemSeletor, ItemStack itemParaPlayer, Color corArmadura) {
		this.nome = nome;
		this.descricao = descricao;
		this.itemSeletor = itemSeletor;
		this.itemParaPlayer = itemParaPlayer;
		this.corArmadura = corArmadura;
		kits.add(this);
	}
}
