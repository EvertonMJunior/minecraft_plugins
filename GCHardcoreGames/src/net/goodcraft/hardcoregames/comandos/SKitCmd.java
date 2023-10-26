package net.goodcraft.hardcoregames.comandos;

import net.goodcraft.api.Comando;
import net.goodcraft.api.Message;
import net.goodcraft.api.Rank;
import net.goodcraft.hardcoregames.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;
import java.util.HashMap;

public class SKitCmd extends Comando {

    public SKitCmd() {
        super("skit", Rank.MODPLUS);
        setInGameOnly(true);
    }

    public HashMap<String, ItemStack[]> inv = new HashMap<>();
    public HashMap<String, ItemStack[]> armor = new HashMap<>();
    public HashMap<String, Collection<PotionEffect>> potions = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 3 || args.length == 0) {
            Message.ERROR.send(p, "Use /skit <create/delete/apply> <kit>");
            return false;
        }

        if (args[0].equalsIgnoreCase("create") && args.length == 2) {
            String kitName = args[1];
            if (inv.containsKey(kitName)) {
                Message.ERROR.send(p, "Este kit já existe!");
                return false;
            }
            inv.put(kitName, p.getInventory().getContents());
            armor.put(kitName, p.getInventory().getArmorContents());
            potions.put(kitName, p.getActivePotionEffects());

            Message.INFO.send(p, "Kit " + kitName + " criado com sucesso!");
            return false;
        }

        if (args[0].equalsIgnoreCase("delete") && args.length == 2) {
            String kitName = args[1];
            if (!inv.containsKey(kitName)) {
                Message.ERROR.send(p, "Este kit não existe!");
                return false;
            }
            inv.remove(kitName);
            armor.remove(kitName);
            potions.remove(kitName);

            Message.INFO.send(p, "Kit " + kitName + " removido com sucesso!");
            return false;
        }

        if (args[0].equalsIgnoreCase("apply")) {
            String kitName = args[1];
            if (!inv.containsKey(kitName)) {
                Message.ERROR.send(p, "Este kit não existe!");
                return false;
            }

            if (args.length == 3) {
                if (args[2].equalsIgnoreCase("all")) {
                    for (Player o : Bukkit.getOnlinePlayers()) {
                        if (p.getUniqueId().equals(o.getUniqueId())) {
                            continue;
                        }
                        if (!Main.players.contains(o.getUniqueId())) {
                            continue;
                        }
                        giveKit(o, kitName);
                    }
                    return false;
                }

                if (args[2].matches("\\d+")) {
                    int area = Integer.valueOf(args[2]);
                    for (Entity n : p.getNearbyEntities(area, area, area)) {
                        if (!(n instanceof Player)) {
                            continue;
                        }
                        Player ne = (Player) n;
                        giveKit(ne, kitName);
                    }
                }

                return false;
            }

        }
        return false;
    }

    public void giveKit(Player p, String kit) {
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.getInventory().setContents(inv.get(kit));
        p.getInventory().setArmorContents(armor.get(kit));
        for (PotionEffect pot : potions.get(kit)) {
            p.addPotionEffect(pot);
        }
    }
}
