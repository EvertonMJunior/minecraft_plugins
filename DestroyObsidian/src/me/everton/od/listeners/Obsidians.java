package me.everton.od.listeners;

import me.everton.od.API;
import me.everton.od.API.GameStage;
import me.everton.od.Main;
import me.everton.od.cmds.Time.Team;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.meta.FireworkMeta;

public class Obsidians implements Listener {
	public static int redObsidians = 2;
	public static int blueObsidians = 2;

	public static Block obsidianRed1 = new Location(Bukkit.getWorld("world"),
			-322, 143, 95).getBlock();
	public static Block obsidianRed2 = new Location(Bukkit.getWorld("world"),
			-322, 142, 95).getBlock();
	public static Block obsidianBlue1 = new Location(Bukkit.getWorld("world"),
			-346, 129, -321).getBlock();
	public static Block obsidianBlue2 = new Location(Bukkit.getWorld("world"),
			-346, 128, -321).getBlock();

	@EventHandler
	public void checkBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (API.getGameStage() != GameStage.PRE_JOGO) {
			if (p.getGameMode() == GameMode.CREATIVE) {
				return;
			}
			
			if(e.getBlock().getType() != Material.OBSIDIAN) {
				return;
			}
			
			if (e.getBlock().getLocation().equals(obsidianRed1.getLocation())) {
				if(Main.red.getPlayers().contains(p)) {
					e.setCancelled(true);
					p.sendMessage("§cVocê nao pode quebrar a Obsidian de seu time!");
					return;
				}
				
				if(API.getGameStage() == GameStage.INVENCIBILIDADE){
					e.setCancelled(true);
					p.sendMessage("§cA quebra de obsidians está desativada por enquanto!");
					return;
				}
				redObsidians--;
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage("§7[§c!§7] A obsidian §c§l1 §7do time Vermelho foi destruída por "
						+ p.getDisplayName()
						+ "§7! Lhes restam "
						+ redObsidians + " obsidians!");
				Bukkit.broadcastMessage(" ");
				obsidianRed1 = new Location(Bukkit.getWorld("world"), 1560,
						1560, 1560).getBlock();
				;
			} else if (e.getBlock().getLocation().equals(obsidianRed2.getLocation())) {
				if(Main.red.getPlayers().contains(p)) {
					e.setCancelled(true);
					p.sendMessage("§cVocê nao pode quebrar a Obsidian de seu time!");
					return;
				}
				
				if(API.getGameStage() == GameStage.INVENCIBILIDADE){
					e.setCancelled(true);
					p.sendMessage("§cA quebra de obsidians está desativada por enquanto!");
					return;
				}
				redObsidians--;
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage("§7[§c!§7] A obsidian §c§l2 §7do time Vermelho foi destruída por "
						+ p.getName()
						+ "§7! Lhes restam "
						+ redObsidians
						+ " obsidians!");
				Bukkit.broadcastMessage(" ");
				obsidianRed2 = new Location(Bukkit.getWorld("world"), 1560,
						1560, 1560).getBlock();
				;
			} else if (e.getBlock().getLocation().equals(obsidianBlue1.getLocation())) {
				if(Main.blue.getPlayers().contains(p)) {
					e.setCancelled(true);
					p.sendMessage("§cVocê nao pode quebrar a Obsidian de seu time!");
					return;
				}
				
				if(API.getGameStage() == GameStage.INVENCIBILIDADE){
					e.setCancelled(true);
					p.sendMessage("§cA quebra de obsidians está desativada por enquanto!");
					return;
				}
				blueObsidians--;
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage("§7[§c!§7] A obsidian §c§l1 §7do time Azul foi destruída por "
						+ p.getDisplayName()
						+ "§7! Lhes restam "
						+ blueObsidians + " obsidians!");
				Bukkit.broadcastMessage(" ");
				obsidianBlue1 = new Location(Bukkit.getWorld("world"), 1560,
						1560, 1560).getBlock();
			} else if (e.getBlock().getLocation().equals(obsidianBlue2.getLocation())) {
				if(Main.blue.getPlayers().contains(p)) {
					e.setCancelled(true);
					p.sendMessage("§cVocê nao pode quebrar a Obsidian de seu time!");
					return;
				}
				
				if(API.getGameStage() == GameStage.INVENCIBILIDADE){
					e.setCancelled(true);
					p.sendMessage("§cA quebra de obsidians está desativada por enquanto!");
					return;
				}
				blueObsidians--;
				Bukkit.broadcastMessage(" ");
				Bukkit.broadcastMessage("§7[§c!§7] A obsidian §c§l2 §7do time Azul foi destruída por "
						+ p.getDisplayName()
						+ "§7! Lhes restam "
						+ blueObsidians + " obsidians!");
				Bukkit.broadcastMessage(" ");
				obsidianBlue2 = new Location(Bukkit.getWorld("world"), 1560,
						1560, 1560).getBlock();
				;
			}

			if (redObsidians == 0) {
				Bukkit.broadcastMessage("§7[§c!§7] O time §9§lazul §7venceu! Parabéns! GG :D");
				for (final Player on : Bukkit.getOnlinePlayers()) {
					on.teleport(API.getSpawn(Team.BLUE));
					on.getInventory().clear();
					on.getInventory().setArmorContents(null);
					on.setGameMode(GameMode.CREATIVE);
				}
				Main.sh.scheduleSyncRepeatingTask(Main.getPlugin(),
						new Runnable() {

							@Override
							public void run() {
								Firework f = (Firework) API
										.getSpawn(Team.BLUE)
										.getWorld()
										.spawn(API.getSpawn(Team.BLUE),
												Firework.class);

								FireworkMeta fm = f.getFireworkMeta();
								fm.addEffect(FireworkEffect.builder()
										.flicker(false).trail(true)
										.with(Type.BALL_LARGE)
										.withColor(Color.RED)
										.withFade(Color.GREEN).build());
								fm.setPower(1);
								f.setFireworkMeta(fm);
							}
						}, 0, 10L);
			}

			if (blueObsidians == 0) {
				Bukkit.broadcastMessage("§7[§c!§7] O time §c§lvermelho §7venceu! Parabéns! GG :D");
				for (final Player on : Bukkit.getOnlinePlayers()) {
					on.teleport(API.getSpawn(Team.RED));
					on.getInventory().clear();
					on.getInventory().setArmorContents(null);
					on.setGameMode(GameMode.CREATIVE);
				}

				Main.sh.scheduleSyncRepeatingTask(Main.getPlugin(),
						new Runnable() {

							@Override
							public void run() {
								Firework f = (Firework) API
										.getSpawn(Team.RED)
										.getWorld()
										.spawn(API.getSpawn(Team.RED),
												Firework.class);

								FireworkMeta fm = f.getFireworkMeta();
								fm.addEffect(FireworkEffect.builder()
										.flicker(false).trail(true)
										.with(Type.BALL_LARGE)
										.withColor(Color.RED)
										.withFade(Color.GREEN).build());
								fm.setPower(5);
								f.setFireworkMeta(fm);
							}
						}, 0, 20L);
			}
		}
	}
}
