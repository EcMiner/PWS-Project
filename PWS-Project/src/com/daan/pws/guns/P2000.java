package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponType;

public class P2000 extends Gun {

	public P2000() {
		super("P2000", "http://panisme.nl/csgo/textures/p2000.png", 65, 13, 352, false, 200, WeaponType.PISTOLS, 2.2, "", "http://panisme.nl/csgo/icons/p2000_icon.png", 240, 50.5, 19);
		setDamagePattern(new DamagePattern() {

			@Override
			public int onBulletHit(PlayerHeight height) {
				switch (height) {
				case HEAD:
					return 140;
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
