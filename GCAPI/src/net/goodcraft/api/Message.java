package net.goodcraft.api;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum Message {

    GOOD(ChatColor.GREEN),
    ERROR(ChatColor.RED),
    INFO(ChatColor.YELLOW);

    static int linesToLeave = 0;
    private final ChatColor attachedColor;

    private Message(ChatColor atacchedColor) {
        this.attachedColor = atacchedColor;
    }

    public void send(CommandSender whoToSend, String whatToSend) {
        if (whoToSend == null) {
            return;
        }
        String message = attachedColor + whatToSend.replace("<p>", "§f" + whoToSend.getName() + attachedColor);

        whoToSend.sendMessage(message);
    }

    public String send(String whatToSend) {
        String message = attachedColor + whatToSend;

        return message;
    }

    public void broadcast(String whatToSend) {
        Utils.broadcast(attachedColor + whatToSend);
    }

    public void broadcast(String whatToSend, Rank rankToBroadcast) {
        if (rankToBroadcast == null) rankToBroadcast = Rank.NORMAL;
        Utils.broadcast(attachedColor + whatToSend, rankToBroadcast);
    }

    public Message space(int linesToLeave2) {
        linesToLeave = linesToLeave2;
        return this;
    }
}
