package com.daan.pws.listeners;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.inventory.ItemStack;
import org.getspout.spoutapi.event.input.KeyPressedEvent;
import org.getspout.spoutapi.event.input.KeyReleasedEvent;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.InputListener;
import com.daan.pws.match.Competitive;
import com.daan.pws.match.CompetitiveGun;
import com.daan.pws.match.items.Bomb;
import com.daan.pws.match.listeners.BombListener;
import com.daan.pws.runnables.AutoGunTimer;
import com.daan.pws.scheduler.ERunnable;
import com.daan.pws.weapon.Gun;

public class LeftClickListener implements InputListener {

	public static LeftClickListener instance;

	private Map<String, ERunnable> fireTasks = new HashMap<String, ERunnable>();
	private Map<String, Long> cooldown = new HashMap<String, Long>();

	public LeftClickListener() {
		instance = this;
	}

	public void keyPressed(KeyPressedEvent evt) {
		SpoutPlayer p = evt.getPlayer();
		ItemStack is = p.getItemInHand();
		if (Competitive.isInMatch(p) && CompetitiveGun.isCompetitiveGun(is)) {
			final CompetitiveGun cGun = CompetitiveGun.getCompetitiveGun(is);
			Gun gun = cGun.getGun();
			if (gun.isAutomatic()) {
				if (cGun.getBulletsInMagazine() > 0 && !gun.isReloading(p)) {
					cGun.startShooting();
					fireTasks.put(p.getName(), new AutoGunTimer(p, gun) {

						@Override
						public void run() {
							if (Competitive.isInMatch(player) && CompetitiveGun.isCompetitiveGun(player.getItemInHand()) && CompetitiveGun.getCompetitiveGun(player.getItemInHand()) == cGun) {
								if (cGun.getBulletsInMagazine() > 0 && !gun.isReloading(player)) {
									this.gun.shootBulllet(player, cGun.getBullet());
								} else {
									if (!this.gun.isReloading(player))
										this.gun.reload(player);
									this.cancel();
									fireTasks.remove(player.getName());
									cGun.stopShooting();
								}
							} else {
								this.cancel();
								fireTasks.remove(player.getName());
								cGun.stopShooting();
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
					if (cGun.getBulletsInMagazine() > 0 && !gun.isReloading(p)) {
						gun.shootBulllet(p, cGun.getBullet());
						cooldown.put(p.getName(), (long) (System.currentTimeMillis() + (1000 / ((double) (Double.valueOf(gun.getRoundsPerMinute() + "") / Double.valueOf(60d))))));
					} else {
						if (gun.canReload(p)) {
							gun.reload(p);
						}
					}
				}
			}
		} else if (p.getItemInHand() != null && Bomb.isBomb(p.getItemInHand())) {
			if (Competitive.isInMatch(p)) {
				Competitive match = Competitive.getMatch(p);
				if (match.getMap().getABombSite().isInRegion(p) || match.getMap().getBBombSite().isInRegion(p)) {
					if (p.isOnGround()) {
						BombListener.instance.onPressLeftClickBomb(match.getCompetitivePlayer(p));
					}
				}
			}
		}
	}

	public void keyReleased(KeyReleasedEvent evt) {
		SpoutPlayer p = evt.getPlayer();
		if (fireTasks.containsKey(p.getName())) {
			fireTasks.get(p.getName()).cancel();
			fireTasks.remove(p.getName());
			if (CompetitiveGun.isCompetitiveGun(p.getItemInHand())) {
				CompetitiveGun.getCompetitiveGun(p.getItemInHand()).stopShooting();
			}
		}
		if (p.getItemInHand() != null && Bomb.isBomb(p.getItemInHand())) {
			if (Competitive.isInMatch(p)) {
				BombListener.instance.onReleaseLeftClickBomb(Competitive.getMatch(p).getCompetitivePlayer(p));
			}
		}
	}

}
