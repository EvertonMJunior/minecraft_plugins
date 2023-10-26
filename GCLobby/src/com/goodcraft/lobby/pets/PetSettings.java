package com.goodcraft.lobby.pets;

import com.goodcraft.api.Item;
import com.goodcraft.api.Message;
import com.goodcraft.lobby.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;


public class PetSettings implements Listener{
    private final Player p;
    
    public PetSettings(Player p){
        this.p = p;
    }
    
    public void open(){
        Inventory inv = Bukkit.createInventory(p, 27, "Pets - Configurações");
        
        inv.setItem(3, Item.item(Material.INK_SACK, 1, "§eVoltar", 10));
        inv.setItem(5, Item.item(Material.INK_SACK, 1, "§eFechar", 8));
        
        inv.setItem(20, Item.item(Material.NAME_TAG, 1, "§aAlterar Nome"));
        inv.setItem(22, Item.item(Material.STONE_SWORD, 1, "§aRemover"));
        inv.setItem(24, Item.item(Material.LEASH, 1, "§aMontar"));
        
        p.openInventory(inv);
    }
    
    @EventHandler
    @SuppressWarnings("deprecation")
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack i = e.getCurrentItem();

        if (inv == null || !inv.getName().equalsIgnoreCase("Pets - Configurações")) {
            return;
        }
        e.setCancelled(true);

        if (i == null || i.getType().equals(Material.AIR)) {
            return;
        }

        if (i.getType().equals(Material.INK_SACK)) {
            if (i.getData().getData() == (short) 10) {
                new PetSelector(p).open();
            } else {
                p.closeInventory();
            }
            return;
        }
        
        if(i.getType().equals(Material.NAME_TAG)){
            Message.INFO.send(p, "Use /pet name <nome do pet>");
            p.closeInventory();
            return;
        }
        
        if(i.getType().equals(Material.STONE_SWORD)){
            Message.GOOD.send(p, "Seu PET foi removido!");
            Main.getPetAPI().removePet(p, false, false);
            p.closeInventory();
            return;
        }
        
        if(i.getType().equals(Material.LEASH)){
            Message.GOOD.send(p, "Agora você está montando seu PET! Use WASD e a barra de espaço para controlá-lo.");
            Main.getPetAPI().getPet(p).ownerRidePet(true);
            p.closeInventory();
            return;
        }
    }
}
