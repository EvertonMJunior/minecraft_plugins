package me.everton.jpvp;

import java.util.ArrayList;
import java.util.UUID;

import me.everton.jpvp.comandos.KitCmd;
import me.everton.jpvp.kits.KitManager;
import me.everton.jpvp.kits.habilidades.Stomper;
import me.everton.jpvp.listeners.onJoin;
import me.everton.jpvp.utils.API;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.packetwrapper.WrapperPlayClientUpdateSign;
import com.comphenix.packetwrapper.WrapperPlayServerUpdateSign;
import com.comphenix.packetwrapper.WrapperStatusServerOutServerInfo;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;

public class Main extends JavaPlugin {
	private static Plugin plugin;
	public static CommandSender cs;
	private static ProtocolManager protocolManager;

	@Override
	public void onLoad() {
		protocolManager = ProtocolLibrary.getProtocolManager();
		super.onLoad();
	}

	@Override
	public void onEnable() {
		start();
		super.onEnable();
	}

	@Override
	public void onDisable() {
		stop();
		super.onDisable();
	}

	private void start() {
		plugin = this;
		cs = Bukkit.getConsoleSender();

		registerCmds();
		registerListeners();
		registerPacketListeners();

		String mensagem = "\n\n";
		mensagem += "         §f> §aO Plugin jPvP foi habilitado com sucesso! \n";
		mensagem += "         §f> §aDesenvolvido por EvertonPvP ";
		mensagem += "\n\n";
		cs.sendMessage(mensagem);
	}

	private void stop() {
		String mensagem = "\n\n";
		mensagem += "         §f> §aO Plugin jPvP foi desabilitado com sucesso! \n";
		mensagem += "         §f> §aDesenvolvido por EvertonPvP ";
		mensagem += "\n\n";
		cs.sendMessage(mensagem);
	}

	private void registerCmds() {
		getCommand("kit").setExecutor(new KitCmd());
	}

	private void registerListeners() {
		Bukkit.getPluginManager().registerEvents(new Stomper(), this);
		Bukkit.getPluginManager().registerEvents(new onJoin(), this);
	}

	private void registerPacketListeners() {
		getPm().addPacketListener(new PacketAdapter(this, PacketType.Status.Server.OUT_SERVER_INFO) {
			@SuppressWarnings("deprecation")
			@Override
			public void onPacketSending(PacketEvent e) {
				WrapperStatusServerOutServerInfo wr = new WrapperStatusServerOutServerInfo(e.getPacket());
				WrappedServerPing wsp = new WrappedServerPing();
				wsp.setPlayersVisible(false);
				ArrayList<WrappedGameProfile> info = new ArrayList<WrappedGameProfile>();
				info.add(new WrappedGameProfile(UUID.randomUUID(), "§m----------§f[ §6§lTiger§f§lPvP §f]§m----------"));
				info.add(new WrappedGameProfile(UUID.randomUUID(), "§fSeja bem-vindo ao"));
				info.add(new WrappedGameProfile(UUID.randomUUID(), "§6§lTiger§f§lPvP"));
				info.add(new WrappedGameProfile(UUID.randomUUID(), "§m-------------------------------"));
				wsp.setPlayers(info);
				wsp.setMotD(Bukkit.getMotd());
				wsp.setPlayersMaximum(Bukkit.getOnlinePlayers().length + 1);
				wsp.setPlayersOnline(Bukkit.getOnlinePlayers().length);
				wr.setJsonResponse(wsp);
				e.setPacket(wr.getHandle());
			}
		});
	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public static KitManager getKitManager() {
		return new KitManager();
	}

	public static API getApi() {
		return new API();
	}

	public static ProtocolManager getPm() {
		return protocolManager;
	}
}
