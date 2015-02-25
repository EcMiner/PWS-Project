package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponType;

public class DesertEagle extends Gun {

	public DesertEagle() {
		super("Desert Eagle", "http://panisme.nl/csgo/textures/deserteagle.png", 42, 7, 267, false, 700, WeaponType.PISTOLS, 2.2, "", "http://panisme.nl/csgo/icons/deserteagle_icon.png", 230, 93.2, 3);
		setDamagePattern(new DamagePattern() {

			@Override
			public int onBulletHit(PlayerHeight height) {
				switch (height) {
				case HEAD:
					return 249;
				case CHEST:
					return 62;
				case STOMACH:
					return 77;
				default:
					return 46;
				}
			}
		});
	}
}
