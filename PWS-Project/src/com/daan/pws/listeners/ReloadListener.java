package com.daan.pws.listeners;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.match.Competitive;
import com.daan.pws.match.CompetitiveGun;
import com.daan.pws.weapon.Gun;

public class ReloadListener implements BindingExecutionDelegate {

	public ReloadListener() {
		SpoutManager.getKeyBindingManager().registerBinding("Reload", Keyboard.KEY_R, "This will reload the gun you're holding", this, Main.getInstance());
	}

	@Override
	public void keyPressed(KeyBindingEvent evt) {
	}

	@Override
	public void keyReleased(KeyBindingEvent evt) {
		SpoutPlayer player = evt.getPlayer();
		if (Competitive.isInMatch(player) && CompetitiveGun.isCompetitiveGun(player.getItemInHand())) {
			Gun gun = CompetitiveGun.getCompetitiveGun(player.getItemInHand()).getGun();
			if (gun.canReload(player)) {
				gun.reload(player);
			}
		}
	}

}
