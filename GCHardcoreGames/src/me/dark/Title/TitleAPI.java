package me.dark.Title;

import net.minecraft.server.v1_7_R4.ChatSerializer;
import net.minecraft.server.v1_7_R4.IChatBaseComponent;
import net.minecraft.server.v1_7_R4.PacketPlayOutChat;
import net.minecraft.server.v1_7_R4.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.spigotmc.ProtocolInjector;

public class TitleAPI {
	
    public static String nmsver;
    
    public static void sTitulo(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
    	s5Titulo(player, fadeIn, stay, fadeOut, message, null);
    }
    public static void sSubTitulo(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String message) {
    	s5Titulo(player, fadeIn, stay, fadeOut, null, message);
    }
    public static void sFullTitulo(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle) {
    	s5Titulo(player, fadeIn, stay, fadeOut, title, subtitle);
    }
    public static void s5Titulo(Player player, Integer fadeIn, Integer stay, Integer fadeOut, String title, String subtitle){
    	CraftPlayer craftPlayer = (CraftPlayer)player;
        if (craftPlayer.getHandle().playerConnection.networkManager.getVersion() != 47) {
        	return;
        }
        if (title == null) {
        	title = "";
        }
        title = ChatColor.translateAlternateColorCodes('&', title);
        if (subtitle == null) {
        	subtitle = "";
        }
        subtitle = ChatColor.translateAlternateColorCodes('&', subtitle);
        title = title.replaceAll("%player%", player.getDisplayName());
        subtitle = subtitle.replaceAll("%player%", player.getDisplayName());
        IChatBaseComponent serializedTitle = ChatSerializer.a(TitleText.convert(title));
        IChatBaseComponent serializedSubTitle = ChatSerializer.a(TitleText.convert(subtitle));
        IChatBaseComponent title2 = serializedTitle;
        IChatBaseComponent subtitle2 = serializedSubTitle;
        
        craftPlayer.getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.TIMES, fadeIn.intValue(), stay.intValue(), fadeOut.intValue()));
        if (title != null) {
        	craftPlayer.getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.TITLE, title2));
        }
        if (subtitle != null) {
        	craftPlayer.getHandle().playerConnection.sendPacket(new ProtocolInjector.PacketTitle(ProtocolInjector.PacketTitle.Action.SUBTITLE, subtitle2));
       }
  }
    public static void sTabTitulo(Player player, String header, String footer) {
    	CraftPlayer craftPlayer = (CraftPlayer)player;
    	if (craftPlayer.getHandle().playerConnection.networkManager.getVersion() != 47) {
    		return;
		}
	   PlayerConnection connection = craftPlayer.getHandle().playerConnection;
	   if (header == null) {
		   header = "";
	   }
	   header = ChatColor.translateAlternateColorCodes('&', header);
	   if (footer == null) {
		  footer = "";
	   }
	   footer = ChatColor.translateAlternateColorCodes('&', footer);
	   header = header.replaceAll("%player%", player.getDisplayName());
	   footer = footer.replaceAll("%player%", player.getDisplayName());
	   IChatBaseComponent header2 = ChatSerializer.a("{'color': 'white', 'text': '" + header + "'}");
	   IChatBaseComponent footer2 = ChatSerializer.a("{'color': 'white', 'text': '" + footer + "'}");
	   connection.sendPacket(new ProtocolInjector.PacketTabHeader(header2, footer2));
   }
    public static boolean is1_8(Player p) {
    	CraftPlayer pl = (CraftPlayer)p;
    	return (pl.getHandle().playerConnection.networkManager.getVersion() >= 47);
    }
    public static void sendActionBar(Player p, String s) {
    	CraftPlayer cp = (CraftPlayer)p;
	    if (cp.getHandle().playerConnection.networkManager.getVersion() != 47) {
	    	return;
	    }
	    IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + s + "\"}");
	    PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, 2);
	    cp.getHandle().playerConnection.sendPacket(ppoc);
    }
  
}
