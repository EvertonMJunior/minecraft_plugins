package net.goodcraft.lobby.eventos;

import net.goodcraft.api.Item;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class JoinEvent implements Listener {

    public static Integer[] itemSlots = new Integer[]{0, 1, 3, 5, 7, 8};
    public static ItemStack gameModes = Item.item(Material.COMPASS, 1, "§e§lModos de Jogo §7(Clique Direito)");
    public static ItemStack hidePlayers = Item.item(Material.INK_SACK, 1, "§fJogadores: §a§lON §7(Clique Direito)", 10);
    public static ItemStack extras = Item.item(Material.CHEST, 1, "§c§lExtras §7(Clique Direito)");
    public static ItemStack lobbys = Item.item(Material.SLIME_BALL, 1, "§3§lLobbys §7(Clique Direito)");
    public static ItemStack book = null;

    private static HashMap<UUID, ItemStack> heads = new HashMap<>();

    public static void setInitialItems(Player p) {
        p.getInventory().clear();

        p.getInventory().setItem(0, gameModes);
        p.getInventory().setItem(1, hidePlayers);
        p.getInventory().setItem(3, getHead(p.getUniqueId(), p.getName()));
        p.getInventory().setItem(5, extras);
        p.getInventory().setItem(7, lobbys);
        p.getInventory().setItem(8, book);

        p.getInventory().setHeldItemSlot(4);
    }

    private static ItemStack getHead(UUID id, String name) {
        if (!heads.containsKey(id)) saveHead(id, name);
        return heads.get(id);
    }

    private static void saveHead(UUID id, String name) {
        if (heads.containsKey(id)) return;
        heads.put(id, Item.getHead(name, 1, "§a§lStatus §7(Clique Direito)"));
    }

    @EventHandler
    public void onLoginAsync(AsyncPlayerPreLoginEvent e) {
        BussolaEvent.saveGameModesGui(e.getUniqueId(), e.getName());
        StatusEvent.saveStatusInv(e.getUniqueId(), e.getName());
        saveHead(e.getUniqueId(), e.getName());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        setInitialItems(p);
    }
}
