package com.daan.pws.match;

import com.daan.pws.match.enums.TeamEnum;

public class CompetitiveLoadout {

	private TeamEnum team;
	private Competitive match;

	private CompetitiveGun primary = null;
	private CompetitiveGun secondary = null;

	private boolean hasHelmet = false;
	private boolean hasKevlar = false;

	private boolean hasBomb = false;
	private boolean hasDefuseKit = false;

	public CompetitiveLoadout(TeamEnum team, Competitive match) {
		this.team = team;
		this.match = match;
		this.secondary = new CompetitiveGun(team.getDefaultGun(), match);
	}

	public boolean hasBomb() {
		return team != TeamEnum.COUNTER_TERRORISTS && hasBomb;
	}

	public boolean hasDefuseKit() {
		return team != TeamEnum.TERRORISTS && hasDefuseKit;
	}

	public boolean hasHelmet() {
		return hasHelmet;
	}

	public boolean hasKevlar() {
		return hasKevlar;
	}

	public void setHasHelmet(boolean hasHelmet) {
		this.hasHelmet = hasHelmet;
	}

	public void setHasKevlar(boolean hasKevlar) {
		this.hasKevlar = hasKevlar;
	}

	public void setHasBomb(boolean hasBomb) {
		this.hasBomb = hasBomb;
	}

	public CompetitiveGun getPrimary() {
		return primary;
	}

	public CompetitiveGun getSecondary() {
		return secondary;
	}

	public boolean hasPrimary() {
		return this.primary != null;
	}

	public void setPrimary(CompetitiveGun primary) {
		this.primary = primary;
	}

	public boolean hasSecondary() {
		return this.secondary != null;
	}

	public void setSecondary(CompetitiveGun secondary) {
		this.secondary = secondary;
	}

	public Competitive getMatch() {
		return match;
	}

}
