package me.everton.eapi;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class UUIDUtils {

    public static HashMap<String, UUID> uuidByName = new HashMap<>();
    public static HashMap<UUID, String> nameByUUID = new HashMap<>();

    public HashMap<String, String> getJSONFieldValues(String link, String[] fields) throws Exception {
        HashMap<String, String> retorno = new HashMap<>();

        URL url = new URL(link);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject rootObj = root.getAsJsonObject();

        for (String f : fields) {
            retorno.put(f, rootObj.get(f).getAsString());
        }

        return retorno;
    }

    public UUID getByName(String name) {
        if (uuidByName.containsKey(name)) {
            return uuidByName.get(name);
        }

        for (Player toGetUUID : Bukkit.getOnlinePlayers()) {
            if (toGetUUID.getName().equalsIgnoreCase(name)) {
                return toGetUUID.getUniqueId();
            }
        }
        BukkitTask runTaskAsynchronously = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    HashMap<String, String> uuid = getJSONFieldValues("https://api.mojang.com/users/profiles/minecraft/" + name, new String[]{"id"});
                    
                } catch (Exception ex) {
                    Logger.getLogger(UUIDUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }.runTaskAsynchronously(Main.getPlugin());

        
        return null;
    }
}
