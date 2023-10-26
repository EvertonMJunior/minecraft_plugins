package net.goodcraft.lobby.eventos;

import com.dsh105.echopet.compat.api.entity.PetType;
import net.goodcraft.api.Message;
import net.goodcraft.lobby.Main;
import net.goodcraft.lobby.pets.PetSelector;
import net.goodcraft.lobby.pets.PetSettings;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class PetCmdEvent implements Listener {

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (!e.getMessage().startsWith("/pet")) {
            return;
        }
        e.setCancelled(true);

        if (e.getMessage().contains("/pet name")) {
            String name = e.getMessage().replace("/pet name ", "");
            if (e.getMessage().equalsIgnoreCase("/pet name")) {
                Message.ERROR.send(p, "Use /pet name <nome>.");
                return;
            }
            if (!Main.getPetAPI().hasPet(p)) {
                Message.ERROR.send(p, "Você não tem um PET ativo.");
                return;
            }
            if (Main.getPetAPI().getPet(p).getPetType() == PetType.SHEEP) {
                Message.ERROR.send(p, "Você não pode renomear a Ovelha Colorida!");
                return;
            }

            if (name.length() > 32) {
                Message.ERROR.send(p, "O nome de seu PET pode ter no máximo 32 caracteres.");
                return;
            }

            Main.getPetAPI().getPet(p).setPetName(name);
            Message.GOOD.send(p, "Agora seu PET se chama §r" + name.replace("&", "§") + "§a!");
            return;
        }

        if (e.getMessage().contains("/pet menu")) {
            if (!Main.getPetAPI().hasPet(p)) {
                Message.ERROR.send(p, "Você não tem um PET ativo.");
                return;
            }
            new PetSettings(p).open();
            return;
        }

        if (e.getMessage().contains("/pet remove")) {
            if (!Main.getPetAPI().hasPet(p)) {
                Message.ERROR.send(p, "Você não tem um PET ativo.");
                return;
            }
            Main.getPetAPI().removePet(p, false, false);
            Message.GOOD.send(p, "Seu PET foi removido!");
            return;
        }

        if (e.getMessage().contains("/pet select")) {
            new PetSelector(p).open();
            return;
        }

        if (e.getMessage().contains("/pet help")) {
            p.sendMessage(" ");
            Message.INFO.send(p, "§6§lPets - Ajuda §e[1/1]");
            Message.INFO.send(p, "- §6/pet name <nome> §e- Definir o nome de seu PET ativo");
            Message.INFO.send(p, "- §6/pet remove §e- Remove seu PET ativo");
            Message.INFO.send(p, "- §6/pet select §e- Abrir menu de seleção");
            Message.INFO.send(p, "- §6/pet menu §e- Abrir menu de configuração");
            p.sendMessage(" ");
            return;
        }
        Message.INFO.send(p, "Use /pet help para receber ajuda.");
    }
}
