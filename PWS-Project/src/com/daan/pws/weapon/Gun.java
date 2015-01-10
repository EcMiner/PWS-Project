package com.daan.pws.weapon;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.material.item.GenericCustomTool;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.hud.HealthHud;
import com.daan.pws.particle.ParticleEffects;
import com.daan.pws.scheduler.ERunnable;
import com.daan.pws.weapon.DamagePattern.PlayerHeight;

public class Gun extends Weapon {

	private List<String> reloading = new ArrayList<String>();

	private String shootUrl, iconUrl, zoomUrl;
	private GunItem gunItem;
	private int totalAmmo, bulletsInRound, roundsPerMinute, price;
	private DamagePattern damagePattern;
	private RecoilPattern recoilPattern;
	private boolean automatic;
	private WeaponType weaponType;
	private final double reloadTime, armourPenetration;
	private int iconWidth;
	private int iconHeight;

	public Gun(String name, String URL, int totalAmmo, int bulletsInRound, int roundsPerMinute, boolean automatic, int price, WeaponType weaponType, double reloadTime, String shootUrl, String iconUrl, float movementSpeed, double armourPenetration) {
		super(name, URL, movementSpeed);
		this.gunItem = new GunItem(name, URL, (int) ((20 * reloadTime)));
		this.price = price;
		Validate.isTrue(totalAmmo % bulletsInRound == 0, "The total ammo must be dividable by the bulletsInRound");
		this.totalAmmo = totalAmmo;
		this.bulletsInRound = bulletsInRound;
		this.roundsPerMinute = roundsPerMinute;
		this.automatic = automatic;
		this.weaponType = weaponType;
		this.reloadTime = reloadTime;
		this.shootUrl = shootUrl;
		this.iconUrl = iconUrl;
		this.armourPenetration = armourPenetration;

		new ERunnable() {

			@Override
			public void run() {
				try {
					URL url = new URL(Gun.this.getIconUrl());
					BufferedImage img = ImageIO.read(url);
					Gun.this.iconWidth = img.getWidth();
					Gun.this.iconHeight = img.getHeight();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		}.runTaskLater(0);
	}

	public int getIconHeight() {
		return iconHeight;
	}

	public int getIconWidth() {
		return iconWidth;
	}

	public String getIconUrl() {
		return iconUrl;
	}

	public String getShootUrl() {
		return shootUrl;
	}

	public GunItem getGunItem() {
		return gunItem;
	}

	public int getTotalAmmo() {
		return totalAmmo;
	}

	public int getBulletsInRound() {
		return bulletsInRound;
	}

	public int getRoundsPerMinute() {
		return roundsPerMinute;
	}

	public boolean isAutomatic() {
		return automatic;
	}

	public int getPrice() {
		return price;
	}

	public WeaponType getWeaponType() {
		return weaponType;
	}

	public double getReloadTime() {
		return reloadTime;
	}

	public double getArmourPenetration() {
		return armourPenetration;
	}

	public String getZoomUrl() {
		return zoomUrl;
	}

	public void setZoomUrl(String zoomUrl) {
		this.zoomUrl = zoomUrl;
	}

	public boolean isZoomable() {
		return this.zoomUrl != null;
	}

	public DamagePattern getDamagePattern() {
		return damagePattern;
	}

	public void setDamagePattern(DamagePattern damagePattern) {
		this.damagePattern = damagePattern;
	}

	public RecoilPattern getRecoilPattern() {
		return recoilPattern;
	}

	public void setRecoilPattern(RecoilPattern recoilPattern) {
		this.recoilPattern = recoilPattern;
	}

	public void playShootSound(SpoutPlayer player) {
		SpoutManager.getSoundManager().playGlobalCustomMusic(Main.getInstance(), shootUrl, false, player.getEyeLocation(), 50);
	}

	int x = 0;

	@SuppressWarnings("deprecation")
	public final void shootBulllet(final SpoutPlayer player) {
		x++;
		WeaponManager.shootBullet(player, this);
		playShootSound(player);

		final Location loc = player.getEyeLocation();
		Vector toAdd = getRecoilPattern() != null ? getRecoilPattern().getBulletDirection(player, x) : loc.getDirection();

		// Vector pVelocity = PlayerUtil.getVelocity(player);
		// Vector v = new Vector(-((Math.random()) * (pVelocity.getX())), -(((Math.random() * 2) * pVelocity.getY()) + ((new Random().nextBoolean() ? -1 : 1) * Math.random() / 10)), -((Math.random()) * (pVelocity.getZ())));
		// toAdd.add(v);

		for (int i = 0; i < 300; i++) {
			loc.add(toAdd);
			if (!loc.getBlock().getType().isSolid() || canGoThrough(loc)) {
				if (i % 3 == 0) {
					try {
						try {
							ParticleEffects.CRIT.sendToPlayer(player, loc, 0, 0, 0, 0.0f, 1);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} catch (ConcurrentModificationException e) {
					}
					for (Entity e : loc.getChunk().getEntities()) {
						if (e instanceof Player && e != player) {
							Player t = (Player) e;
							if (loc.getY() <= t.getLocation().getY() + 1.95 && loc.getY() >= t.getLocation().getY()) {
								if (loc.distanceSquared(t.getLocation()) <= 3 || loc.distanceSquared(t.getEyeLocation()) <= 3) {
									double height = (t.getLocation().getY() + 1.85) - loc.getY();

									double damage = 10d;
									if (this.getDamagePattern() != null) {
										damage = this.getDamagePattern().onBulletHit(PlayerHeight.getPlayerHeight((float) height));
									}

									t.damage(0d, player);

									SpoutPlayer tPlayer;
									if ((tPlayer = SpoutManager.getPlayer(t)) != null) {
										HealthHud.updateHealth(tPlayer, (int) (HealthHud.getHealth(tPlayer) - damage));
									}
									return;
								}
							}
						} else if (!(e instanceof Player)) {
							if (e instanceof LivingEntity) {
								final LivingEntity le = (LivingEntity) e;
								if (loc.distanceSquared(e.getLocation()) <= 4 || loc.distanceSquared(le.getEyeLocation()) <= 4) {
									Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {

										@Override
										public void run() {
											le.damage(20d, player);
										}

									});
								}
							}
						}
					}
				}
			} else {
				try {
					loc.getWorld().playEffect(loc.clone().subtract(toAdd), Effect.STEP_SOUND, loc.getBlock().getTypeId());
				} catch (ConcurrentModificationException e) {
				}
				break;
			}
		}
	}

	@SuppressWarnings("deprecation")
	private boolean canGoThrough(Location loc) {
		Block b = loc.getBlock();
		if (b.getTypeId() == 44 || b.getTypeId() == 126) {
			if (loc.getY() > b.getY() + .5) {
				return true;
			}
		}
		return b.getType().isSolid();
	}

	public final void reload(final SpoutPlayer player) {
		if (WeaponManager.getReserve(player, this) > 0) {
			reloading.add(player.getName());
			final int reloadTimeInTicks = (int) ((20 * reloadTime));
			final ItemStack gun = player.getItemInHand();
			final int oldHeldSlot = player.getInventory().getHeldItemSlot();
			setDurability(gun, reloadTimeInTicks);
			new BukkitRunnable() {

				int x = 0;

				@Override
				public void run() {
					if (player.getInventory().getHeldItemSlot() == oldHeldSlot && player.isOnline()) {
						if (x <= reloadTimeInTicks) {
							setDurability(gun, reloadTimeInTicks - x);
						} else {
							setDurability(gun, 0);
							WeaponManager.reload(player, Gun.this);
							this.cancel();
							reloading.remove(player.getName());
							return;
						}
						x++;
					} else {
						setDurability(gun, 0);
						this.cancel();
						reloading.remove(player.getName());
					}
				}

			}.runTaskTimer(Main.getInstance(), 0, 1);
		}
	}

	private void setDurability(ItemStack is, int durability) {
		GenericCustomTool.setDurability(is, (short) durability);
	}

	public boolean isReloading(SpoutPlayer player) {
		return reloading.contains(player.getName());
	}

	public boolean canReload(SpoutPlayer player) {
		return !reloading.contains(player.getName()) && WeaponManager.getMagazine(player, this) != bulletsInRound && WeaponManager.getReserve(player, this) > 0;
	}

}