package com.sanjay900.jnb;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.sanjay900.jnb.utils.WonderlandPlayer;

public class JnbGame {
	
	public jnb plugin;
	public Scoreboard board;
	public String world;
	Objective objective;
	public ArrayList<WonderlandPlayer> players= new ArrayList<>();
	public boolean hasStarted = false;
	public JnbGame(jnb plugin, World world) {
		this.plugin = plugin;
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		
		this.world = world.getName();
	}
	public void startgame(WonderlandPlayer... players) {
		if (hasStarted) return;
		hasStarted = true;

		board = Bukkit.getScoreboardManager().getNewScoreboard();
		objective = board.registerNewObjective("Score", "dummy");
		objective.setDisplaySlot(DisplaySlot.SIDEBAR);
		objective.setDisplayName("Score:");
		this.players.addAll(Arrays.asList(players));
		for (WonderlandPlayer p: players) {
			p.game = this;
			objective.getScore(p.getPlayer().getName()).setScore(0);
			p.getPlayer().setScoreboard(board);
			p.respawn();
		}
		
	}
	public void stopGame(){
		if (!hasStarted) return;
		hasStarted = false;
		plugin.players.removeAll(players);
		for (WonderlandPlayer p: players) {
			p.game = this;
			p.getPlayer().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
		
	}
	public void addScore(Player killer) {
		objective.getScore(killer.getName()).setScore(objective.getScore(killer.getName()).getScore()+1);
	}
	public void sendMessageToAll(String string) {
		for (WonderlandPlayer p : players) {
			p.getPlayer().sendMessage(string);
		}
	}
	
}
