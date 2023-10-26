package net.goodcraft.lobby.signs;

public enum SignState {
    DISPONIVEL("§2§lDISPONíVEL"), INDISPONIVEL("§c§lINDISPONíVEL"), MANUTENCAO("§4§lMANUTENÇãO");

    private final String name;

    SignState(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
