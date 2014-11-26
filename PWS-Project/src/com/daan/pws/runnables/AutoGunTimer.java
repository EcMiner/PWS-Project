package com.daan.pws.runnables;

import org.bukkit.scheduler.BukkitRunnable;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.weapon.Gun;

public abstract class AutoGunTimer extends BukkitRunnable {

	public final Gun gun;
	public final SpoutPlayer player;

	public AutoGunTimer(SpoutPlayer player, Gun gun) {
		this.gun = gun;
		this.player = player;
		runTaskTimer(Main.getInstance(), 0, Math.round((20 / (gun.getRoundsPerMinute() / 60))));
	}

	@Override
	public abstract void run();

}
