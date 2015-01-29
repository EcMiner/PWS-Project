package com.daan.pws.weapon;

import org.bukkit.Location;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.player.SpoutPlayer;

public abstract class RecoilPattern {

	public RecoilPattern() {
	}

	public Vector getBulletDirection(SpoutPlayer player, int bullet) {
		float[] i = onGunShoot(bullet);
		if (i.length == 2) {
			float pitch = -i[1];
			float yaw = i[0];

			Location loc = player.getEyeLocation().clone();
			loc.setPitch(loc.getPitch() + pitch);
			loc.setYaw(loc.getYaw() + yaw);
			return loc.getDirection();
		}
		return new Vector(0, 0, 0);
	}

	protected abstract float[] onGunShoot(int bullet);

}
