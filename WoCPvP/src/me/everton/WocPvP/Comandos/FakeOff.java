package me.everton.WocPvP.Comandos;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;
import org.kitteh.tag.TagAPI;

public class FakeOff
  implements CommandExecutor, Listener
{
  public static String dono = Tag.dono;
  public static String admin = Tag.admin;
  public static String mod = Tag.mod;
  public static String builder = Tag.builder;
  public static String trial = Tag.trial;
  public static String yt = Tag.yt;
  public static String tvip = Tag.tvip;
  public static String pro = Tag.pro;
  public static String vip = Tag.vip;
  public static String normal = Tag.normal;
  public static String nickfake;
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player))
    {
      sender.sendMessage("Comando apenas in-game!");
      return true;
    }
    Player p = (Player)sender;
    if (label.equalsIgnoreCase("tfake"))
    {
      if (p.hasPermission("wochg.fake")) {
        if (args.length == 0)
        {
        	if(Fake.fakes.containsKey(p)){
          Fake.fakes.remove(p);
          Fake.fakesplay.remove(p);
          Fake.fakestag.remove(p);
          
          if (p.hasPermission("*"))
          {
            p.setDisplayName(dono + p.getName());
            p.setPlayerListName(dono + p.getName());
            nickfake = dono + p.getName();
          }
          else if (p.hasPermission("tag.dono"))
          {
            p.setDisplayName(dono + p.getName());
            p.setPlayerListName(dono + p.getName());
            nickfake = dono + p.getName();
          }
          else if (p.hasPermission("tag.admin"))
          {
            p.setDisplayName(admin + p.getName());
            p.setPlayerListName(admin + p.getName());
            nickfake = admin + p.getName();
          }
          else if (p.hasPermission("tag.mod"))
          {
            p.setDisplayName(mod + p.getName());
            p.setPlayerListName(mod + p.getName());
            nickfake = mod + p.getName();
          }
          else if (p.hasPermission("tag.builder"))
          {
            p.setDisplayName(builder + p.getName());
            p.setPlayerListName(builder + p.getName());
            nickfake = builder + p.getName();
          }
          else if (p.hasPermission("tag.trial"))
          {
            p.setDisplayName(trial + p.getName());
            p.setPlayerListName(trial + p.getName());
            nickfake = trial + p.getName();
          }
          else if (p.hasPermission("tag.youtuber"))
          {
            p.setDisplayName(yt + p.getName());
            p.setPlayerListName(yt + p.getName());
            nickfake = yt + p.getName();
          }else if (p.hasPermission("tag.tvip"))
          {
              p.setDisplayName(tvip + p.getName());
              p.setPlayerListName(tvip + p.getName());
              nickfake = tvip + p.getName();
            }
          else if (p.hasPermission("tag.pro"))
          {
            p.setDisplayName(pro + p.getName());
            p.setPlayerListName(pro + p.getName());
            nickfake = pro + p.getName();
          }
          else if (p.hasPermission("tag.vip"))
          {
            p.setDisplayName(vip + p.getName());
            p.setPlayerListName(vip + p.getName());
            nickfake = vip + p.getName();
          }
          else if (p.hasPermission("tag.normal"))
          {
            p.setDisplayName(normal + p.getName());
            p.setPlayerListName(normal + p.getName());
            nickfake = normal + p.getName();
          }
          p.sendMessage(ChatColor.RED + "Nick Resetado!");
          TagAPI.refreshPlayer(p);
        }
        }
        else if (args.length == 1)
        {
          p.sendMessage(ChatColor.RED + 
            "Use /tfake para tirar o fake!");
        }
      }
    }
    else {
      p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
    }
    return false;
  }
  
  @EventHandler
  public void onNameTag(AsyncPlayerReceiveNameTagEvent e)
  {
    if (e.getPlayer().getName().equals(e.getNamedPlayer())) {
      e.setTag(ChatColor.GRAY + e.getPlayer().getName());
    }
  }
}
