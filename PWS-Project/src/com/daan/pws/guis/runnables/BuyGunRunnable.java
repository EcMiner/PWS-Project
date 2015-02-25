package com.daan.pws.guis.runnables;

import com.daan.pws.guis.BuyGUI;
import com.daan.pws.guis.MainBuyGUI;
import com.daan.pws.match.CompetitiveGun;
import com.daan.pws.match.CompetitivePlayer;
import com.daan.pws.utilities.PlayerUtil;
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
			if (compGun.getGun().getWeaponType().isPrimary()) {
				player.getLoadout().setPrimary(compGun);
			} else {
				player.getLoadout().setSecondary(compGun);
			}
			if (player.getPlayer().getInventory().getHeldItemSlot() == gun.getWeaponType().getMainSlot()) {
				PlayerUtil.addPotionEffectNoParticles(player.getPlayer(), 3, 1029389, 100);
				com.daan.pws.match.hud.GunHud.updateGunHud(player.getPlayer(), compGun);
				player.getPlayer().setWalkSpeed((gun.getMovementSpeed() * 0.3F) / 250);
			}
			BuyGUI.openPage(player, MainBuyGUI.class);
		}
	}
}
