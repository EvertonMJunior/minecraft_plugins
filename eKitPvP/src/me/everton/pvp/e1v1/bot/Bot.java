package me.everton.pvp.e1v1.bot;

import java.util.ArrayList;
import java.util.HashMap;

import me.everton.pvp.API;
import me.everton.pvp.LocationsManager;
import me.everton.pvp.Main;
import me.everton.pvp.SpawnProtection;
import me.everton.pvp.e1v1.Kits1v1;
import me.everton.pvp.e1v1.Main1v1;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.event.NPCDamageByEntityEvent;
import net.citizensnpcs.api.npc.NPC;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class Bot implements Listener {
	public static HashMap<String, Integer> sopas = new HashMap<>();
	public static HashMap<String, NPC> batalhando = new HashMap<>();
	public static HashMap<String, Integer> task = new HashMap<>();
	public static ArrayList<NPC> hided = new ArrayList<>();
	
	public static enum Dificuldade{
		NORMAL(6),
		MÉDIO(3),
		DIFÍCIL(0);
		
		int repeticao;
		
		private Dificuldade(int re) {
			repeticao = re;
		}
		
		public int getRepeating() {
			return repeticao;
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void entrarBattle(final Player p, final Dificuldade d) {
		final NPC npc = CitizensAPI.getNPCRegistry().createNPC(
				EntityType.PLAYER, "§6§lWoC§b§lBot");
		npc.spawn(LocationsManager.getLocation("1v1pos1"));
		npc.getNavigator().setTarget(p, true);
		npc.setProtected(false);
		SpawnProtection.removeProtection(p, false);

		batalhando.put(p.getName(), npc);
		hided.add(npc);
		Main1v1.onBattle.put(p.getName(), npc.getName());
		p.teleport(LocationsManager.getLocation("1v1pos2"));
		Kits1v1.kitFulliron(p);
		Kits1v1.kitFulliron((Player) npc.getEntity());
		sopas.put(npc.getBukkitEntity().getUniqueId().toString(), API.getAmount(p, Material.MUSHROOM_SOUP));
		p.sendMessage("§7§l1v1 §8> §aVocê entrou no 1v1 contra o §6§lWoC§b§lBot§a, nível " + API.format(d.name()) + "§a!");
		API.sendActionBar(p, "§6§lA quantidade de §b§lSopas §6§lserá mostrada aqui.");
		
		final int task1 = Main.sh.scheduleSyncRepeatingTask(Main.getPlugin(),
				new Runnable() {

					@Override
					public void run() {
						if (npc.isSpawned()) {
							npc.getNavigator().setTarget(p.getLocation());
							npc.getNavigator().setTarget(p, true);
							
							int botSoups = sopas.get((npc.getBukkitEntity()).getUniqueId().toString());
							int mySoups = API.getAmount(p, Material.MUSHROOM_SOUP);
							
							API.sendActionBar(p, "§6§lBot §c» §b§l" + botSoups + " §7┃ §6§lVocê §c» §b§l" + mySoups);
						}
					}
				}, 80L, d.getRepeating() * 1L);
		task.put(p.getName(), task1);
		for(Player on : Bukkit.getOnlinePlayers()) {
			p.hidePlayer(on);
		}
		p.showPlayer((Player) npc.getBukkitEntity());
	}

	@EventHandler
	public void onBotD(NPCDamageByEntityEvent e) {
		Damageable hp = (Damageable) e.getNPC().getEntity();
		Entity np = e.getNPC().getEntity();
		if(!hided.contains(e.getNPC())) {
			return;
		}

		if (np instanceof Player) {
			if (hp.getHealth() <= 13) {
				tomarSopa((Player) np);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void dE(PlayerDeathEvent e) {
		if (e.getEntity() instanceof Player
				&& e.getEntity().getKiller() instanceof LivingEntity
				&& e.getEntity().getKiller().hasMetadata("NPC")) {
			Player p = (Player) e.getEntity();
			p.setHealth(20.0D);
			SpawnProtection.addProtection(p, false);
			p.teleport(LocationsManager.getLocation("1v1"));
			NPC npc = CitizensAPI.getNPCRegistry().getNPC(
					e.getEntity().getKiller());
			p.sendMessage("§7§l1v1 §8> §aVocê perdeu do §6§lWoC§b§lBot§a! Ele ficou com " + sopas.get(npc.getBukkitEntity().getUniqueId().toString()) + " sopas! Seu status não é afetado!");
			Main1v1.giveItens(p);
			p.hidePlayer(e.getEntity().getKiller());
			if (task.containsKey(p.getName())) {
				Main.sh.cancelTask(task.get(p.getName()));
				task.remove(p.getName());
			}
			if(batalhando.containsKey(p.getName())) {
				batalhando.remove(p.getName());
			}
			if(Main1v1.onBattle.containsKey(p.getName())) {
				Main1v1.onBattle.remove(p.getName());
			}
			if(sopas.containsKey(npc.getBukkitEntity().getUniqueId().toString())) {
				sopas.remove(npc.getBukkitEntity().getUniqueId().toString());
			}
			for(Player on : Bukkit.getOnlinePlayers()) {
				p.showPlayer(on);
				on.hidePlayer((Player) npc.getBukkitEntity());
			}
			p.hidePlayer((Player) npc.getEntity());
			npc.despawn();
			npc.destroy();
		}

		if (e.getEntity().getKiller() instanceof Player
				&& e.getEntity() instanceof LivingEntity
				&& e.getEntity().hasMetadata("NPC")) {
			Player p = (Player) e.getEntity().getKiller();
			p.setHealth(20.0D);
			SpawnProtection.addProtection(p, false);
			p.teleport(LocationsManager.getLocation("1v1"));
			NPC npc = CitizensAPI.getNPCRegistry().getNPC(e.getEntity());
			p.sendMessage("§7§l1v1 §8> §aVocê ganhou do §6§lWoC§b§lBot§a! Seu status não é afetado!");
			p.hidePlayer(e.getEntity());
			Main1v1.giveItens(p);
			if (task.containsKey(p.getName())) {
				Main.sh.cancelTask(task.get(p.getName()));
				task.remove(p.getName());
			}
			if(batalhando.containsKey(p.getName())) {
				batalhando.remove(p.getName());
			}
			if(Main1v1.onBattle.containsKey(p.getName())) {
				Main1v1.onBattle.remove(p.getName());
			}
			if(sopas.containsKey(npc.getBukkitEntity().getUniqueId().toString())) {
				sopas.remove(npc.getBukkitEntity().getUniqueId().toString());
			}
			for(Player on : Bukkit.getOnlinePlayers()) {
				p.showPlayer(on);
				on.hidePlayer((Player) npc.getBukkitEntity());
			}
			p.hidePlayer((Player) npc.getEntity());
			
			npc.despawn();
			npc.destroy();
		}

		e.getDrops().clear();
		e.setDroppedExp(0);
		e.setDeathMessage(null);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(batalhando.containsKey(p.getName())) {
			NPC npc = batalhando.get(p.getName());
			if (task.containsKey(p.getName())) {
				Main.sh.cancelTask(task.get(p.getName()));
				task.remove(p.getName());
			}
			if(batalhando.containsKey(p.getName())) {
				batalhando.remove(p.getName());
			}
			if(Main1v1.onBattle.containsKey(p.getName())) {
				Main1v1.onBattle.remove(p.getName());
			}
			if(sopas.containsKey(npc.getBukkitEntity().getUniqueId().toString())) {
				sopas.remove(npc.getBukkitEntity().getUniqueId().toString());
			}
			for(Player on : Bukkit.getOnlinePlayers()) {
				p.showPlayer(on);
				on.hidePlayer((Player) npc.getBukkitEntity());
			}
			p.hidePlayer((Player) npc.getEntity());
			npc.despawn();
			npc.destroy();
		}
	}

	public static void tomarSopa(final Player npc) {
		final ItemStack itemH = npc.getItemInHand();
		if(itemH.getType() != Material.DIAMOND_SWORD) {
			return;
		}
		
		if (sopas.containsKey(npc.getUniqueId().toString())
				&& sopas.get(npc.getUniqueId().toString()) <= 0) {
			return;
		}
		
		if (sopas.containsKey(npc.getUniqueId().toString())) {
			sopas.put(npc.getUniqueId().toString(),
					sopas.get(npc.getUniqueId().toString()) - 1);
		}

		npc.setItemInHand(API.getSoup());
		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				if (sopas.get(npc.getUniqueId().toString()) > 0) {
					Damageable hp = npc;
					npc.setHealth(hp.getHealth() + 7 > hp.getMaxHealth() ? hp
							.getMaxHealth() : hp.getHealth() + 7);
					npc.setItemInHand(API.getBowl());
				}
			}
		}, 8L);
		Main.sh.scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

			@Override
			public void run() {
				npc.setItemInHand(itemH);
			}
		}, 12L);
	}
}
