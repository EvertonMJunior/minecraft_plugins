package com.goodcraft.bungee.servers;

import com.goodcraft.bungee.Main;
import dev.wolveringer.BungeeUtil.Material;
import dev.wolveringer.BungeeUtil.Player;
import dev.wolveringer.BungeeUtil.item.ItemStack;
import dev.wolveringer.BungeeUtil.item.itemmeta.ItemMeta;
import dev.wolveringer.api.inventory.Inventory;
import net.md_5.bungee.api.config.ServerInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LobbyGUI implements Runnable{
    public static Inventory inv;

    public static void initialize(){
        inv = new Inventory(27, "Lobbys");
        inv.setClickable(false);
    }

    public static void open(Player p){
        p.openInventory(inv);
    }

    @Override
    public void run() {
        List<ServerInfo> lobbys = new ArrayList<>();

        for(ServerInfo sInfo : Main.getPlugin().getProxy().getServers().values()){
            if(!sInfo.getName().startsWith("lobby")) continue;
            lobbys.add(sInfo);
        }

        for(int i = 0; i < lobbys.size(); i++){
            final ServerInfo sInfo = lobbys.get(i);
            ItemStack iS = inv.getItem(i + 10) == null ? new ItemStack(Material.SLIME_BALL) {
                @Override
                public void click(Click e) {}
            } : inv.getItem(i + 10);

            ItemMeta iM = iS.getItemMeta();
            iM.setDisplayName("§eLobby " + sInfo.getName().substring(5));
            int count = sInfo.getPlayers().size();
            iM.setLore(Arrays.asList("§aHá " + count + " jogador" + (count > 1 || count == 0 ? "es" : "") + " nesse Lobby."));

            inv.setItem(i + 10, iS);
            inv.updateInventory();
        }
    }
}
