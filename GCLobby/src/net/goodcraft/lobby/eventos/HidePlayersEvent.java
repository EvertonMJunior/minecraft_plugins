package net.goodcraft.lobby.eventos;

import net.goodcraft.api.AdminAPI;
import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import net.goodcraft.api.Rank;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class HidePlayersEvent implements Listener {

    public static ArrayList<String> hiding = new ArrayList<>();

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getItemInHand();

        if (!e.getAction().name().contains("RIGHT")) {
            return;
        }

        if ((i.getType() == Material.AIR) || (i == null)) {
            return;
        }

        if (!i.getType().equals(Material.INK_SACK)) {
            return;
        }

        if (!i.getItemMeta().getDisplayName().contains("§fJogadores: ")) {
            return;
        }

        if (i.getData().getData() == (short) 10 && !hiding.contains(p.getName())) {
            hiding.add(p.getName());
            p.setItemInHand(Item.item(Material.INK_SACK, 1, "§fJogadores: §c§lOFF §7(Clique Direito)", 8));
            Message.INFO.send(p, "Os jogadores não estão mais vísiveis.");

            for (Player h : Bukkit.getOnlinePlayers()) {
                if (Rank.has(p.getUniqueId(), Rank.YOUTUBER)) continue;
                p.hidePlayer(h);
            }
            return;
        }

        hiding.remove(p.getName());
        p.setItemInHand(Item.item(Material.INK_SACK, 1, "§fJogadores: §a§lON §7(Clique Direito)", 10));
        Message.INFO.send(p, "Os jogadores estão novamente visíveis.");

        for (Player h : Bukkit.getOnlinePlayers()) {
            if (AdminAPI.admins.containsKey(h.getName())) continue;
            p.showPlayer(h);
        }

        e.setCancelled(true);
    }

    @EventHandler
    public void onJoin2(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for (String h : hiding) {
            Player ph = Bukkit.getPlayerExact(h);
            if (ph == null) {
                hiding.remove(h);
                continue;
            }

            ph.hidePlayer(p);
        }
    }
}
