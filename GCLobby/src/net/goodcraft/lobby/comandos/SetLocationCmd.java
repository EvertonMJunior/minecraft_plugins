package net.goodcraft.lobby.comandos;

import net.goodcraft.GameMode;
import net.goodcraft.api.Comando;
import net.goodcraft.api.Rank;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SetLocationCmd extends Comando {

    public SetLocationCmd() {
        super("setlocation", Rank.DEVELOPER, new String[]{"setloc", "sloc"});
        setInGameOnly(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Inventory inv = Bukkit.createInventory(p, 18, "Setar localização");
        for (GameMode l : GameMode.values()) {
            ItemStack item = l.getItem().clone();
            ItemMeta im = item.getItemMeta();
            im.setDisplayName("§f§l" + l.getName());
            item.setItemMeta(im);
            inv.addItem(item);
        }

        p.openInventory(inv);
        return false;
    }

}
