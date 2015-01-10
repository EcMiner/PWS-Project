package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponType;

public class AUG extends Gun {

	public AUG() {
		super("AUG", "http://panisme.nl/csgo/textures/aug.png", 90, 30, 666, true, 3300, WeaponType.RIFLES, 3.8, "http://localhost/csgo/weapons/ak47/ak47-1.ogg", "http://panisme.nl/csgo/icons/aug_icon.png", 221, 90d);
		setZoomUrl("http://panisme.nl/csgo/others/dotsite.png");
		setDamagePattern(new DamagePattern() {

			@Override
			public int onBulletHit(PlayerHeight height) {
				switch (height) {
				case HEAD:
					return 112;
				case CHEST:
					return 28;
				case STOMACH:
					return 35;
				default:
					return 21;
				}
			}

		});
	}

}
