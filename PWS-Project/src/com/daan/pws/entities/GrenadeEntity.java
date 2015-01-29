package com.daan.pws.entities;

import org.bukkit.craftbukkit.v1_6_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_6_R3.inventory.CraftItemStack;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.inventory.SpoutItemStack;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.match.Competitive;
import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.weapon.Grenade;
import com.daan.pws.weapon.DamagePattern.PlayerHeight;
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

		EntityCustomItem entityGrenade = new EntityCustomItem(((CraftWorld) player.getWorld()).getHandle(), player.getEyeLocation().getX(), player.getEyeLocation().getY(), player.getEyeLocation().getZ(), CraftItemStack.asNMSCopy(new SpoutItemStack(grenade.getGrenadeItem())));
		((CraftWorld) player.getWorld()).getHandle().addEntity(entityGrenade);

		this.grenadeEntity = (Item) entityGrenade.getBukkitEntity();
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
		// Run deze timer elke tick, 1 tick = 50 ms. Een Minecraft server stuurt 20 updates per second, het is dus een 20 tick server, en je kan nu uitrekenen
		// Hoe lang 1 tick is. Je weet namelijk 1000ms is 1 sec, en in 1 sec worden er 20 updates gestuurt. Dus duut 1 tick 1000 / 20 = 50 ms.
		ticker.runTaskTimer(Main.getInstance(), 0, 1);
	}

	// Simpele manier om ervoor te zorgen dat granaten tegen muren afstoten
	// Wat ik doe, is ik sla de Velocity op die ze de vorige keer hadden, daarna check ik of hun huidige x, y en z velocities 0 zijn, als dit zo is
	// Betekent het dat ze tegen een muur aangekomen zijn, en dan zet ik hun x, y en z velocity gelijk aan de min x, y en z velocity van de vorige
	// Velocity van de granaat. Verder zorg ik er ook voor dat de granaten bij een bepaalde voorwaarde ontploffen.
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
			if (grenade.getTrackingType() == GrenadeExplosionTrackerType.HIT || grenade.getTrackingType() == GrenadeExplosionTrackerType.HIT_GROUND) {
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
			} else if (grenade.getTrackingType() == GrenadeExplosionTrackerType.HIT || grenade.getTrackingType() == GrenadeExplosionTrackerType.HIT_GROUND) {
				grenade.onExplode(player, this);
				die();
				return;
			}
		}

		if (!grenadeEntity.isOnGround()) {
			if (Competitive.isInMatch(this.player)) {
				for (Entity nearby : grenadeEntity.getNearbyEntities(0.2, 0.2, 0.2)) {
					if (nearby instanceof SpoutPlayer && nearby != this.player) {
						SpoutPlayer player = (SpoutPlayer) nearby;
						if (Competitive.isInMatch(player)) {
							CompetitivePlayer cPlayer = Competitive.getMatch(player).getCompetitivePlayer(player);
							if (cPlayer.getMatch().equals(Competitive.getMatch(this.player))) {
								currentVelocity.setX(-previousVelocity.getX() * .01).setZ(-previousVelocity.getZ() * .01).setY(.05);
								if (cPlayer.getTeam() != Competitive.getMatch(this.player).getCompetitivePlayer(this.player).getTeam()) {
									if (cPlayer.getArmour() > 0) {
										cPlayer.damageArmour(1);
									} else {
										cPlayer.damageHealth(Competitive.getMatch(this.player).getCompetitivePlayer(this.player), 1, PlayerHeight.FEET);
									}
								}
							}
						}
					}
				}
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
