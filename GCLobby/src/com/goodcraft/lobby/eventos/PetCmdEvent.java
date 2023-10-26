package com.goodcraft.lobby.eventos;

import com.dsh105.echopet.compat.api.entity.PetType;
import com.goodcraft.api.Message;
import com.goodcraft.lobby.Main;
import com.goodcraft.lobby.pets.PetSelector;
import com.goodcraft.lobby.pets.PetSettings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PetCmdEvent implements Listener {

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent e){
        Player p = e.getPlayer();
        if(!e.getMessage().startsWith("/pet")){
            return;
        }
        e.setCancelled(true);
        
        if(e.getMessage().contains("/pet name")){
            String name = e.getMessage().replace("/pet name ", "");
            if(e.getMessage().equalsIgnoreCase("/pet name")){
                Message.ERROR.send(p, "Use /pet name <nome>.");
                return;
            }
            if(!Main.getPetAPI().hasPet(p)){
                Message.ERROR.send(p, "Você não tem um PET ativo.");
                return;
            }
            if(Main.getPetAPI().getPet(p).getPetType() == PetType.SHEEP){
                Message.ERROR.send(p, "Você não pode renomear a Ovelha Colorida!");
                return;
            }
            
            if(name.length() > 32){
                Message.ERROR.send(p, "O nome de seu PET pode ter no máximo 32 caracteres.");
                return;
            }
            
            Main.getPetAPI().getPet(p).setPetName(name);
            Message.GOOD.send(p, "Agora seu PET se chama §r" + name + "§a!");
            return;
        }
        
        if(e.getMessage().contains("/pet menu")){
            if(!Main.getPetAPI().hasPet(p)){
                Message.ERROR.send(p, "Você não tem um PET ativo.");
                return;
            }
            new PetSettings(p).open();
            return;
        }
        
        if(e.getMessage().contains("/pet remove")){
            if(!Main.getPetAPI().hasPet(p)){
                Message.ERROR.send(p, "Você não tem um PET ativo.");
                return;
            }
            Main.getPetAPI().removePet(p, false, false);
            Message.GOOD.send(p, "Seu PET foi removido!");
            return;
        }
        
        if(e.getMessage().contains("/pet select")){
            new PetSelector(p).open();
            return;
        }
        
        if(e.getMessage().contains("/pet help")){
            Message.INFO.send(p, " §r§m---------§r §ePETs - Ajuda §r§m---------");
            Message.INFO.send(p, "    /pet name <nome> - Definir o nome de seu PET ativo");
            Message.INFO.send(p, "    /pet remove - Remove seu PET ativo");
            Message.INFO.send(p, "    /pet select - Abrir menu de seleção");
            Message.INFO.send(p, "    /pet menu - Abrir menu de configuração");
            return;
        }
        
        Message.INFO.send(p, "Use /pet help para receber ajuda.");
    }
}
