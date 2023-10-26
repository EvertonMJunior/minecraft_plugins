package me.everton.WocPvP.Kits.Habilidades;

import java.util.ArrayList;

import me.everton.WocPvP.KitManager;
import me.everton.WocPvP.Main;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class Endermage implements CommandExecutor, Listener {

	public static ArrayList<Player> maged = new ArrayList<>();
	String teleport = "§cVocê foi puxado por um Endermage! §cTens 5 segundos de invencibilidade!";

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (label.equalsIgnoreCase("endermage")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Comando apenas in-game");
				return true;
			}
			Player p = (Player) sender;
			if (Main.usandokit.contains(p)) {
				p.sendMessage("§cVocê ja esta usando um kit!");
				return true;
			}
			KitManager.kitEndermage(p);
		}
		return false;
	}

	private boolean isEnderable(Location portal, Location player) {
		return (Math.abs(portal.getX() - player.getX()) < 3.0D)
				&& (Math.abs(portal.getZ() - player.getZ()) < 3.0D)
				&& (Math.abs(portal.getY() - player.getY()) >= 3.5D);
	}

	public void TeleportP(Location portal, Player p1, Player p2) {
		p1.teleport(portal.clone().add(0.0D, 1.0D, 0.0D));
		p2.teleport(portal.clone().add(0.0D, 1.0D, 0.0D));
		p1.setNoDamageTicks(100);
		p2.setNoDamageTicks(100);
		p1.sendMessage(this.teleport);
		p2.sendMessage(this.teleport);
		p2.getWorld().playEffect(p2.getLocation(), Effect.ENDER_SIGNAL, 9);
		p1.getWorld().playEffect(portal, Effect.ENDER_SIGNAL, 9);
		p2.playSound(p2.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.2F);
		p1.playSound(portal, Sound.ENDERMAN_TELEPORT, 1.0F, 1.2F);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void EndermageInteract(PlayerInteractEvent e) {

		final ItemStack bow = new ItemStack(Material.PORTAL);
		ItemMeta bowmeta = bow.getItemMeta();
		bowmeta.setDisplayName("§5Portal de Endermage");
		bow.setItemMeta(bowmeta);
		final Player mage = e.getPlayer();
		if ((e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& (mage.getItemInHand().getType() == Material.PORTAL)
				&& (Main.endermage.contains(mage))) {
			e.setCancelled(true);
			mage.updateInventory();
			mage.setItemInHand(new ItemStack(Material.AIR));
			mage.updateInventory();
			final Block b = e.getClickedBlock();

			final Location bLoc = b.getLocation();
			final BlockState bs = b.getState();

			b.setType(Material.ENDER_PORTAL_FRAME);
			for (Player nearby : Bukkit.getOnlinePlayers()) {
				final Player target = nearby.getPlayer();
				new BukkitRunnable() {
					int time = 5;

					public void run() {
						this.time -= 1;
						if ((!target.getInventory().contains(Material.PORTAL))
								&& (Endermage.this.isEnderable(bLoc,
										target.getLocation()))
								&& (target != mage) && (!target.isDead())) {
							b.setType(bs.getType());
							b.setData(bs.getBlock().getData());
							cancel();
							Endermage.this.TeleportP(bLoc, mage, target);
							if ((!mage.getInventory().contains(
									new ItemStack(Material.PORTAL)))
									&& (Main.endermage.contains(mage))) {
								mage.getInventory().addItem(
										new ItemStack[] { bow });
								mage.updateInventory();
							}
						} else if (this.time == 0) {
							cancel();
							b.setType(bs.getType());
							b.setData(bs.getBlock().getData());
							if ((!mage.getInventory().contains(
									new ItemStack(Material.PORTAL)))
									&& (Main.endermage.contains(mage))) {
								mage.getInventory().addItem(
										new ItemStack[] { bow });
								mage.updateInventory();
							}
						}
					}
				}.runTaskTimer(Main.plugin, 0L, 20L);
			}
		}
	}
}