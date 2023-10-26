package com.goodcraft.lobby.pets;

import com.goodcraft.Main;
import com.goodcraft.api.JSONUtils;
import com.goodcraft.sql.MySQL;
import com.google.gson.reflect.TypeToken;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class PetAPI {

    public static ArrayList<String> getPets(UUID id) {
        if (!MySQL.ativo) {
            return null;
        }
        try {
            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("SELECT pets FROM good_lobby WHERE uuid='" + id + "'");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String json = rs.getString("pets");
                if (json.isEmpty() || json == null) {
                    return new ArrayList<String>();
                }
                ArrayList<String> pets = (ArrayList<String>) JSONUtils.fromJson(rs.getString("pets"),
                        new TypeToken<ArrayList<String>>() {
                }.getType());
                return pets;
            }
            rs.close();
            ps.close();
        } catch (SQLException e1) {
        }
        return new ArrayList<String>();
    }

    public static boolean hasBoughtPet(UUID id, Pet pet) {
        try {
            return getPets(id).contains(pet.getName());
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean addPet(UUID id, Pet pet) {
        if (!MySQL.ativo) {
            return false;
        }
        try {
            ArrayList<String> pets = getPets(id);
            pets.add(pet.getName());

            PreparedStatement ps = Main.getSQL().getConnection()
                    .prepareStatement("UPDATE good_lobby SET pets='"
                            + JSONUtils.toJson(pets)
                            + "' WHERE uuid='"
                            + id
                            + "'");
            ps.executeUpdate();
            ps.close();

            return true;
        } catch (SQLException e1) {
        }
        return false;
    }
}
