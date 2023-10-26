package net.goodcraft.minigames.comandos;

import net.goodcraft.Main;
import net.goodcraft.api.*;
import net.goodcraft.minigames.kits.FreeKits;
import net.goodcraft.minigames.kits.Kit;
import net.goodcraft.minigames.kits.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.Inventory;

public class FreeKitCmd extends Comando {

    public FreeKitCmd(){
        super("freekit", Rank.ADMIN);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length != 2 && args.length != 1){
            Message.ERROR.send(sender, "Use /freekit <add/remove/update/list> [kit]");
            return false;
        }
        Action a = null;
        try {
            a = Action.valueOf(args[0].toUpperCase());
        } catch (Exception e){
            return false;
        }
        Kit kit = (a.hasParameter && args.length == 2 ? KitManager.getKitByString(Utils.getSentence(args, 1)) : null);
        if(a.hasParameter && args.length != 2){
            Message.ERROR.send(sender, "Use /freekit " + a.name().toLowerCase() + " <kit>");
            return false;
        }
        FreeKits fKits = Main.minigame.getFreeKits();

        if(a.hasParameter){
            fKits.modifyFromSQL(kit, (a.equals(Action.ADD)), a.equals(Action.REMOVE));
            Message.INFO.send(sender, "O kit " + kit.toString() + " foi " + a.toString() + " lista de FreeKits!");
            return false;
        }
        if(a.equals(Action.UPDATE)){
            fKits.retrieveAllData();
            Message.INFO.send(sender, "A lista de FreeKits foi atualizada!");
            return false;
        }
        if(a.equals(Action.LIST)){
            Inventory inv = Bukkit.createInventory(p, 54, "FreeKits");
            inv.clear();
            for (int i = 0; i < 9; i++) {
                inv.setItem(i, Item.item(Material.STAINED_GLASS_PANE, 1, " ", 1));
            }
            for(Kit k : fKits.get()){
                inv.addItem(k.getKitSelectorItem());
            }
            p.openInventory(inv);
        }

        return false;
    }

    public enum Action{
        ADD("adicionado a", true),
        REMOVE("removido da", true),
        UPDATE("atualizados", false),
        LIST("listados", false);

        private final String name;
        private final boolean hasParameter;

        Action(String name, boolean hasParameter){
            this.name = name;
            this.hasParameter = hasParameter;
        }

        public String toString(){
            return name;
        }

        public boolean hasParameter(){
            return hasParameter;
        }
    }
}
