package com.daan.pws.gun;

public abstract class DamagePattern {

	public abstract double onBulletHit(PlayerHeight height);

	public static enum PlayerHeight {

		FEET(0f, 0.2f), LOWER_LEGS(0.2f, 0.4f), KNEES(.4f, .45f), UPPER_LEGS(.45f, .65f);

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
