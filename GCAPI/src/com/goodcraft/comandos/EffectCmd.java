package com.goodcraft.comandos;

import com.goodcraft.api.Comando;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.potion.PotionEffectType;

public class EffectCmd extends Comando{
    public EffectCmd(){
        super("effect", "effect");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        return false;
    }
    
    public enum Effects{
        FORCA(PotionEffectType.DAMAGE_RESISTANCE);
        
        private Effects(PotionEffectType pet){
            
        }
    }
}
