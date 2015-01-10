package com.daan.pws.weapon;

public abstract class DamagePattern {

	public abstract int onBulletHit(PlayerHeight height);

	public static enum PlayerHeight {

		FEET(1.734375f, 1.90f), LOWER_LEGS(1.503125f, 1.734375f), KNEES(1.3296875f, 1.503125f), UPPER_LEGS(1.15625f, 1.3296875f), STOMACH(.7515625f, 1.040625f), CHEST(.4625f, .7515625f), HEAD(-.1f, .4625f);

		private float startHeight;
		private float endHeight;

		private PlayerHeight(float startHeight, float endHeight) {
			this.startHeight = startHeight;
			this.endHeight = endHeight;
		}

		public float getStartHeight() {
			return startHeight;
		}

		public float getEndHeight() {
			return endHeight;
		}

		public static PlayerHeight getPlayerHeight(float height) {
			for (PlayerHeight h : values()) {
				if (height >= h.getStartHeight() && height < h.getEndHeight()) {
					return h;
				}
			}
			return PlayerHeight.FEET;
		}

	}

}
