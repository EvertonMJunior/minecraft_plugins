package me.everton.etutorial;

import me.everton.etutorial.cmds.SayCmd;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCmds();

        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registerCmds() {
        ((CraftServer) this.getServer()).getCommandMap().register("say", new SayCmd());
        //O método acima registra o comando 'say'.
    }

}
