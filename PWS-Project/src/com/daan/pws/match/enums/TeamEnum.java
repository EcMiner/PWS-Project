package com.daan.pws.match.enums;

import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.GunManager;

public enum TeamEnum {

	COUNTER_TERRORISTS("USP-S"), TERRORISTS("Glock-18");

	private String defaultGunName;

	private TeamEnum(String defaultGunName) {
		this.defaultGunName = defaultGunName;
	}

	public Gun getDefaultGun() {
		return GunManager.getGun(defaultGunName);
	}

}
