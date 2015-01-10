package com.daan.pws.hud;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.getspout.spoutapi.SpoutManager;
import org.getspout.spoutapi.gui.Color;
import org.getspout.spoutapi.gui.GenericGradient;
import org.getspout.spoutapi.gui.GenericLabel;
import org.getspout.spoutapi.gui.WidgetAnchor;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.daan.pws.Main;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponManager;

public class GunHud {

	private static Map<String, GunHud> gunAmmo = new HashMap<String, GunHud>();

	public static void updateBulletsOnScreen(SpoutPlayer player, Gun gun) {
		if (gunAmmo.containsKey(player.getName())) {
			gunAmmo.get(player.getName()).updateBullets(player, gun);
		} else {
			gunAmmo.put(player.getName(), new GunHud(player, gun));
		}
	}

	public static void removeBulletsOnScreen(SpoutPlayer player) {
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

	public GunHud(SpoutPlayer player, Gun gun) {
		bulletsLabel = new GenericLabel();
		bulletsLabel.setText(WeaponManager.getMagazine(player, gun) + " / " + WeaponManager.getReserve(player, gun));
		bulletsLabel.setScale(2f);
		bulletsLabel.setAnchor(WidgetAnchor.BOTTOM_RIGHT);
		bulletsLabel.shiftXPos(-(bulletsLabel.getWidth() * 2) + 10).shiftYPos(-30);
		bulletsLabel.setTextColor(new Color(154, 163, 116)).setShadow(false);

		background = new GenericGradient(new Color(0, 0, 0, 70));
		background.setAnchor(WidgetAnchor.BOTTOM_RIGHT).shiftXPos(-115).shiftYPos(-35).setWidth(115).setHeight(25);

		player.getMainScreen().attachWidgets(Main.getInstance(), bulletsLabel, background);
	}

	public void updateBullets(SpoutPlayer player, Gun gun) {
		bulletsLabel.setText(WeaponManager.getMagazine(player, gun) + " / " + WeaponManager.getReserve(player, gun));
	}

	public void remove(SpoutPlayer player) {
		player.getMainScreen().removeWidget(bulletsLabel).removeWidget(background);
	}

}
