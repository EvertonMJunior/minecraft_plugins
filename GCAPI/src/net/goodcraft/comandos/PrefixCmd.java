package net.goodcraft.comandos;

import net.goodcraft.api.*;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;


public class PrefixCmd extends Comando {

    public PrefixCmd() {
        super("updatetag", new String[]{"updaterank"});
        setInGameOnly(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        final boolean[] executed = {false};
        Rank.syncPlayerRank(p.getUniqueId(), new SQLStatus.Callback<Rank>() {
            @Override
            public void onSucess(Rank r) {
                if (executed[0]) return;
                executed[0] = true;
                Tag t = Tag.getByUUID(p.getUniqueId()) != null ? Tag.getByUUID(p.getUniqueId()) : new Tag(p.getName(), null);
                if (r.equals(t.getRank())) {
                    Message.ERROR.send(p, "Seu rank continua sendo [" + r.toString() + "].");
                    return;
                }
                t.setRank(r);
                t.updateForAll();
                Message.INFO.send(sender, "Sua tag foi atualizada.");
            }

            @Override
            public void onFailure(Throwable cause) {
            }
        });
        return false;
    }

}
