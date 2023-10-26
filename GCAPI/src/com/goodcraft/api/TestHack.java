package com.goodcraft.api;

import com.goodcraft.Main;
import java.util.HashMap;
import java.util.UUID;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

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
    
    public Player getAdmin(){
        return admin;
    }
    
    public Hack getType(){
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
        }

        if (type == Hack.ANTIKNOCKBACK) {
        }
        Message.INFO.send(admin, "Testando " + type.getName() + " em " + toTest.getName() + ".");
        testing.put(toTest.getUniqueId(), this);
    }
    
    public void infoUsingHack(){
        Message.INFO.send(admin, toTest.getName() + " está provavelmente usando " + type.getName() + "!");
    }
    
    public void infoNotUsingHack(){
        Message.INFO.send(admin, toTest.getName() + " provavelmente não está usando " + type.getName() + ".");
    }

    public static enum Hack {
        KILLAURA("KillAura/FF"),
        MACRO("Macro"),
        NOFALL("NoFall"),
        AUTOSOUP("AutoSoup"),
        ANTIKNOCKBACK("AntiKnockback");

        private final String name;

        private Hack(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
