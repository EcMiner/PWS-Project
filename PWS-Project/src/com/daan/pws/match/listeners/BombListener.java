package com.daan.pws.match.listeners;

import org.bukkit.event.Listener;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.runnables.BombPlaceTimer;

public class BombListener implements Listener {

	public static BombListener instance;

	public BombListener() {
		instance = this;
	}

	public void onPressLeftClickBomb(SpoutPlayer player) {
		if (BombPlaceTimer.map.containsKey(player.getName())) {
			BombPlaceTimer.map.get(player.getName()).cancel();
		}
		new BombPlaceTimer(player);
	}

	public void onReleaseLeftClickBomb(SpoutPlayer player) {
		if (BombPlaceTimer.map.containsKey(player.getName())) {
			BombPlaceTimer.map.get(player.getName()).cancel();
		}
	}

}
