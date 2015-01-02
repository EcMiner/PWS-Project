package com.daan.pws.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.event.input.KeyBindingEvent;
import org.getspout.spoutapi.keyboard.BindingExecutionDelegate;
import org.getspout.spoutapi.keyboard.Keyboard;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.match.listeners.BombListener;
import com.daan.pws.runnables.AutoGunTimer;
import com.daan.pws.scheduler.ERunnable;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.GunManager;

public class GunFireListener implements BindingExecutionDelegate, Listener {

	private Map<String, ERunnable> fireTasks = new HashMap<String, ERunnable>();
	private Map<String, Long> cooldown = new HashMap<String, Long>();

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
				if (GunManager.getMagazine(p, gun) > 0 && !gun.isReloading(p)) {
					fireTasks.put(p.getName(), new AutoGunTimer(p, gun) {

						@Override
						public void run() {
							if (GunManager.isGun(player.getItemInHand()) && GunManager.getGun(player.getItemInHand()).getName().equals(gun.getName())) {
								if (GunManager.getMagazine(player, gun) > 0 && !gun.isReloading(player)) {
									this.gun.shootBulllet(player);
								} else {
									if (!this.gun.isReloading(player))
										this.gun.reload(player);
									this.cancel();
									fireTasks.remove(player.getName());
								}
							} else {
								this.cancel();
								fireTasks.remove(player.getName());
							}
						}

					});
				} else {
					if (gun.canReload(p)) {
						gun.reload(p);
					}
				}
			} else {
				if (!cooldown.containsKey(p.getName()) || System.currentTimeMillis() > cooldown.get(p.getName())) {
					if (GunManager.getMagazine(p, gun) > 0 && !gun.isReloading(p)) {
						gun.shootBulllet(p);
						cooldown.put(p.getName(), (long) (System.currentTimeMillis() + (1000 / ((double) (gun.getRoundsPerMinute() / 60)))));
					} else {
						if (gun.canReload(p)) {
							gun.reload(p);
						}
					}
				}
			}
		} else if (p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Bomb")) {
			BombListener.instance.onPressLeftClickBomb(p);
		}
	}

	@Override
	public void keyReleased(KeyBindingEvent evt) {
		SpoutPlayer p = evt.getPlayer();
		if (fireTasks.containsKey(p.getName())) {
			fireTasks.get(p.getName()).cancel();
			fireTasks.remove(p.getName());
		}
		if (p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("Bomb")) {
			BombListener.instance.onReleaseLeftClickBomb(p);
		}
	}

}
