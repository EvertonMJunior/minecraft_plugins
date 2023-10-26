package com.goodcraft.skywars.game;

public enum GameState {
    AGUARDANDO_JOGADORES("Aguardando..."),
    INVENCIBILIDADE("Invencibilidade"),
    JOGO("Em jogo");

    private final String name;

    private GameState(String name) {
        this.name = name;
    }
}
