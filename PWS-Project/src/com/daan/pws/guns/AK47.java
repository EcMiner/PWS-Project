package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.RecoilPattern;
import com.daan.pws.weapon.WeaponType;

public class AK47 extends Gun {

	public AK47() {
		super("AK-47", "http://panisme.nl/csgo/textures/ak47.png", 120, 30, 600, true, 2500, WeaponType.RIFLES, 2.5, "http://localhost/csgo/weapons/ak47/ak47-1.ogg", "http://panisme.nl/csgo/icons/ak47_icon.png", 197, 77.5);
		setDamagePattern(new DamagePattern() {

			@Override
			public int onBulletHit(PlayerHeight height) {
				switch (height) {
				case HEAD:
					return 143;
				case CHEST:
					return 35;
				case STOMACH:
					return 44;
				default:
					return 26;
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
					return new float[] { .5f, 0.875f };
				case 3:
					return new float[] { 0, 4f };
				case 4:
					return new float[] { .25f, 8.75f };
				case 5:
					return new float[] { .5f, 13.75f };
				case 6:
					return new float[] { -2.5f, 19.25f };
				case 7:
					return new float[] { -5f, 23.75f };
				case 8:
					return new float[] { -9.5f, 27f };
				case 9:
					return new float[] { -4f, 29.5f };
				case 10:
					return new float[] { 8.5f, 28.875f };
				case 11:
					return new float[] { 15.8f, 29.25f };
				case 12:
					return new float[] { 11.5f, 30.5f };
				case 13:
					return new float[] { 16.5f, 32f };
				case 14:
					return new float[] { 25.2f, 30.75f };
				case 15:
					return new float[] { 26.2f, 31.5f };
				case 16:
					return new float[] { 13f, 31.75f };
				case 17:
					return new float[] { 6.5f, 33f };
				case 18:
					return new float[] { 2f, 34.625f };
				case 19:
					return new float[] { -5.2f, 34.5f };
				case 20:
					return new float[] { -16.2f, 33f };
				case 21:
					return new float[] { -10.25f, 32.5f };
				case 22:
					return new float[] { 33, -11.5f };
				case 23:
					return new float[] { -10.5f, 33.85f };
				case 24:
					return new float[] { -6.5f, 35.25f };
				case 25:
					return new float[] { -12.8f, 34.625f };
				case 26:
					return new float[] { -15f, 35.5f };
				case 27:
					return new float[] { -8f, 35.35f };
				case 28:
					return new float[] { 1f, 35f };
				case 29:
					return new float[] { 16.2f, 31.5f };
				case 30:
					return new float[] { 21f, 31.5f };
				default:
					return new float[] { 21f, 31.5f };
				}
			}
		});
	}
}
