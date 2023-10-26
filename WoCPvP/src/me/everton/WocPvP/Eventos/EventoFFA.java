package me.everton.WocPvP.Eventos;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EventoFFA
  implements Listener
{
  public static void itemsFFA(Player p)
  {
    p.getInventory().clear();
    p.getInventory().setItem(13, new ItemStack(Material.BOWL, 64));
    p.getInventory().setItem(14, new ItemStack(Material.RED_MUSHROOM, 64));
    p.getInventory()
      .setItem(15, new ItemStack(Material.BROWN_MUSHROOM, 64));
    ItemStack espada = new ItemStack(Material.DIAMOND_SWORD);
    ItemMeta emeta = espada.getItemMeta();
    emeta.setDisplayName("§6Espada FFA");
    espada.setItemMeta(emeta);
    espada.addEnchantment(Enchantment.DAMAGE_ALL, 1);
    p.getInventory().setItem(0, espada);
    p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
    p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
    p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
    p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
    for (int i = 0; i < 32; i++)
    {
      ItemStack sopa = new ItemStack(Material.MUSHROOM_SOUP);
      ItemMeta smeta = sopa.getItemMeta();
      smeta.setDisplayName("§2Sopa de Cogumelos");
      sopa.setItemMeta(smeta);
      p.getInventory().addItem(new ItemStack[] { sopa });
    }
  }
}
