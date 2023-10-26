package me.everton.WocPvP.Eventos;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EventoMDR
  implements Listener
{
  public static ArrayList<Player> mdr = new ArrayList<>();
  
  public static void itensMDR(Player p, boolean mae)
  {
    for (Player on : Bukkit.getOnlinePlayers()) {
      p.showPlayer(on);
    }
    if (!mae)
    {
      p.getInventory().clear();
      p.getInventory().setArmorContents(null);
    }
    else if (mae)
    {
      ItemStack espada = new ItemStack(Material.DIAMOND_SWORD);
      ItemMeta emeta = espada.getItemMeta();
      emeta.setDisplayName("§6Espada MDR");
      espada.setItemMeta(emeta);
      espada.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 8);
      espada.addEnchantment(Enchantment.FIRE_ASPECT, 2);
      p.getInventory().setItem(0, espada);
      
      ItemStack capacete = new ItemStack(Material.DIAMOND_HELMET);
      ItemMeta cmeta = capacete.getItemMeta();
      cmeta.setDisplayName("§bCapacete MDR");
      capacete.setItemMeta(cmeta);
      capacete.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
      capacete.addEnchantment(Enchantment.DURABILITY, 2);
      p.getInventory().setHelmet(capacete);
      
      ItemStack peitoral = new ItemStack(Material.DIAMOND_CHESTPLATE);
      ItemMeta pmeta = peitoral.getItemMeta();
      pmeta.setDisplayName("§bPeitoral MDR");
      peitoral.setItemMeta(pmeta);
      peitoral.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
      peitoral.addEnchantment(Enchantment.DURABILITY, 2);
      p.getInventory().setChestplate(peitoral);
      
      ItemStack calca = new ItemStack(Material.DIAMOND_LEGGINGS);
      ItemMeta lmeta = calca.getItemMeta();
      lmeta.setDisplayName("§bcalca MDR");
      calca.setItemMeta(lmeta);
      calca.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
      calca.addEnchantment(Enchantment.DURABILITY, 2);
      p.getInventory().setLeggings(calca);
      
      ItemStack bota = new ItemStack(Material.DIAMOND_BOOTS);
      ItemMeta bmeta = bota.getItemMeta();
      bmeta.setDisplayName("§bBota MDR");
      bota.setItemMeta(bmeta);
      bota.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
      bota.addEnchantment(Enchantment.DURABILITY, 2);
      p.getInventory().setBoots(bota);
      
      p.sendMessage("§6Você foi escolhido aleatoriamente para ser a §lMãe da Rua§r§6!");
    }
  }
}
