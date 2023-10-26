package me.dark.Listener;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;
import me.dark.Utils.DarkUtils;

public class PreGameFight {
	public static ItemStack p1v1 = DarkUtils.create_item(Material.STONE_SWORD, "§9PvP", 1, 0, null);
	public static ArrayList<Player> in1v1 = new ArrayList<Player>();
	
	public static void add1v1(Player p) {
		if (Main.toStart < 15) {
			p.sendMessage("§bO jogo vai iniciar, aguarde!");
			return;
		}
		in1v1.add(p);
		
		p.teleport(new Location(p.getWorld(), 0, 192, 0));
		p.getInventory().clear();
		
		p.getInventory().addItem(DarkUtils.create_item(Material.STONE_SWORD, "", 1, 0, null));
		
        ItemStack cogu1 = new ItemStack(Material.BROWN_MUSHROOM, 32);
        p.getInventory().setItem(13, cogu1);
        
        ItemStack cogu2 = new ItemStack(Material.RED_MUSHROOM, 32);
        p.getInventory().setItem(14, cogu2);
        
        ItemStack pote = new ItemStack(Material.BOWL, 32);
        p.getInventory().setItem(15, pote);
		for (int i = 0; i < 34; i++) {
			p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
		}
	}
}
