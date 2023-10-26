package net.goodcraft.minigames.kits;

import org.bukkit.entity.Player;

public class KitManager {
    public static Kit getKitByString(String k) {
        Kit s = null;
        for (Kit todos : Kit.kits) {
            if (todos.toString().equalsIgnoreCase(k)) {
                s = todos;
            }
        }
        return s;
    }

    public static boolean hasAnyKit(Player p) {
        return getKit(p) != null;
    }

    public static Kit getKit(Player p) {
        for (Kit k : Kit.kits) {
            if (k.hasAbility(p)) {
                return k;
            }
        }
        return null;
    }

    public static void removeKit(Player p) {
        Kit k = getKit(p);
        if (k != null) {
            k.removePlayer(p);
        }
    }

}
