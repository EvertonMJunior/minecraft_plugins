package net.goodcraft.lobby.eventos;

import com.dsh105.echopet.compat.api.entity.PetType;
import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import net.goodcraft.api.Rank;
import net.goodcraft.api.Utils;
import net.goodcraft.lobby.Main;
import net.goodcraft.lobby.pets.PetSelector;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class ExtrasEvent implements Listener {

    public static Inventory inv;
    public static ArrayList<String> coloredArmorOn = new ArrayList<>();

    public static void saveInv() {
        Inventory in = Bukkit.createInventory(null, 27, "Extras");

        in.setItem(3, Item.item(Material.FIREWORK, 1, "§6Foguete"));
        in.setItem(5, Item.item(Material.JUKEBOX, 1, "§6Efeitos"));
        in.setItem(9, Item.item(Material.BONE, 1, "§6Pets"));
        in.setItem(17, Item.item(Material.FEATHER, 1, "§6Double-Jump/Voar"));
        in.setItem(21, Item.item(Material.LEATHER_CHESTPLATE, 1, "§6Armadura Colorida"));
        in.setItem(23, Item.item(Material.MONSTER_EGG, 1, "§6Ovelha Colorida", 91));
        ExtrasEvent.inv = in;
    }

    public static void open(Player p) {
        if (ExtrasEvent.inv != null) {
            p.openInventory(ExtrasEvent.inv);
            return;
        }
    }

    private Color getColor(int i) {
        Color c = null;
        int cOrdinal = new Random().nextInt(16);

        if (i == 1) {
            c = Color.AQUA;
        }
        if (i == 2) {
            c = Color.BLACK;
        }
        if (i == 3) {
            c = Color.BLUE;
        }
        if (i == 4) {
            c = Color.FUCHSIA;
        }
        if (i == 5) {
            c = Color.GRAY;
        }
        if (i == 6) {
            c = Color.GREEN;
        }
        if (i == 7) {
            c = Color.LIME;
        }
        if (i == 8) {
            c = Color.MAROON;
        }
        if (i == 9) {
            c = Color.NAVY;
        }
        if (i == 10) {
            c = Color.OLIVE;
        }
        if (i == 11) {
            c = Color.ORANGE;
        }
        if (i == 12) {
            c = Color.PURPLE;
        }
        if (i == 13) {
            c = Color.RED;
        }
        if (i == 14) {
            c = Color.SILVER;
        }
        if (i == 15) {
            c = Color.TEAL;
        }
        if (i == 16) {
            c = Color.WHITE;
        }
        if (i == 17) {
            c = Color.YELLOW;
        }

        return c;
    }

    private void spawnRandomFirework(Location l) {
        Firework fw = (Firework) l.getWorld().spawnEntity(l, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        Random r = new Random();

        int rt = r.nextInt(4) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        if (rt == 1) {
            type = FireworkEffect.Type.BALL;
        }
        if (rt == 2) {
            type = FireworkEffect.Type.BALL_LARGE;
        }
        if (rt == 3) {
            type = FireworkEffect.Type.BURST;
        }
        if (rt == 4) {
            type = FireworkEffect.Type.CREEPER;
        }
        if (rt == 5) {
            type = FireworkEffect.Type.STAR;
        }

        int r1i = r.nextInt(17) + 1;
        int r2i = r.nextInt(17) + 1;
        Color c1 = getColor(r1i);
        Color c2 = getColor(r2i);

        FireworkEffect effect = FireworkEffect.builder().flicker(r.nextBoolean()).withColor(c1).
                withFade(c2).with(type).trail(r.nextBoolean()).build();

        fwm.addEffect(effect);

        int rp = r.nextInt(2) + 1;
        fwm.setPower(rp);

        fw.setFireworkMeta(fwm);
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

        if (!i.getType().equals(Material.CHEST)) {
            return;
        }

        if (!i.getItemMeta().getDisplayName().contains("Extras")) {
            return;
        }

        open(p);

        e.setCancelled(true);
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        ItemStack i = e.getCurrentItem();

        if (inv == null || !inv.getName().equalsIgnoreCase("Extras")) {
            return;
        }
        e.setCancelled(true);

        if (i == null || i.getType().equals(Material.AIR)) {
            return;
        }

        switch (i.getType().toString()) {
            case "BONE":
                new PetSelector(p).open();
                break;
            case "FIREWORK":
                p.closeInventory();
                if (!Rank.has(p.getUniqueId(), Rank.BRONZE)) {
                    Utils.onlyVip(p);
                    return;
                }
                spawnRandomFirework(p.getLocation());
                break;
            case "JUKEBOX":
                p.closeInventory();
                Message.ERROR.send(p, "Em breve!");
                break;
            case "FEATHER":
                p.closeInventory();
                if (!Rank.has(p.getUniqueId(), Rank.BRONZE)) {
                    Utils.onlyVip(p);
                    break;
                }
                if (DoubleJumpEvent.flyOff.contains(p.getName())) {
                    DoubleJumpEvent.flyOff.remove(p.getName());
                    Message.INFO.send(p, "Agora você pode voar!");
                } else {
                    DoubleJumpEvent.flyOff.add(p.getName());
                    p.setFlying(false);
                    Message.INFO.send(p, "Agora você pode dar Double-Jumps!");
                }
                break;
            case "LEATHER_CHESTPLATE":
                p.closeInventory();
                if (coloredArmorOn.contains(p.getName())) {
                    coloredArmorOn.remove(p.getName());
                    break;
                } else {
                    if (!Rank.has(p.getUniqueId(), Rank.BRONZE)) {
                        Utils.onlyVip(p);
                        break;
                    }
                    coloredArmorOn.add(p.getName());
                }

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!p.isOnline()) {
                            cancel();
                        }
                        if (!coloredArmorOn.contains(p.getName())) {
                            p.getInventory().setArmorContents(null);
                            cancel();
                            return;
                        }
                        if (LavaChallengeEvent.playersOnLavaChallenge.contains(p.getName())) {
                            p.getInventory().setArmorContents(null);
                            return;
                        }
                        Random r = new Random();
                        Color peitoral = getColor(r.nextInt(17) + 1);
                        Color calca = getColor(r.nextInt(17) + 1);
                        Color bota = getColor(r.nextInt(17) + 1);

                        p.getInventory().setChestplate(Item.leatherArmor(Material.LEATHER_CHESTPLATE, " ", peitoral));
                        p.getInventory().setLeggings(Item.leatherArmor(Material.LEATHER_LEGGINGS, " ", calca));
                        p.getInventory().setBoots(Item.leatherArmor(Material.LEATHER_BOOTS, " ", bota));
                    }
                }.runTaskTimer(Main.getPlugin(), 0, 1L);

                break;
            case "MONSTER_EGG":
                p.closeInventory();
                if (!Rank.has(p.getUniqueId(), Rank.BRONZE)) {
                    Utils.onlyVip(p);
                    break;
                }
                if (Main.getPetAPI().hasPet(p)) {
                    if (Main.getPetAPI().getPet(p).getPetType() == PetType.SHEEP) {
                        Main.getPetAPI().removePet(p, false, false);
                        break;
                    }
                    Main.getPetAPI().removePet(p, false, false);
                }

                Main.getPetAPI().givePet(p, PetType.SHEEP, false);
                Main.getPetAPI().getPet(p).setPetName("jeb_");
                Main.getPetAPI().getPet(p).ownerRidePet(true);
                break;
        }
    }
}
