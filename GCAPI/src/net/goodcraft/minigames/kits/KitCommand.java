package net.goodcraft.minigames.kits;

import net.goodcraft.Main;
import net.goodcraft.api.*;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.minigames.Option;
import net.goodcraft.minigames.eventos.AddToKitEvent;
import net.goodcraft.minigames.game.GameState;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand extends Comando {

    public KitCommand() {
        super("kit");
        setInGameOnly(true);
    }

    public static void add(Player p, Kit k) {
        Minigame mg = Main.minigame;

        if (mg == null) {
            return;
        }
        p.closeInventory();

        KitManager.removeKit(p);
        k.addPlayer(p);
        p.sendMessage(" ");
        Title.INFO.send(p, k.toString(), "Kit selecionado!");

        String s = "";
        for (String ss : k.getDesc()) {
            s = s + " " + ss;
        }
        Message.INFO.send(p, "Você escolheu o kit " + k.toString() + ".");
        Message.INFO.send(p, "Descrição: " + s);
        p.sendMessage(" ");

        p.playSound(p.getLocation(), Sound.NOTE_PIANO, 1.0F, 1.0F);

        if (mg.getGameState() != GameState.PREGAME) {
            k.addItems(p);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Minigame mg = Main.minigame;

        if (mg == null) {
            return false;
        }

        if (!mg.hasOption(Option.HAS_KITS)) {
            p.closeInventory();
            Message.ERROR.send(p, "O sistema de kits está desativado no " + mg.getName() + ".");
            return false;
        }

        if (!mg.areKitsEnabled()) {
            p.closeInventory();
            Message.ERROR.send(p, "Os kits estão desativados.");
            return false;
        }

        if (mg.getGameState() != GameState.PREGAME && mg.hasOption(Option.HAS_PREGAME)) {
            Message.ERROR.send(p, "O jogo já iniciou!");
            return false;
        }

        if (args.length == 0) {
            KitSelector.openInv(p);
            return false;
        }
        Kit k = KitManager.getKitByString(Utils.getSentence(args));

        if (k == null) {
            Message.ERROR.send(p, "O kit [" + args[0] + "] não foi encontrado.");
            return false;
        }

        AddToKitEvent event = new AddToKitEvent(p, k, mg);
        Bukkit.getPluginManager().callEvent(event);

        if (mg.getDisabledKits().contains(k)) {
            p.closeInventory();
            Message.ERROR.send(p, "Este kit encontra-se desativado!");
            event.setCancelled(true);
            return false;
        }

        if (event.isCancelled()) {
            return false;
        }

        if (mg.getFreeKits().isFree(k)) {
            add(p, k);
            event.setCancelled(false);
            return false;
        }

        KitAPI.hasKit(p.getUniqueId(), k, new SQLStatus.Callback<Boolean>() {
            @Override
            public void onSucess(Boolean hasKit) {
                p.closeInventory();
                if (!hasKit) {
                    Message.ERROR.send(p, "Você não tem este kit. Compre-o na loja de kits!");
                    event.setCancelled(true);
                    return;
                }
                if (k.isVip() && !Rank.has(p.getUniqueId(), Rank.BRONZE)) {
                    Message.ERROR.send(p, "Você precisa de um VIP para poder usar este kit! Adquira um em loja.good-craft.net!");
                    event.setCancelled(true);
                    return;
                }

                add(p, k);
                event.setCancelled(false);
            }

            @Override
            public void onFailure(Throwable throwable) {
                p.closeInventory();
                event.setCancelled(true);
                Message.ERROR.send(p, "Ocorreu um erro, tente mais tarde.");
            }
        });
        return false;
    }

}
