package net.goodcraft.hardcoregames.chests;

public enum ChestsTypes {
    G1("g1"), G2("g2"), G3("g3");

    private String tipo;

    ChestsTypes(String sql) {
        tipo = sql;
    }


    public String getType() {
        return tipo;
    }

}
