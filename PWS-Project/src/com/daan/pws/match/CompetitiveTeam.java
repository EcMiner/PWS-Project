package com.daan.pws.match;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.match.enums.TeamEnum;

public class CompetitiveTeam {

	private final TeamEnum team;
	private final int maxPlayers;
	private int wins;
	private Map<UUID, CompetitivePlayer> players = new HashMap<UUID, CompetitivePlayer>();

	public CompetitiveTeam(TeamEnum team, int maxPlayers) {
		this.team = team;
		this.maxPlayers = maxPlayers;
	}

	public TeamEnum getTeam() {
		return team;
	}

	public Collection<CompetitivePlayer> getPlayers() {
		return players.values();
	}

	protected void addPlayer(SpoutPlayer player) {
		players.put(player.getUniqueId(), new CompetitivePlayer(player, team));
	}

	protected void removePlayer(SpoutPlayer player) {
		players.remove(player.getUniqueId());
	}

	public boolean hasPlayer(SpoutPlayer player) {
		return players.containsKey(player.getUniqueId());
	}

	public int getMaxPlayers() {
		return maxPlayers;
	}

	public boolean canAddPlayer() {
		return players.size() < maxPlayers;
	}

	public CompetitivePlayer getCompetitivePlayer(SpoutPlayer player) {
		return players.get(player.getUniqueId());
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

}
