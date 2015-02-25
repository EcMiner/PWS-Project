package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.RecoilPattern;
import com.daan.pws.weapon.WeaponType;

public class GalilAR extends Gun {

	public GalilAR() {
		super("Galil AR", "http://panisme.nl/csgo/textures/galilar.png", 140, 35, 666, true, 2000, WeaponType.RIFLES, 3.0, "", "http://panisme.nl/csgo/icons/galilar_icon.png", 215, 77.5, 20);
		setDamagePattern(new DamagePattern() {

			@Override
			public int onBulletHit(PlayerHeight height) {
				switch (height) {
				case HEAD:
					return 120;
				case CHEST:
					return 30;
				case STOMACH:
					return 37;
				default:
					return 22;
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
					return new float[] { 2.4f, .75f };
				case 3:
					return new float[] { 1.5f, 1.5f };
				case 4:
					return new float[] { -2.8f, 3.2f };
				case 5:
					return new float[] { -6.8f, 5.55f };
				case 6:
					return new float[] { -7.4f, 9.05f };
				case 7:
					return new float[] { -8, 13.1f };
				case 8:
					return new float[] { -10.1f, 16 };
				case 9:
					return new float[] { -13.8f, 17.5f };
				case 10:
					return new float[] { -12.2f, 19.2f };
				case 11:
					return new float[] { -4f, 21.25f };
				case 12:
					return new float[] { 7f, 20.55f };
				case 13:
					return new float[] { 16.6f, 18.75f };
				case 14:
					return new float[] { 9.8f, 19.8f };
				case 15:
					return new float[] { 23.6f, 20.3f };
				case 16:
					return new float[] { 25.6f, 20.3f };
				case 17:
					return new float[] { 26.8f, 20.5f };
				case 18:
					return new float[] { 24.5f, 21.75f };
				case 19:
					return new float[] { 16f, 22.9f };
				case 20:
					return new float[] { 11.4f, 23.5f };
				case 21:
					return new float[] { 3.1f, 22.9f };
				case 22:
					return new float[] { -9.1f, 21.55f };
				case 23:
					return new float[] { -11, 22 };
				case 24:
					return new float[] { -7.1f, 22.55f };
				case 25:
					return new float[] { -11.2f, 22.2f };
				case 26:
					return new float[] { -14.8f, 22.05f };
				case 27:
					return new float[] { -19.8f, 21.55f };
				case 28:
					return new float[] { -17f, 22.3f };
				case 29:
					return new float[] { -6.2f, 21.45f };
				case 30:
					return new float[] { 3, 21 };
				case 31:
					return new float[] { 8.5f, 21.9f };
				case 32:
					return new float[] { 6.5f, 23.25f };
				case 33:
					return new float[] { 11.2f, 22.6f };
				case 34:
					return new float[] { 19.1f, 20.3f };
				case 35:
					return new float[] { 23f, 20.05f };
				}
				return new float[] { 23f, 20.05f };
			}
		});
	}
}
