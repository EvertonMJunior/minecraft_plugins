package com.goodcraft.bungee.comandos;

import com.goodcraft.bungee.Main;
import com.goodcraft.bungee.api.Message;
import com.goodcraft.bungee.api.Rank;
import com.goodcraft.bungee.api.Utils;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.TabCompleteEvent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.event.EventHandler;
import net.md_5.bungee.event.EventPriority;

public class KickCmd extends Command {
    public KickCmd() {
        super("kick", "bungeecord.command.default");
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        if (sender instanceof ProxiedPlayer && !Rank.check((ProxiedPlayer) sender, Rank.ADMIN)) {
            return;
        }

        if (args.length < 2) {
            Message.ERROR.send(sender, "Use /kick <nick> <motivo>");
            return;
        }
        ProxiedPlayer pl = Main.getPlugin().getProxy().getPlayer(args[0]);
        String reason = Utils.getSentence(args, 1);

        if (pl == null) {
            Message.ERROR.send(sender, args[0] + " está offline.");
            return;
        }

        Message.ERROR.broadcast(" ", Rank.MOD);
        Message.ERROR.broadcast("[!] " + pl.getName() + " foi kickado por " + sender.getName() + ".", Rank.MOD);
        Message.ERROR.broadcast("[!] Motivo: " + reason, Rank.MOD);
        Message.ERROR.broadcast(" ", Rank.MOD);

        pl.disconnect(new TextComponent(
                "§c§lGOOD CRAFT\n\n" +
                        "§cVocê foi kickado do servidor.\n" +
                        "§cMotivo: " + reason
        ));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onTab(TabCompleteEvent e) {
        if (!e.getSuggestions().isEmpty()) {
            return;
        }

        if (!e.getCursor().startsWith("/kick")) return;
        String[] args = e.getCursor().split(" ");

        final String checked = (args.length > 0 ? args[args.length - 1] : e.getCursor()).toLowerCase();

        if (args.length == 2) {
            for (ProxiedPlayer player : Main.getPlugin().getProxy().getPlayers()) {
                if (player.getName().toLowerCase().startsWith(checked)) {
                    e.getSuggestions().add(player.getName());
                }
            }
        }
    }
}
