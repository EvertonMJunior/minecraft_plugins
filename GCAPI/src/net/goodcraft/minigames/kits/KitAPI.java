package net.goodcraft.minigames.kits;

import com.google.gson.reflect.TypeToken;
import net.goodcraft.Main;
import net.goodcraft.api.json.JSONUtils;
import net.goodcraft.minigames.Option;
import net.goodcraft.sql.MySQL;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class KitAPI {
    @SuppressWarnings("unchecked")
    public static void getKits(UUID id, SQLStatus.Callback<ArrayList<Kit>> c) {
        if(!Main.minigame.hasOption(Option.HAS_KITS)){
            c.onSucess(new ArrayList<>());
            return;
        }

        new BukkitRunnable(){
            @Override
            public void run() {
                if (!MySQL.ativo) {
                    c.onSucess(new ArrayList<>());
                    return;
                }
                try {
                    PreparedStatement ps = Main.getSQL().getConnection()
                            .prepareStatement("SELECT kits FROM good_" + Main.minigame.getMode().name().toLowerCase() +
                                    " WHERE uuid='" + id + "'");
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        String json = rs.getString("kits");
                        if (json.isEmpty()) {
                            c.onSucess(new ArrayList<>());
                            return;
                        }
                        ArrayList<Kit> kits = new ArrayList<>();
                        ArrayList<String> kitNames = (ArrayList<String>) JSONUtils.fromJson(json,
                                new TypeToken<ArrayList<String>>() {
                                }.getType());

                        for (String kit : kitNames) {
                            Kit k = KitManager.getKitByString(kit);
                            if (k == null) continue;
                            kits.add(k);
                        }
                        c.onSucess(kits);
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
        }.runTaskAsynchronously(Main.minigame.getPlugin());
    }

    public static void hasKit(UUID id, Kit kit, SQLStatus.Callback<Boolean> c) {
        getKits(id, new SQLStatus.Callback<ArrayList<Kit>>() {
            @Override
            public void onSucess(ArrayList<Kit> kits) {
                c.onSucess(kits.contains(kit));
            }

            @Override
            public void onFailure(Throwable throwable) {}
        });
    }

    public static void addKit(UUID id, Kit kit) {
        if(!Main.minigame.hasOption(Option.HAS_KITS)){
            return;
        }

        if (!MySQL.ativo) {
            return;
        }

        getKits(id, new SQLStatus.Callback<ArrayList<Kit>>() {
            @Override
            public void onSucess(ArrayList<Kit> pKits) {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        final ArrayList<String> kits = new ArrayList<>();

                        for(Kit k : pKits){
                            kits.add(k.toString());
                        }
                        kits.add(kit.toString());

                        try {
                            PreparedStatement ps = Main.getSQL().getConnection()
                                    .prepareStatement("UPDATE good_" + Main.minigame.getMode().name().toLowerCase()
                                            + " SET kits='" +
                                            JSONUtils.toJson(kits) +
                                            "' WHERE uuid='" +
                                            id +
                                            "';");
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
                }.runTaskAsynchronously(Main.minigame.getPlugin());

            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }
}
