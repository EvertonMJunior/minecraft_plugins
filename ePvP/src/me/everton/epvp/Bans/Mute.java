package me.everton.epvp.Bans;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.everton.epvp.Main;

import com.evilmidget38.UUIDFetcher;

public class Mute implements CommandExecutor{
	public static void mutar(String nome, String motivo, String autor, int minutos) throws Exception{
		Main.settings.getMutes().set(UUIDFetcher.getUUIDOf(nome) + ".motivo", motivo);
		Main.settings.getMutes().set(UUIDFetcher.getUUIDOf(nome) + ".autor", autor);
		if(minutos != 0){
			int nn = minutos * 60;
			long timestamp = System.currentTimeMillis() / 1000L;
			Main.settings.getMutes().set(UUIDFetcher.getUUIDOf(nome) + ".tempoatual", Long.valueOf(timestamp));
			Main.settings.getMutes().set(UUIDFetcher.getUUIDOf(nome) + ".tempo", Integer.valueOf(nn));
		} else {
			Main.settings.getMutes().set(UUIDFetcher.getUUIDOf(nome) + ".tempo", 0);
			Main.settings.getMutes().set(UUIDFetcher.getUUIDOf(nome) + ".tempoatual", 0);
		}
		Main.settings.saveMutes();
	}
	
	public static void desmutar(String nome) throws Exception{
		Main.settings.getMutes().set(UUIDFetcher.getUUIDOf(nome) + ".motivo", null);
		Main.settings.getMutes().set(UUIDFetcher.getUUIDOf(nome) + ".autor", null);
		Main.settings.getMutes().set(UUIDFetcher.getUUIDOf(nome) + ".tempo", null);
		Main.settings.getMutes().set(UUIDFetcher.getUUIDOf(nome) + ".tempoatual", null);		
		Main.settings.saveMutes();
	}
	
	public static void desmutarUUID(UUID id) throws Exception{
		Main.settings.getMutes().set(id + ".motivo", null);
		Main.settings.getMutes().set(id + ".autor", null);
		Main.settings.getMutes().set(id + ".tempo", null);
		Main.settings.getMutes().set(id + ".tempoatual", null);
		Main.settings.saveMutes();
	}
	
	public static String motivoMute(String nome) throws Exception{
		return Main.settings.getMutes().getString(UUIDFetcher.getUUIDOf(nome) + ".motivo");
	}
	
	public static String autorMute(String nome) throws Exception{
		return Main.settings.getMutes().getString(UUIDFetcher.getUUIDOf(nome) + ".autor");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		
		if(!sender.hasPermission("pvp.admin")){
			sender.sendMessage("§cSem permissao!");
			return true;	
		}
		
		CommandSender p = sender;
		
		if(label.equalsIgnoreCase("mute")){
			if(!(sender instanceof Player)){
				return true;
			}
			
			if(args.length == 3){
				StringBuilder sb = new StringBuilder();
				for (int i = 2; i < args.length; i++) {
					sb.append(args[i] + " ");
				}
				String allArgs = sb.toString().trim();
				
				try {
					if(UUIDFetcher.getUUIDOf(args[0]) != null){
						mutar(args[0], allArgs, sender.getName(), Integer.valueOf(args[1]));
						for(Player on : Bukkit.getOnlinePlayers()){
							if(args[1].equalsIgnoreCase("0")){
								if(on.hasPermission("pvp.admin")){
									on.sendMessage("§7" + args[0] + " foi mutado por " + sender.getName() + "! Motivo: " + allArgs);
								}
							} else {
								if(on.hasPermission("pvp.admin")){
									on.sendMessage("§7" + args[0] + " foi mutado por " + sender.getName() + " por " + args[1] + " minutos! Motivo: " + allArgs);
								}
							}
						}
						if(!(sender instanceof Player)){
							sender.sendMessage("§aO jogador foi mutado com sucesso!");
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
						t.sendMessage("§cVocê foi mutado para sempre por " + sender.getName() + " ! Motivo: " + allArgs);	
					} else {
						t.sendMessage("§cVocê foi mutado por " + sender.getName() + " por " + args[1] + " minutos! Motivo: " + allArgs);
					}
				}
			} else {
				p.sendMessage("§cUse: /mute <jogador> <tempo em minutos, 0 para eterno> <motivo>");
			}
		} else if (label.equalsIgnoreCase("unmute")) {
			if (args.length == 1) {
				try {
					if(UUIDFetcher.getUUIDOf(args[0]) != null){
						if(motivoMute(args[0]) != null){
							desmutar(args[0]);
							for(Player on : Bukkit.getOnlinePlayers()){
								if(on.hasPermission("pvp.admin")){
									on.sendMessage("§7" + args[0] + " foi desmutado por " + sender.getName() + "!");
								}
							}
							if(!(sender instanceof Player)){
								sender.sendMessage("§aO jogador foi desmutado com sucesso!");
							}
						} else {
							sender.sendMessage("§cO jogador nao esta mutado!");
						}
					} else {
						sender.sendMessage("§cO jogador nao existe!");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				sender.sendMessage("§cUse: /unmute <jogador>");
			}
		}
		
		
		
		return false;
	}
}
