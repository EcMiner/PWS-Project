package com.daan.pws.match.listeners;

import org.bukkit.event.Listener;

import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.runnables.BombPlaceTimer;

public class BombListener implements Listener {

	public static BombListener instance;

	public BombListener() {
		instance = this;
	}

	public void onPressLeftClickBomb(CompetitivePlayer player) {
		if (BombPlaceTimer.map.containsKey(player.getPlayer().getName())) {
			BombPlaceTimer.map.get(player.getPlayer().getName()).cancel();
		}
		new BombPlaceTimer(player);
	}

	public void onReleaseLeftClickBomb(CompetitivePlayer player) {
		if (BombPlaceTimer.map.containsKey(player.getPlayer().getName())) {
			BombPlaceTimer.map.get(player.getPlayer().getName()).cancel();
		}
	}

}
