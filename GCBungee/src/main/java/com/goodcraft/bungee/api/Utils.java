package com.goodcraft.bungee.api;

import com.goodcraft.bungee.Main;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.util.concurrent.TimeUnit;

public class Utils {

    /*
        Pega uma frase de um array de Strings.
     */
    public static String getSentence(String[] args) {
        String sentence = "";
        for (int i = 0; i < args.length; i++) {
            sentence += args[i] + (i == (args.length - 1) ? "" : " ");
        }
        return sentence;
    }

    /*
        Pega uma frase de um array de Strings, com um índice inicial.
     */
    public static String getSentence(String[] args, int inicial) {
        String sentence = "";
        for (int i = inicial; i < args.length; i++) {
            sentence += args[i] + (i == (args.length - 1) ? "" : " ");
        }
        return sentence;
    }

    /*
        Envia uma mensagem para todos os jogadores.
     */
    public static void broadcast(String message) {
        for (ProxiedPlayer p : Main.getPlugin().getProxy().getPlayers()) {
            p.sendMessage(new TextComponent(message));
        }
    }

    /*
        Envia uma mensagem para todos os jogadores de determinado rank.
     */
    public static void broadcast(String message, Rank rank) {
        for (ProxiedPlayer p : Main.getPlugin().getProxy().getPlayers()) {
            if (!Rank.has(p.getUniqueId(), rank)) {
                continue;
            }
            p.sendMessage(new TextComponent(message));
        }
    }

    /*
        Limpa o chat do jogador.
     */
    public static void clearChat(ProxiedPlayer p) {
        for (int i = 0; i < 90; i++) {
            p.sendMessage(new TextComponent(" "));
        }
    }

    /*
        Envia uma mensagem de recurso destinado apenas para VIPs.
     */
    public static void onlyVip(ProxiedPlayer p) {
        Message.INFO.send(p, "Este recurso é destinado apenas para VIPs! Compre um em good-craft.net");
    }

    /*
        Transforma um número com apenas um digito em um com dois.
     */
    public static String doisDigitos(int number) {
        if (number == 0) {
            return "00";
        }
        if (number / 10 == 0) {
            return "0" + number;
        }
        return String.valueOf(number);
    }

    /*
        Transforma uma Integer de segundos em String no formato mm:ss.
     */
    public static String secondsToString(int seconds) {
        return String.format("%02d:%02d", seconds / 60, seconds % 60);
    }

    /*
        Transforma uma Integer de segundos em String no formato "mm minutos e ss segundos".
     */
    public static String secondsToSentence(int seconds) {
        if (seconds >= 86400) {
            int d = (int) TimeUnit.SECONDS.toDays(seconds);
            int h = (int) (TimeUnit.SECONDS.toHours(seconds) - (d * 24));
            int m = (int) (TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS
                    .toHours(seconds) * 60));
            int s = (int) (TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS
                    .toMinutes(seconds) * 60));
            return doisDigitos(d) + " dia(s), " + doisDigitos(h) + " hora(s), "
                    + doisDigitos(m) + " minuto(s) e " + doisDigitos(s)
                    + " segundo(s)";
        } else if (seconds >= 3600) {
            int h = (int) TimeUnit.SECONDS.toHours(seconds);
            int m = (int) (TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS
                    .toHours(seconds) * 60));
            int s = (int) (TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS
                    .toMinutes(seconds) * 60));

            return doisDigitos(h) + " hora(s), " + doisDigitos(m)
                    + " minuto(s) e " + doisDigitos(s) + " segundo(s)";
        } else {
            int m = (int) TimeUnit.SECONDS.toMinutes(seconds);
            int s = seconds - (m * 60);
            if ((m == 0) && (s != 0)) {
                if (s > 1) {
                    return doisDigitos(s) + " segundos";
                } else {
                    return doisDigitos(s) + " segundo";
                }
            } else if ((m != 0) && (s == 0)) {
                if (m > 1) {
                    return doisDigitos(m) + " minutos";
                } else {
                    return doisDigitos(m) + " minuto";
                }
            } else {
                if ((s > 1) && (m > 1)) {
                    return doisDigitos(m) + " minutos e " + s + " segundos";
                } else if ((s > 1) && (!(m > 1))) {
                    return doisDigitos(m) + " minuto e " + s + " segundos";
                } else if ((m > 1) && (!(s > 1))) {
                    return doisDigitos(m) + " minutos e " + s + " segundo";
                }
            }
            return doisDigitos(s) + " minutos e " + s + " segundos";
        }
    }
}
