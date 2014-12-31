package com.daan.pws.match;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.match.enums.TeamEnum;
import com.daan.pws.match.map.CompetitiveMap;

public class Competitive {

	private static final Map<UUID, Competitive> playersInMatch = new HashMap<UUID, Competitive>();

	public static Competitive getMatch(Player player) {
		return getMatch(player.getUniqueId());
	}

	public static Competitive getMatch(UUID uuid) {
		return playersInMatch.get(uuid);
	}

	public static boolean isInMatch(SpoutPlayer player) {
		return isPlayerInMatch(player.getUniqueId());
	}

	public static boolean isPlayerInMatch(UUID uuid) {
		return playersInMatch.containsKey(uuid);
	}

	private final CompetitiveMap map;
	private final CompetitiveTeam counter_terrorists, terrorists;

	public Competitive(CompetitiveMap map) {
		this.map = map;
		this.map.setInUse(true);
		this.counter_terrorists = new CompetitiveTeam(TeamEnum.COUNTER_TERRORISTS, 5);
		this.terrorists = new CompetitiveTeam(TeamEnum.TERRORISTS, 5);
	}

	public CompetitiveMap getMap() {
		return map;
	}

	public CompetitiveTeam getCounterTerrorists() {
		return counter_terrorists;
	}

	public CompetitiveTeam getTerrorists() {
		return terrorists;
	}

	public void addPlayer(SpoutPlayer player, TeamEnum team) {
		if (team != null) {
			if (team == TeamEnum.COUNTER_TERRORISTS) {
				counter_terrorists.addPlayer(player);
			} else {
				terrorists.addPlayer(player);
			}
			playersInMatch.put(player.getUniqueId(), this);
		}
	}

	public void removePlayer(SpoutPlayer player) {
		if (player != null) {
			if (counter_terrorists.hasPlayer(player)) {
				counter_terrorists.removePlayer(player);
			} else if (terrorists.hasPlayer(player)) {
				terrorists.removePlayer(player);
			}
			playersInMatch.remove(player.getUniqueId());
		}
	}

	public boolean isPlayerInMatch(SpoutPlayer player) {
		return counter_terrorists.hasPlayer(player) || terrorists.hasPlayer(player);
	}

	public TeamEnum getTeam(SpoutPlayer player) {
		return counter_terrorists.hasPlayer(player) ? TeamEnum.COUNTER_TERRORISTS : (terrorists.hasPlayer(player) ? TeamEnum.TERRORISTS : null);
	}

	public CompetitivePlayer getCompetitivePlayer(SpoutPlayer player) {
		return counter_terrorists.hasPlayer(player) ? counter_terrorists.getCompetitivePlayer(player) : (terrorists.hasPlayer(player) ? terrorists.getCompetitivePlayer(player) : null);
	}

	public void stopMatch() {
		this.map.setInUse(false);
	}

}
