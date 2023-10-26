package net.goodcraft.kitpvp.eventos;

import net.goodcraft.api.Item;
import net.goodcraft.kitpvp.ConfigManager;
import net.goodcraft.kitpvp.Main;
import net.goodcraft.minigames.eventos.AddToKitEvent;
import net.goodcraft.minigames.kits.KitManager;
import net.goodcraft.minigames.kits.KitSelector;
import net.goodcraft.minigames.kits.ShopManager;
import net.goodcraft.sql.SQLStatus;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;

public class GeneralEvents implements Listener {
    public static ItemStack stoneSword = Item.item(Material.STONE_SWORD, 1, " ");
    public static ItemStack sharpSword = Item.item(Material.STONE_SWORD, 1, " ", Enchantment.DAMAGE_ALL, 1);
    public static ItemStack soup = Item.item(Material.MUSHROOM_SOUP, 1);
    public static ItemStack potion = Item.item(PotionType.INSTANT_HEAL, 1, null, new String[]{}, true, false, 1);

    public static ItemStack kitsItem = Item.item(Material.CHEST, 1, "§a§lKits §7(Clique Direito)");
    public static ItemStack shopItem = Item.item(Material.EMERALD, 1, "§c§lLoja §7(Clique Direito)");
    public static ItemStack warpsItem = Item.item(Material.ENDER_CHEST, 1, "§e§lWarps §7(Clique Direito)");
    public static ItemStack configsItem = Item.item(Material.DIODE, 1, "§3§lConfigurações §7(Clique Direito)");

    public static void setInitialItems(Player p) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(new ItemStack[]{});
        p.getInventory().setItem(1, kitsItem);
        p.getInventory().setItem(3, shopItem);
        p.getInventory().setItem(5, warpsItem);
        p.getInventory().setItem(7, configsItem);

        p.getInventory().setHeldItemSlot(1);
    }

    @EventHandler
    public void onAddToKit(AddToKitEvent e) {
        if(e.isCancelled()){
            return;
        }
        Player p = e.getPlayer();

        p.getInventory().clear();
        p.getInventory().setArmorContents(new ItemStack[]{});

        if(e.getKit().equals(KitManager.getKitByString("PvP"))){
            p.getInventory().setItem(0, sharpSword);
        } else {
            p.getInventory().setItem(0, stoneSword);
        }

        new BukkitRunnable(){
            @Override
            public void run() {
                final ItemStack[] item = {soup};
                SQLStatus.getStat(p.getUniqueId(), "good_kitpvp", "regenerationitem", new SQLStatus.Callback<Object>() {
                    @Override
                    public void onSucess(Object o) {
                        Integer type = (Integer) o;
                        item[0] = type == 0 ? soup : potion;
                        for (int i = 0; i < 36; i++) {
                            p.getInventory().addItem(item[0]);
                        }
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        for (int i = 0; i < 36; i++) {
                            p.getInventory().addItem(item[0]);
                        }
                    }
                });
            }
        }.runTaskLater(Main.getPlugin(), 5L);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        setInitialItems(e.getPlayer());
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (e.getItem() != null && e.getAction().name().contains("RIGHT")) {
            if (e.getItem().equals(kitsItem)) {
                KitSelector.openInv(e.getPlayer());
            } else if (e.getItem().equals(shopItem)) {
                ShopManager.open(p);
            } else if(e.getItem().equals(configsItem)){
                ConfigManager.open(p);
            } else {
                return;
            }
            e.setCancelled(true);
            e.setUseInteractedBlock(Event.Result.DENY);
        }
    }

    @EventHandler
    public void soupEvent(PlayerInteractEvent e){
        Player p = e.getPlayer();

        ItemStack bowl = new ItemStack(Material.BOWL, 1);
        ItemMeta meta = bowl.getItemMeta();
        EntityPlayer p2 = ((CraftPlayer) p).getHandle();
        if (p2.getHealth() <= 19
                && e.getAction() == Action.RIGHT_CLICK_AIR
                || e.getAction() == Action.RIGHT_CLICK_BLOCK
                && p.getItemInHand().getType() == Material.MUSHROOM_SOUP
                && p2.getHealth() < p2.getMaxHealth()) {
            int heal = 7;
            if (e.getPlayer().getItemInHand().getType() == Material.MUSHROOM_SOUP) {
                if (p2.getHealth() >= 13) {
                    p.setHealth(20);
                } else {
                    p.setHealth(p2.getHealth() + heal > p2
                            .getMaxHealth() ? p2.getMaxHealth() : p2
                            .getHealth() + heal);
                }
                p.getItemInHand().setType(Material.BOWL);
                p.getItemInHand().setItemMeta(meta);
                p.setItemInHand(bowl);
                p.updateInventory();
            }
        } else if (p.getFoodLevel() <= 19
                && e.getAction() == Action.RIGHT_CLICK_AIR
                || e.getAction() == Action.RIGHT_CLICK_BLOCK
                && p.getItemInHand().getType() == Material.MUSHROOM_SOUP
                && p.getFoodLevel() < 20) {
            if (!(p.getFoodLevel() == 20)) {
                if (e.getPlayer().getItemInHand().getType() == Material.MUSHROOM_SOUP) {
                    if (p.getFoodLevel() >= 15) {
                        p.setFoodLevel(20);
                    } else {
                        int tofeed = 5;

                        e.getPlayer().setFoodLevel(
                                e.getPlayer().getFoodLevel() + tofeed);
                    }

                    p.getItemInHand().setType(Material.BOWL);
                    p.getItemInHand().setItemMeta(meta);
                    p.setItemInHand(bowl);
                    p.updateInventory();
                }
            }
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        setInitialItems(e.getPlayer());
    }
}
