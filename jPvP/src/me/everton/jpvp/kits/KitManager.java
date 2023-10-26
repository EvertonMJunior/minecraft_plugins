package me.everton.jpvp.kits;

import java.util.HashMap;

import me.everton.jpvp.utils.API;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class KitManager{
	private static HashMap<String, Kit> kitJogador = new HashMap<>();
	
	public KitManager(){
		super();
	}
	
	public static Kit getKit(String kit) {
		for(Kit k : Kit.kits) {
			if(k.nome.equalsIgnoreCase(kit)) {
				return k;
			}
		}
		return null;
	}
	
	public static void chooseKit(Player p, Kit kit) {
		if(kitJogador.containsKey(p.getName())) {
			new API().sendTitle(p, "§e§l" + kitJogador.get(p.getName()).nome, "§4Kit sendo utilizado no momento", 1, 2, 1);
			return;
		}
		p.getInventory().clear();
		p.getInventory().setArmorContents(null);
		
		for(int i = 0; i < p.getInventory().getSize(); i++) {
			p.getInventory().setItem(i, new API().getSoup());
		}
		new API().giveMushrooms(p);
		p.getInventory().setChestplate(new API().leatherArmor(Material.LEATHER_CHESTPLATE, "§ePeitoral", kit.corArmadura));
		p.getInventory().setItem(8, new API().item(Material.COMPASS, 1, "§cBússola"));
		if(kit.itemParaPlayer != null) {
			p.getInventory().setItem(1, kit.itemParaPlayer);
		}
		
		new API().sendTitle(p, "§f§l" + kit.nome, "§eKit Escolhido", 1, 3, 1);
		p.playSound(p.getLocation(), Sound.NOTE_BASS, 1F, 1F);
		kitJogador.put(p.getName(), kit);
	}
}