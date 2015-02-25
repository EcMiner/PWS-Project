package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.RecoilPattern;
import com.daan.pws.weapon.WeaponType;

public class CZ75 extends Gun {

	public CZ75() {
		super("CZ-75 Auto", "http://panisme.nl/csgo/textures/cz75.png", 24, 12, 750, true, 500, WeaponType.PISTOLS, 2.7, "", "http://panisme.nl/csgo/icons/cz75_icon.png", 240, 77.65, 17);
		setDamagePattern(new DamagePattern() {

			@Override
			public int onBulletHit(PlayerHeight height) {
				switch (height) {
				case HEAD:
					return 131;
				case CHEST:
					return 32;
				case STOMACH:
					return 40;
				default:
					return 24;
				}
			}

		});
		setRecoilPattern(new RecoilPattern() {

			@Override
			protected float[] onGunShoot(int bullet) {
				switch (bullet) {
				case 1:
					return new float[] { 0, 0 };
				case 2:
					return new float[] { 2.8f, .1f };
				case 3:
					return new float[] { 4.2f, .05f };
				case 4:
					return new float[] { 2.2f, 1.2f };
				case 5:
					return new float[] { -2.6f, .7f };
				case 6:
					return new float[] { -9.1f, 2.5f };
				case 7:
					return new float[] { -1.2f, 2.55f };
				case 8:
					return new float[] { 4.5f, 4.75f };
				case 9:
					return new float[] { 13.8f, 6.4f };
				case 10:
					return new float[] { 5.6f, 10f };
				case 11:
					return new float[] { 14.1f, 13.5f };
				case 12:
					return new float[] { 16f, 17.05f };
				}
				return new float[] { 16f, 17.05f };
			}
		});
	}

}
