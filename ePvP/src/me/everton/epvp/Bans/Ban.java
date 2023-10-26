package me.everton.epvp.Bans;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import com.evilmidget38.UUIDFetcher;

import me.everton.epvp.Main;

public class Ban implements Listener,CommandExecutor{
	public static void banir(String nome, String motivo, String autor, int minutos) throws Exception{
		Main.settings.getBans().set(UUIDFetcher.getUUIDOf(nome) + ".motivo", motivo);
		Main.settings.getBans().set(UUIDFetcher.getUUIDOf(nome) + ".autor", autor);
		if(minutos != 0){
			int nn = minutos * 60;
			long timestamp = System.currentTimeMillis() / 1000L;
			Main.settings.getBans().set(UUIDFetcher.getUUIDOf(nome) + ".tempoatual", Long.valueOf(timestamp));
			Main.settings.getBans().set(UUIDFetcher.getUUIDOf(nome) + ".tempo", Integer.valueOf(nn));
		} else {
			Main.settings.getBans().set(UUIDFetcher.getUUIDOf(nome) + ".tempo", 0);
			Main.settings.getBans().set(UUIDFetcher.getUUIDOf(nome) + ".tempoatual", 0);
		}
		Main.settings.saveBans();
	}
	
	public static void desbanir(String nome) throws Exception{
		Main.settings.getBans().set(UUIDFetcher.getUUIDOf(nome) + ".motivo", null);
		Main.settings.getBans().set(UUIDFetcher.getUUIDOf(nome) + ".autor", null);
		Main.settings.getBans().set(UUIDFetcher.getUUIDOf(nome) + ".tempo", null);
		Main.settings.getBans().set(UUIDFetcher.getUUIDOf(nome) + ".tempoatual", null);		
		Main.settings.saveBans();
	}
	
	public static void desbanirUUID(UUID id) throws Exception{
		Main.settings.getBans().set(id + ".motivo", null);
		Main.settings.getBans().set(id + ".autor", null);
		Main.settings.getBans().set(id + ".tempo", null);
		Main.settings.getBans().set(id + ".tempoatual", null);
		Main.settings.saveBans();
	}
	
	public static String motivoBan(String nome) throws Exception{
		return Main.settings.getBans().getString(UUIDFetcher.getUUIDOf(nome) + ".motivo");
	}
	
