package me.everton.eapi.comandos;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.goodcraft.bungee.Comando;
import me.everton.eapi.Main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class ReloadCmd extends Comando {

    public ReloadCmd() {
        super("reload", "eapi.reload");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!cmd.testPermission(sender)) {
            return false;
        }
        Path apiSource = Paths.get("C:/Users/Everton/Desktop/JavaDev/Sources/EAPI/dist/EAPI.jar");
        Path eFactionsSource = Paths.get("C:/Users/Everton/Desktop/JavaDev/Sources/EFactions/dist/EFactions.jar");
        Path apiDestination = Paths.get("C:/Users/Everton/Desktop/JavaDev/Testes/plugins/EAPI.jar");
        Path eFactionsDestination = Paths.get("C:/Users/Everton/Desktop/JavaDev/Testes/plugins/EFactions.jar");

        try {
            Files.deleteIfExists(apiDestination);
            Files.deleteIfExists(eFactionsDestination);
            Files.move(apiSource, apiDestination, StandardCopyOption.REPLACE_EXISTING);
            Files.move(eFactionsSource, eFactionsDestination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            Logger.getLogger(ReloadCmd.class.getName()).log(Level.SEVERE, null, ex);
        }
        Bukkit.dispatchCommand(Main.getPlugin().getServer().getConsoleSender(), "stop");

        return false;
    }
}
