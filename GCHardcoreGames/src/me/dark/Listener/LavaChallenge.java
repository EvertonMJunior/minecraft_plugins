package me.dark.Listener;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.Utils.DarkUtils;

public class LavaChallenge implements Listener {
	public static ArrayList<Player> inLava = new ArrayList<Player>();
	
	public static void addLava(Player p) {
		if (Main.toStart < 15) {
			p.sendMessage("§bO jogo vai iniciar, aguarde!");
			return;
		}
		inLava.add(p);
		
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
	@EventHandler
	public void move(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		if (Main.estado != GameState.PREGAME) {
			return;
		}
		if (inLava.contains(p)) {
			if (p.getLocation().getX() < -18) {
				PreGameFight.in1v1.remove(p);
				LavaChallenge.inLava.remove(p);
				GamerJoin.preInv(p);
				p.setHealth(20D);
				p.setFireTicks(0);
			}else if (p.getLocation().getX() > -12) {
				PreGameFight.in1v1.remove(p);
				LavaChallenge.inLava.remove(p);
				GamerJoin.preInv(p);	
				p.setHealth(20D);
				p.setFireTicks(0);
			} else if (p.getLocation().getZ() > -10) {
				PreGameFight.in1v1.remove(p);
				LavaChallenge.inLava.remove(p);
				GamerJoin.preInv(p);	
				p.setHealth(20D);
				p.setFireTicks(0);
			} else if (p.getLocation().getZ() > -16) {
				PreGameFight.in1v1.remove(p);
				LavaChallenge.inLava.remove(p);
				GamerJoin.preInv(p);	
				p.setHealth(20D);
				p.setFireTicks(0);
			}
		} else {
			Block b = p.getLocation().getBlock();
			if ((b.getType().name().contains("LAVA") || b
					.getRelative(BlockFace.UP).getType().name()
					.contains("LAVA"))) {
				addLava(p);
				return;
			}
		}
	}
}
