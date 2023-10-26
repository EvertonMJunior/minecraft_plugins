package me.dark.kit.habilidade;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.Utils.DarkUtils;
import me.dark.kit.Kit;

public class Monk extends Kit  {
	public Monk() {
		super("Monk", Material.BLAZE_ROD, 
				new ItemStack[] { DarkUtils.create_item(Material.BLAZE_ROD, "§3Monk", 1, 0, null) },
				Arrays.asList(""));
	}
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onRightClick(PlayerInteractEntityEvent event) {
		if (Main.estado == GameState.PREGAME) return;
		event.getPlayer().getItemInHand();
		Player p = event.getPlayer();
		if (hasAbility(p)
				&& p.getItemInHand().getType() == Material.BLAZE_ROD) {
			if (hasCooldown(p)){
				mensagemcooldown(p);
			} else {
				if (!(event.getPlayer() instanceof Player)) {
					return;
				}
				PlayerInventory inv = ((Player) event.getRightClicked())
						.getInventory();
				@SuppressWarnings("unused")
				int slot = new Random().nextInt(true ? 36 : 9);
				ItemStack replaced = inv.getItemInHand();
				if (replaced == null)
					replaced = new ItemStack(0);
				ItemStack replacer = inv.getItem(slot);
				if (replacer == null)
					replacer = new ItemStack(0);
				inv.setItemInHand(replacer);
				inv.setItem(slot, replaced);
				addCooldown(p, 30);
				((Player) event.getRightClicked()).sendMessage("§bVoce foi §3monkado§b!");
			}
		}
	}

}
