package me.everton.WocPvP.Kits.Habilidades;

import me.everton.WocPvP.KitManager;
import me.everton.WocPvP.Main;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class Stomper implements Listener, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (label.equalsIgnoreCase("stomper")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Comando apenas in-game");
				return true;
			}
			Player p = (Player) sender;
			if (Main.usandokit.contains(p)) {
				p.sendMessage("§cVocê ja esta usando um kit!");
				return true;
			}
			KitManager.kitStomper(p);
		}
		return false;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void aoFall(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getEntity();
		if (e.getCause() == EntityDamageEvent.DamageCause.FALL) {
			if (Main.stomper.contains(p)) {
				for (Entity ent : p.getNearbyEntities(5.0D, 2.0D, 5.0D)) {
					if ((ent instanceof Player)) {
						Player plr = (Player) ent;
						if (Main.areaPvP(plr)) {
							if (e.getDamage() <= 4.0D) {
								e.setCancelled(true);
								return;
							}
							if (plr.isSneaking()) {
								plr.damage(6.0D, p);
								plr.sendMessage("§cVocê foi stompado por "
										+ p.getDisplayName());
							} else {
								plr.damage(e.getDamage(), p);
								plr.sendMessage("§cVocê foi stompado por "
										+ p.getDisplayName());
							}
						} else {
							p.sendMessage("§cVocê nao pode usar seu kit aqui!");
						}
					}
				}
				e.setDamage(4.0D);
				return;
			}
			return;
		}
	}
}
