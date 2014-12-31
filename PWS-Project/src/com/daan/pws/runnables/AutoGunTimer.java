package com.daan.pws.runnables;

import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.scheduler.ERunnable;
import com.daan.pws.weapon.Gun;

public abstract class AutoGunTimer extends ERunnable {

	public final Gun gun;
	public final SpoutPlayer player;

	public AutoGunTimer(SpoutPlayer player, Gun gun) {
		this.gun = gun;
		this.player = player;

		long delay = Math.round((1000 / (gun.getRoundsPerMinute() / 60)));
		runTaskTimer(0, delay);
	}

	@Override
	public abstract void run();

}
