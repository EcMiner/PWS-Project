package com.daan.pws.weapon;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.Validate;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.material.item.GenericCustomTool;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.events.PlayerShotEvent;
import com.daan.pws.match.Competitive;
import com.daan.pws.match.CompetitiveGun;
import com.daan.pws.match.hud.GunHud;
import com.daan.pws.particle.ParticleEffects;
import com.daan.pws.scheduler.ERunnable;
import com.daan.pws.utilities.PlayerUtil;
import com.daan.pws.weapon.DamagePattern.PlayerHeight;

public class Gun extends Weapon {

	private String shootUrl, iconUrl, zoomUrl;
	private GunItem gunItem;
	private int totalAmmo, bulletsInRound, roundsPerMinute, price, recoilControl;
	private boolean automatic;
	private WeaponType weaponType;
	private final double reloadTime, armourPenetration;

	public Gun(String name, String URL, int totalAmmo, int bulletsInRound, int roundsPerMinute, boolean automatic, int price, WeaponType weaponType, double reloadTime, String shootUrl, String iconUrl, float movementSpeed, double armourPenetration, int recoilControl) {
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
		this.recoilControl = recoilControl;

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

	private static final Random random = new Random();

	private List<String> reloading = new ArrayList<String>();

	private DamagePattern damagePattern;
	private RecoilPattern recoilPattern;

	private int[] zoomFactors;

	private int iconWidth;
	private int iconHeight;

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
		return this.zoomUrl != null && zoomFactors.length >= 1;
	}

	public void setZoomFactors(int[] factors) {
		this.zoomFactors = factors;
	}

	public int[] getZoomFactors() {
		return zoomFactors;
	}

	public int getRecoilControl() {
		return recoilControl;
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
		if (shootUrl != null && shootUrl.length() > 0)
			SpoutManager.getSoundManager().playGlobalCustomMusic(Main.getInstance(), shootUrl, false, player.getEyeLocation(), 50);
	}

