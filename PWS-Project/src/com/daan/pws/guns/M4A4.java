package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.RecoilPattern;
import com.daan.pws.weapon.WeaponType;

public class M4A4 extends Gun {

	public M4A4() {
		super("M4A4", "http://panisme.nl/csgo/textures/m4a4.png", 120, 30, 666, true, 3100, WeaponType.RIFLES, 3.1, "", "http://panisme.nl/csgo/icons/m4a4_icon.png", 225, 70, 20);
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
					return new float[] { -1.6f, 1.35f };
				case 3:
					return new float[] { -1.6f, 2.35f };
				case 4:
					return new float[] { 1, 5.5f };
				case 5:
					return new float[] { -1.8f, 9 };
				case 6:
					return new float[] { 2.8f, 13 };
				case 7:
					return new float[] { 4.2f, 17.25f };
				case 8:
					return new float[] { -3, 19.9f };
				case 9:
					return new float[] { -6.4f, 22f };
				case 10:
					return new float[] { -13.6f, 22.8f };
				case 11:
					return new float[] { -2.2f, 24.6f };
				case 12:
					return new float[] { -6.4f, 25.7f };
				case 13:
					return new float[] { 5.4f, 24.95f };
				case 14:
					return new float[] { 13.4f, 24.9f };
				case 15:
					return new float[] { 22, 23.45f };
				case 16:
					return new float[] { 22, 24.7f };
				case 17:
					return new float[] { 19, 25.3f };
				case 18:
					return new float[] { 22.8f, 25.5f };
				case 19:
					return new float[] { 27.8f, 25 };
				case 20:
					return new float[] { 26.5f, 25.25f };
				case 21:
					return new float[] { 15.2f, 25.4f };
				case 22:
					return new float[] { 12, 26.5f };
				case 23:
					return new float[] { 3.1f, 26.95f };
				case 24:
					return new float[] { -1.8f, 27.2f };
				case 25:
					return new float[] { -5.5f, 27.3f };
				case 26:
					return new float[] { -2, 27.2f };
				case 27:
					return new float[] { -3.8f, 28.1f };
				case 28:
					return new float[] { -5.1f, 28.9f };
				case 29:
					return new float[] { -6, 29f };
				case 30:
					return new float[] { -7.5f, 28.75f };
				}
				return new float[] { -7.5f, 28.75f };
			}
		});
	}

}
