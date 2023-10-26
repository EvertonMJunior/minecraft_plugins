package net.goodcraft.api;

import net.goodcraft.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.UUID;

public class TestHack {

    public static HashMap<UUID, TestHack> testing = new HashMap<>();

    private final Player admin;
    private final Player toTest;
    private final Hack type;

    public TestHack(Player admin, Player toTest, Hack type) {
        this.admin = admin;
        this.toTest = toTest;
        this.type = type;
    }

    public Player getAdmin() {
        return admin;
    }

    public Hack getType() {
        return type;
    }

    public void test() {
        if (type == Hack.KILLAURA) {

        }

        if (type == Hack.MACRO) {

        }

        if (type == Hack.NOFALL) {
            toTest.teleport(toTest.getLocation().clone().add(0, 0.3, 0));
            toTest.setVelocity(new Vector(0, 5D, 0));
            new BukkitRunnable() {
                @Override
                public void run() {
                    toTest.setVelocity(new Vector(0, -5D, 0));
                }
            }.runTaskLater(Main.getPlugin(), 5L);
        }

        if (type == Hack.AUTOSOUP) {
            final ItemStack[] items = toTest.getInventory().getContents().clone();
            toTest.getInventory().clear();
            toTest.getInventory().setHeldItemSlot(8);
            toTest.getInventory().setItem(0, Item.item(Material.MUSHROOM_SOUP));
            toTest.getInventory().setItem(22, Item.item(Material.MUSHROOM_SOUP));
            toTest.getInventory().setItem(26, Item.item(Material.MUSHROOM_SOUP));

            new BukkitRunnable(){
                int contagem = 0;
                @Override
                public void run() {
                    if(contagem == 0) admin.chat("/invsee " + toTest.getName());

                    if(contagem == 3){
                        Inventory inv = toTest.getInventory();
                        int drank = 0;
                        for(ItemStack i : inv.getContents()){
                            if(i == null) continue;
                            if(i.getType() != Material.BOWL) continue;
                            drank++;
                        }

                        if(drank >= 2){
                            infoUsingHack();
                        } else {
                            infoNotUsingHack();
                        }

                        admin.closeInventory();
                        toTest.getInventory().setContents(items);

                        cancel();
                        return;
                    }
                    toTest.closeInventory();
                    toTest.setHealth(toTest.getMaxHealth());
                    toTest.damage(8D);
                    contagem++;
                }
            }.runTaskTimer(Main.getPlugin(), 5L, 20L);
        }

        if (type == Hack.ANTIKNOCKBACK) {
            toTest.setNoDamageTicks(31);
            final Location l = toTest.getLocation().clone();
            toTest.teleport(toTest.getLocation().clone().add(0, 0.1, 0));

            final float walkSpeed = toTest.getWalkSpeed();
            final float flySpeed = toTest.getFlySpeed();

            toTest.setWalkSpeed(-1);
            toTest.setFlySpeed(-1);

            toTest.setVelocity(toTest.getEyeLocation().getDirection().multiply(4).setY(20D));
            toTest.setAllowFlight(true);
            new BukkitRunnable(){
                @Override
                public void run() {
                    toTest.setWalkSpeed(walkSpeed);
                    toTest.setFlySpeed(flySpeed);
                    toTest.setAllowFlight(false);

                    if(toTest.getLocation().distance(l) >= 10){
                        toTest.teleport(l);
                        infoNotUsingHack();
                        return;
                    }
                    toTest.teleport(l);
                    infoUsingHack();
                }
            }.runTaskLater(Main.getPlugin(), 30L);
        }
        Message.INFO.send(admin, "Testando " + type.getName() + " em " + toTest.getName() + "...");
        testing.put(toTest.getUniqueId(), this);
    }

    public void infoUsingHack() {
        Message.ERROR.send(admin, toTest.getName() + " está provavelmente usando " + type.getName() + ".");
        Title.ERROR.send(admin, toTest.getName(), "está provavelmente usando " + type.getName() + ".");
    }

    public void infoNotUsingHack() {
        Message.GOOD.send(admin, toTest.getName() + " provavelmente não está usando " + type.getName() + ".");
        Title.GOOD.send(admin, toTest.getName(), "provavelmente não está usando " + type.getName() + ".");
    }

    public static enum Hack {
        KILLAURA("KillAura/FF"),
        MACRO("Macro"),
        NOFALL("NoFall"),
        AUTOSOUP("AutoSoup"),
        ANTIKNOCKBACK("AntiKnockback");

        private final String name;

        Hack(String name) {
            this.name = name;
        }

        public static Hack getByName(String name) {
            for (Hack h : Hack.values()) {
                if (h.getName().equalsIgnoreCase(name)) {
                    return h;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }
    }
}
