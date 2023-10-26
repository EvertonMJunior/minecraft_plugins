package me.everton.epvp.Comandos;

import java.util.HashMap;

import me.everton.epvp.Main;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Sopa implements CommandExecutor {
	public static HashMap<String, Integer> cd = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();

	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Comando apenas in-game!");
			return true;
		}
		if (label.equalsIgnoreCase("sopa")) {
			Player p = (Player) sender;
			if (task.containsKey(p.getName())) {
				p.sendMessage("§bAguarde §l" + cd.get(p.getName())
						+ " §r§bsegundos para pegar mais sopas!");
				return true;
			}
			final Player pf = p;
			for (int i = 0; i < p.getInventory().getContents().length; i++) {
				p.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));
			}
			p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 100,
					4));
			p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 100, 4));
			p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, 100, 4));
			p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 100,
					4));

			int coolDown = Main.sh.scheduleSyncRepeatingTask(Main.plugin,
					new Runnable() {
						int tempo = 60;

						public void run() {
							if (tempo > 0) {
								cd.put(pf.getName(), tempo);
								tempo--;
							} else if (tempo == 0) {
								cd.remove(pf.getName());
								Main.sh.cancelTask(task.get(pf.getName()));
								task.remove(pf.getName());
							}
						}
					}, 0L, 20L);
			task.put(p.getName(), coolDown);
			p.sendMessage("§7Você ganhou sopas! Poderás pegar mais daqui 1 minuto!");

		}
		return false;
	}
}
