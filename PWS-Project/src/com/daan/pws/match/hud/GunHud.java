package com.daan.pws.match.hud;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.RenderPriority;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.match.CompetitiveGun;

public class GunHud {

	private static Map<String, GunHud> gunAmmo = new HashMap<String, GunHud>();

	public static void updateGunHud(SpoutPlayer player, CompetitiveGun gun) {
		updateGunHud(player, gun.getBulletsInMagazine(), gun.getBulletsReserve());
	}

	public static void updateGunHud(SpoutPlayer player, int inMagazine, int reserve) {
		if (gunAmmo.containsKey(player.getName())) {
			gunAmmo.get(player.getName()).updateBullets(player, inMagazine, reserve);
		} else {
			gunAmmo.put(player.getName(), new GunHud(player, inMagazine, reserve));
		}
	}

	public static void removeGunHud(SpoutPlayer player) {
		if (gunAmmo.containsKey(player.getName())) {
			gunAmmo.get(player.getName()).remove(player);
			gunAmmo.remove(player.getName());
		}
	}

	public static void onDisable() {
		for (String name : gunAmmo.keySet()) {
			SpoutPlayer player;
			if ((player = SpoutManager.getPlayer(Bukkit.getPlayer(name))) != null) {
				gunAmmo.get(name).remove(player);
			}
		}
	}

	private GenericLabel bulletsLabel;
	private GenericGradient background;

	public GunHud(SpoutPlayer player, int inMagazine, int reserve) {
		bulletsLabel = new GenericLabel();
		bulletsLabel.setText(inMagazine + " / " + reserve);
		bulletsLabel.setScale(2f);
		bulletsLabel.setAnchor(WidgetAnchor.BOTTOM_RIGHT);
		bulletsLabel.shiftXPos(-(bulletsLabel.getWidth() * 2) + 10).shiftYPos(-30);
		bulletsLabel.setTextColor(new Color(154, 163, 116)).setShadow(false);

		background = new GenericGradient(new Color(0, 0, 0, 129));
		background.setAnchor(WidgetAnchor.BOTTOM_RIGHT).shiftXPos(-115).shiftYPos(-35).setWidth(115).setHeight(25).setPriority(RenderPriority.High);

		player.getMainScreen().attachWidgets(Main.getInstance(), bulletsLabel, background);
	}

	public void updateBullets(SpoutPlayer player, int inMagazine, int reserve) {
		bulletsLabel.setText(inMagazine + " / " + reserve);
	}

	public void remove(SpoutPlayer player) {
		player.getMainScreen().removeWidget(bulletsLabel).removeWidget(background);
	}

}
