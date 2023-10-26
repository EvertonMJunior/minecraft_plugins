package net.goodcraft.minigames.kits;

import com.google.gson.reflect.TypeToken;
import net.goodcraft.Main;
import net.goodcraft.api.json.JSONUtils;
import net.goodcraft.minigames.Minigame;
import net.goodcraft.sql.MySQL;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class FreeKits {

    private final Minigame MINIGAME;

    public FreeKits(Minigame minigame){
        this.MINIGAME = minigame;
    }

    private final ArrayList<Kit> freeKits = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public void retrieveAllData() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!MySQL.ativo) {
                    return;
                }
                try {
                    PreparedStatement ps = Main.getSQL().getConnection()
                            .prepareStatement("SELECT swfreekits FROM good_data");
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        String json = rs.getString("swfreekits");
                        if (json.isEmpty()) {
                            return;
                        }
                        ArrayList<String> kitNames = (ArrayList<String>) JSONUtils.fromJson(json,
                                new TypeToken<ArrayList<String>>() {
                                }.getType());
                        freeKits.clear();
                        for (String kit : kitNames) {
                            add(KitManager.getKitByString(kit));
                        }
                    }
                    rs.close();
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    try {
                        if (!Main.getSQL().hasConnection()) Main.getSQL().openConnection();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.runTaskAsynchronously(MINIGAME.getPlugin());
    }

    public void add(Kit kit) {
        if (kit == null) return;
        if (freeKits.contains(kit)) return;
        freeKits.add(kit);
    }

    public void remove(Kit kit){
        if (kit == null) return;
        if (!freeKits.contains(kit)) return;
        freeKits.remove(kit);
    }

    public ArrayList<Kit> get() {
        return freeKits;
    }

    public boolean isFree(Kit kit) {
        if (kit == null) return false;
        return freeKits.contains(kit);
    }

    public void modifyFromSQL(Kit kit, boolean add, boolean remove) {
        new BukkitRunnable(){
            @Override
            public void run() {
                if (!MySQL.ativo) {
                    return;
                }
                try {
                    retrieveAllData();
                    ArrayList<String> kits = new ArrayList<>();
                    for (Kit k : freeKits) {
                        kits.add(k.toString());
                    }

                    if(add){
                        if(!kits.contains(kit.toString())){
                            kits.add(kit.toString());
                            freeKits.clear();
                            add(kit);
                        }
                    }

                    if(remove){
                        if(kits.contains(kit.toString())){
                            kits.remove(kit.toString());
                            remove(kit);
                        }
                    }

                    PreparedStatement ps = Main.getSQL().getConnection()
                            .prepareStatement("UPDATE good_data SET swfreekits='"
                                    + JSONUtils.toJson(kits)
                                    + "'");
                    ps.executeUpdate();
                    ps.close();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                    try {
                        if (!Main.getSQL().hasConnection()) Main.getSQL().openConnection();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.runTaskAsynchronously(MINIGAME.getPlugin());
    }

}
