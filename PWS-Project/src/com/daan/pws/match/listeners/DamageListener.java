package com.daan.pws.match.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.daan.pws.events.PlayerShotEvent;
import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.weapon.DamagePattern.PlayerHeight;
import com.daan.pws.weapon.Gun;

public class DamageListener implements Listener {

	@EventHandler
	public void onPlayerDamage(PlayerShotEvent evt) {
		CompetitivePlayer damaged = evt.getDamaged();
		CompetitivePlayer shooter = evt.getShooter();
		Gun gun = evt.getGun();

		if (damaged.getMatch() == shooter.getMatch()) {
			if (evt.getHeight() == PlayerHeight.HEAD) {
				if (damaged.getLoadout().hasHelmet()) {
					int newDamage = (int) Math.round((double) (evt.getDamage() * (gun.getArmourPenetration() / 100)));
					evt.setDamage(newDamage);
					// TODO Change armour.
				}
			} else if (evt.getHeight() == PlayerHeight.CHEST || evt.getHeight() == PlayerHeight.STOMACH) {
				if (damaged.getLoadout().hasKevlar()) {
					int newDamage = (int) Math.round((double) (evt.getDamage() * (gun.getArmourPenetration() / 100)));
					evt.setDamage(newDamage);
					// TODO Change armour.
				}
			}

			if (damaged.getTeam() == shooter.getTeam()) {
				evt.setDamage(evt.getDamage() / 2);
			}
		} else {
			evt.setCancelled(true);
		}
	}

}
