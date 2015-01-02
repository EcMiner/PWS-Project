package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.RecoilPattern;
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
		setRecoilPattern(new RecoilPattern() {

			@Override
			public int[] onGunShoot(int bullet) {
				switch (bullet) {
				case 1:
					return new int[] { 0, 0 };
				case 2:
					return new int[] { 2, 2 };
				case 3:
					return new int[] { 0, 5 };
				case 4:
					return new int[] { 2, 13 };
				case 5:
					return new int[] { 3, 21 };
				case 6:
					return new int[] { -4, 29 };
				case 7:
					return new int[] { -8, 35 };
				case 8:
					return new int[] { -15, 40 };
				case 9:
					return new int[] { -7, 43 };
				case 10:
					return new int[] { 20, 45 };
				case 11:
					return new int[] { 28, 46 };
				case 12:
					return new int[] { 24, 48 };
				case 13:
					return new int[] { 30, 49 };
				case 14:
					return new int[] { 40, 45 };
				case 15:
					return new int[] { 41, 46 };
				case 16:
					return new int[] { 25, 49 };
				case 17:
					return new int[] { 18, 50 };
				case 18:
					return new int[] { 4, 52 };
				case 19:
					return new int[] { -7, 51 };
				case 20:
					return new int[] { -30, 48 };
				}
				return new int[] { 0, 0 };
			}
		});
	}
}
