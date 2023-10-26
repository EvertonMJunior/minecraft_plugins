package me.everton.eapi;

import java.util.logging.Level;

public class Log {
    public Log(String whatToLog){
        Main.getPlugin().getLogger().log(Level.ALL, whatToLog);
    }
}
