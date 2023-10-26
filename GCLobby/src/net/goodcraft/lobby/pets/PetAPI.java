package net.goodcraft.lobby.pets;

import com.google.gson.reflect.TypeToken;
import net.goodcraft.Main;
import net.goodcraft.api.json.JSONUtils;
import net.goodcraft.sql.MySQL;
import net.goodcraft.sql.SQLStatus;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class PetAPI {
    @SuppressWarnings("unchecked")
    public static void getPets(UUID id, SQLStatus.Callback<ArrayList<String>> c) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!MySQL.ativo) {
                    return;
                }
                try {
                    PreparedStatement ps = Main.getSQL().getConnection()
                            .prepareStatement("SELECT pets FROM good_lobby WHERE uuid='" + id + "'");
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        String json = rs.getString("pets");
                        if (json.isEmpty()) {
                            c.onSucess(new ArrayList<>());
                            return;
                        }
                        ArrayList<String> pets = (ArrayList<String>) JSONUtils.fromJson(rs.getString("pets"),
                                new TypeToken<ArrayList<String>>() {
                                }.getType());
                        Main.getSQL().closeResources(rs, ps);
                        c.onSucess(pets);
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
        }.runTaskAsynchronously(Main.getPlugin());
    }

    public static void hasBoughtPet(UUID id, Pet pet, SQLStatus.Callback<Boolean> c) {
        getPets(id, new SQLStatus.Callback<ArrayList<String>>() {
            @Override
            public void onSucess(ArrayList<String> pets) {
                c.onSucess(pets.contains(pet.getName()));
            }

            @Override
            public void onFailure(Throwable cause) {

            }
        });
    }

    public static void addPet(UUID id, Pet pet, SQLStatus.Callback<Boolean> c) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!MySQL.ativo) {
                    return;
                }

                getPets(id, new SQLStatus.Callback<ArrayList<String>>() {
                    @Override
                    public void onSucess(ArrayList<String> done) {
                        ArrayList<String> pets = done;
                        pets.add(pet.getName());
                        try {
                            PreparedStatement ps = Main.getSQL().getConnection()
                                    .prepareStatement("UPDATE good_lobby SET pets='"
                                            + JSONUtils.toJson(pets)
                                            + "' WHERE uuid='"
                                            + id
                                            + "'");
                            ps.executeUpdate();
                            ps.close();
                            c.onSucess(true);
                        } catch (SQLException e1) {
                            e1.printStackTrace();
                            try {
                                if (!Main.getSQL().hasConnection()) Main.getSQL().openConnection();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            c.onSucess(false);
                        }
                    }

                    @Override
                    public void onFailure(Throwable cause) {

                    }
                });
            }
        }.runTaskAsynchronously(Main.getPlugin());
    }
}
