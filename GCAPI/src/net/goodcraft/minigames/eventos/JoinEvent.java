package net.goodcraft.minigames.eventos;

import net.goodcraft.api.BarAPI;
import net.goodcraft.api.Rank;
import net.goodcraft.api.Title;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Option;
import net.goodcraft.minigames.game.GameState;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

public class JoinEvent implements Listener {
    private final Minigame MINIGAME;

    public JoinEvent(Minigame minigame) {
        this.MINIGAME = minigame;
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        Rank pRank = Rank.getPlayerRank(p.getUniqueId());

        if (MINIGAME.getCurrentPlayers() >= MINIGAME.getMaximumPlayers()) {
            if (!Rank.has(pRank, Rank.BRONZE)) {
                e.setKickMessage("§eO servidor já está cheio! \n§eCompre VIP para isso não acontecer de novo: \n§lloja.good-craft.net");
            }
        } else if (MINIGAME.getGameState() == null) {
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§cAguarde...");
        } else if (MINIGAME.getGameState() != GameState.PREGAME && !MINIGAME.hasOption(Option.CANJOIN_AFTERSTART)) {
            if (MINIGAME.getGameTime() < 300) {
                if (!Rank.has(pRank, Rank.BRONZE)) {
                    e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§eO jogo já iniciou! \n§eCompre VIP para isso não acontecer de novo: \n§lloja.good-craft.net");
                }
            } else {
                if (!Rank.has(pRank, Rank.BRONZE)) {
                    e.disallow(PlayerLoginEvent.Result.KICK_OTHER, "§eO jogo já iniciou! \n§eCompre VIP para poder espectar partidas: \n§lloja.good-craft.net");
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        if (MINIGAME.hasOption(Option.TELEPORT_SPAWN_ONJOIN)) p.teleport(
                MINIGAME.getUsingWorld().getSpawnLocation().clone().add(0.5, 0.5, 0.5));
        if (MINIGAME.hasOption(Option.MAXFOODLEVEL_ONJOIN)) p.setFoodLevel(20);
        if (MINIGAME.hasOption(Option.MAXHEALTH_ONJOIN)) p.setHealth(p.getMaxHealth());

        e.setJoinMessage(null);
        p.setWalkSpeed(0.2f);

        p.setHealth(20.0D);
        p.setFoodLevel(20);
        Title.INFO.send(p, MINIGAME.getName(), "Seja o melhor dos jogadores!");
        if (MINIGAME.getGameState() == GameState.PREGAME || !MINIGAME.hasOption(Option.HAS_PREGAME)) {
            p.setGameMode(GameMode.ADVENTURE);

            if (MINIGAME.getGameState() == GameState.PREGAME) {
                BarAPI.setName(p, "§e§lJOGANDO §f§l" + MINIGAME.getName().toUpperCase() + " §e§lNO §f§lGOODCRAFT", 300);
            }

            if (!MINIGAME.isPlayer(p.getUniqueId())) {
                MINIGAME.getPlayers().add(p.getUniqueId());
            }
        } else {
            if (MINIGAME.getGameTime() >= 300) {
                MINIGAME.getSpectatorManager().put(p);
            }
        }

    }
}
