package com.daan.pws.match;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.getspout.spoutapi.player.SpoutPlayer;
import org.kitteh.tag.TagAPI;

import com.daan.pws.Main;
import com.daan.pws.match.enums.TeamEnum;
import com.daan.pws.match.hud.DeathsHud;
import com.daan.pws.match.hud.GameHud;
import com.daan.pws.match.hud.HealthHud;
import com.daan.pws.match.hud.MoneyHud;
import com.daan.pws.match.map.CompetitiveMap;
import com.daan.pws.weapon.Gun;

public class Competitive {

	private static final Map<String, Competitive> playersInMatch = new HashMap<String, Competitive>();
	private static final List<Competitive> matches = new ArrayList<Competitive>();
	private static final Random random = new Random();

	public static Collection<Competitive> getAllMatches() {
		return matches;
	}

	public static Competitive getMatch(Player player) {
		return getMatch(player.getName());
	}

	public static Competitive getMatch(String name) {
		return playersInMatch.get(name);
	}

	public static boolean isInMatch(Player player) {
		return isInMatch(player.getName());
	}

	public static boolean isInMatch(String name) {
		return playersInMatch.containsKey(name);
	}

	private final CompetitiveMap map;
	private final CompetitiveTimer timer;
	private final CompetitiveTeam counter_terrorists, terrorists;
	private boolean gameStarted = false;
	private SpoutPlayer host;
	private int inRound = 1;
	private final DeathsHud deathsHud;

	private Map<String, Location> oldLocs = new HashMap<String, Location>();
	private List<Item> droppedItems = new ArrayList<Item>();
	private Block bombBlock;

	public Competitive(CompetitiveMap map, SpoutPlayer host) {
		this.map = map;
		this.map.setInUse(true);
		this.timer = new CompetitiveTimer(this);
		this.counter_terrorists = new CompetitiveTeam(TeamEnum.COUNTER_TERRORISTS, 5, this);
		this.terrorists = new CompetitiveTeam(TeamEnum.TERRORISTS, 5, this);
		this.host = host;
		this.deathsHud = new DeathsHud();
		matches.add(this);
	}