	@SuppressWarnings("deprecation")
	public final void shootBulllet(final SpoutPlayer player, int bullet) {
		if (Competitive.isInMatch(player) && CompetitiveGun.isCompetitiveGun(player.getItemInHand())) {
			CompetitiveGun.getCompetitiveGun(player.getItemInHand()).shoot();
			com.daan.pws.match.hud.GunHud.updateGunHud(player, CompetitiveGun.getCompetitiveGun(player.getItemInHand()));
		}
		playShootSound(player);

		// Een aantal berekeningen voor de terugslag van een geweer, de kogels worden minder accuraat als je aan het lopen bent
		final Location loc = player.getEyeLocation();

		if (recoilPattern != null) {
			float[] floatArray = recoilPattern.onGunShoot(bullet);
			loc.setYaw(loc.getYaw() + floatArray[0]);
			loc.setPitch(loc.getPitch() - floatArray[1]);
		}

		Vector toAdd = loc.getDirection();

		double multiplyX = ((Math.random() * 20) / 26) * (26 - (!PlayerUtil.isZoomedIn(player) ? recoilControl : 20));
		double multiplyY = ((Math.random() * 20) / 26) * (26 - (!PlayerUtil.isZoomedIn(player) ? recoilControl : 20));
		double multiPlyZ = ((Math.random() * 20) / 26) * (26 - (!PlayerUtil.isZoomedIn(player) ? recoilControl : 20));

		Vector pVelocity = PlayerUtil.getVelocity(player);
		Vector v = new Vector(pVelocity.getX() * multiplyX, ((random.nextBoolean() ? -1 : 1) * (pVelocity.getY() * (player.getLocation().getBlock().getRelative(BlockFace.DOWN).getType().isSolid() ? 1 : 4))) * multiplyY, pVelocity.getZ() * multiPlyZ);
		if (!PlayerUtil.isZoomedIn(player)) {
			if (v.getX() == 0) {
				v.setX((random.nextBoolean() ? -1 : 1) * (multiplyX / 40));
			}
			if (v.getY() == 0) {
				v.setY(((random.nextBoolean() ? -1 : 1) * (multiplyY)) / 40);
			}
			if (v.getZ() == 0) {
				v.setZ((random.nextBoolean() ? -1 : 1) * (multiPlyZ / 40));
			}
		}
		v.multiply(0.8);

		toAdd.add(v);

		// Een for loop die 300 blokjes ver kijkt of de kogel iets raakt.
		for (int i = 0; i < 300; i++) {
			loc.add(toAdd);
			if (i % 3 == 0) {
				try {
					ParticleEffects.CRIT.sendToPlayer(player, loc, 0, 0, 0, 0.0f, 1);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// Hier kijken ik of de kogel niet tegen een vast blokje aankomt
			if (!loc.getBlock().getType().isSolid() || canGoThrough(loc)) {
				// Een for loop die door alle entities die in dezelfde Chunk (een stuk van 16x16x256 van de wereld) gaat.
				for (Entity e : loc.getChunk().getEntities()) {
					// Kijken of de entity wel een Player (een online speler) is, en dat het niet degene is
					// Die de kogel heeft afgeschoten
					if (e instanceof Player && e != player) {
						Player t = (Player) e;
						// Kijken of de kogel niet over het hoofd van de speler, of onder de voeten van de speler gaat.
						if (loc.getY() <= t.getLocation().getY() + 1.95 && loc.getY() >= t.getLocation().getY()) {
							// Checken of de entities wel echt dichtbij en niet 8 blokjes ver weg of iets dergelijks.
							if (loc.distanceSquared(t.getLocation()) <= 3 || loc.distanceSquared(t.getEyeLocation()) <= 3) {
								// Hier berekenen we de hoogte waar de kogel de speler heeft geraakt, dit wordt
								// gebruikt om de damage van de kogel te berekenen, want een schot in de benen van de speler
								// doet minder schade dan een schot in het bovenlichaam.
								double height = (t.getLocation().getY() + 1.85) - loc.getY();

								int damage = 1;
								if (this.getDamagePattern() != null) {
									// We berekenen de schade die de kogel zal aanrichten
									damage = this.getDamagePattern().onBulletHit(PlayerHeight.getPlayerHeight((float) height));
								}
								SpoutPlayer tPlayer;
								// Hier check ik of de speler wel echt aan het spelen is en in dezelfde match zit
								if ((tPlayer = SpoutManager.getPlayer(t)) != null) {
									if (Competitive.isInMatch(player) && Competitive.isInMatch(tPlayer)) {
										if (Competitive.getMatch(player).equals(Competitive.getMatch(tPlayer))) {
											// We maken gebruik van Craftbukkit's even systeem om ervoor te zorgen dat een andere
											// class de schade aanpast, bijvoorbeeld als de speler een kevlar vest aan heeft wordt
											// de schade minder.
											PlayerShotEvent event = new PlayerShotEvent(Competitive.getMatch(player).getCompetitivePlayer(player), Competitive.getMatch(tPlayer).getCompetitivePlayer(tPlayer), damage, PlayerHeight.getPlayerHeight((float) height), this);
											Main.getInstance().getServer().getPluginManager().callEvent(event);
											if (!event.isCancelled()) {
												// Hier richten we de schade (nadat alles is berekent) aan aan de speler.
												event.getDamaged().damageHealth(event.getShooter(), event.getDamage(), event.getHeight());
											}
										}
									}
								}
								return;
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
		if (CompetitiveGun.isCompetitiveGun(player.getItemInHand())) {
			final CompetitiveGun cGun = CompetitiveGun.getCompetitiveGun(player.getItemInHand());
			if (cGun.getBulletsReserve() > 0) {
				if (PlayerUtil.isZoomedIn(player)) {
					PlayerUtil.zoomOut(player);
				}
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
								cGun.reload();
								GunHud.updateGunHud(player, cGun);
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
	}

	private void setDurability(ItemStack is, int durability) {
		GenericCustomTool.setDurability(is, (short) durability);
	}

	public boolean isReloading(SpoutPlayer player) {
		return reloading.contains(player.getName());
	}

	public boolean canReload(SpoutPlayer player) {
		return !reloading.contains(player.getName()) && Competitive.isInMatch(player) && CompetitiveGun.isCompetitiveGun(player.getItemInHand()) && CompetitiveGun.getCompetitiveGun(player.getItemInHand()).getBulletsInMagazine() != bulletsInRound && CompetitiveGun.getCompetitiveGun(player.getItemInHand()).getBulletsReserve() > 0;
	}

}