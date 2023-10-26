package net.goodcraft.hardcoregames.eventos;

import net.goodcraft.api.Item;
import net.goodcraft.api.Rank;
import net.goodcraft.api.Title;
import net.goodcraft.hardcoregames.Main;
import net.goodcraft.hardcoregames.chests.Chests;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class GamerJoin implements Listener {

    public static void preInv(Player p) {
        p.getInventory().clear();
        p.getInventory().setItem(4, GeralGameL.chests);
        p.getInventory().setItem(2, Item.getHead(p.getName(), 1, "§eStatus"));
        p.getInventory().setItem(0, Chests.chest);
        p.getInventory().setItem(6, HotBar.soup);
        p.getInventory().setItem(8, PreGameFight.p1v1);

        p.getInventory().setHeldItemSlot(4);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        if (e.getResult() == Result.KICK_FULL) {
            if (!Rank.has(p.getUniqueId(), Rank.BRONZE)) {
                e.setKickMessage("§eO servidor já está cheio! \n§eCompre VIP para isto não acontecer de novo: \n§loja.good-craft.net");
            }
        } else if (Main.estado == null) {
            e.disallow(Result.KICK_OTHER, "§cAguarde...");
        } else if (Main.estado != GameState.PREGAME) {
            if (Main.gameTime < 300) {
                if (!Rank.has(p.getUniqueId(), Rank.BRONZE)) {
                    e.disallow(Result.KICK_OTHER, "§eO jogo já iniciou! \n§eCompre VIP para isto não acontecer de novo: \n§loja.good-craft.net");
                }
            } else {
                if (!Rank.has(p.getUniqueId(), Rank.BRONZE)) {
                    e.disallow(Result.KICK_OTHER, "§eO jogo já iniciou! \n§eCompre VIP para poder espectar partidas: \n§loja.good-craft.net");
                }
            }
        }
    }

    @EventHandler
    public void quit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        e.setQuitMessage(null);

        if (Main.estado == GameState.PREGAME) {
            Main.players.remove(p.getUniqueId());
        }
        if (PreGameFight.in1v1.contains(e.getPlayer().getUniqueId())) {
            PreGameFight.in1v1.remove(e.getPlayer().getUniqueId());
        }
        LavaChallenge.playersOnLavaChallenge.remove(e.getPlayer().getUniqueId());
        Main.getScoreboard().removeBoard(p);
    }

    @EventHandler
    public void kick(PlayerKickEvent e) {
        Player p = e.getPlayer();

        if (Main.estado == GameState.PREGAME) {
            Main.players.remove(p.getUniqueId());
        }
        if (PreGameFight.in1v1.contains(e.getPlayer().getUniqueId())) {
            PreGameFight.in1v1.remove(e.getPlayer().getUniqueId());
        }
        LavaChallenge.playersOnLavaChallenge.remove(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void join(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (PreGameFight.in1v1.contains(e.getPlayer().getUniqueId())) {
            PreGameFight.in1v1.remove(e.getPlayer().getUniqueId());
        }
        LavaChallenge.playersOnLavaChallenge.remove(e.getPlayer().getUniqueId());

        e.setJoinMessage(null);
        p.setWalkSpeed(0.2f);

        p.setHealth(20.0D);
        p.setFoodLevel(20);
        Title.INFO.send(p, "HardcoreGames", "Sobreviva e seja o melhor!");
        if (Main.estado == GameState.PREGAME) {
            p.teleport(Main.usingWorld.getSpawnLocation().clone().add(0.5, 0, 0.5));
            preInv(p);
            if (!Main.players.contains(p.getUniqueId())) {
                Main.players.add(p.getUniqueId());
            }
        } else {
            if (!GamerRelog.reloging.contains(p.getUniqueId())) {
                if (Main.gameTime >= 300) {
                    p.chat("/admin");
                } else {
                    if (!Main.players.contains(p.getUniqueId())) {
                        Main.players.add(p.getUniqueId());
                    }
                    p.getInventory().addItem(new ItemStack(Material.COMPASS));
                }
            }
        }
    }

}