	public SpoutPlayer getHost() {
		return host;
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
			if (counter_terrorists.getPlayers().size() == 0 && terrorists.getPlayers().size() == 0) {
				TeamEnum team = random.nextBoolean() ? TeamEnum.TERRORISTS : TeamEnum.COUNTER_TERRORISTS;
				addPlayer(player, team);
				return team;
			}

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
			deathsHud.addReceiever(player);
			playersInMatch.put(player.getName(), this);
		}
		TagAPI.refreshPlayer(player);
	}

	public void removePlayer(SpoutPlayer player) {
		if (player != null) {
			if (counter_terrorists.hasPlayer(player)) {
				counter_terrorists.removePlayer(player);
			} else if (terrorists.hasPlayer(player)) {
				terrorists.removePlayer(player);
			}
			deathsHud.removeReceiver(player);
			playersInMatch.remove(player.getName());
		}
		TagAPI.refreshPlayer(player);
	}

	public void addDeath(String killers, String killed, Gun gun, boolean headshot) {
		this.deathsHud.addDeath(killers, killed, gun, headshot);
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
		return timer.isBombPlanted();
	}

	public void stopMatch() {
		this.map.setInUse(false);
		matches.remove(this);
		for (CompetitivePlayer player : counter_terrorists.getPlayers()) {
			if (oldLocs.containsKey(player.getPlayer().getName())) {
				player.getPlayer().teleport(oldLocs.get(player.getPlayer().getName()));
				GameHud.removeGameHud(player.getPlayer());
				HealthHud.removeHealth(player.getPlayer());
				MoneyHud.removeMoney(player.getPlayer());
				deathsHud.removeReceiver(player.getPlayer());
			}
		}
		for (CompetitivePlayer player : terrorists.getPlayers()) {
			if (oldLocs.containsKey(player.getPlayer().getName())) {
				player.getPlayer().teleport(oldLocs.get(player.getPlayer().getName()));
				GameHud.removeGameHud(player.getPlayer());
				HealthHud.removeHealth(player.getPlayer());
				MoneyHud.removeMoney(player.getPlayer());
				deathsHud.removeReceiver(player.getPlayer());
			}
		}
		oldLocs.clear();
		timer.cancel();
	}

	public void startMatch() {
		giveRandomBomb();
		this.gameStarted = true;
		List<String> terrorists = new ArrayList<String>();
		List<String> counter_terrorists = new ArrayList<String>();
		terrorists.addAll(this.terrorists.getPlayerNames());
		counter_terrorists.addAll(this.counter_terrorists.getPlayerNames());
		terrorists.addAll(Arrays.asList("Kierano1000", "JackoDEJ", "bball0298", "schmockyyy"));
		counter_terrorists.addAll(Arrays.asList("thomashomsy", "PheysHunt", "Scude2", "EfficiencyPvP"));
		for (CompetitivePlayer player : this.counter_terrorists.getPlayers()) {
			oldLocs.put(player.getPlayer().getName(), player.getPlayer().getLocation());
			GameHud.updateGameHud(player.getPlayer(), 15, 0, 0, terrorists, counter_terrorists);
			HealthHud.updateHealth(player.getPlayer(), 100, 0);
			MoneyHud.updateMoney(player.getPlayer(), 800);
			player.resetPlayer();
			GameHud.updateIsPlayAlive(player.getPlayer(), "Kierano1000", false);
			GameHud.updateIsPlayAlive(player.getPlayer(), "PheysHunt", false);
			GameHud.updateIsPlayAlive(player.getPlayer(), "EfficiencyPvP", false);
		}
		for (CompetitivePlayer player : this.terrorists.getPlayers()) {
			oldLocs.put(player.getPlayer().getName(), player.getPlayer().getLocation());
			GameHud.updateGameHud(player.getPlayer(), 15, 0, 0, terrorists, counter_terrorists);
			MoneyHud.updateMoney(player.getPlayer(), 800);
			player.resetPlayer();
			GameHud.updateIsPlayAlive(player.getPlayer(), "Kierano1000", false);
			GameHud.updateIsPlayAlive(player.getPlayer(), "PheysHunt", false);
			GameHud.updateIsPlayAlive(player.getPlayer(), "Scude2", false);
		}

		spawnAllPlayers();
		timer.start();
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

	public void winRound(TeamEnum team) {
		if (team != null) {
			if (bombBlock != null) {
				bombBlock.setType(Material.AIR);
				bombBlock = null;
			}
			if (team == TeamEnum.COUNTER_TERRORISTS) {
				this.counter_terrorists.addWin();
				for (CompetitivePlayer player : counter_terrorists.getPlayers()) {
					player.resetPlayer();
					GameHud.updateCtWins(player.getPlayer(), this.counter_terrorists.getWins());
					GameHud.resetAllPlayers(player.getPlayer());
				}
				for (CompetitivePlayer player : terrorists.getPlayers()) {
					player.resetPlayer();
					GameHud.updateCtWins(player.getPlayer(), this.counter_terrorists.getWins());
					GameHud.resetAllPlayers(player.getPlayer());
				}
			} else {
				this.terrorists.addWin();
				for (CompetitivePlayer player : counter_terrorists.getPlayers()) {
					player.resetPlayer();
					GameHud.updateTWins(player.getPlayer(), this.terrorists.getWins());
					GameHud.resetAllPlayers(player.getPlayer());
				}
				for (CompetitivePlayer player : terrorists.getPlayers()) {
					player.resetPlayer();
					GameHud.updateTWins(player.getPlayer(), this.terrorists.getWins());
					GameHud.resetAllPlayers(player.getPlayer());
				}
			}
		}
		inRound++;
		timer.setPaused(true);
		timer.updateTimeVisibility(false);
		timer.updateBombActive(false);
		new BukkitRunnable() {

			@Override
			public void run() {
				timer.reset();
				destroyItems();
				giveRandomBomb();
			}

		}.runTaskLater(Main.getInstance(), 100l);
	}

	private void giveRandomBomb() {
		int randomIndex = random.nextInt(terrorists.getPlayers().size());
		int i = 0;
		for (CompetitivePlayer player : terrorists.getPlayers()) {
			if (player.getLoadout().hasBomb()) {
				player.getLoadout().setHasBomb(false);
			}
			if (i == randomIndex) {
				player.getLoadout().setHasBomb(true);
			}
			i++;
		}
	}

	public void plantBomb(CompetitivePlayer player) {
		this.bombBlock = player.getPlayer().getLocation().getBlock();
		player.getPlayer().getLocation().getBlock().setType(Material.DAYLIGHT_DETECTOR);
		player.getPlayer().getInventory().setItem(8, null);
		player.getLoadout().setHasBomb(false);
		timer.plantBomb();
	}

	public void defuseBomb(CompetitivePlayer player) {
		winRound(TeamEnum.COUNTER_TERRORISTS);
	}

	public void explodeBomb() {
		bombBlock.getWorld().createExplosion(bombBlock.getX(), bombBlock.getY(), bombBlock.getZ(), 10, false, false);
	}

	public Block getBombBlock() {
		return bombBlock;
	}

	public int getTotalAmountOfPlayers() {
		return counter_terrorists.getPlayers().size() + terrorists.getPlayers().size();
	}

	public int getInRound() {
		return inRound;
	}

	public void addDroppedItem(Item item) {
		droppedItems.add(item);
	}

	public void removeDroppedItem(Item item) {
		droppedItems.remove(item);
	}

	private void destroyItems() {
		for (Item item : droppedItems) {
			item.remove();
		}
		droppedItems.clear();
	}

	public CompetitiveTimer getTimer() {
		return timer;
	}

	public CompetitiveTeam getCompetitiveTeam(TeamEnum team) {
		return team != null ? (team == TeamEnum.TERRORISTS ? terrorists : counter_terrorists) : null;
	}
}
