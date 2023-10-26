package me.everton.tigerpvp;

import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JButton;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Janela_Principal extends JFrame{
	public Janela_Principal(){
		setTitle("TigerPvP");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 500);
		setLocationRelativeTo(null);
		setResizable(true);
		setVisible(true);
		setLayout(new FlowLayout());
		final JButton bt1 = new JButton("Denúncias");
		bt1.setVisible(true);
		bt1.setEnabled(true);
	    add(bt1);
	    bt1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
			}
		});
	}
}
