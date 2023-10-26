package me.everton.eapi;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum Message {
    GOOD(ChatColor.GREEN),
    ERROR(ChatColor.RED),
    INFO(ChatColor.YELLOW);
    
    private final ChatColor attachedColor;
    static int linesToLeave = 0;
    
    private Message(ChatColor atacchedColor){
        this.attachedColor = atacchedColor;
    }
    
    public void send(CommandSender whoToSend, String whatToSend){
        if(whoToSend == null || whatToSend == null){
            return;
        }
        
        String message = attachedColor + whatToSend;
        
        whoToSend.sendMessage(message);
    }
    
    public String send(String whatToSend){
        if(whatToSend == null){
            return null;
        }
        
        String message = attachedColor + whatToSend;
        
        return message;
    }
    
    public Message space(int linesToLeave2){
        linesToLeave = linesToLeave2;
        return this;
    }
}
