package com.daan.pws.guis.runnables;

import com.daan.pws.match.CompetitiveGun;
import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.weapon.Gun;
import com.daan.pws.weapon.WeaponManager;

public class BuyGunRunnable implements Runnable {

	private Gun gun;
	private CompetitivePlayer player;

	public BuyGunRunnable(String gunName, CompetitivePlayer player) {
		this(WeaponManager.getGun(gunName), player);
	}

	public BuyGunRunnable(Gun gun, CompetitivePlayer player) {
		this.gun = gun;
		this.player = player;
	}

	@Override
	public void run() {
		if (gun.getPrice() <= player.getMoney()) {
			if (player.getPlayer().getInventory().getItem(gun.getWeaponType().getMainSlot()) != null) {
				if (CompetitiveGun.isCompetitiveGun(player.getPlayer().getInventory().getItem(gun.getWeaponType().getMainSlot()))) {
					CompetitiveGun cGun = CompetitiveGun.getCompetitiveGun(player.getPlayer().getInventory().getItem(gun.getWeaponType().getMainSlot()));
					cGun.drop(player);
				}
				player.getPlayer().getInventory().setItem(gun.getWeaponType().getMainSlot(), null);
			}
			CompetitiveGun compGun = new CompetitiveGun(gun, player.getMatch());
			player.getPlayer().getInventory().setItem(gun.getWeaponType().getMainSlot(), compGun.getGunItem());
			player.setMoney(player.getMoney() - gun.getPrice());
		}
	}
}
