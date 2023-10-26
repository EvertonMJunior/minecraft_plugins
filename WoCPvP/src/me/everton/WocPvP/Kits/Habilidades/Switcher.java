package me.everton.WocPvP.Kits.Habilidades;

import me.everton.WocPvP.KitManager;
import me.everton.WocPvP.Main;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Switcher implements Listener, CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (label.equalsIgnoreCase("switcher")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage("Comando apenas in-game");
				return true;
			}
			Player p = (Player) sender;
			if (Main.usandokit.contains(p)) {
				p.sendMessage("§cVocê ja esta usando um kit!");
				return true;
			}
			KitManager.kitSwitcher(p);
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void trocar(EntityDamageByEntityEvent e) {
		if (((e.getEntity() instanceof Player))
				&& ((e.getDamager() instanceof Snowball))) {
			Player p = (Player) e.getEntity();
			Snowball b = (Snowball) e.getDamager();
			if ((b.getShooter() instanceof Player)) {
				Player h = (Player) b.getShooter();
				if (Main.switcher.contains(h)) {
					if ((Main.areaPvP(p)) && (Main.areaPvP(h))) {
						Location ploc = p.getLocation();
						Location hloc = h.getLocation();
						p.teleport(hloc);
						h.teleport(ploc);
					} else {
						h.sendMessage("§cVocê nao pode usar seu kit aqui!");
					}
				}
			}
		}
	}
}
