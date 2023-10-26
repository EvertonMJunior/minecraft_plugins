package com.goodcraft.lobby.eventos;

import com.goodcraft.api.Item;
import com.goodcraft.api.Message;
import com.goodcraft.api.Title;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class JoinEvent implements Listener {

    public static Integer[] itemSlots = new Integer[]{0, 1, 3, 5, 7, 8};
    public static ItemStack gameModes = Item.item(Material.COMPASS, 1, "§e§lModos de Jogo §7(Clique Direito)");
    public static ItemStack hidePlayers = Item.item(Material.INK_SACK, 1, "§fJogadores: §a§lON §7(Clique Direito)", 10);
    public static ItemStack extras = Item.item(Material.CHEST, 1, "§c§lExtras §7(Clique Direito)");
    public static ItemStack lobbys = Item.item(Material.SLIME_BALL, 1, "§3§lLobbys §7(Clique Direito)");
    public static ItemStack book = null;

    public static void setInitialItems(Player p) {
        p.getInventory().clear();
        
        p.getInventory().setItem(0, gameModes);
        p.getInventory().setItem(1, hidePlayers);
        p.getInventory().setItem(3, Item.getHead(p.getName(), 1, "§a§lStatus §7(Clique Direito)"));
        p.getInventory().setItem(5, extras);
        p.getInventory().setItem(7, lobbys);
        p.getInventory().setItem(8, book);
        
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Message.GOOD.send(p, "Bem-vindo, <p>!");
        Message.GOOD.send(p, "Use a bússola para navegar entre os modos de jogo!");

        Title.GOOD.send(p, "Bem-vindo!", "Confira nossos §lMODOS DE JOGO§r!");

        setInitialItems(p);
        BussolaEvent.saveGameModesGui(p);
        StatusEvent.saveStatusInv(p);

        if(p.getGameMode() != GameMode.ADVENTURE) p.setGameMode(GameMode.ADVENTURE);
        if(p.getMaxHealth() != 20D) p.setMaxHealth(20D);
        if(p.getHealth() != 20D) p.setHealth(20D);
    }
}
