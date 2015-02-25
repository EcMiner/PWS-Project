package com.daan.pws.guns;

import java.util.Random;

import com.daan.pws.weapon.DamagePattern;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.RecoilPattern;
import com.daan.pws.weapon.WeaponType;

public class PPBizon extends Gun {

	private static final Random random = new Random();

	public PPBizon() {
		super("PP-Bizon", "http://panisme.nl/csgo/textures/ppbizon.png", 192, 64, 750, true, 1400, WeaponType.SMG, 2.4, "", "http://panisme.nl/csgo/icons/ppbizon_icon.png", 240, 57.5, 21);
		setDamagePattern(new DamagePattern() {

			@Override
			public int onBulletHit(PlayerHeight height) {
				switch (height) {
				case HEAD:
					return 108;
				case CHEST:
					return 27;
				case STOMACH:
					return 33;
				default:
					return 20;
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
					return new float[] { 1.4f, .75f };
				case 3:
					return new float[] { 2.2f, 1.25f };
				case 4:
					return new float[] { 3.5f, 2.5f };
				case 5:
					return new float[] { 6f, 4.5f };
				case 6:
					return new float[] { 10.1f, 6.95f };
				case 7:
					return new float[] { 9.1f, 10.2f };
				case 8:
					return new float[] { 6.1f, 13.3f };
				case 9:
					return new float[] { 6f, 16.05f };
				case 10:
					return new float[] { -2.1f, 17f };
				case 11:
					return new float[] { -6.8f, 17.8f };
				case 12:
					return new float[] { -8.6f, 19.25f };
				case 13:
					return new float[] { -13.1f, 19.55f };
				case 14:
					return new float[] { -11.1f, 20.8f };
				case 15:
					return new float[] { -2.6f, 20.6f };
				}
				float rand1 = (float) ((random.nextBoolean() ? -1 : 1) * (Math.random() * 15));
				float rand2 = (float) ((random.nextBoolean() ? -1 : 1) * (Math.random() * 3.75));
				return new float[] { -2.6f + rand1, 20.6f + rand2 };
			}
		});
	}
}
