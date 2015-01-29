package com.daan.pws.match;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.match.enums.TeamEnum;

public class CompetitiveTeam {

	private final Competitive match;
	private final TeamEnum team;
	private final int maxPlayers;
	private int wins;
	private Map<String, CompetitivePlayer> players = new HashMap<String, CompetitivePlayer>();

	public CompetitiveTeam(TeamEnum team, int maxPlayers, Competitive match) {
		this.team = team;
		this.maxPlayers = maxPlayers;
		this.match = match;
	}

	public TeamEnum getTeam() {
		return team;
	}

	public Collection<CompetitivePlayer> getPlayers() {
		return players.values();
	}
	
	public Collection<String> getPlayerNames() {
		return players.keySet();
	}

	protected void addPlayer(SpoutPlayer player) {
		players.put(player.getName(), new CompetitivePlayer(player, team, match));
	}

	protected void removePlayer(SpoutPlayer player) {
		players.remove(player.getName());
	}

	public boolean hasPlayer(SpoutPlayer player) {
		return players.containsKey(player.getName());
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public boolean canAddPlayer() {
		return players.size() < maxPlayers;
	}

	public CompetitivePlayer getCompetitivePlayer(SpoutPlayer player) {
		return players.get(player.getName());
	}

	public int getWins() {
		return wins;
	}

	public void setWins(int wins) {
		this.wins = wins;
	}

	public void addWins(int wins) {
		this.wins += wins;
	}

	public void addWin() {
		this.wins += 1;
	}

	public int getPlayersAlive() {
		int i = players.size();
		for (CompetitivePlayer player : players.values()) {
			if (!player.isAlive()) {
				i--;
			}
		}
		return i;
	}

}
