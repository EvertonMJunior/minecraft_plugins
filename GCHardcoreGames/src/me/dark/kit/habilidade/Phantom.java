package me.dark.kit.habilidade;

import java.util.Arrays;
import java.util.HashMap;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.dark.Main;
import me.dark.Title.TitleAPI;
import me.dark.Utils.DarkUtils;
import me.dark.kit.Kit;

public class Phantom extends Kit {
	public Phantom() {
		super("Phantom", Material.FEATHER, 
				new ItemStack[] { DarkUtils.create_item(Material.FEATHER, "§3Phantom", 1, 0, null) },
				Arrays.asList(""));
	}
	public static HashMap<String, ItemStack[]> armour = new HashMap<>();
	
	
	@EventHandler
	public void kitPhantom(PlayerInteractEvent e) {
		final Player p = e.getPlayer();
		if (p.getItemInHand().getType() == Material.FEATHER
				&& hasAbility(p)) {
			if (!hasCooldown(p)) {
				armour.put(p.getName(), p.getInventory().getArmorContents());
				p.getInventory().setArmorContents(null);
				p.getInventory().setHelmet(DarkUtils.leatherArmor(Material.LEATHER_HELMET, Color.WHITE));
				p.getInventory().setChestplate(DarkUtils.leatherArmor(Material.LEATHER_CHESTPLATE, Color.WHITE));
				p.getInventory().setLeggings(DarkUtils.leatherArmor(Material.LEATHER_LEGGINGS, Color.WHITE));
				p.getInventory().setBoots(DarkUtils.leatherArmor(Material.LEATHER_BOOTS, Color.WHITE));
				p.playSound(p.getLocation(), Sound.WITHER_DEATH, 2.0F, 2.0F);
				p.setAllowFlight(true);
				p.setFlying(true);
				p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 6*20, 2));
				new BukkitRunnable() {
					int time = 6;
					public void run() {
						time -=1;
						if(time > 0) {
							if (TitleAPI.is1_8(p)) {
								TitleAPI.sendActionBar(p, "§bVocê tem §3" + time + " §bsegundos de voo!");
							} else {
								p.sendMessage("§bVocê tem §3" + time + " §bsegundos de voo!");	
							}
						} else {
							p.getInventory().setArmorContents(null);
							p.getInventory().setArmorContents(armour.get(p.getName()));
							armour.remove(p.getName());
							if (TitleAPI.is1_8(p)) {
								TitleAPI.sendActionBar(p, "§3Seu voo acabou!");
							}else {
								p.sendMessage("§3Seu voo acabou!");	
							}
							p.setAllowFlight(false);
							p.setFlying(false);
							cancel();
						}
					} 
				}.runTaskTimer(Main.getMain(), 0L, 20);
				addCooldown(p, 60);
			} else {
				mensagemcooldown(p);
				return;
			}
		}
	}
	
}
