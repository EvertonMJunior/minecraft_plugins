package me.everton.epvp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Placas
  implements Listener
{
  @EventHandler
  public void criarPlaca(SignChangeEvent e)
  {
    Player p = e.getPlayer();
    for(int i = 0; i < 4; i++) {
    	e.setLine(i, e.getLine(i).replace("&", "§"));
    }
    if (e.getLine(0).equalsIgnoreCase("sopa"))
    {
      if (p.hasPermission("pvp.placas"))
      {
        e.setLine(0, "§3§l["+Main.nomeservidor+"]");
        e.setLine(1, "§4Sopas");
      }
      else
      {
        p.sendMessage("§4§7[§c!§7] Sem permissao!");
        e.setCancelled(true);
        e.getBlock().breakNaturally();
      }
    }
    else if (e.getLine(0).equalsIgnoreCase("recraft"))
    {
      if (p.hasPermission("pvp.placas"))
      {
        e.setLine(0, "§3§l["+Main.nomeservidor+"]");
        e.setLine(1, "§4Recraft");
      }
      else
      {
        p.sendMessage("§4§7[§c!§7] Sem permissao!");
        e.setCancelled(true);
        e.getBlock().breakNaturally();
      }
    }
    else if (e.getLine(0).equalsIgnoreCase("reparar")) {
      if (p.hasPermission("pvp.placas"))
      {
        e.setLine(0, "§3§l["+Main.nomeservidor+"]");
        e.setLine(1, "§4Reparar");
      }
      else
      {
        p.sendMessage("§4§7[§c!§7] Sem permissao!");
        e.setCancelled(true);
        e.getBlock().breakNaturally();
      }
    } else if(e.getLine(0).equalsIgnoreCase("lava")){
    	e.setLine(0, "§6§lParabens!");
    	e.setLine(1, "§4Você venceu o");
    	e.setLine(2, "§4LavaChallenge");
    	e.setLine(3, "§6§lClique aqui");
    }				  ///////////////
  }
  
  @EventHandler
  public void clicarPlaca(PlayerInteractEvent e) throws Exception
  {
    Player p = e.getPlayer();
    
    ItemStack sopa = new ItemStack(Material.MUSHROOM_SOUP);
    ItemMeta smeta = sopa.getItemMeta();
    smeta.setDisplayName("§2Sopa de Cogumelos");
    sopa.setItemMeta(smeta);
    
    ItemStack cr = new ItemStack(Material.RED_MUSHROOM, 64);
    ItemMeta crm = cr.getItemMeta();
    crm.setDisplayName("§cCogumelo Vermelho");
    cr.setItemMeta(crm);
    
    ItemStack cb = new ItemStack(Material.BROWN_MUSHROOM, 64);
    ItemMeta cbm = cb.getItemMeta();
    cbm.setDisplayName("§7Cogumelo Marrom");
    cb.setItemMeta(cbm);
    
    ItemStack t = new ItemStack(Material.BOWL, 64);
    ItemMeta tm = t.getItemMeta();
    tm.setDisplayName("§6Tigela");
    t.setItemMeta(tm);
    if ((e.getAction() == Action.RIGHT_CLICK_BLOCK) && (
      (e.getClickedBlock().getType() == Material.SIGN_POST) || 
      (e.getClickedBlock().getType() == Material.WALL_SIGN)))
    {
      Sign s = (Sign)e.getClickedBlock().getState();
      if ((s.getLine(0).equalsIgnoreCase("§3§l["+Main.nomeservidor+"]")) && 
        (s.getLine(1).equalsIgnoreCase("§4Sopas")))
      {
        Inventory sopas = Bukkit.createInventory(p, 54, ChatColor.BLACK + 
          "Sopas");
        for (int i = 0; i < sopas.getSize(); i++) {
          sopas.setItem(i, new ItemStack(sopa));
        }
        p.openInventory(sopas);
      }
      else
      {
        if ((s.getLine(0).equalsIgnoreCase("§3§l["+Main.nomeservidor+"]")) && 
          (s.getLine(1).equalsIgnoreCase("§4Recraft")))
        {
          Inventory recraft = Bukkit.createInventory(p, 54, ChatColor.BLACK + 
            "Recraft");
          for (int i = 0; i < 9; i++) {
            recraft.setItem(i, cr);
          }
          for (int i = 9; i < 18; i++) {
            recraft.setItem(i, cb);
          }
          for (int i = 18; i < 27; i++) {
            recraft.setItem(i, t);
          }
          for (int i = 27; i < 36; i++) {
            recraft.setItem(i, cr);
          }
          for (int i = 36; i < 45; i++) {
            recraft.setItem(i, cb);
          }
          for (int i = 45; i < 54; i++) {
            recraft.setItem(i, t);
          }
          p.openInventory(recraft);
        }
        else if ((s.getLine(0).equalsIgnoreCase("§3§l["+Main.nomeservidor+"]")) && 
          (s.getLine(1).equalsIgnoreCase("§4Reparar")))
        {
          for (ItemStack is : p.getInventory().getContents()) {
            try
            {
              is.setDurability((short)0);
            }
            catch (NullPointerException localNullPointerException) {}
          }
          for (ItemStack is : p.getEquipment().getArmorContents()) {
            try
            {
              is.setDurability((short)0);
            }
            catch (NullPointerException localNullPointerException1) {}
          }
          p.sendMessage("§2Seus itens foram reparados!");
        } else if(s.getLine(0).equalsIgnoreCase("§6§lParabens!")){
        	p.getInventory().clear();
        	p.getInventory().setArmorContents(null);
        	p.getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
        	p.getInventory().setItem(0, new ItemStack(Material.STONE_SWORD));
        	for(int i = 0; i < 36; i++){
        		p.getInventory().addItem(sopa);
        	}
        	p.getInventory().setItem(14, t);
        	p.getInventory().setItem(15, cr);
        	p.getInventory().setItem(16, cb);
        	p.teleport(Main.loc("lavachallenge", p));
        	p.sendMessage("§7[§c!§7] Você foi venceu um dos LavaChallenges!");
        	MoneyManager.addMoney(p.getName(), 100);
        }
      }
    }
  }
}
