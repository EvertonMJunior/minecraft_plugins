package me.dark.kit.habilidade;

import java.util.Arrays;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Wolf;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import me.dark.Utils.DarkUtils;
import me.dark.kit.Kit;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

public class MobSpawner extends Kit {
	public MobSpawner() {
		super("MobSpawner", Material.MOB_SPAWNER, 
				new ItemStack[] { DarkUtils.create_item(Material.MONSTER_EGG, "§3MobSpawner", 1, 0, null) },
				Arrays.asList(""));

	}
	
	@EventHandler
	public void inte(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (hasAbility(p)) {
			if (p.getItemInHand() != null) {
				if (p.getItemInHand().getType() == Material.MONSTER_EGG) {
					e.setCancelled(true);
					if (hasCooldown(p)) {
						mensagemcooldown(p);
					} else {
						Wolf f = (Wolf) p.getWorld().spawnEntity(p.getLocation(), EntityType.WOLF);
						MobDisguise mob = null;
						Random ra = new Random();
						int r = ra.nextInt(2)+1;
						
						if (r==1) {
							MobDisguise zombie = new MobDisguise(DisguiseType.ZOMBIE);
							mob = zombie;
						} else if (r==2) {
							MobDisguise skeleton = new MobDisguise(DisguiseType.SKELETON);
							mob = skeleton;
						} else if (r==3) {
							MobDisguise spider = new MobDisguise(DisguiseType.SPIDER);
							mob = spider;
						}
						f.setCustomName("§bMob de "+p.getName());
						f.setCustomNameVisible(true);
						f.setOwner(p);
						f.setMaxHealth(20D);
						f.setHealth(20D);
						DisguiseAPI.disguiseToAll(f, mob);
						addCooldown(p, 60);
					}
				}
			}
		}
	}

}
