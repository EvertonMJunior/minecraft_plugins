package net.goodcraft.minigames.comandos;

import net.goodcraft.Main;
import net.goodcraft.api.Comando;
import net.goodcraft.api.Utils;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Option;
import net.goodcraft.minigames.Stat;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.minigames.kits.KitManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class JogoCmd extends Comando {

    public JogoCmd() {
        super("jogo", new String[]{"game", "help"});
        setInGameOnly(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        String kit = "Nenhum";
        if (KitManager.hasAnyKit(p)) {
            kit = KitManager.getKit(p).toString();
        }
        Minigame mg = Main.minigame;
        if(mg == null){
            return false;
        }
        GameState gs = mg.getGameState();

        p.sendMessage(" ");

        p.sendMessage("§e» §6" + mg.getName() + " - Estatísticas");

        p.sendMessage(" §fSeu kit §b➛ §7" + kit);


        if(gs != GameState.PREGAME) {
            for (Stat s : mg.getStatsOnKill()) {
                p.sendMessage(" §f" + s.getName() + " §b➛ §7" + s.getStat(p.getUniqueId()));
            }

            if(mg.hasOption(Option.UNLIMITED_LIFES)){
                for (Stat s : mg.getStatsOnDeath()) {
                    p.sendMessage(" §f" + s.getName() + " §b➛ §7" + s.getStat(p.getUniqueId()));
                }
            }
        }

        if (mg.hasOption(Option.HAS_INVENCIBILITY) &&
                gs == GameState.INVENCIBILITY)
            p.sendMessage(" §fInvencibilidade  §b➛ §7" + Utils.secondsToString(120 - mg.getGameTime()));

        if (mg.hasOption(Option.HAS_TIMELIMIT) &&
                gs != GameState.INVENCIBILITY && gs != GameState.PREGAME)
            p.sendMessage(" §fTempo  §b➛ §7" + Utils.secondsToString(mg.getGameTime()));

        if (mg.hasOption(Option.HAS_PREGAME) &&
                gs == GameState.PREGAME) p.sendMessage(" §fIniciando  §b➛ §7" + Utils.secondsToString(mg.getTimeToStart()));

        p.sendMessage(" ");
        p.sendMessage("§e» §6www.good-craft.net");
        p.sendMessage(" ");

        return false;
    }

}
