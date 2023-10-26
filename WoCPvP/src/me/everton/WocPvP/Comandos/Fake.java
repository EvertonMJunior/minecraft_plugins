package me.everton.WocPvP.Comandos;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.kitteh.tag.AsyncPlayerReceiveNameTagEvent;
import org.kitteh.tag.TagAPI;

public class Fake
  implements CommandExecutor, Listener
{
  public static String nickfake;
  public static HashMap<Player, String> fakes = new HashMap<>();
  public static HashMap<Player, String> fakesplay = new HashMap<>();
  public static HashMap<Player, String> fakestag = new HashMap<>();
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player))
    {
      sender.sendMessage("Comando apenas in-game!");
      return true;
    }
    if (label.equalsIgnoreCase("fake"))
    {
      Player p = (Player)sender;
      if (p.hasPermission("wochg.fake"))
      {
        if (args.length == 0)
        {
          if (fakes.containsKey(p)) {
            p.sendMessage("§6Você esta usando o fake: §b§l§o" + fakes.get(p) + "§r§6 com a tag §o§l" + fakestag.get(p));
          }
          p.sendMessage(ChatColor.RED + 
            "Use /fake (nick) (tag), /tfake para tirar o fake e /fakes para listar os fakes!");
        }
        else if (args.length == 1)
        {
          if (args[0] != "off")
          {
            String nickdigitado = args[0];
            if (nickdigitado.length() >= 16)
            {
              p.sendMessage(ChatColor.RED + "Nick nao permitido!");
            }
            else if (nickdigitado.length() <= 16)
            {
              if ((fakes.containsKey(p)) || 
                (fakesplay.containsKey(p)) || 
                (fakestag.containsKey(p)))
              {
                fakes.remove(p);
                fakesplay.remove(p);
                fakesplay.remove(p);
              }
              nickfake = Tag.normal + nickdigitado;
              if (fakes.containsKey(p))
              {
                fakes.remove(p);
                fakesplay.remove(p);
                fakestag.remove(p);
              }
              fakes.put(p, args[0]);
              fakestag.put(p, Tag.normal + "Normal");
              fakesplay.put(p, p.getName());
              p.setDisplayName(nickfake);
              p.setPlayerListName(nickfake);
              p.sendMessage(ChatColor.RED + "Agora seu nick é: " + 
                nickfake);
              TagAPI.refreshPlayer(p);
            }
          }
        }
        else if ((args.length == 2) && 
          (args[0] != "off"))
        {
          String nickdigitado = args[0];
          String tag = args[1];
          if (nickdigitado.length() >= 16)
          {
            p.sendMessage(ChatColor.RED + "Nick nao permitido!");
          }
          else if (nickdigitado.length() <= 16)
          {
            if ((fakes.containsKey(p)) || 
              (fakesplay.containsKey(p)) || 
              (fakestag.containsKey(p)))
            {
              fakes.remove(p);
              fakesplay.remove(p);
              fakesplay.remove(p);
            }
            if (fakes.containsKey(p))
            {
              fakes.remove(p);
              fakesplay.remove(p);
              fakestag.remove(p);
            }
            fakes.put(p, args[0]);
            fakesplay.put(p, p.getName());
            if (tag.equalsIgnoreCase("dono"))
            {
              if (sender.hasPermission("tag.dono"))
              {
                nickfake = Tag.dono + nickdigitado;
                p.setDisplayName(nickfake);
                p.setPlayerListName(nickfake);
                p.sendMessage(ChatColor.RED + 
                  "Agora seu nick e: " + nickfake);
                TagAPI.refreshPlayer(p);
                fakestag.put(p, Tag.dono+"Dono");
              }
              else
              {
                p.sendMessage(ChatColor.DARK_RED + 
                  "§7[§c!§7] Sem permissao!");
              }
            }
            else if (tag.equalsIgnoreCase("admin"))
            {
              if ((sender.hasPermission("tag.admin")) || 
                (sender.hasPermission("tag.dono")))
              {
                nickfake = Tag.admin + nickdigitado;
                p.setDisplayName(nickfake);
                p.setPlayerListName(nickfake);
                p.sendMessage(ChatColor.RED + 
                  "Agora seu nick e: " + nickfake);
                TagAPI.refreshPlayer(p);
                fakestag.put(p, Tag.admin+"Admin");
              }
              else
              {
                p.sendMessage(ChatColor.DARK_RED + 
                  "§7[§c!§7] Sem permissao!");
              }
            }
            else if (tag.equalsIgnoreCase("mod"))
            {
              if ((sender.hasPermission("tag.mod")) || 
                (sender.hasPermission("tag.admin")) || 
                (sender.hasPermission("tag.dono")))
              {
                nickfake = Tag.mod + nickdigitado;
                p.setDisplayName(nickfake);
                p.setPlayerListName(nickfake);
                p.sendMessage(ChatColor.RED + 
                  "Agora seu nick e: " + nickfake);
                TagAPI.refreshPlayer(p);
                fakestag.put(p, Tag.mod+"Mod");
              }
              else
              {
                p.sendMessage(ChatColor.DARK_RED + 
                  "§7[§c!§7] Sem permissao!");
              }
            }
            else if (tag.equalsIgnoreCase("builder"))
            {
              if ((sender.hasPermission("tag.builder")) || 
                (sender.hasPermission("tag.mod")) || 
                (sender.hasPermission("tag.admin")) || 
                (sender.hasPermission("tag.dono")))
              {
                nickfake = Tag.builder + nickdigitado;
                p.setDisplayName(nickfake);
                p.setPlayerListName(nickfake);
                p.sendMessage(ChatColor.RED + 
                  "Agora seu nick e: " + nickfake);
                TagAPI.refreshPlayer(p);
                fakestag.put(p, Tag.builder+"Builder");
              }
              else
              {
                p.sendMessage(ChatColor.DARK_RED + 
                  "§7[§c!§7] Sem permissao!");
              }
            }
            else if (tag.equalsIgnoreCase("trial"))
            {
              if ((sender.hasPermission("tag.trial")) || 
                (sender.hasPermission("tag.builder")) || 
                (sender.hasPermission("tag.mod")) || 
                (sender.hasPermission("tag.admin")) || 
                (sender.hasPermission("tag.dono")))
              {
                nickfake = Tag.trial + nickdigitado;
                p.setDisplayName(nickfake);
                p.setPlayerListName(nickfake);
                p.sendMessage(ChatColor.RED + 
                  "Agora seu nick e: " + nickfake);
                TagAPI.refreshPlayer(p);
                fakestag.put(p, Tag.trial+"Trial");
              }
              else
              {
                p.sendMessage(ChatColor.DARK_RED + 
                  "§7[§c!§7] Sem permissao!");
              }
            }
            else if ((tag.equalsIgnoreCase("youtuber")) || 
              (tag.equalsIgnoreCase("yt")))
            {
              if ((sender.hasPermission("tag.youtuber")) || 
                (sender.hasPermission("tag.builder")) || 
                (sender.hasPermission("tag.trial")) || 
                (sender.hasPermission("tag.mod")) || 
                (sender.hasPermission("tag.admin")) || 
                (sender.hasPermission("tag.dono")))
              {
                nickfake = Tag.yt + nickdigitado;
                p.setDisplayName(nickfake);
                p.setPlayerListName(nickfake);
                p.sendMessage(ChatColor.RED + 
                  "Agora seu nick e: " + nickfake);
                TagAPI.refreshPlayer(p);
                fakestag.put(p, Tag.yt+"Youtuber");
              }
              else
              {
                p.sendMessage(ChatColor.DARK_RED + 
                  "§7[§c!§7] Sem permissao!");
              }
            }else if ((tag.equalsIgnoreCase("tvip")))
            {
                if ((sender.hasPermission("tag.tvip")) ||
                  (sender.hasPermission("tag.pro")) || 
                  (sender.hasPermission("tag.builder")) || 
                  (sender.hasPermission("tag.youtuber")) || 
                  (sender.hasPermission("tag.trial")) || 
                  (sender.hasPermission("tag.mod")) || 
                  (sender.hasPermission("tag.admin")) || 
                  (sender.hasPermission("tag.dono")))
                {
                  nickfake = Tag.tvip + nickdigitado;
                  p.setDisplayName(nickfake);
                  p.setPlayerListName(nickfake);
                  p.sendMessage(ChatColor.RED + 
                    "Agora seu nick e: " + nickfake);
                  TagAPI.refreshPlayer(p);
                  fakestag.put(p, Tag.tvip + "Pro");
                }
                else
                {
                  p.sendMessage(ChatColor.DARK_RED + 
                    "§7[§c!§7] Sem permissao!");
                }
              }
            else if ((tag.equalsIgnoreCase("pro")))
            {
              if ((sender.hasPermission("tag.tvip")) ||
            	(sender.hasPermission("tag.pro")) || 
                (sender.hasPermission("tag.builder")) || 
                (sender.hasPermission("tag.youtuber")) || 
                (sender.hasPermission("tag.trial")) || 
                (sender.hasPermission("tag.mod")) || 
                (sender.hasPermission("tag.admin")) || 
                (sender.hasPermission("tag.dono")))
              {
                nickfake = Tag.pro + nickdigitado;
                p.setDisplayName(nickfake);
                p.setPlayerListName(nickfake);
                p.sendMessage(ChatColor.RED + 
                  "Agora seu nick e: " + nickfake);
                TagAPI.refreshPlayer(p);
                fakestag.put(p, Tag.pro + "Pro");
              }
              else
              {
                p.sendMessage(ChatColor.DARK_RED + 
                  "§7[§c!§7] Sem permissao!");
              }
            }
            else if (tag.equalsIgnoreCase("vip"))
            {
              if ((sender.hasPermission("tag.tvip")) ||
            	(sender.hasPermission("tag.vip")) || 
                (sender.hasPermission("tag.builder")) || 
                (sender.hasPermission("tag.vip+")) || 
                (sender.hasPermission("tag.youtuber")) || 
                (sender.hasPermission("tag.trial")) || 
                (sender.hasPermission("tag.mod")) || 
                (sender.hasPermission("tag.admin")) || 
                (sender.hasPermission("tag.dono")))
              {
                nickfake = Tag.vip + nickdigitado;
                p.setDisplayName(nickfake);
                p.setPlayerListName(nickfake);
                p.sendMessage(ChatColor.RED + 
                  "Agora seu nick e: " + nickfake);
                TagAPI.refreshPlayer(p);
                fakestag.put(p, Tag.vip+"Vip");
              }
              else
              {
                p.sendMessage(ChatColor.DARK_RED + 
                  "§7[§c!§7] Sem permissao!");
              }
            }
            else if (tag.equalsIgnoreCase("normal"))
            {
              nickfake = Tag.normal + nickdigitado;
              p.setDisplayName(nickfake);
              p.setPlayerListName(nickfake);
              p.sendMessage(ChatColor.RED + 
                "Agora seu nick e: " + nickfake);
              TagAPI.refreshPlayer(p);
              fakestag.put(p, Tag.normal+"Normal");
            }
            else
            {
              p.sendMessage("§6A tag " + tag.toUpperCase() + " nao existe!");
            }
          }
        }
      }
      else {
        p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
      }
    }
    return false;
  }
  
  @EventHandler
  public void onNameTag(AsyncPlayerReceiveNameTagEvent e)
  {
    if (e.getPlayer().getName().equals(e.getNamedPlayer())) {
      e.setTag(nickfake);
    }
  }
}
