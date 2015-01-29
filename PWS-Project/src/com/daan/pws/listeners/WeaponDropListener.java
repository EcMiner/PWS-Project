package com.daan.pws.listeners;

import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.match.Competitive;
import com.daan.pws.match.CompetitiveGun;
import com.daan.pws.weapon.WeaponManager;

public class WeaponDropListener implements BindingExecutionDelegate {

	public WeaponDropListener() {
		SpoutManager.getKeyBindingManager().registerBinding("Weapon Drop", Keyboard.KEY_G, "This will drop the weapon you're holding", this, Main.getInstance());
	}

	@Override
	public void keyPressed(KeyBindingEvent evt) {
	}

	@Override
	public void keyReleased(KeyBindingEvent evt) {
		SpoutPlayer player = evt.getPlayer();
		if (WeaponManager.isGun(player.getItemInHand())) {
			if (CompetitiveGun.isCompetitiveGun(player.getItemInHand()) && Competitive.isInMatch(player)) {
				CompetitiveGun.getCompetitiveGun(player.getItemInHand()).drop(Competitive.getMatch(player).getCompetitivePlayer(player));
			}
		}
	}
}
