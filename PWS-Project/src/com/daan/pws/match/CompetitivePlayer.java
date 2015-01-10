package com.daan.pws.match;

import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.match.enums.TeamEnum;

public class CompetitivePlayer {

	private final SpoutPlayer player;
	private TeamEnum team;
	private final CompetitiveLoadout loadout;
	private int money = 800, health = 100, armour = 0;
	private final Competitive match;

	public CompetitivePlayer(SpoutPlayer player, TeamEnum team, Competitive match) {
		this.player = player;
		this.team = team;
		this.loadout = new CompetitiveLoadout(team);
		this.match = match;
	}

	public SpoutPlayer getPlayer() {
		return player;
	}

	public TeamEnum getTeam() {
		return team;
	}

	public void setTeam(TeamEnum team) {
		this.team = team;
	}

	public CompetitiveLoadout getLoadout() {
		return loadout;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getArmour() {
		return armour;
	}

	public void setArmour(int armour) {
		this.armour = armour;
	}

	public Competitive getMatch() {
		return match;
	}

}
