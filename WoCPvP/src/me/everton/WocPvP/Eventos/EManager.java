package me.everton.WocPvP.Eventos;

import java.util.ArrayList;

import me.everton.WocPvP.Main;
import me.everton.WocPvP.Comandos.Tag;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

public class EManager
  extends JavaPlugin
{
  public static ArrayList<Player> pe = new ArrayList<>();
  public static ArrayList<Player> pespec = new ArrayList<>();
  public static String ocorrendo = null;
  public static String premio = null;
  public static String tipo = null;
  public static Boolean ocorrendoe = Boolean.valueOf(false);
  public static ArrayList<Integer> cooldown = new ArrayList<>();
  public static ArrayList<Integer> cooldownt = new ArrayList<>();
  
  public static void iniciarEvento(Player p, String evento)
  {
    if ((ocorrendo == null) && (!ocorrendoe.booleanValue()))
    {
      ocorrendo = evento;
      me.everton.WocPvP.Main.log(p.getName() + " iniciou o evento " + ocorrendo + " valendo " + premio + " " + tipo);
      if (!cooldown.isEmpty())
      {
        me.everton.WocPvP.Main.sh.cancelTask(((Integer)cooldown.get(0)).intValue());
        cooldown.remove(0);
      }
      cooldownEvento();
    }
    else if (ocorrendo != null)
    {
      p.sendMessage("§bO evento §6§l" + ocorrendo.toUpperCase() + 
        " §r§bjá está ocorrendo no momento!");
    }
  }
  
  public static void tp(Player p)
  {
    if (ocorrendo != null)
    {
      World mundoffa = Bukkit.getServer().getWorld("Yuri");
      World mundomdr = Bukkit.getServer().getWorld("mlg");
      
      me.everton.WocPvP.Main.log(p.getName() + " se teleportou ao evento " + ocorrendo);
      if (ocorrendo.equalsIgnoreCase("ffa"))
      {
        Location ffa = new Location(mundoffa, 208.0D, 80.0D, 252.0D);
        ffa.setPitch(0.0F);
        ffa.setYaw(180.0F);
        p.teleport(ffa);
      }
      else if (ocorrendo.equalsIgnoreCase("mdr"))
      {
        Location mdr = new Location(mundomdr, 208.0D, 63.0D, 263.0D);
        mdr.setPitch(0.0F);
        mdr.setYaw(180.0F);
        p.teleport(mdr);
      }
    }
    else
    {
      p.sendMessage("§bNenhum evento está ocorrendo no momento!");
    }
  }
  
  public static void premio(Player p)
  {
    if (premio.equalsIgnoreCase("vip"))
    {
      if (tipo.equalsIgnoreCase("normal"))
      {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), 
          "pex user " + p.getName() + " group set vip");
        for (Player on : Bukkit.getServer().getOnlinePlayers()) {
          on.sendMessage("§6[WoCPvP] §b" + p.getName() + 
            " ativou um Vip");
        }
        Tag.setarTag("vip", p, false);
      }
      else if (tipo.equalsIgnoreCase("plus"))
      {
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), 
          "pex user " + p.getName() + " group set pro");
        for (Player on : Bukkit.getServer().getOnlinePlayers()) {
          on.sendMessage("§6[WoCPvP] §b" + p.getName() + 
            " ativou um Pro");
        }
        Tag.setarTag("pro", p, false);
      }
    }
    else if (premio.equalsIgnoreCase("kit"))
    {
      Bukkit.getServer().dispatchCommand(
        Bukkit.getConsoleSender(), 
        "pex user " + p.getName() + " add kit." + 
        tipo.toLowerCase());
      for (Player on : Bukkit.getServer().getOnlinePlayers()) {
        on.sendMessage("§6[WoCPvP] §b" + p.getName() + 
          " ativou um Kit " + tipo.toUpperCase());
      }
    }
    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), 
      "pex reload");
  }
  
  public static void cooldownEvento()
  {
    for (final Player on : Bukkit.getServer().getOnlinePlayers()) {
      if ((!ocorrendoe.booleanValue()) && (ocorrendo != null))
      {
        int coolDown = me.everton.WocPvP.Main.sh.scheduleSyncRepeatingTask(me.everton.WocPvP.Main.plugin, new Runnable()
        {
          int tempo = 10;
          
          public void run()
          {
            if (this.tempo > 0)
            {
              if (this.tempo == 10) {
                on.sendMessage("§bEvento §6§l" + EManager.ocorrendo.toUpperCase() + " §biniciando em 5 minutos!");
              } else if (this.tempo == 9) {
                on.sendMessage("§bEvento §6§l" + EManager.ocorrendo.toUpperCase() + " §biniciando em 4 minutos e 30 segundos!");
              } else if (this.tempo == 8) {
                on.sendMessage("§bEvento §6§l" + EManager.ocorrendo.toUpperCase() + " §biniciando em 4 minutos!");
              } else if (this.tempo == 7) {
                on.sendMessage("§bEvento §6§l" + EManager.ocorrendo.toUpperCase() + " §biniciando em 3 minutos e 30 segundos!");
              } else if (this.tempo == 6) {
                on.sendMessage("§bEvento §6§l" + EManager.ocorrendo.toUpperCase() + " §biniciando em 3 minutos!");
              } else if (this.tempo == 5) {
                on.sendMessage("§bEvento §6§l" + EManager.ocorrendo.toUpperCase() + " §biniciando em 2 minutos e 30 segundos!");
              } else if (this.tempo == 4) {
                on.sendMessage("§bEvento §6§l" + EManager.ocorrendo.toUpperCase() + " §biniciando em 2 minutos!");
              } else if (this.tempo == 3) {
                on.sendMessage("§bEvento §6§l" + EManager.ocorrendo.toUpperCase() + " §biniciando em 1 minuto e 30 segundos!");
              } else if (this.tempo == 9) {
                on.sendMessage("§bEvento §6§l" + EManager.ocorrendo.toUpperCase() + " §biniciando em 1 minuto!");
              }
              on.sendMessage("§bValendo " + 
                EManager.premio.toUpperCase() + " " + 
                EManager.tipo.toUpperCase());
              on.sendMessage("§bPara entrar no evento, use /evento!");
              tempo--;
            }
            if (this.tempo == 0)
            {
              me.everton.WocPvP.Main.sh.cancelTask(((Integer)EManager.cooldown.get(0)).intValue());
              on.sendMessage("§bO evento §6§l" + EManager.ocorrendo.toUpperCase() + " iniciou!");
              EManager.cooldown.remove(0);
            }
          }
        }, 0L, 600L);
        cooldown.add(Integer.valueOf(coolDown));
      }
    }
  }
  
  public static void pararEvento(Player p)
  {
    if (ocorrendo != null)
    {
      p.sendMessage("§6O evento " + ocorrendo.toUpperCase() + 
        " foi forçado a parar com sucesso!");
      me.everton.WocPvP.Main.log(p.getName() + " parou o evento " + ocorrendo + " valendo " + premio + " " + tipo);
      ocorrendo = null;
      ocorrendoe = Boolean.valueOf(false);
      me.everton.WocPvP.Main.sh.cancelTask(((Integer)cooldown.get(0)).intValue());
      cooldown.remove(0);
      for (Player eventop : pe)
      {
        forcarSpawn(eventop);
        for (Player specs : pespec) {
          eventop.showPlayer(specs);
        }
        eventop.sendMessage("§bO evento foi forçado a parar! Você foi teleportado para o spawn!");
      }
      pe.clear();
      pespec.clear();
    }
    else if (ocorrendo == null)
    {
      p.sendMessage("§bNenhum evento está ocorrendo no momento!");
    }
  }
  
  public static void specEvento(Player p)
  {
    World mundoffa = Bukkit.getServer().getWorld("Yuri");
    if (ocorrendo != null)
    {
      if (!pespec.contains(p))
      {
        pespec.add(p);
        p.sendMessage("§bAgora você está espectando o evento §l" + 
          ocorrendo.toUpperCase());
        p.setGameMode(GameMode.CREATIVE);
        me.everton.WocPvP.Main.log(p.getName() + " entrou no modo espectador do evento " + ocorrendo + " valendo " + premio + " " + tipo);
        for (Player ple : pe) {
          ple.hidePlayer(p);
        }
        if (ocorrendo.equalsIgnoreCase("ffa"))
        {
          Location ffa = new Location(mundoffa, 208.0D, 80.0D, 252.0D);
          ffa.setPitch(0.0F);
          ffa.setYaw(180.0F);
          p.teleport(ffa);
        }
      }
      else if (pespec.contains(p))
      {
        pespec.remove(p);
        p.sendMessage("§bVocê saiu do modo espectador!");
        me.everton.WocPvP.Main.log(p.getName() + " saiu do modo espectador do evento " + ocorrendo + " valendo " + premio + " " + tipo);
        forcarSpawn(p);
        p.setGameMode(GameMode.SURVIVAL);
        for (Player on : Bukkit.getServer().getOnlinePlayers()) {
          on.showPlayer(p);
        }
      }
    }
    else {
      p.sendMessage("§bNenhum evento está ocorrendo no momento!");
    }
  }
  
  public static void entrarEvento(Player p)
  {
    World mundoffa = Bukkit.getServer().getWorld("Yuri");
    if (ocorrendo != null)
    {
      if (!ocorrendoe.booleanValue())
      {
        if (!pe.contains(p))
        {
          pe.add(p);
          if (ocorrendo.equalsIgnoreCase("ffa"))
          {
            Location ffaspawn = new Location(mundoffa, 208.0D, 67.0D, 252.0D);
            p.getInventory().clear();
            UltraKits.Main.resetKit(p);
            p.teleport(ffaspawn);
            me.everton.WocPvP.Main.log(p.getName() + " entrou no evento " + ocorrendo + " valendo " + premio + " " + tipo);
          }
          for (Player specs : pespec) {
            p.hidePlayer(specs);
          }
          p.sendMessage("§bVocê entrou no evento " + 
            ocorrendo.toUpperCase());
        }
        else
        {
          p.sendMessage("§cVocê já está no evento " + 
            ocorrendo.toUpperCase() + 
            "! Use /evento sair para sair do evento!");
        }
      }
      else {
        p.sendMessage("§bO evento §6§l" + ocorrendo.toUpperCase() + 
          " §bjá começou!");
      }
    }
    else {
      p.sendMessage("§bNenhum evento está ocorrendo no momento!");
    }
  }
  
  public static void terminoEvento()
  {
    for (final Player pev : pe)
    {
      for (Player on : Bukkit.getServer().getOnlinePlayers()) {
        on.sendMessage("§2§l" + pev.getName() + 
          " §aganhou o evento §2§l" + ocorrendo.toUpperCase() + 
          "§a! Parabéns!");
      }
      pev.sendMessage("§cParabéns! Você ganhou o evento §4§l" + 
        ocorrendo.toUpperCase() + "§c!");
      Main.log(pev.getName() + " ganhou o evento " + ocorrendo + " valendo " + premio + " " + tipo);
      premio(pev);
      
      int coolDown = Main.sh.scheduleSyncRepeatingTask(Main.plugin, new Runnable()
      {
        int tempo = 20;
        
        public void run()
        {
          if (this.tempo > 0)
          {
            Firework fw = (Firework)pev.getWorld().spawn(pev.getLocation(), Firework.class);
            FireworkMeta fm = fw.getFireworkMeta();
            fm.addEffect(FireworkEffect.builder().flicker(true).withColor(Color.RED).withColor(Color.AQUA).withFade(Color.BLACK).with(FireworkEffect.Type.BALL).trail(true).build());
            fm.setPower(1);
            fw.setFireworkMeta(fm);
            this.tempo -= 1;
          }
          if (this.tempo == 0)
          {
            Main.sh.cancelTask(((Integer)EManager.cooldownt.get(0)).intValue());
            EManager.cooldownt.remove(0);
            EManager.forcarSpawn(pev);
          }
        }
      }, 0L, 20L);
      cooldownt.add(Integer.valueOf(coolDown));
    }
    for (Player specs : pespec)
    {
      forcarSpawn(specs);
      pespec.remove(specs);
    }
    ocorrendoe = Boolean.valueOf(false);
    ocorrendo = null;
    me.everton.WocPvP.Main.sh.cancelTask(((Integer)cooldown.get(0)).intValue());
    cooldown.remove(0);
    pe.clear();
    pespec.clear();
  }
  
  public static void sairEvento(Player p, Boolean morreu)
  {
    if (pe.contains(p))
    {
      pe.remove(p);
      if (!morreu.booleanValue()) {
        p.sendMessage("§bVocê saiu do evento " + 
          ocorrendo.toUpperCase());
      } else {
        p.sendMessage("§bVocê foi morto por ");
      }
      forcarSpawn(p);
      me.everton.WocPvP.Main.log(p.getName() + " saiu do evento " + ocorrendo + " valendo " + premio + " " + tipo);
      for (Player specs : pespec) {
        p.showPlayer(specs);
      }
    }
    else
    {
      p.sendMessage("§bVocê não está em nenhum evento!");
    }
  }
  
  public static void teleportarPEvento(boolean forcado)
  {
    World mundoffa = Bukkit.getServer().getWorld("Yuri");
    World mundomdr = Bukkit.getServer().getWorld("mlg");
    if (!ocorrendoe.booleanValue())
    {
      if (!forcado)
      {
        if (pe.size() < 10) {
          for (Player ep : pe) {
            ep.sendMessage("§bO evento §6§l" + 
              ocorrendo.toUpperCase() + 
              "§b precisa de ao menos 10 players para começar! §6(" + 
              pe.size() + " player(s) no evento)");
          }
        }
        return;
      }
      me.everton.WocPvP.Main.log("O evento" + ocorrendo + " iniciou e os jogadores foram teleportados!");
      for (Player eventop : pe)
      {
        if (ocorrendo.equalsIgnoreCase("ffa"))
        {
          Location ffa = new Location(mundoffa, 208.0D, 63.0D, 263.0D);
          ffa.setPitch(0.0F);
          ffa.setYaw(180.0F);
          UltraKits.Main.resetKit(eventop);
          EventoFFA.itemsFFA(eventop);
          eventop.teleport(ffa);
          me.everton.WocPvP.Main.log("O evento" + ocorrendo + " iniciou e os jogadores foram teleportados!");
        }
        else if (ocorrendo.equalsIgnoreCase("mdr"))
        {
          Location mdr = new Location(mundomdr, 208.0D, 63.0D, 263.0D);
          mdr.setPitch(0.0F);
          mdr.setYaw(180.0F);
          eventop.teleport(mdr);
          EventoMDR.itensMDR(eventop, false);
          EventoMDR.itensMDR((Player)pe.get(1), true);
        }
        ocorrendoe = Boolean.valueOf(true);
        eventop.sendMessage("§6Você foi teleportado ao evento! Boa sorte!");
        me.everton.WocPvP.Main.sh.cancelTask(((Integer)cooldown.get(0)).intValue());
        cooldown.remove(0);
      }
    }
  }
  
  public static void forcarSpawn(Player p)
  {
    UltraKits.Main.resetKit(p);
    me.everton.WocPvP.Main.spawnItens(p);
    p.teleport(Main.loc("spawn", p));
  }
}
