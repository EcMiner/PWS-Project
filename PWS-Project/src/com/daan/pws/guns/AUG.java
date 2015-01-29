package com.daan.pws.guns;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.RecoilPattern;
import com.daan.pws.weapon.WeaponType;

public class AUG extends Gun {

	public AUG() {
		super("AUG", "http://panisme.nl/csgo/textures/aug.png", 90, 30, 666, true, 3300, WeaponType.RIFLES, 3.8, "http://localhost/csgo/weapons/ak47/ak47-1.ogg", "http://panisme.nl/csgo/icons/aug_icon.png", 221, 90d, 19);
		setZoomUrl("http://panisme.nl/csgo/others/dotsite.png");
		setZoomFactors(new int[] { 2 });
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
		setRecoilPattern(new RecoilPattern() {

			@Override
			protected float[] onGunShoot(int bullet) {
				switch (bullet) {
				case 1:
					return new float[] { 0, 0 };
				case 2:
					return new float[] { -0.8f, 0.8f };
				case 3:
					return new float[] { -0.8f, 3f };
				case 4:
					return new float[] { 0f, 6.55f };
				case 5:
					return new float[] { 1.2f, 11f };
				case 6:
					return new float[] { 0.5f, 15.75f };
				case 7:
					return new float[] { -1.6f, 20.875f };
				case 8:
					return new float[] { -6f, 24.25f };
				case 9:
					return new float[] { -8.5f, 26.9f };
				case 10:
					return new float[] { -12.8f, 28.95f };
				case 11:
					return new float[] { -7.5f, 30.9f };
				case 12:
					return new float[] { -5.8f, 31.8f };
				case 13:
					return new float[] { -10.2f, 31.75f };
				case 14:
					return new float[] { -10.6f, 32.4f };
				case 15:
					return new float[] { -2.8f, 33.5f };
				case 16:
					return new float[] { 7.5f, 31.375f };
				case 17:
					return new float[] { 18f, 29.4f };
				case 18:
					return new float[] { 19.5f, 30.5f };
				case 19:
					return new float[] { 20.8f, 31.5f };
				case 20:
					return new float[] { 23.5f, 31.4f };
				case 21:
					return new float[] { 15.4f, 31.6f };
				case 22:
					return new float[] { 4.8f, 32.2f };
				case 23:
					return new float[] { .2f, 33.1f };
				case 24:
					return new float[] { 1.6f, 33.25f };
				case 25:
					return new float[] { -2f, 32.4f };
				case 26:
					return new float[] { -12f, 30.6f };
				case 27:
					return new float[] { -17.2f, 30.6f };
				case 28:
					return new float[] { -13f, 31.45f };
				case 29:
					return new float[] { -7.2f, 31.9f };
				case 30:
					return new float[] { -6f, 32.2f };
				default:
					return new float[] { -6f, 32.2f };
				}
			}
		});
	}

}
