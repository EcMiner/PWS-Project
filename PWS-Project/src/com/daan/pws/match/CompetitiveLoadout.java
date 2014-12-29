package com.daan.pws.match;

import com.daan.pws.match.enums.TeamEnum;

public class CompetitiveLoadout {

	private TeamEnum team;

	private CompetitiveGun primary = null;
	private CompetitiveGun secondary = null;

	private boolean hasHelmet = false;
	private boolean hasKevlar = false;

	private boolean hasBomb = false;
	private boolean hasDefuseKit = false;

	public CompetitiveLoadout(TeamEnum team) {
		this.team = team;
		this.secondary = new CompetitiveGun(team.getDefaultGun());
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

	public CompetitiveGun getPrimary() {
		return primary;
	}

	public CompetitiveGun getSecondary() {
		return secondary;
	}

	public void setPrimary(CompetitiveGun primary) {
		this.primary = primary;
	}

	public void setSecondary(CompetitiveGun secondary) {
		this.secondary = secondary;
	}

}
