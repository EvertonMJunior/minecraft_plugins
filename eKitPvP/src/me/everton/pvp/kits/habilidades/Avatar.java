package me.everton.pvp.kits.habilidades;

import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.Main;
import me.everton.pvp.kits.KitManager;
import me.everton.pvp.kits.KitType;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;

public class Avatar implements Listener {
	public static HashMap<String, Integer> cd = new HashMap<>();
	
	public static enum HabilidadesAvatar {
		TERRA("GRASS", Sound.STEP_GRASS, 2), AGUA("LAPIS_BLOCK",
				Sound.STEP_WOOD, 9), AR("WOOL", Sound.STEP_WOOL, 35), FOGO(
				"REDSTONE_BLOCK", Sound.FIRE, 10);

		String nomeItem;
		Sound som;
		int efeitoNumero;

		private HabilidadesAvatar(String nomeItem, Sound som, int efeitoNumero) {
			this.nomeItem = nomeItem;
			this.som = som;
			this.efeitoNumero = efeitoNumero;
		}
	}

	@EventHandler
	public void noInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();

		if (KitManager.getKit(p) != KitType.AVATAR) {
			return;
		}

		if (!KitManager.isWithKitItemInHand(p)) {
			return;
		}
		e.setCancelled(true);

		if (!API.getGamers().contains(p)) {
			p.sendMessage("§7[§c!§7] Você não pode usar seu kit aqui!");
			return;
		}

		if (cd.containsKey(p.getName())) {
			p.sendMessage("§7[§c!§7] Kit em cooldown de §c§l"
					+ cd.get(p.getName()) + " segundos§7!");
			return;
		}

		if (e.getAction().name().contains("LEFT")) {
			switch (p.getItemInHand().getType().name()) {
			case "GRASS":
				p.getItemInHand().setType(Material.LAPIS_BLOCK);
				break;

			case "LAPIS_BLOCK":
				p.getItemInHand().setType(Material.WOOL);
				break;

			case "WOOL":
				p.getItemInHand().setType(Material.REDSTONE_BLOCK);
				break;

			case "REDSTONE_BLOCK":
				p.getItemInHand().setType(Material.GRASS);
				break;
			}

			return;
		}

		if (e.getAction().name().contains("RIGHT")) {
			for (HabilidadesAvatar ha : HabilidadesAvatar.values()) {
				if (!ha.nomeItem.equalsIgnoreCase(p.getItemInHand().getType()
						.name())) {
					p.sendMessage(ha.name());
					continue;
				}
				p.sendMessage(ha.name() + "!");
				API.startCd(p, 0, 0, cd, "§7[§c!§7] O cooldown acabou!");

				Vector snowballVelocity = p.getLocation().getDirection()
						.normalize().multiply(55);
				Snowball snowball = (Snowball) p
						.launchProjectile(Snowball.class);
				snowball.setVelocity(snowballVelocity);
				snowball.setMetadata(API.format(ha.name()),
						new FixedMetadataValue(Main.getPlugin(), true));

				Location playerEyeLocation = p.getEyeLocation();

				BlockIterator blockIterator = new BlockIterator(
						playerEyeLocation, 0.0D, 40);
				while (blockIterator.hasNext()) {
					Location nextBILocation = blockIterator.next()
							.getLocation();

					Effect efeito = Effect.STEP_SOUND;
					p.playSound(p.getLocation(), ha.som, 5.5F, 5.5F);
					p.playSound(p.getLocation(), ha.som, 1.5F, 1.5F);
					p.playSound(p.getLocation(), ha.som, 2.5F, 2.5F);
					p.playSound(p.getLocation(), ha.som, 3.5F, 3.5F);
					p.playSound(p.getLocation(), ha.som, 4.5F, 4.5F);

					p.getWorld().playEffect(nextBILocation, efeito,
							ha.efeitoNumero);
				}
				break;
			}
			return;
		}
	}

	@EventHandler
	public void danoAvatar(EntityDamageByEntityEvent e) {
		if (e.isCancelled()) {
			return;
		}
		
		final Entity en = e.getEntity();
		if (!(e.getDamager() instanceof Snowball)) {
			return;
		}
		Snowball s = (Snowball) e.getDamager();

		if (s.hasMetadata("Ar")) {
			e.setDamage(16.0D);
			((LivingEntity) en).addPotionEffect(new PotionEffect(
					PotionEffectType.BLINDNESS, 100, 0), true);
		}

		if (s.hasMetadata("Agua")) {
			e.setDamage(11.0D);
			((LivingEntity) en).addPotionEffect(new PotionEffect(
					PotionEffectType.POISON, 300, 0), true);
			Vector vector = en.getLocation().getDirection();
			vector.multiply(-1.0F);
			en.setVelocity(vector);
		}

		if (s.hasMetadata("Terra")) {
			e.setDamage(14.0D);

			new BukkitRunnable() {
				
				@Override
				public void run() {
					Vector vector = en.getVelocity();
					vector.multiply(3.2F);
					en.setVelocity(vector);
				}
			}.runTaskLater(Main.getPlugin(), 5L);
		}

		if (s.hasMetadata("Fogo")) {
			e.setDamage(12.0D);
			en.setFireTicks(150);
		}
	}
}
