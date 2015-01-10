package com.daan.pws.entities;

import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.weapon.Grenade;
import com.daan.pws.weapon.Grenade.GrenadeExplosionTrackerType;

public class GrenadeEntity {

	private final Grenade grenade;
	private final SpoutPlayer player;
	private final Item grenadeEntity;

	private BukkitRunnable ticker;

	private Vector previousBallVelocity;

	public GrenadeEntity(Grenade grenade, SpoutPlayer player) {
		this.grenade = grenade;
		this.player = player;
		this.grenadeEntity = player.getWorld().dropItem(player.getEyeLocation(), new SpoutItemStack(grenade.getGrenadeItem()));
		this.grenadeEntity.setPickupDelay(Integer.MAX_VALUE);
		ticker = new BukkitRunnable() {

			@Override
			public void run() {
				if (!grenadeEntity.isDead()) {
					tick();
				} else {
					cancel();
				}
			}

		};
		ticker.runTaskTimer(Main.getInstance(), 0, 1);
	}

	private void tick() {
		Vector previousVelocity = previousBallVelocity != null ? previousBallVelocity : grenadeEntity.getVelocity();

		Vector currentVelocity = grenadeEntity.getVelocity();

		if (currentVelocity.getX() == 0) {
			if (grenade.getTrackingType() == GrenadeExplosionTrackerType.HIT) {
				grenade.onExplode(player, this);
				die();
				return;
			}
			currentVelocity.setX(-previousVelocity.getX() * 0.6D);
		}

		if ((currentVelocity.getY() == 0D || currentVelocity.getY() == -0.0D) && (previousVelocity.getY() > 0.08 || previousVelocity.getY() < -0.08)) {
			if (grenade.getTrackingType() == GrenadeExplosionTrackerType.HIT) {
				grenade.onExplode(player, this);
				die();
				return;
			}
			currentVelocity.setY(-previousVelocity.getY() * 0.6D);
		}

		if (currentVelocity.getZ() == 0D) {
			if (grenade.getTrackingType() == GrenadeExplosionTrackerType.HIT) {
				grenade.onExplode(player, this);
				die();
				return;
			}
			currentVelocity.setZ(-previousVelocity.getZ() * 0.6D);
		}

		if ((currentVelocity.getX() <= 0.08 && currentVelocity.getX() >= -0.08) && (currentVelocity.getY() <= 0.08 && currentVelocity.getY() >= -0.08) && (currentVelocity.getZ() <= 0.08 && currentVelocity.getZ() >= -0.08)) {
			currentVelocity.setX(0).setY(0).setZ(0);
			if (grenade.getTrackingType() == GrenadeExplosionTrackerType.NO_VELOCITY) {
				grenade.onExplode(player, this);
				die();
				return;
			} else if (grenade.getTrackingType() == GrenadeExplosionTrackerType.HIT) {
				grenade.onExplode(player, this);
				die();
				return;
			}
		}

		setVelocity(currentVelocity);

		previousBallVelocity = currentVelocity;

		if (grenade.getMaxLivingTicks() != -1 && grenadeEntity.getTicksLived() >= grenade.getMaxLivingTicks()) {
			grenade.onExplode(player, this);
			die();
			return;
		}
	}

	public Vector setVelocity(double x, double y, double z) {
		return setVelocity(new Vector(x, y, z));
	}

	public Vector setVelocity(Vector v) {
		grenadeEntity.setVelocity(v);
		return v;
	}

	public void die() {
		grenadeEntity.remove();
		ticker.cancel();
	}

	public SpoutPlayer getPlayer() {
		return player;
	}

	public Item getGrenadeEntity() {
		return grenadeEntity;
	}

	public Grenade getGrenade() {
		return grenade;
	}

}
