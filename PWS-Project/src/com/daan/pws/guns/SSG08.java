package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponType;

public class SSG08 extends Gun {

	public SSG08() {
		super("SSG-08", "http://panisme.nl/csgo/textures/ssg08.png", 90, 10, 48, false, 1700, WeaponType.RIFLES, 3.3, "http://localhost/csgo/weapons/ak47/ak47-1.ogg", "http://panisme.nl/csgo/icons/ssg08_icon.png", 230, 85, 12);
		setDamagePattern(new DamagePattern() {

			@Override
			public int onBulletHit(PlayerHeight height) {
				switch (height) {
				case HEAD:
					return 352;
				case CHEST:
					return 88;
				case STOMACH:
					return 110;
				default:
					return 66;
				}
			}
		});
		setZoomUrl("http://panisme.nl/csgo/others/sniperScreen.png");
		setZoomFactors(new int[] { 4 });
	}

}
