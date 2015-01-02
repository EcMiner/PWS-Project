package com.daan.pws.match;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.match.enums.TeamEnum;
import com.daan.pws.match.map.CompetitiveMap;

public class Competitive {

	private static final Map<UUID, Competitive> playersInMatch = new HashMap<UUID, Competitive>();
	private static final List<Competitive> matches = new ArrayList<Competitive>();
	private static final Random random = new Random();

	public static Collection<Competitive> getAllMatches() {
		return matches;
	}

	public static Competitive getMatch(Player player) {
		return getMatch(player.getUniqueId());
	}

	public static Competitive getMatch(UUID uuid) {
		return playersInMatch.get(uuid);
	}

	public static boolean isInMatch(SpoutPlayer player) {
		return isInMatch(player.getUniqueId());
	}

	public static boolean isInMatch(UUID uuid) {
		return playersInMatch.containsKey(uuid);
	}

	private final CompetitiveMap map;
	private final CompetitiveTeam counter_terrorists, terrorists;
	private boolean bombPlanted = false, gameStarted = false;

	public Competitive(CompetitiveMap map) {
		this.map = map;
		this.map.setInUse(true);
		this.counter_terrorists = new CompetitiveTeam(TeamEnum.COUNTER_TERRORISTS, 5);
		this.terrorists = new CompetitiveTeam(TeamEnum.TERRORISTS, 5);
		matches.add(this);
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

	public boolean canJoin() {
		return counter_terrorists.canAddPlayer() && terrorists.canAddPlayer();
	}

	public TeamEnum addPlayer(SpoutPlayer player) {
		if (counter_terrorists.canAddPlayer() && terrorists.canAddPlayer()) {
			if (counter_terrorists.getPlayers().size() > terrorists.getPlayers().size()) {
				addPlayer(player, TeamEnum.TERRORISTS);
				return TeamEnum.TERRORISTS;
			} else {
				addPlayer(player, TeamEnum.COUNTER_TERRORISTS);
				return TeamEnum.COUNTER_TERRORISTS;
			}
		} else {
			if (counter_terrorists.canAddPlayer()) {
				addPlayer(player, TeamEnum.COUNTER_TERRORISTS);
				return TeamEnum.COUNTER_TERRORISTS;
			} else if (terrorists.canAddPlayer()) {
				addPlayer(player, TeamEnum.TERRORISTS);
				return TeamEnum.TERRORISTS;
			}
		}
		return null;
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

	public boolean isBombPlanted() {
		return bombPlanted;
	}

	public void setBombPlanted(boolean bombPlanted) {
		this.bombPlanted = bombPlanted;
	}

	public void stopMatch() {
		this.map.setInUse(false);
		matches.remove(this);
	}

	public void startMatch() {
		
	}

	public boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	public void spawnAllPlayers() {
		spawnCounterTerrorists();
		spawnTerrorists();
	}

	public void spawnCounterTerrorists() {
		List<String> usedIndexes = new ArrayList<String>();
		Collection<CompetitivePlayer> ctPlayers = counter_terrorists.getPlayers();
		for (CompetitivePlayer player : ctPlayers) {
			int index = random.nextInt(ctPlayers.size());
			while (usedIndexes.contains(index + "")) {
				index = random.nextInt(ctPlayers.size());
			}
			player.getPlayer().teleport(map.getCounterTerroristsSpawns().get(index));
			usedIndexes.add(index + "");
		}
	}

	public void spawnTerrorists() {
		List<String> usedIndexes = new ArrayList<String>();
		Collection<CompetitivePlayer> tPlayers = terrorists.getPlayers();
		for (CompetitivePlayer player : tPlayers) {
			int index = random.nextInt(tPlayers.size());
			while (usedIndexes.contains(index + "")) {
				index = random.nextInt(tPlayers.size());
			}
			player.getPlayer().teleport(map.getTerroristsSpawns().get(index));
			usedIndexes.add(index + "");
		}
	}

}
