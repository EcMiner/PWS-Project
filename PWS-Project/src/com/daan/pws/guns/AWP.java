package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponType;

public class AWP extends Gun {

	public AWP() {
		super("AWP", "http://panisme.nl/csgo/textures/awp.png", 40, 10, 41, false, 4750, WeaponType.RIFLES, 3.6, "http://localhost/csgo/weapons/ak47/ak47-1.ogg", "http://panisme.nl/csgo/icons/awp_icon.png", 210, 97.5);
		setZoomUrl("http://panisme.nl/csgo/others/sniperScreen.png");
		setDamagePattern(new DamagePattern() {

			@Override
			public int onBulletHit(PlayerHeight height) {
				switch (height) {
				case HEAD:
					return 460;
				case CHEST:
					return 115;
				case STOMACH:
					return 143;
				default:
					return 86;
				}
			}

		});
	}
}
