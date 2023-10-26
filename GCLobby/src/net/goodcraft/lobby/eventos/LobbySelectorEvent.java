package net.goodcraft.lobby.eventos;

import com.google.common.io.ByteArrayDataInput;
import net.goodcraft.api.BungeeUtils;
import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import net.goodcraft.api.ProxyMessageEvent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Set;

public class LobbySelectorEvent implements Listener, Runnable {
    public static Inventory inv;
    public static HashMap<String, Integer> lobbys = new HashMap<>();

    public static void initialize() {
        inv = Bukkit.createInventory(null, 27, "Lobbys");
        for (String server : BungeeUtils.servers) {
            if (!server.startsWith("lobby")) continue;
            lobbys.put(server, 0);
            BungeeUtils.getPlayerCount(server);
        }
        refresh();
    }

    private static void refresh() {
        for (String server : BungeeUtils.servers) {
            if (!server.startsWith("lobby")) continue;
            lobbys.put(server, 0);
            BungeeUtils.getPlayerCount(server);
        }

        for (int i = 10; i < lobbys.size() + 10; i++) {
            Set<String> strings = lobbys.keySet();
            String server = strings.toArray(new String[strings.size()])[i - 10];
            BungeeUtils.getPlayerCount(server);
            Integer count = lobbys.get(server);
            ItemStack item = Item.item(Material.SLIME_BALL, 1, "§aLobby " + server.replace("lobby", ""), new String[]{"§7" + count + " jogadores."});
            if (BungeeUtils.serverName.equalsIgnoreCase(server)) Item.addGlow(item);
            inv.setItem(i, item);
            for (HumanEntity e : inv.getViewers()) {
                Player pl = (Player) e;
                pl.updateInventory();
            }
        }
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        ItemStack i = p.getItemInHand();

        if (!e.getAction().name().contains("RIGHT")) {
            return;
        }

        if (i.getType() == Material.AIR) {
            return;
        }

        if (!i.getType().equals(Material.SLIME_BALL)) {
            return;
        }

        if (!i.getItemMeta().getDisplayName().contains("Lobbys")) {
            return;
        }
        e.setCancelled(true);

        p.openInventory(inv);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack i = e.getCurrentItem();

        if (inv == null || !inv.getName().contains("Lobbys")) {
            return;
        }
        e.setCancelled(true);

        if (i == null || i.getType().equals(Material.AIR)) {
            return;
        }

        if (!i.getItemMeta().hasDisplayName()) {
            return;
        }
        String thisServer = "lobby" + ChatColor.stripColor(i.getItemMeta().getDisplayName().replace("Lobby ", ""));

        if (BungeeUtils.serverName.contains(thisServer)) {
            p.closeInventory();
            Message.ERROR.send(p, "Você já está neste Lobby!");
            return;
        }
        BungeeUtils.sendToServer(p.getName(), thisServer);
    }

    @EventHandler
    public void onProxyMessage(ProxyMessageEvent e) {
        if (e.getChannel().equals("PlayerCount")) {
            ByteArrayDataInput in = e.getIn();
            String server = in.readUTF();
            Integer count = in.readInt();
            if (!server.startsWith("lobby")) return;
            if (!lobbys.containsKey(server)) {
                lobbys.put(server, count);
            }
            lobbys.replace(server, count);
            return;
        }
    }

    @Override
    public void run() {
        refresh();
    }
}
