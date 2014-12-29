package com.daan.pws.match;

import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.match.enums.TeamEnum;

public class CompetitivePlayer {

	private final SpoutPlayer player;
	private TeamEnum team;
	private final CompetitiveLoadout loadout;

	public CompetitivePlayer(SpoutPlayer player, TeamEnum team) {
		this.player = player;
		this.team = team;
		this.loadout = new CompetitiveLoadout(team);
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

}
