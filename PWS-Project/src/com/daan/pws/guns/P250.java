package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponType;

public class P250 extends Gun {

	public P250() {
		super("P250", "http://panisme.nl/csgo/textures/p250.png", 39, 13, 400, false, 300, WeaponType.PISTOLS, 2.2, "", "http://panisme.nl/csgo/icons/p250_icon.png", 240, 77.65, 18);
		setDamagePattern(new DamagePattern() {

			@Override
			public int onBulletHit(PlayerHeight height) {
				switch (height) {
				case HEAD:
					return 138;
				case CHEST:
					return 34;
				case STOMACH:
					return 43;
				default:
					return 26;
				}
			}

		});
	}

}
