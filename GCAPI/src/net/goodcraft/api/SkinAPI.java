package net.goodcraft.api;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;

public class SkinAPI {

    UUID uuid;
    String name;
    String value;
    String signatur;

    public SkinAPI(UUID uuid) {
        this.uuid = uuid;
        load();
    }


    private void load() {
        try {
            // Get the name from SwordPVP
            URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid.toString().replace("-", "")
                    + "?unsigned=false");
            URLConnection uc = url.openConnection();
            uc.setUseCaches(false);
            uc.setDefaultUseCaches(false);
            uc.addRequestProperty("User-Agent", "Mozilla/5.0");
            uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
            uc.addRequestProperty("Pragma", "no-cache");

            // Parse it
            String json = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(json);
            JSONArray properties = (JSONArray) ((JSONObject) obj).get("properties");
            for (Object property1 : properties) {
                try {
                    JSONObject property = (JSONObject) property1;
                    String name = (String) property.get("name");
                    String value = (String) property.get("value");
                    String signature = property.containsKey("signature") ? (String) property.get("signature") : null;


                    this.name = name;
                    this.value = value;
                    this.signatur = signature;


                } catch (Exception e) {
                    Bukkit.getLogger().log(Level.WARNING, "Failed to apply auth property", e);
                }
            }
        } catch (Exception e) {
        }
    }

    public String getSkinValue() {
        return value;
    }

    public String getSkinName() {
        return name;
    }

    public String getSkinSignatur() {
        return signatur;
    }

    public void set(Player player, GameProfile gp) {
        if (getSkinName() != null) {
            gp.getProperties().put(getSkinName(), new Property(getSkinName(), getSkinValue(), getSkinSignatur()));
        }
    }

}
