package com.daan.pws.match;

import org.bukkit.scheduler.BukkitRunnable;

import com.daan.pws.Main;
import com.daan.pws.match.hud.GameHud;
import com.daan.pws.utilities.PlayerUtil;

public class CompetitiveTimer extends BukkitRunnable {

	private Competitive match;
	private boolean started = false;

	public CompetitiveTimer(Competitive match) {
		this.match = match;
	}

	int pregame = 5;
	int ingame = 180;
	int bombtimer = -1;

	@Override
	public void run() {
		if (bombtimer != -1) {
			if (pregame > 0) {
				updateTime(pregame);
				--pregame;
			} else {
				if (ingame > 0) {
					if (ingame == 180) {
						unfreezeAll();
					}
					updateTime(ingame);
					--ingame;
				} else {
					// Make counter-terrorists win.
				}
			}
		} else {
			if (bombtimer > 0) {
				--bombtimer;
			} else {
				// Make terrorists win and explode bomb.
			}
		}
	}

	public void reset() {
		this.pregame = 5;
		this.ingame = 180;
		freezeAll();
	}

	private void updateTime(int time) {
		for (CompetitivePlayer ctPlayer : match.getCounterTerrorists().getPlayers()) {
			GameHud.updateTime(ctPlayer.getPlayer(), time);
		}
		for (CompetitivePlayer tPlayer : match.getTerrorists().getPlayers()) {
			GameHud.updateTime(tPlayer.getPlayer(), time);
		}
	}

	private void freezeAll() {
		for (CompetitivePlayer ctPlayer : match.getCounterTerrorists().getPlayers()) {
			PlayerUtil.freeze(ctPlayer.getPlayer());
		}
		for (CompetitivePlayer tPlayer : match.getTerrorists().getPlayers()) {
			PlayerUtil.freeze(tPlayer.getPlayer());
		}
	}

	private void unfreezeAll() {
		for (CompetitivePlayer ctPlayer : match.getCounterTerrorists().getPlayers()) {
			PlayerUtil.unfreeze(ctPlayer.getPlayer());
		}
		for (CompetitivePlayer tPlayer : match.getTerrorists().getPlayers()) {
			PlayerUtil.unfreeze(tPlayer.getPlayer());
		}
	}

	public void start() {
		if (!started) {
			this.started = true;
			runTaskTimer(Main.getInstance(), 0, 20);
		} else {
			try {
				throw new Exception("Timer already started!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void plantBomb() {
		this.bombtimer = 45;
	}

}
