package com.daan.pws.grenades;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.entities.GrenadeEntity;
import com.daan.pws.weapon.Grenade;

public class Flashbang extends Grenade {

	public Flashbang() {
		super("Flashbang", "http://panisme.nl/csgo/textures/flashbang.png", 245f, 2, GrenadeExplosionTrackerType.TIME, 200);
		setMaxLivingTicks(40);
	}

	@Override
	public void onExplode(SpoutPlayer player, GrenadeEntity grenade) {
		for (Entity e : grenade.getGrenadeEntity().getNearbyEntities(30, 30, 30)) {
			if (e instanceof SpoutPlayer) {
				SpoutPlayer t = (SpoutPlayer) e;
				if (t.hasLineOfSight(grenade.getGrenadeEntity())) {
					t.sendMessage(ChatColor.DARK_RED + "You have been flashed!");
				}

				Vector v1 = t.getEyeLocation().getDirection();
				Vector v2 = grenade.getGrenadeEntity().getLocation().toVector().subtract(player.getLocation().toVector());

				/* Formule gebruikt zoals bij: http://www.wisfaq.nl/show3archive.asp?id=5978&j=2002 */
				double a = ((v1.getX() * v2.getX()) + (v1.getZ() * v2.getZ())) / (Math.sqrt(kwadrateer(v1.getX()) + kwadrateer(v1.getZ())) * Math.sqrt(kwadrateer(v2.getX()) + kwadrateer(v2.getZ())));
				double anglePitch = Math.toDegrees(Math.acos(a));

				boolean farAway = t.getLocation().distance(grenade.getGrenadeEntity().getLocation()) <= 12 ? false : true;
				boolean notInSight = anglePitch >= 90;

				new FlashbangEffect(t, !notInSight, farAway);
			}
		}
	}

	private double kwadrateer(double arg0) {
		arg0 = arg0 < 0 ? -arg0 : arg0;
		return Math.pow(arg0, 2);
	}

	private class FlashbangEffect {

		private GenericGradient whiteScreen;

		private int fullWhiteScreen;
		private int opacityChangeWithin;
		private int opacityChangePer;

		public FlashbangEffect(final SpoutPlayer player, boolean isOnScreen, boolean isFarAway) {
			whiteScreen = new GenericGradient(new Color(255, 255, 255, Integer.MAX_VALUE));
			whiteScreen.setAnchor(WidgetAnchor.SCALE).setWidth(player.getMainScreen().getWidth()).setHeight(player.getMainScreen().getHeight()).setPriority(RenderPriority.Lowest);

			player.getMainScreen().attachWidget(Main.getInstance(), whiteScreen);

			fullWhiteScreen = isOnScreen ? (isFarAway ? 40 : 80) : 20;
			opacityChangeWithin = isOnScreen ? 20 : 20;
			opacityChangePer = 100 / opacityChangeWithin;

			new BukkitRunnable() {

				@Override
				public void run() {
					if (player.isOnline()) {
						new BukkitRunnable() {

							int i = 0;
							int opacity = 100;

							@Override
							public void run() {
								if (i < opacityChangeWithin) {
									if (player.isOnline()) {
										opacity -= opacityChangePer;
										whiteScreen.setColor(new Color(255, 255, 255, opacity));
									} else {
										cancel();
									}
								} else {
									player.getMainScreen().removeWidget(whiteScreen);
									cancel();
								}
								i++;
							}

						}.runTaskTimer(Main.getInstance(), 0, 1);
					}
				}

			}.runTaskLater(Main.getInstance(), fullWhiteScreen);
		}
	}

}
