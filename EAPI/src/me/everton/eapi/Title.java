package me.everton.eapi;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum Title {
    GOOD(ChatColor.DARK_GREEN, ChatColor.GREEN),
    ERROR(ChatColor.DARK_RED, ChatColor.RED),
    INFO(ChatColor.YELLOW, ChatColor.GOLD);
    
    private final ChatColor titleColor;
    private final ChatColor subtitleColor;
    
    private Title(ChatColor titleColor, ChatColor subtitleColor){
        this.titleColor = titleColor;
        this.subtitleColor = subtitleColor;
    }
    
    public void send(CommandSender whoToSend, String titleMessage, String subtitleMessage){
        if(whoToSend == null || titleMessage == null && subtitleMessage == null){
            return;
        }
        
        
    }
}
