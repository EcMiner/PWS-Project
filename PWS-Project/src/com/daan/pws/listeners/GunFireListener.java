package com.daan.pws.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.runnables.AutoGunTimer;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.GunManager;

public class GunFireListener implements BindingExecutionDelegate, Listener {

	private Map<String, BukkitRunnable> fireTasks = new HashMap<String, BukkitRunnable>();

	public GunFireListener() {
		SpoutManager.getKeyBindingManager().registerBinding("Gun Fire", Keyboard.MOUSE_LEFT, "This will fire the gun you're holding", this, Main.getInstance());
		Bukkit.getServer().getPluginManager().registerEvents(this, Main.getInstance());
	}

	@Override
	public void keyPressed(KeyBindingEvent evt) {
		SpoutPlayer p = evt.getPlayer();
		ItemStack is = p.getItemInHand();
		if (GunManager.isGun(is)) {
			Gun gun = GunManager.getGun(is);
			if (gun.isAutomatic()) {
				fireTasks.put(p.getName(), new AutoGunTimer(p, gun) {

					@Override
					public void run() {
						if (GunManager.isGun(player.getItemInHand()) && GunManager.getGun(player.getItemInHand()).getName().equals(gun.getName())) {
							// TODO Check if player has more than 0 bullets.
							this.gun.shootBulllet(player);
						} else {
							this.cancel();
							fireTasks.remove(player.getName());
						}
					}

				});
			} else {
				// TODO Cooldown for non-automatic guns.
				gun.shootBulllet(p);
			}
		}
	}

	@Override
	public void keyReleased(KeyBindingEvent evt) {
		SpoutPlayer p = evt.getPlayer();
		if (fireTasks.containsKey(p.getName())) {
			fireTasks.get(p.getName()).cancel();
			fireTasks.remove(p.getName());
		}
	}

}
