package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponType;

public class Glock18 extends Gun {

	public Glock18() {
		super("Glock-18", "http://panisme.nl/csgo/textures/glock18.png", 120, 20, 400, false, 200, WeaponType.PISTOLS, 2.2, "http://localhost/csgo/weapons/glock18/glock18-1.ogg", "http://panisme.nl/csgo/icons/glock18_icon.png", 240, 47d, 22);
		setDamagePattern(new DamagePattern() {

			@Override
			public int onBulletHit(PlayerHeight height) {
				switch (height) {
				case HEAD:
					return 111;
				case CHEST:
					return 27;
				case STOMACH:
					return 34;
				default:
					return 21;
				}
			}

		});
	}

}
