package me.everton.WocPvP.Eventos;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class EventoCmd
  implements CommandExecutor, Listener
{
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (!(sender instanceof Player))
    {
      sender.sendMessage("Comando apenas in-game!");
      return true;
    }
    Player p = (Player)sender;
    if (cmd.getName().equalsIgnoreCase("evento")) {
      if (args.length == 0)
      {
        EManager.entrarEvento(p);
      }
      else if (args.length >= 1)
      {
        if ((p.hasPermission("wocpvp.eventos")) && 
          (!args[0].equalsIgnoreCase("sair")) && 
          (!args[0].equalsIgnoreCase("stop")) && 
          (!args[0].equalsIgnoreCase("start")) && 
          (!args[0].equalsIgnoreCase("spec")) && 
          (!args[0].equalsIgnoreCase("tp")) && 
          (!args[0].equalsIgnoreCase("setspawn")) && 
          (!args[0].equalsIgnoreCase("setlobby")) && 
          (!args[0].equalsIgnoreCase("setspec"))) {
          if (args[0].equalsIgnoreCase("ffa"))
          {
            if (args.length == 3)
            {
              if ((args[1].equalsIgnoreCase("kit")) || 
                (args[1].equalsIgnoreCase("vip")))
              {
                if (args[1].equalsIgnoreCase("kit"))
                {
                  EManager.premio = args[1];
                  EManager.tipo = args[2];
                  EManager.iniciarEvento(p, args[0]);
                }
                else if ((args[2].equalsIgnoreCase("vip")) || 
                  (args[2].equalsIgnoreCase("vipplus")))
                {
                  EManager.premio = args[1];
                  EManager.tipo = args[2];
                  EManager.iniciarEvento(p, args[0]);
                }
                else
                {
                  p.sendMessage("ßcUse: ß4/evento (evento) (vip/kit) (tipo de vip (normal/plus) ou kit(ex: anchor/stomper))");
                }
              }
              else {
                p.sendMessage("ßcUse: ß4/evento (evento) (vip/kit) (tipo de vip (normal/plus) ou kit(ex: anchor/stomper))");
              }
            }
            else {
              p.sendMessage("ßcUse: ß4/evento (evento) (vip/kit) (tipo de vip (normal/plus) ou kit(ex: anchor/stomper))");
            }
          }
          else if (args[0].equalsIgnoreCase("mdr"))
          {
            if (args.length == 3)
            {
              if ((args[1].equalsIgnoreCase("kit")) || 
                (args[1].equalsIgnoreCase("vip")))
              {
                if (args[1].equalsIgnoreCase("kit"))
                {
                  EManager.premio = args[1];
                  EManager.tipo = args[2];
                  EManager.iniciarEvento(p, args[0]);
                }
                else if ((args[2].equalsIgnoreCase("vip")) || 
                  (args[2].equalsIgnoreCase("vipplus")))
                {
                  EManager.premio = args[1];
                  EManager.tipo = args[2];
                  EManager.iniciarEvento(p, args[0]);
                }
                else
                {
                  p.sendMessage("ßcUse: ß4/evento (evento) (vip/kit) (tipo de vip (normal/plus) ou kit(ex: anchor/stomper))");
                }
              }
              else {
                p.sendMessage("ßcUse: ß4/evento (evento) (vip/kit) (tipo de vip (normal/plus) ou kit(ex: anchor/stomper))");
              }
            }
            else {
              p.sendMessage("ßcUse: ß4/evento (evento) (vip/kit) (tipo de vip (normal/plus) ou kit(ex: anchor/stomper))");
            }
          }
          else {
            p.sendMessage("ß6Este evento n√£o existe!");
          }
        }
        if (args[0].equalsIgnoreCase("sair")) {
          EManager.sairEvento(p, Boolean.valueOf(false));
        }
        if (args[0].equalsIgnoreCase("spec")) {
          if (p.hasPermission("wocpvp.eventospec")) {
            EManager.specEvento(p);
          } else {
            p.sendMessage("ß4Voc√™ n√£o tem permiss√£o!");
          }
        }
        if (p.hasPermission("wocpvp.eventos"))
        {
          if (args[0].equalsIgnoreCase("stop")) {
            EManager.pararEvento(p);
          } else if (args[0].equalsIgnoreCase("start")) {
            EManager.teleportarPEvento(true);
          }
          if (args[0].equalsIgnoreCase("tp")) {
            EManager.tp(p);
          }
        }
        else
        {
          p.sendMessage("ß4AVoc√™ n√£o tem permiss√£o!");
        }
      }
    }
    return false;
  }
  
  @EventHandler
  public void aoMover(PlayerMoveEvent e)
  {
    Player p = e.getPlayer();
    if ((EManager.pe.contains(p)) && 
      (EManager.pe.size() == 1) && (EManager.ocorrendoe.booleanValue())) {
      EManager.terminoEvento();
    }
    if ((EManager.pespec.contains(p)) && 
      (e.getFrom().getY() != e.getTo().getY()))
    {
      p.sendMessage("ßcVocÍ nao pode subir nem descer!");
      p.setAllowFlight(true);
      p.setFlying(true);
      Location loc = e.getFrom();
      p.teleport(loc);
    }
  }
}
