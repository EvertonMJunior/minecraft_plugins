package me.everton.jpvp.kits.habilidades;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import me.everton.jpvp.kits.Kit;

public class Stomper extends Kit implements Listener{
	public Stomper(){
		super("Stomper", new String[] {"Mate seus inimigos caindo", "sobre suas cabeças!"}, new ItemStack(Material.IRON_BOOTS), new ItemStack(Material.IRON_BOOTS), Color.WHITE);
	}
	
	
}
