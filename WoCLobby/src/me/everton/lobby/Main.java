package me.everton.lobby;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import me.everton.lobby.tags.TagCmd;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;

public class Main extends JavaPlugin{
	public static Plugin plugin;
	
	public static ProtocolManager protocolManager;
	public static BukkitScheduler sh;
	
	public void onEnable() {
		plugin = this;
		protocolManager = ProtocolLibrary.getProtocolManager();
		sh = Bukkit.getScheduler();
		getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		getServer().getPluginManager().registerEvents(new Listeners(), this);
		getServer().getPluginManager().registerEvents(new AdminMode(), this);
		getCommand("admin").setExecutor(new AdminMode());
		getCommand("tag").setExecutor(new TagCmd());
		getCommand("specs").setExecutor(new Specs());
		
		sh.scheduleSyncRepeatingTask(this, new Runnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				for(Player p : Bukkit.getOnlinePlayers()) {
					String header = "";
					header += "§b§m--(-------------------)--";
					header += "\n§rBem-Vindo à §6§lWoC§b§lNetwork§r!\n";
					
					String footer = "";
					footer += "\n§fVocê está no §6§lLobby§f!";
					footer += "\n§fIP: §6§ljogar.wocpvp.com";
					footer += "\n§fPing: §6§l" + ((CraftPlayer)p).getHandle().ping;
					footer += "\n§b§m--(-------------------)--";
					API.setHeaderFooter(p, header, footer);
				}
			}
		}, 0L, 5L);
	}
	
	public void onDisable() {
		
	}
	
	public static void sendServer(Player p, String server) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(b);
		try {
			out.writeUTF("Connect");
			out.writeUTF(server);
		} catch(Exception e) {
			e.printStackTrace();
		}
		p.sendPluginMessage(plugin, "BungeeCord", b.toByteArray());
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static ProtocolManager getPM() {
		return protocolManager;
	}
}
