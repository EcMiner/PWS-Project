package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.RecoilPattern;
import com.daan.pws.weapon.WeaponType;

public class Famas extends Gun {

	public Famas() {
		super("Famas", "http://panisme.nl/csgo/textures/famas.png", 100, 25, 666, true, 2250, WeaponType.RIFLES, 3.3, "", "http://panisme.nl/csgo/icons/famas_icon.png", 220, 70, 21);
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
					return new float[] { 2.1f, .8f };
				case 3:
					return new float[] { 1.8f, 1.5f };
				case 4:
					return new float[] { 4, 3.1f };
				case 5:
					return new float[] { 4.2f, 5.9f };
				case 6:
					return new float[] { 4.1f, 9.45f };
				case 7:
					return new float[] { -2.4f, 12.4f };
				case 8:
					return new float[] { -7.8f, 14.3f };
				case 9:
					return new float[] { -5.6f, 16.3f };
				case 10:
					return new float[] { 2.9f, 17.55f };
				case 11:
					return new float[] { 8.2f, 18.55f };
				case 12:
					return new float[] { 12.6f, 18.8f };
				case 13:
					return new float[] { 11.1f, 19.8f };
				case 14:
					return new float[] { 3.4f, 20.4f };
				case 15:
					return new float[] { -2.4f, 21.3f };
				case 16:
					return new float[] { -9f, 20.8f };
				case 17:
					return new float[] { -10.9f, 21f };
				case 18:
					return new float[] { -15.9f, 20.95f };
				case 19:
					return new float[] { -16.9f, 21.75f };
				case 20:
					return new float[] { -15.2f, 22.25f };
				case 21:
					return new float[] { -7f, 22f };
				case 22:
					return new float[] { -6.1f, 22.5f };
				case 23:
					return new float[] { -9.8f, 22.3f };
				case 24:
					return new float[] { -14.6f, 21.1f };
				case 25:
					return new float[] { -20f, 19.5f };
				}
				return new float[] { -20f, 19.5f };
			}
		});
	}

}
