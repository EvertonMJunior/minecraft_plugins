package me.everton.WocPvP.Comandos;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class Fakes
  implements CommandExecutor, Listener
{
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player))
    {
      sender.sendMessage("Comando apenas in-game!");
      return true;
    }
    if (label.equalsIgnoreCase("fakes"))
    {
      Player p = (Player)sender;
      if (p.hasPermission("wochg.fake"))
      {
        if (args.length >= 0) {
          if (Fake.fakes.isEmpty())
          {
            p.sendMessage("§2Ninguém está usando um nick §cFAKE §2no momento!");
          }
          else if (!Fake.fakes.isEmpty())
          {
            p.sendMessage("§c********** §4§lFAKES §c**********");
            p.sendMessage("§3 ");
            
            String[] fakes = Fake.fakes.values().toString().split(",");
            String[] ps = Fake.fakesplay.values().toString().split(",");
            String[] tgs = Fake.fakestag.values().toString().split(",");
            for (int i = 0; i < fakes.length; i++) {
              p.sendMessage("§3Nick Fake: §b" + fakes[i].replace("[", "").replace("]", "").replace("§", "") + " §7| §3Player: §b" + ps[i].replace("[", "").replace("]", "").replace("§", "") + " §7| §3Tag: §b" + tgs[i].replace("[", "").replace("]", ""));
            }
            p.sendMessage("§3 ");
            p.sendMessage("§c********** §4§lFAKES §c**********");
          }
        }
      }
      else {
        p.sendMessage(ChatColor.DARK_RED + "§7[§c!§7] Sem permissao!");
      }
    }
    return false;
  }
}
