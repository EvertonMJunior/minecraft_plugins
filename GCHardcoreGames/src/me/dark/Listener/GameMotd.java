package me.dark.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

import me.dark.Main;
import me.dark.Game.GameState;
import me.dark.Utils.DarkUtils;

public class GameMotd implements Listener{
	@EventHandler
	public void ping(ServerListPingEvent e) {
		if (Main.estado == null) {
//			e.setMotd("          "+Main.prefix+"§fServidor reiniciando!\n"
//					+ "               §c§lSERVIDOR EM TESTES");
			e.setMotd("§bGoodCraft | "+"§3§lHardcore Games\n§cAguarde, servidor reiniciando");
		}else if (Main.estado == GameState.PREGAME) {
//			e.setMotd("          "+Main.prefix+"§fIniciando em [§3"+DarkUtils.tempoInt(Main.toStart)+"§b]\n"
//					+ "               §c§lSERVIDOR EM TESTES");
			e.setMotd("§bGoodCraft | "+"§3§lHardcore Games\n§bPartida iniciando: §f"+DarkUtils.tempoInt(Main.toStart));
		} else if (Main.estado == GameState.INVENCIBILITY) {
//			e.setMotd("     "+Main.prefix+"§fInvencibilidade acaba em [§3"+DarkUtils.tempoInt(120-Main.gameTime)+"§b]\n"
//					+ "                §c§lSERVIDOR EM TESTES");
			e.setMotd("§bGoodCraft | "+"§3§lHardcore Games\n§cInvencibilidade acaba em: §f"+DarkUtils.tempoInt(120-Main.gameTime)+"]");
		}else if (Main.estado == GameState.STARTED) {
//			e.setMotd("          "+Main.prefix+"§fO jogo iniciou [§3"+DarkUtils.tempoInt(Main.gameTime)+"§b]\n"
//					+ "                §c§lSERVIDOR EM TESTES");
			e.setMotd("§bGoodCraft | "+"§3§lHardcore Games\n§4A partida está em andamento: §f"+DarkUtils.tempoInt(Main.gameTime)+"]");
		}
	}

}
