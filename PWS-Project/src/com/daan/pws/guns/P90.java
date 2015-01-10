package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.RecoilPattern;
import com.daan.pws.weapon.WeaponType;

public class P90 extends Gun {

	public P90() {
		super("P90", "http://panisme.nl/csgo/textures/p90.png", 150, 50, 857, true, 300, WeaponType.SMG, 3.3, "localhost/csgo/weapons/p90/p90-1.wav", "http://panisme.nl/csgo/icons/p90_icon.png", 230, 69d);
		setDamagePattern(new DamagePattern() {

			@Override
			public int onBulletHit(PlayerHeight height) {
				switch (height) {
				case HEAD:
					return 103;
				case CHEST:
					return 25;
				case STOMACH:
					return 32;
				default:
					return 19;
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
					return new float[] { 0.5f, 0.5f };
				case 3:
					return new float[] { 0.4f, 1f };
				case 4:
					return new float[] { 0.1f, 1.5f };
				case 5:
					return new float[] { 1f, 3.5f };
				case 6:
					return new float[] { 4f, 6f };
				case 7:
					return new float[] { 8f, 8f };
				case 8:
					return new float[] { 11f, 10.5f };
				case 9:
					return new float[] { 8.2f, 13f };
				case 10:
					return new float[] { 5f, 15f };
				case 11:
					return new float[] { 4.5f, 17f };
				case 12:
					return new float[] { 3.5f, 19f };
				case 13:
					return new float[] { 4.5f, 20.25f };
				case 14:
					return new float[] { 9, 20.35f };
				case 15:
					return new float[] { 10.5f, 21 };
				case 16:
					return new float[] { 13f, 21.25f };
				case 17:
					return new float[] { 16f, 21.25f };
				case 18:
					return new float[] { 12f, 21.5f };
				case 19:
					return new float[] { 7f, 21.75f };
				case 20:
					return new float[] { 3f, 22f };
				case 21:
					return new float[] { 0.5f, 22.25f };
				case 22:
					return new float[] { -3f, 22f };
				case 23:
					return new float[] { -8f, 21.5f };
				case 24:
					return new float[] { -6f, 21.35f };
				case 25:
					return new float[] { -7.8f, 21.5f };
				case 26:
					return new float[] { -4f, 21.75f };
				case 27:
					return new float[] { 0, 21.5f };
				case 28:
					return new float[] { 6f, 21.25f };
				case 29:
					return new float[] { 6.8f, 21.5f };
				case 30:
					return new float[] { 5f, 21.8f };
				case 31:
					return new float[] { 6.5f, 22.25f };
				case 32:
					return new float[] { 8.3f, 22.75f };
				case 33:
					return new float[] { 6f, 23 };
				case 34:
					return new float[] { 1f, 22.9f };
				case 35:
					return new float[] { -0.5f, 22.75f };
				case 36:
					return new float[] { 0.2f, 22.9f };
				case 37:
					return new float[] { 0.5f, 23f };
				case 38:
					return new float[] { 4f, 22.7f };
				case 39:
					return new float[] { 8f, 22.5f };
				case 40:
					return new float[] { 7.5f, 22.7f };
				case 41:
					return new float[] { 3f, 22.7f };
				case 42:
					return new float[] { -1.5f, 22f };
				case 43:
					return new float[] { -4.5f, 21.9f };
				case 44:
					return new float[] { -8.5f, 21.75f };
				case 45:
					return new float[] { -13.5f, 21.25f };
				case 46:
					return new float[] { -16.5f, 21.35f };
				case 47:
					return new float[] { -20.5f, 20.75f };
				case 48:
					return new float[] { -20f, 21.20f };
				case 49:
					return new float[] { -15f, 21.75f };
				case 50:
					return new float[] { -6.5f, 21.5f };
				}
				return new float[] { -6.5f, 21.5f };
			}
		});
	}
}
