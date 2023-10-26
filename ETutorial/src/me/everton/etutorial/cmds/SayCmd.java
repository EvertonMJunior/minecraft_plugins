package me.everton.etutorial.cmds;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

public class SayCmd extends BukkitCommand {

    public SayCmd() {
        super("say");
        super.setDescription("Manda uma mensagem destacada no chat!");
        super.setPermission("etutorial.say");
        super.setPermissionMessage("§cVocê não pode executar este comando!");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length > 0) {
            String sentence = " §2§l> §f";
            for (int i = 0; i < args.length; i++) {
                sentence += args[i] + (i == (args.length - 1) ? "" : " ");
            }
            //O método acima pega todos os valores da String[] args e passa para uma String: sentence.
            
            for(Player p : Bukkit.getOnlinePlayers()){
                p.sendMessage(" ");
                p.sendMessage((sender instanceof Player ? ((Player) sender).getDisplayName() : "§4Console")
                        + sentence); //Verificamos se é um player, se for mostramos o nome dele com a tag(caso possua), se não mostramos o nome 'Console' em cor vermelha. Então, mandamos a mensagem digitada.
                p.sendMessage(" ");
            }
            return true;
        }

        sender.sendMessage("§cUse /say [mensagem]");
        return true;
    }
}