	public static String autorBan(String nome) throws Exception{
		return Main.settings.getBans().getString(UUIDFetcher.getUUIDOf(nome) + ".autor");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		if(!sender.hasPermission("pvp.admin")){
			sender.sendMessage("§cSem permissao!");
			return true;	
		}
		
		if (label.equalsIgnoreCase("ban")) {
			if (args.length == 3) {
				StringBuilder sb = new StringBuilder();
				for (int i = 2; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				String allArgs = sb.toString().trim();
				
				try {
					if(UUIDFetcher.getUUIDOf(args[0]) != null){
						banir(args[0], allArgs, sender.getName(), Integer.valueOf(args[1]));
						for(Player on : Bukkit.getOnlinePlayers()){
							if(args[1].equalsIgnoreCase("0")){
								on.sendMessage("§7" + args[0] + " foi banido por " + sender.getName() + "! Motivo: " + allArgs);	
							} else {
								on.sendMessage("§7" + args[0] + " foi banido por " + sender.getName() + " por " + args[1] + " minutos! Motivo: " + allArgs);
							}
						}
						if(!(sender instanceof Player)){
							sender.sendMessage("§aO jogador foi banido com sucesso!");
						}
					} else {
						sender.sendMessage("§cO jogador nao existe! (Use letras maiusculas e minuculas corretamente!)");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Player t = Bukkit.getPlayerExact(args[0]);
				
				if(t != null){
					if(args[1].equalsIgnoreCase("0")){
						t.kickPlayer("§7[§3§l" + Main.settings.getConfig().getString("NomeServidor") + "§7] \n §cVocê foi banido permanentemente! \n Motivo: " + allArgs + " \n Por: " + sender.getName() + " \n§6Compre unban em " + Main.settings.getConfig().getString("Site_Loja"));	
					} else {
						t.kickPlayer("§7[§3§l" + Main.settings.getConfig().getString("NomeServidor") + "§7] \n §cVocê foi banido por " + args[1] + " minutos! \n Motivo: " + allArgs + " \n Por: " + sender.getName() + " \n§6Compre unban em " + Main.settings.getConfig().getString("Site_Loja"));
					}
				}
			} else {
				sender.sendMessage("§cUse: /ban <jogador> <tempo em minutos, 0 para eterno> <motivo>");
			}
		} else if (label.equalsIgnoreCase("unban")) {
			if (args.length == 1) {
				try {
					if(UUIDFetcher.getUUIDOf(args[0]) != null){
						if(motivoBan(args[0]) != null){
							desbanir(args[0]);
							for(Player on : Bukkit.getOnlinePlayers()){
								on.sendMessage("§7" + args[0] + " foi desbanido por " + sender.getName() + "!");
							}
							if(!(sender instanceof Player)){
								sender.sendMessage("§aO jogador foi desbanido com sucesso!");
							}
						} else {
							sender.sendMessage("§cO jogador nao esta banido!");
						}
					} else {
						sender.sendMessage("§cO jogador nao existe!");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				sender.sendMessage("§cUse: /unban <jogador>");
			}
		}
		return false;
	}
	
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) throws Exception{
		Player p = e.getPlayer();
		if(!Main.pirata){
			if((Main.settings.getBans().getString(p.getUniqueId() + ".motivo") != null) && (Main.settings.getBans().getString(p.getUniqueId() + ".autor") != null) && (((Main.settings.getBans().getLong(p.getUniqueId() + ".tempo") - System.currentTimeMillis()) / 1000 / 3600) != 0)){
				
				if((Main.settings.getBans().getLong(p.getUniqueId() + ".tempoatual") == 0) && (Main.settings.getBans().getInt(p.getUniqueId() + ".tempo") == 0)){
					e.disallow(Result.KICK_BANNED, "§7[§3§l" + Main.settings.getConfig().getString("NomeServidor") + "§7] \n §cVocê foi banido permanentemente! \n Motivo: " + (Main.settings.getBans().getString(p.getUniqueId() + ".motivo") + " \nPor: " + Main.settings.getBans().getString(p.getUniqueId() + ".autor")) + " \n§6Compre unban em " + Main.settings.getConfig().getString("Site_Loja"));
				} else {
				
				long ts;
				
				ts = System.currentTimeMillis() / 1000L;
		          if (ts - Main.settings.getBans().getLong(p.getUniqueId() + ".tempoatual") > Main.settings.getBans().getInt(p.getUniqueId() + ".tempo"))
		          {
		        	  desbanirUUID(p.getUniqueId());
		        	  Main.settings.saveBans();
		          } else {
		        	  e.disallow(Result.KICK_BANNED, "§7[§3§l" + Main.settings.getConfig().getString("NomeServidor") + "§7] \n §cVocê foi banido por "+(Main.settings.getBans().getInt(p.getUniqueId() + ".tempo") / 60)+" minutos! \n Motivo: " + (Main.settings.getBans().getString(p.getUniqueId() + ".motivo") + " \nPor: " + Main.settings.getBans().getString(p.getUniqueId() + ".autor")) + " \n§6Compre unban em " + Main.settings.getConfig().getString("Site_Loja"));
		          }
			}
			}
		} else {
		if ((Main.settings.getBans().getString(p.getName().toLowerCase() + ".motivo") != null)
				&& (Main.settings.getBans().getString(
						p.getName().toLowerCase() + ".autor") != null)
				&& (((Main.settings.getBans().getLong(
						p.getName().toLowerCase() + ".tempo") - System
						.currentTimeMillis()) / 1000 / 3600) != 0)) {

			if ((Main.settings.getBans().getLong(
					p.getName().toLowerCase() + ".tempoatual") == 0)
					&& (Main.settings.getBans().getInt(
							p.getName().toLowerCase() + ".tempo") == 0)) {
				e.disallow(
						Result.KICK_BANNED,
						"§7[§3§l"
								+ Main.settings.getConfig().getString(
										"NomeServidor")
								+ "§7] \n §cVocê foi banido permanentemente! \n Motivo: "
								+ (Main.settings.getBans().getString(
										p.getName().toLowerCase() + ".motivo")
										+ " \nPor: " + Main.settings.getBans()
										.getString(p.getName().toLowerCase() + ".autor"))
								+ " \n§6Compre unban em "
								+ Main.settings.getConfig().getString(
										"Site_Loja"));
			} else {

				long ts;

				ts = System.currentTimeMillis() / 1000L;
				if (ts
						- Main.settings.getBans().getLong(
								p.getName().toLowerCase() + ".tempoatual") > Main.settings
						.getBans().getInt(p.getName().toLowerCase() + ".tempo")) {
					desbanir(p.getName().toLowerCase());
					Main.settings.saveBans();
				} else {
					e.disallow(
							Result.KICK_BANNED,
							"§7[§3§l"
									+ Main.settings.getConfig().getString(
											"NomeServidor")
									+ "§7] \n §cVocê foi banido por "
									+ (Main.settings.getBans().getInt(
											p.getName().toLowerCase() + ".tempo") / 60)
									+ " minutos! \n Motivo: "
									+ (Main.settings.getBans().getString(
											p.getName().toLowerCase() + ".motivo")
											+ " \nPor: " + Main.settings
											.getBans().getString(
													p.getName().toLowerCase() + ".autor"))
									+ " \n§6Compre unban em "
									+ Main.settings.getConfig().getString(
											"Site_Loja"));
				}
			}
		}
		}
	}
}
