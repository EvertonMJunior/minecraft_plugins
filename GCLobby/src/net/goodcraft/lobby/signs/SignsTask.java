package net.goodcraft.lobby.signs;

public class SignsTask implements Runnable {
    @Override
    public void run() {
        GameSign.syncAllSigns();
        for (GameSign gs : GameSign.gameSigns) {
            gs.sync();
            gs.reload();
        }
    }
}
