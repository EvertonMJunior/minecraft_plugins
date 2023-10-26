package net.goodcraft.eventos;

import net.goodcraft.api.*;
import net.goodcraft.api.json.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class AdminEvent implements Listener {

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!AdminAPI.admins.containsKey(p.getName())) {
            return;
        }
        AdminAPI admin = AdminAPI.admins.get(p.getName());

        if (p.getItemInHand().getType() == Material.AIR || p.getItemInHand().getType() == null) {
            return;
        }

        if (p.getItemInHand().getType() == Material.MAGMA_CREAM) {
            admin.fastChange();
            return;
        }
    }

    @EventHandler
    public void onClickPlayer(PlayerInteractEntityEvent e) {
        Player p = e.getPlayer();

        if (!AdminAPI.admins.containsKey(p.getName())) {
            return;
        }
        AdminAPI admin = AdminAPI.admins.get(p.getName());

        if (!(e.getRightClicked() instanceof Player)) {
            return;
        }
        Player r = (Player) e.getRightClicked();

        if (p.getItemInHand().getType() == Material.AIR || p.getItemInHand().getType() == null) {
            SpecialPlayerInventory sPl = new SpecialPlayerInventory(r, true);
            p.openInventory(sPl.getBukkitInventory());
            return;
        }

        if (p.getItemInHand().getType() == Material.ARROW) {
            admin.testHacks(r);
            return;
        }

        if (p.getItemInHand().getType() == Material.BLAZE_ROD) {
            admin.showInfo(r);
        }
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onInfoInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack i = e.getCurrentItem();

        if (!AdminAPI.admins.containsKey(p.getName())) {
            return;
        }
        AdminAPI admin = AdminAPI.admins.get(p.getName());

        if (inv == null || !inv.getName().equalsIgnoreCase("Informações do Jogador")) {
            return;
        }
        e.setCancelled(true);

        if (i == null || i.getType().equals(Material.AIR)) {
            return;
        }

        if (i.getType() == Material.NAME_TAG) {
            Player toOpen = Bukkit.getPlayer(admin.playerInfo);
            if (toOpen == null) {
                return;
            }
            p.closeInventory();

            JSONChatMessage msg = new JSONChatMessage("§6UUID de " + toOpen.getName() + ": ", JSONChatColor.WHITE, null);
            JSONChatExtra extra = new JSONChatExtra("§e" + toOpen.getUniqueId().toString(),
                    JSONChatColor.WHITE, Arrays.asList(JSONChatFormat.NENHUM));

            extra.setHoverEvent(JSONChatHoverEventType.SHOW_TEXT, "§eClique para copiar");
            extra.setClickEvent(JSONChatClickEventType.SUGGEST_COMMAND, toOpen.getUniqueId().toString());

            msg.addExtra(extra);

            msg.sendToPlayer(p);

            Message.ERROR.send(p, "Clique sobre o ID para copiá-lo.");
            return;
        }

        if (i.getType() == Material.CHEST) {
            Player toOpen = Bukkit.getPlayer(admin.playerInfo);
            if (toOpen != null) {
                SpecialPlayerInventory sPl = new SpecialPlayerInventory(toOpen, true);
                p.openInventory(sPl.getBukkitInventory());
                return;
            }
            p.closeInventory();
        }
    }

    @EventHandler
    @SuppressWarnings("deprecation")
    public void onHackInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack i = e.getCurrentItem();

        if (!AdminAPI.admins.containsKey(p.getName())) {
            return;
        }
        AdminAPI admin = AdminAPI.admins.get(p.getName());

        if (inv == null || !inv.getName().equalsIgnoreCase("Verificar Hacks")) {
            return;
        }
        e.setCancelled(true);

        if (i == null || i.getType().equals(Material.AIR)) {
            return;
        }
        TestHack.Hack hack = TestHack.Hack.getByName(ChatColor.stripColor(i.getItemMeta().getDisplayName()));
        Player toTest = Bukkit.getPlayer(admin.playerBeingTested);

        if (toTest == null) {
            p.closeInventory();
            return;
        }

        if (hack == null) {
            return;
        }
        new TestHack(p, toTest, hack).test();

        p.closeInventory();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (!AdminAPI.admins.containsKey(e.getPlayer().getName())) {
            return;
        }

        if (e.getClickedBlock() == null) {
            return;
        }

        if (!e.getAction().name().contains("RIGHT")) {
            return;
        }

        if (e.getClickedBlock().getType().equals(Material.CHEST)) {
            e.setCancelled(true);
            Utils.activateChest(e.getPlayer(), true, true,
                    e.getClickedBlock().getLocation().getBlockX(),
                    e.getClickedBlock().getLocation().getBlockY(),
                    e.getClickedBlock().getLocation().getBlockZ());
        }
    }
}
