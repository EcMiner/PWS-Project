package com.daan.pws.match;

import org.bukkit.scheduler.BukkitRunnable;

public class CompetitiveTimer extends BukkitRunnable {

	private Competitive match;

	public CompetitiveTimer(Competitive match) {
		this.match = match;
	}

	int pregame = 5;
	int ingame = 180;

	@Override
	public void run() {
		if (pregame > 0) {
			
		} else {
			if (ingame > 0) {

			}
		}
	}

	public void reset() {
		this.pregame = 5;
		this.ingame = 180;
	}

}
