package com.daan.pws.weapon;

public abstract class DamagePattern {

	public abstract double onBulletHit(PlayerHeight height);

	public static enum PlayerHeight {

		FEET(0f, 0f), LOWER_LEGS(0f, 0f), KNEES(0f, 0f), UPPER_LEGS(0f, 0f), CHEST(0f, 0f), STOMACH(0f, 0f), HEAD(0f, 0f), LEG(0f, 0f);

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
			return null;
		}

	}

}
