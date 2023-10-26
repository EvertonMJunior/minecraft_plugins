package net.goodcraft.minigames.eventos;

import net.goodcraft.Main;
import net.goodcraft.api.Message;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Option;
import net.goodcraft.minigames.game.GameState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class QuitEvent implements Listener {
    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        event(e.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        event(e.getPlayer());
    }

    private void event(Player p){
        Minigame mg = Main.minigame;

        mg.getScoreboard().removeBoard(p);

        if(mg.hasOption(Option.CAN_RELOG)) return;

        if (mg.getGameState() == GameState.PREGAME) {
            mg.getPlayers().remove(p.getUniqueId());
        }
        if(DamageEvent.combatLog.containsKey(p.getUniqueId()) && !mg.hasOption(Option.CAN_DISCONNECT_IN_COMBAT)){
            int count = mg.getCurrentPlayers();
            Message.INFO.broadcast("§6[" + mg.getName() + "]§e " + p.getName() + " deslogou em combate!" +
                    (mg.hasOption(Option.HAS_WIN) ? "(" + count +  " jogador" + (count > 1 ? "es" : "") + ")" : ""));
            if (p.isOnline()) {
                p.damage(p.getMaxHealth(), p);

                for (ItemStack item : p.getInventory().getContents()) {
                    if (item != null) {
                        if (p.getLocation() != null) {
                            p.getLocation().getWorld().dropItemNaturally(p.getLocation(), item);
                        }
                    }
                }

                for (ItemStack item : p.getInventory().getArmorContents()) {
                    if (item != null) {
                        if (p.getLocation() != null) {
                            p.getLocation().getWorld().dropItemNaturally(p.getLocation(), item);
                        }
                    }
                }
            }
            return;
        }
        if(mg.isPlayer(p.getUniqueId())){
            mg.getPlayers().remove(p.getUniqueId());
            if(mg.hasOption(Option.HAS_WIN)){
                int count = mg.getCurrentPlayers();
                Message.INFO.broadcast("§6[" + mg.getName() + "]§e " + p.getName() + " saiu da partida! (" + count + " jogador" +
                        (count > 1 ? "es" : "") + ")");
            }
        }
    }
}
