package me.everton.WocPvP.Eventos;

import java.util.HashMap;
import java.util.Random;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class EventoRDM
  implements Listener
{
  public static HashMap<Player, Player> duelando = new HashMap<>();
  
  public static void duelar()
  {
    Random r = new Random();
    Player p1 = (Player)EManager.pe.get(r.nextInt(EManager.pe.size()));
    Player p2 = (Player)EManager.pe.get(r.nextInt(EManager.pe.size() - 1));
    
    p1.sendMessage(p1.getName() + p2.getName());
  }
}
