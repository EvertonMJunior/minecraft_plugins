package me.dark.kit.habilidade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.kit.Kit;

public class Surprise extends Kit{
	public Surprise() {
		super("Surprise", Material.CAKE, 
				new ItemStack[] { null },
				Arrays.asList(""));
	}
	public static ArrayList<Player> surprise = new ArrayList<Player>();
	
	@Override
	public void addPlayer(Player p) {
		super.addPlayer(p);
		if (Main.estado != GameState.PREGAME) {
			int random = new Random().nextInt(Kit.kits.size());
			Kit k = Kit.kits.get(random);
			for (int i = 0; i < 20; i++) {
				if (k==this) {
					random = new Random().nextInt(Kit.kits.size());
					k = Kit.kits.get(random);
				}
			}
			for (int i = 0; i < 30; i++) {
				if (Main.notToggled.contains(k)) {
					random = new Random().nextInt(Kit.kits.size());
					k = Kit.kits.get(random);
				}
			}
			removePlayer(p);
			k.addPlayer(p);
			surprise.add(p);
			p.sendMessage("§bO kit surprise escolheu o kit §3"+k.toString());
			if (k.getItems() != null) {
				for (ItemStack items : k.getItems()) {
					if (items!=null) {
						p.getInventory().addItem(items);
					}
				}
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void onStartGame() {
		super.onStartGame();
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (hasAbility(p)) {
				int random = new Random().nextInt(Kit.kits.size());
				Kit k = Kit.kits.get(random);
				for (int i = 0; i < 20; i++) {
					if (k==this) {
						random = new Random().nextInt(Kit.kits.size());
						k = Kit.kits.get(random);
					}
				}
				for (int i = 0; i < 30; i++) {
					if (Main.notToggled.contains(k)) {
						random = new Random().nextInt(Kit.kits.size());
						k = Kit.kits.get(random);
					}
				}
				removePlayer(p);
				k.addPlayer(p);
				surprise.add(p);
				p.sendMessage("§bO kit surprise escolheu o kit §3"+k.toString());
				if (k.getItems() != null) {
					for (ItemStack items : k.getItems()) {
						if (items!=null) {
							p.getInventory().addItem(items);
						}
					}
				}
			}
		}
	}

}
