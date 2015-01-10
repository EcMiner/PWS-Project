package com.daan.pws.match.enums;

import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponManager;

public enum TeamEnum {

	COUNTER_TERRORISTS("P2000", "Counter Terrorists"), TERRORISTS("Glock-18", "Terrorists");

	private String defaultGunName;
	private String name;

	private TeamEnum(String defaultGunName, String name) {
		this.defaultGunName = defaultGunName;
		this.name = name;
	}

	public Gun getDefaultGun() {
		return WeaponManager.getGun(defaultGunName);
	}

	public String getName() {
		return name;
	}

}
