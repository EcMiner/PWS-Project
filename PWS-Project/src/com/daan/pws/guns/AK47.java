package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponType;

public class AK47 extends Gun {

	public AK47() {
		super("AK-47", "http://i.imgur.com/gGFQM3z.png", 120, 30, 600, true, 2500, WeaponType.RIFLES, 2.5, "http://localhost/csgo/weapons/ak47/ak47-1.ogg", "http://i.imgur.com/CxXkYUu.png", 197);
		setDamagePattern(new DamagePattern() {

			@Override
			public double onBulletHit(PlayerHeight height) {
				switch (height) {
				case HEAD:
					return 143d;
				case CHEST:
					return 35d;
				case STOMACH:
					return 44d;
				default:
					return 26d;
				}
			}

		});
	}
}
