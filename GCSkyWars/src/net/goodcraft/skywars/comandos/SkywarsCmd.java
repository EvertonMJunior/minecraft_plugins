package net.goodcraft.skywars.comandos;

import net.goodcraft.api.Comando;
import net.goodcraft.api.Item;
import net.goodcraft.api.Message;
import net.goodcraft.api.Rank;
import net.goodcraft.skywars.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.UUID;

public class SkywarsCmd extends Comando {
    public static ArrayList<UUID> settingLocations = new ArrayList<>();
    public static ArrayList<Location> locations = new ArrayList<>();

    public SkywarsCmd() {
        super("skywars", Rank.ADMIN, new String[]{"sw", "skywar"});
        setInGameOnly(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length != 1 && args.length != 2) {
            Message.INFO.send(p, "Use /skywars <setspawnlocations/setmaxplayers/setmap/setwaitinglobbyhigh>");
            return false;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("setspawnlocations")) {
            if (!settingLocations.contains(p.getUniqueId())) {
                Message.INFO.send(p, "Clique com o botão direito na SlimeBall para setar a localização de um dos spawns.");
                p.getInventory().setItem(0, Item.item(Material.SLIME_BALL, 1, "§3Setar Localização de Spawn §7(Clique Direito)"));
                settingLocations.add(p.getUniqueId());
                return false;
            }
            FileConfiguration c = Main.getPlugin().getConfig();
            String prefix = "SPAWN_LOCATIONS.";
            for (int i = 0; i < locations.size(); i++) {
                Location l = locations.get(i);
                c.set(prefix + i + ".w", l.getWorld().getName());
                c.set(prefix + i + ".x", l.getX());
                c.set(prefix + i + ".y", l.getY());
                c.set(prefix + i + ".z", l.getZ());
                c.set(prefix + i + ".yaw", l.getYaw());
                c.set(prefix + i + ".pitch", l.getPitch());
            }
            Main.getPlugin().saveConfig();
            if (!locations.isEmpty()) {
                Message.INFO.send(p, "As localizações foram salvas com sucesso! (" + locations.size() + ")");
            }
            locations.clear();
            settingLocations.remove(p.getUniqueId());
            p.getInventory().remove(Material.SLIME_BALL);
            return false;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("setmaxplayers")) {
                try {
                    FileConfiguration c = Main.getPlugin().getConfig();
                    c.set("MAX_PLAYERS", Integer.valueOf(args[1]));
                    Main.getPlugin().saveConfig();
                    Main.getMg().setMaximumPlayers(Integer.valueOf(args[1]));
                    Main.getMg().setMinimumPlayers(Main.getMg().getMaximumPlayers() / 3);
                    Message.INFO.send(p, "Máximo de jogadores definido como '" + args[1] + "'.");
                } catch (Exception e) {
                    Message.ERROR.send(p, "Use números!");
                }
                return false;
            }
            if(args[0].equalsIgnoreCase("setwaitinglobbyhigh")){
                try {
                    FileConfiguration c = Main.getPlugin().getConfig();
                    c.set("WAITING-LOBBY_Y", Integer.valueOf(args[1]));
                    Main.getPlugin().saveConfig();
                    Message.INFO.send(p, "Agora o Lobby de Espera vai spawnar no Y '" + args[1] + "'.");
                } catch (Exception e) {
                    Message.ERROR.send(p, "Use números!");
                }
                return false;
            }
            if (args[0].equalsIgnoreCase("setmap")) {
                FileConfiguration c = Main.getPlugin().getConfig();
                c.set("MAPA", args[1]);
                Main.getPlugin().saveConfig();
                Message.INFO.send(p, "Nome do mapa definido como '" + args[1] + "'.");
                return false;
            }
            Message.INFO.send(p, "Use /skywars <setspawnlocations/setmaxplayers/setmap/setwaitinglobbyhigh>");
            return false;
        }
        Message.INFO.send(p, "Use /skywars <setspawnlocations/setmaxplayers/setmap/setwaitinglobbyhigh>");
        return false;
    }
}
