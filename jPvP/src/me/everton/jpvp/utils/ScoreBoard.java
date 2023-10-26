package me.everton.jpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

public class ScoreBoard {
	public static void criarScoreboard(Player p) {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		/*  ^      ^^          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		Tipo de  Nome da               M�todo que retorna um
		objeto  vari�vel                   Scoreboard
		*/
		
		Objective ob = sb.registerNewObjective("NomeDoObj", "dummy");//Ordem: Nome do Objetivo(M�ximo de 16 caracteres), Tipo
		/*  ^     ^^          ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		Tipo de  Nome da      M�todo que registra um Objetivo
		objeto  vari�vel    Tipos em: http://minecraft.gamepedia.com/Scoreboard#Objectives
		*/
		
		ob.setDisplaySlot(DisplaySlot.SIDEBAR);//Ordem: Display Slot do Objetivo
		/*  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
          M�todo que seta onde o Objetivo ser� 
        mostrado: SIDEBAR, BELOW_NAME ou PLAYER_LIST
        http://minecraft.gamepedia.com/Scoreboard#Display_slots
		*/
		
		ob.setDisplayName("�lPress�3�lStart");//Ordem: Display Name do Objetivo
		/*  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        M�todo que seta o nome que ser� visualizado do Objetivo, o nome a ser mostrado. 
        Pode-se usar ChatColor ou simplificar e usar �(se faz pressionando ALT + 21 no teclado num�rico)
        M�ximo de 16 caracteres
		*/
		
		ob.getScore("MensagemAqui").setScore(14);//Ordem: Mensagem da "Linha", Score(no tipo primitivo int)
		/*  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
        M�todo que registra os Scores, ou seja, adiciona as "linhas" nas SIDEBAR
        Lembrando que se for Spigot, pode-se usar apenas assim em String diretamente, mas no caso
        do Bukkit, h� a necessidade de usar: ob.getScore(Bukkit.getOfflinePlayer("MensagemAqui")).setScore(14);
        Lembrando que s�o no m�ximo 16 caracteres e que os maiores n�meros como Score ficam em cima e vice-versa
		*/
		
		p.setScoreboard(sb);//Ordem: Scoreboard � ser setada ao jogador
		/*  ^^^^^^^^^^^^^
        M�todo que seta o Scoreboard de um determinado jogador que nesse caso est� definido como a
        vari�vel 'p'.
		*/
	}
}
